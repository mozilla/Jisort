package com.mozilla.hackathon.kiboko.services;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mozilla.hackathon.kiboko.R;
import com.mozilla.hackathon.kiboko.activities.DashboardActivity;
import com.mozilla.hackathon.kiboko.utilities.Utils;

import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

public class ChatHeadService extends Service {

    private static final int TRAY_HIDDEN_FRACTION 			= 6; 	// Controls fraction of the tray hidden when open
    private static final int TRAY_MOVEMENT_REGION_FRACTION 	= 6;	// Controls fraction of y-axis on screen within which the tray stays.
    private static final int TRAY_CROP_FRACTION 			= 12;	// Controls fraction of the tray chipped at the right end.
    private static final int ANIMATION_FRAME_RATE 			= 30;	// Animation frame rate per second.
    private static final int TRAY_DIM_X_DP 					= 48;	// Width of the tray in dps
    private static final int TRAY_DIM_Y_DP 					= 48; 	// Height of the tray in dps
    private static final int BUTTONS_DIM_Y_DP 				= 27;	// Height of the buttons in dps

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams 	mRootLayoutParams;		// Parameters of the root layout
    private RelativeLayout              mRootLayout;			// Root layout
    private RelativeLayout 				mContentContainerLayout;// Contains everything other than buttons and song info
    private RelativeLayout 				mLogoLayout;			// Contains icons
    // Variables that control drag
    private int mStartDragX;
    private boolean mIsTrayOpen;
    //private int mStartDragY; // Unused as yet
    private int mPrevDragX;
    private int mPrevDragY;

    private long pressStartTime;
    private float pressedX;
    private float pressedY;
    private boolean stayedWithinClickDistance;
    /**
     * Max allowed duration for a "click", in milliseconds.
     */
    private static final int MAX_CLICK_DURATION = 100;

    /**
     * Max allowed distance to move during a "click", in DP.
     */
    private static final int MAX_CLICK_DISTANCE = 15;

    // Controls for animations
    private Timer 					mTrayAnimationTimer;
    private TrayAnimationTimerTask 	mTrayTimerTask;
    private Handler mAnimationHandler = new Handler();

    @Override public IBinder onBind(Intent intent) {
        // Not used
        return null;
    }

    @Override public void onCreate() {
        super.onCreate();

        // Get references to all the views and add them to root view as needed.
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        mRootLayout = (RelativeLayout) LayoutInflater.from(this).
                inflate(R.layout.service_floating_button, null);
        mContentContainerLayout = (RelativeLayout) mRootLayout.findViewById(R.id.content_container);
        mLogoLayout = (RelativeLayout) mRootLayout.findViewById(R.id.logo_layout);
        mLogoLayout.setOnTouchListener(new TrayTouchListener());
        mRootLayoutParams = new WindowManager.LayoutParams(
                Utils.dpToPixels(TRAY_DIM_X_DP, getResources()),
                Utils.dpToPixels(TRAY_DIM_Y_DP, getResources()),
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                PixelFormat.TRANSLUCENT);

        mRootLayoutParams.gravity = Gravity.TOP | Gravity.LEFT;
        mWindowManager.addView(mRootLayout, mRootLayoutParams);

        // Post these actions at the end of looper message queue so that the layout is
        // fully inflated once these functions execute
        mRootLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Reusable variables
                InputStream is;
                Bitmap bmap;

                RelativeLayout.LayoutParams params;
                // Setup background icon
                is = getResources().openRawResource(R.drawable.android_head);
                int containerNewWidth = (TRAY_CROP_FRACTION)*mLogoLayout.getHeight()/TRAY_CROP_FRACTION;
                bmap = Utils.loadMaskedBitmap(is, mLogoLayout.getHeight(), containerNewWidth);
                params = (RelativeLayout.LayoutParams) mLogoLayout.getLayoutParams();
                params.width = (bmap.getWidth() * mLogoLayout.getHeight()) / bmap.getHeight();
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,0);
                mLogoLayout.setLayoutParams(params);
                mLogoLayout.requestLayout();
                mLogoLayout.setBackgroundDrawable(new BitmapDrawable(getResources(), bmap));
                // Setup the root layout
                mRootLayoutParams.x = 0;
                mRootLayoutParams.y = 0;
                mWindowManager.updateViewLayout(mRootLayout, mRootLayoutParams);

                // Make everything visible
                mRootLayout.setVisibility(View.VISIBLE);

