package com.mozilla.hackathon.kiboko.activities;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.mozilla.hackathon.kiboko.R;
import com.mozilla.hackathon.kiboko.models.Question;
import com.mozilla.hackathon.kiboko.provider.DsoContract;
import com.mozilla.hackathon.kiboko.utilities.Utils;
import com.mozilla.hackathon.kiboko.widgets.CheckableLinearLayout;
import com.mozilla.hackathon.kiboko.widgets.DSORadioGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.mozilla.hackathon.kiboko.utilities.LogUtils.makeLogTag;

/**
 * Created by mwadime on 6/10/2016.
 */

public class IconQuizActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final List<Integer> possibleIcons;
    static {
        List<Integer> icons = new ArrayList<Integer>();

        icons.add(R.drawable.ic_network_wifi_white_48dp);
        icons.add(R.drawable.ic_phone_white_48dp);
        icons.add(R.drawable.ic_get_app_white_48dp);
        icons.add(R.drawable.ic_account_box_white_48dp);
        icons.add(R.drawable.ic_perm_contact_calendar_white_48dp);
        icons.add(R.drawable.ic_alarm_white_48dp);
        icons.add(R.drawable.ic_settings_applications_white_48dp);
        icons.add(R.drawable.ic_search_white_48dp);
        icons.add(R.drawable.ic_location_on_white_48dp);
        icons.add(R.drawable.ic_power_settings_new_white_48dp);
        icons.add(R.drawable.ic_battery_full_white_48dp);
        icons.add(R.drawable.ic_bluetooth_connected_white_48dp);
        icons.add(R.drawable.ic_email_white_48dp);
        icons.add(R.drawable.ic_android_white_48dp);
        icons.add(R.drawable.ic_sd_storage_white_48dp);
        icons.add(R.drawable.ic_delete_white_48dp);
        icons.add(R.drawable.whatsapp_white);
        icons.add(R.drawable.googleplay_white);
        icons.add(R.drawable.ic_battery_20_white_48dp);
        icons.add(R.drawable.ic_airplanemode_active_white_48dp);
        icons.add(R.drawable.ic_contacts_white_48dp);
        icons.add(R.drawable.twitter_white);
        icons.add(R.drawable.ic_android_white_48dp);
        icons.add(R.drawable.ic_get_app_white_48dp);
        icons.add(R.drawable.facebook_white);
        icons.add(R.drawable.calendar_white);
        icons.add(R.drawable.opera_white);

        possibleIcons = Collections.unmodifiableList(icons);
    }

    private static final String TAG = makeLogTag(IconQuizActivity.class);
    private static final int LOADER_ID = 0x02;
    List<Question> quizList = new ArrayList<Question>();
    int score=0;
    int question_id=0;
    Question currentQuestion;
    private String mQuiz;
    TextView quizStepView, quizStepScore;
    CheckableLinearLayout[] choiceOptions;
    ImageView[] imageOptions;
    DSORadioGroup quizGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon_quiz);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        choiceOptions = new CheckableLinearLayout[4];
        imageOptions = new ImageView[4];

        choiceOptions[0] = (CheckableLinearLayout)findViewById(R.id.optionA);
        choiceOptions[1] = (CheckableLinearLayout)findViewById(R.id.optionB);
        choiceOptions[2] = (CheckableLinearLayout)findViewById(R.id.optionC);
        choiceOptions[3] = (CheckableLinearLayout)findViewById(R.id.optionD);

        imageOptions[0]  = (ImageView)findViewById(R.id.answera);
        imageOptions[1]  = (ImageView)findViewById(R.id.answerb);
        imageOptions[2]  = (ImageView)findViewById(R.id.answerc);
        imageOptions[3]  = (ImageView)findViewById(R.id.answerd);

        quizStepView  = (TextView) findViewById(R.id.quizStepView);
        quizStepScore = (TextView) findViewById(R.id.quizStepScore);

        quizGroup = (DSORadioGroup)findViewById(R.id.quizGroup);

        quizGroup.setOnCheckedChangeListener(new DSORadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(DSORadioGroup group, @IdRes int checkedId) {
                if(checkedId != -1){
                    CheckableLinearLayout checkedView = (CheckableLinearLayout) findViewById(checkedId);
                    checkAnswer(checkedView);
                }
            }
        });

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                showExitConfirmDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) { // this is override method
//        if(keyCode == KeyEvent.KEYCODE_BACK){
//            showExitConfirmDialog(); // call the function below
//        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Show exit confirm dialog
     */
    public void showExitConfirmDialog(){ // just show an dialog
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Exit Quiz?"); // set title
        dialog.setMessage("Are you sure you want to exit the Quiz page?"); // set message
        dialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish(); // when click OK button, finish current activity!
                        onBackPressed();
                    }
                });
        dialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        dialog.create().show();
    }

    /**
     * Check whether icon answer is correct {@link CheckableLinearLayout}
     * @param checkedView selected view
     * @return true/false
     */
    public boolean checkAnswer(CheckableLinearLayout checkedView){

        boolean correct = false;

        if(currentQuestion.getANSWER().equals(checkedView.getTag()))
        {
            score++;
            quizStepScore.setText(getString(R.string.quiz_template_score, score));
            correct = true;
        }else {
            correct = false;
        }

        if(question_id < quizList.size()){
            currentQuestion = quizList.get(question_id);
            setQuestionView(currentQuestion);
        }else{
            Intent intent = new Intent(IconQuizActivity.this, ResultActivity.class);
            Bundle scoreBundle = new Bundle();
            scoreBundle.putInt("score", score);
            intent.putExtras(scoreBundle); // Send user score to your next Intent
            startActivity(intent);
            finish();
        }
        quizGroup.clearCheck();
        return correct;
    }

    private int getChoice(int answerResId) {
        int choiceResId = -1;

        while (true) {
            choiceResId = possibleIcons.get(new Double(Math.floor(Math.random() * possibleIcons.size())).intValue());
            if (choiceResId != answerResId) {
                return choiceResId;
            }
        }
    }

    /**
     * Populate quiz question view {@link Question}
     */
    private void setQuestionView(Question question)
    {
        quizStepView.setText(question.getQUESTION());
        choiceOptions[0].setTag(question.getOPTIONA());

        int answerIndex = new Double(Math.floor(Math.random()*4)).intValue();
        int answerResId = Utils.getResId(this, question.getANSWER());

        choiceOptions[answerIndex].setTag(question.getANSWER());
        imageOptions[answerIndex].setImageDrawable(ContextCompat.getDrawable(this, answerResId));

        for (int i = 0; i < 4; ++i) {
            if (i != answerIndex) {
                int choiceResId = getChoice(answerResId);
                choiceOptions[i].setTag("");
                imageOptions[i].setImageDrawable(ContextCompat.getDrawable(this, choiceResId));
            }
        }

        question_id++;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Define the columns to retrieve
        String[] projectionFields = new String[] {
                DsoContract.Quizes.KEY_ID,
                DsoContract.Quizes.KEY_QUESTION,
                DsoContract.Quizes.KEY_ANSWER,
                DsoContract.Quizes.KEY_OPTIONA,
                DsoContract.Quizes.KEY_OPTIONB,
                DsoContract.Quizes.KEY_OPTIONC,
                DsoContract.Quizes.KEY_OPTIOND};
        CursorLoader cursorLoader = new CursorLoader(IconQuizActivity.this,
                DsoContract.Quizes.CONTENT_URI, // URI
                projectionFields, // projection fields
                null, // the selection criteria
                null, // the selection args
                null // the sort order
        );
        // Return the loader for use
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    Question question = loadQuizData(cursor);
                    quizList.add(question);
                } while (cursor.moveToNext());
            }
        }
        // Check whether we have any questions and setup the view.
        if(quizList.size() > 0){
            currentQuestion = quizList.get(question_id);
            setQuestionView(currentQuestion);
        }
    }

    /**
     * Populate quiz {@link Question} with data from {@link Cursor}.
     * @param cursor
     * @return
     */
    private Question loadQuizData(Cursor cursor) {
        // Get Question ID
        int questionIndex = cursor.getColumnIndex(DsoContract.Quizes.KEY_QUESTION);
        int answerIndex = cursor.getColumnIndex(DsoContract.Quizes.KEY_ANSWER);
        int optionaIndex = cursor.getColumnIndex(DsoContract.Quizes.KEY_OPTIONA);
        int optionbIndex = cursor.getColumnIndex(DsoContract.Quizes.KEY_OPTIONB);
        int optioncIndex = cursor.getColumnIndex(DsoContract.Quizes.KEY_OPTIONC);
        int optiondIndex = cursor.getColumnIndex(DsoContract.Quizes.KEY_OPTIOND);
        Question question = new Question(cursor.getString(questionIndex), cursor.getString(answerIndex), cursor.getString(optionaIndex), cursor.getString(optionbIndex), cursor.getString(optioncIndex), cursor.getString(optiondIndex));
        return question;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
