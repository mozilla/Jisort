package com.mozilla.hackathon.kiboko.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.webkit.WebView;

/**
 * Created by Brian Mwadime on 24/06/2016.
 */

/**
 * a WebView is able to do what a browser does.
 * And since the browser of Android devices using Android 2.2 + supports the animation of GIFs (at least on most devices),
 * we will use just that
 *
 * Sample Usage:
 * GifWebView view = new GifWebView(this, "file:///android_asset/piggy.gif");
 * setContentView(view);
 */
public class GifWebView extends WebView {

    public GifWebView(Context context, String path) {
        super(context);
        loadUrl(path);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);
        super.onDraw(canvas);
//        this.invalidate();
    }
}