                // Animate the Tray
                mTrayTimerTask = new TrayAnimationTimerTask();
                mTrayAnimationTimer = new Timer();
                mTrayAnimationTimer.schedule(mTrayTimerTask, 0, ANIMATION_FRAME_RATE);
            }
        }, ANIMATION_FRAME_RATE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRootLayout != null)
            mWindowManager.removeView(mRootLayout);
    }

    // Drags the tray as per touch info
    private void dragTray(int action, int x, int y){
        switch (action){
            case MotionEvent.ACTION_DOWN:
                // Cancel any currently running animations/automatic tray movements.
                if (mTrayTimerTask!=null){
                    mTrayTimerTask.cancel();
                    mTrayAnimationTimer.cancel();
                }

                // Store the start points
                mStartDragX = x;
                //mStartDragY = y;
                mPrevDragX = x;
                mPrevDragY = y;
                break;

            case MotionEvent.ACTION_MOVE:
                if (stayedWithinClickDistance && distance(pressedX, pressedY, x, y) > MAX_CLICK_DISTANCE) {
                    stayedWithinClickDistance = false;
                    // Calculate position of the whole tray according to the drag, and update layout.
                    float deltaX = x - mPrevDragX;
                    float deltaY = y - mPrevDragY;
                    mRootLayoutParams.x += deltaX;
                    mRootLayoutParams.y += deltaY;
                    mPrevDragX = x;
                    mPrevDragY = y;

                    mWindowManager.updateViewLayout(mRootLayout, mRootLayoutParams);
                }else{
                    stayedWithinClickDistance = true;
                }

                break;

            case MotionEvent.ACTION_UP:
                long pressDuration = System.currentTimeMillis() - pressStartTime;
                if (pressDuration < MAX_CLICK_DURATION) { //&& stayedWithinClickDistance
                    openAppClicked();
                }
                break;
            case MotionEvent.ACTION_CANCEL:

                // When the tray is released, bring it back to "open" or "closed" state.
                if ((mIsTrayOpen && (x-mStartDragX)<=0) ||
                        (!mIsTrayOpen && (x-mStartDragX)>=0))
                    mIsTrayOpen = !mIsTrayOpen;

                mTrayTimerTask = new TrayAnimationTimerTask();
                mTrayAnimationTimer = new Timer();
                mTrayAnimationTimer.schedule(mTrayTimerTask, 0, ANIMATION_FRAME_RATE);
                break;
        }
    }

    // Listens to the touch events on the tray.
    private class TrayTouchListener implements OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final int action = event.getActionMasked();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    pressedX = event.getX();
                    pressedY = event.getY();
                    pressStartTime = System.currentTimeMillis();
                    stayedWithinClickDistance = true;
                case MotionEvent.ACTION_MOVE:
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    dragTray(action, (int)event.getRawX(), (int)event.getRawY());
                    break;
                default:
                    return false;
            }
            return true;

        }
    }

    // Timer for animation/automatic movement of the tray.
    private class TrayAnimationTimerTask extends TimerTask {

        // Ultimate destination coordinates toward which the tray will move
        int mDestX;
        int mDestY;

        public TrayAnimationTimerTask(){

            // Setup destination coordinates based on the tray state.
            super();

            mDestX = - mRootLayout.getWidth()/TRAY_HIDDEN_FRACTION;
//
//            // Keep upper edge of the widget within the upper limit of screen
            int screenHeight = getResources().getDisplayMetrics().heightPixels;
            mDestY = Math.max(
                    screenHeight/TRAY_MOVEMENT_REGION_FRACTION,
                    mRootLayoutParams.y);

            // Keep lower edge of the widget within the lower limit of screen
            mDestY = Math.min(
                    ((TRAY_MOVEMENT_REGION_FRACTION-1)*screenHeight)/TRAY_MOVEMENT_REGION_FRACTION - mRootLayout.getWidth(),
                    mDestY);
        }

        // This function is called after every frame.
        @Override
        public void run() {

            // handler is used to run the function on main UI thread in order to
            // access the layouts and UI elements.
            mAnimationHandler.post(new Runnable() {
                @Override
                public void run() {

                    // Update coordinates of the tray
                    mRootLayoutParams.x = (2*(mRootLayoutParams.x-mDestX))/3 + mDestX;
                    mRootLayoutParams.y = (2*(mRootLayoutParams.y-mDestY))/3 + mDestY;
                    mWindowManager.updateViewLayout(mRootLayout, mRootLayoutParams);

                    // Cancel animation when the destination is reached
                    if (Math.abs(mRootLayoutParams.x-mDestX)<2 && Math.abs(mRootLayoutParams.y-mDestY)<2){
                        TrayAnimationTimerTask.this.cancel();
                        mTrayAnimationTimer.cancel();
                    }
                }
            });
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

//        if (intent.getBooleanExtra("stop_jisort_service", false)){
//            // If it's a call from the notification, stop the service.
//            stopSelf();
//        }else{
//            // Make the service run in foreground so that the system does not shut it down.
//            Intent notificationIntent = new Intent(this, ChatHeadService.class);
//            notificationIntent.putExtra("stop_jisort_service", true);
//            PendingIntent pendingIntent = PendingIntent.getService(this, 0, notificationIntent, 0);
//            Notification notification = new Notification.Builder(getApplicationContext())
//                    .setContentTitle("Jisort Tray")
//                    .setContentText("Tap to close the widget.")
//                    .setSmallIcon(R.drawable.ic_launcher)
//                    .setContentIntent(pendingIntent)
//                    .build();
//            startForeground(86, notification);
//        }
//        return START_STICKY;
        return START_STICKY;
    }

    /**
     * Open application
     */
    private void openAppClicked() {
        Intent dashboardIntent = new Intent(this, DashboardActivity.class);
        dashboardIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(dashboardIntent);
    }

    /**
     * Gets the distance between to points
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return distance as a float value
     */
    private float distance(float x1, float y1, float x2, float y2) {
        float dx = x1 - x2;
        float dy = y1 - y2;
        float distanceInPx = (float) Math.sqrt(dx * dx + dy * dy);
        return pxToDp(distanceInPx);
    }

    /**
     * Convert pixels to device pixels
     * @param px
     * @return device pixels
     */
    private float pxToDp(float px) {
        return px / getResources().getDisplayMetrics().density;
    }
}