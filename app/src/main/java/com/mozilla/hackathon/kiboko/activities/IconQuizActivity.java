package com.mozilla.hackathon.kiboko.activities;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mozilla.hackathon.kiboko.R;
import com.mozilla.hackathon.kiboko.models.Question;
import com.mozilla.hackathon.kiboko.provider.DsoContract;
import com.mozilla.hackathon.kiboko.widgets.CheckableLinearLayout;

import java.util.ArrayList;
import java.util.List;

import static com.mozilla.hackathon.kiboko.utilities.LogUtils.LOGI;
import static com.mozilla.hackathon.kiboko.utilities.LogUtils.makeLogTag;

/**
 * Created by mwadime on 6/10/2016.
 */
public class IconQuizActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = makeLogTag(IconQuizActivity.class);
    private static final int LOADER_ID = 0x02;
    List<Question> quizList = new ArrayList<Question>();
    int score=0;
    int question_id=0;
    Question currentQuestion;
    private String mQuiz;
    TextView quizStepView;
    CheckableLinearLayout choiceOptionA, choiceOptionB, choiceOptionC, choiceOptionD;
    ImageView imageOptionA,imageOptionB,imageOptionC,imageOptionD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon_quiz);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        choiceOptionA = (CheckableLinearLayout)findViewById(R.id.optionA);
        choiceOptionB = (CheckableLinearLayout)findViewById(R.id.optionB);
        choiceOptionC = (CheckableLinearLayout)findViewById(R.id.optionC);
        choiceOptionD = (CheckableLinearLayout)findViewById(R.id.optionD);

        //bootstrap content
        choiceOptionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                checkAnswer(imageOptionA);
            }
        });

        choiceOptionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checkAnswer(imageOptionB);
            }
        });

        choiceOptionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checkAnswer(imageOptionC);
            }
        });

        choiceOptionD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // checkAnswer(imageOptionD);
            }
        });

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                showExitConfirmDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) { // this is override method
        if(keyCode == KeyEvent.KEYCODE_BACK){
            showExitConfirmDialog(); // call the function below
        }
        return super.onKeyDown(keyCode, event);
    }

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

    public boolean checkAnswer(ImageView imageView){

        boolean correct = false;

        if(currentQuestion.getANSWER().equals(String.valueOf(imageView.getId())))
        {
            score++;
            LOGI("score", "Your score" + score);
            correct=true;
        }
        if(question_id<5){
            currentQuestion = quizList.get(question_id);
            setQuestionView();
        }else{
//            Intent intent = new Intent(IconQuizActivity.this, ResultActivity.class);
//            Bundle b = new Bundle();
//            b.putInt("score", score); //Your score
//            intent.putExtras(b); //Put your score to your next Intent
//            startActivity(intent);
//            finish();
        }

        return correct;
    }

    private void setQuestionView()
    {
        imageOptionA = (ImageView)findViewById(R.id.answer1);
        imageOptionA.setImageDrawable(ContextCompat.getDrawable(IconQuizActivity.this,getDrawableId(quizList.get(question_id).getOPTIONA())));
        imageOptionB = (ImageView)findViewById(R.id.answer2);
        imageOptionB.setImageDrawable(ContextCompat.getDrawable(IconQuizActivity.this,getDrawableId(quizList.get(question_id).getOPTIONB())));
        imageOptionC = (ImageView)findViewById(R.id.answer3);
        imageOptionC.setImageDrawable(ContextCompat.getDrawable(IconQuizActivity.this,getDrawableId(quizList.get(question_id).getOPTIONC())));
        imageOptionD = (ImageView)findViewById(R.id.answer4);
        imageOptionD.setImageDrawable(ContextCompat.getDrawable(IconQuizActivity.this,getDrawableId(quizList.get(question_id).getOPTIOND())));


        question_id++;
    }

    private int getDrawableId(String name){
        int id = 0;
            switch(name){
                case "ic_bluetooth_connected_black_48dp":
                    id = R.drawable.ic_bluetooth_black_48dp;
                break;
                case "ic_phone_black_48dp":
                    id = R.drawable.ic_phone_black_48dp;
                break;
                case "ic_battery_20_black_48dp":
                    id = R.drawable.ic_battery_20_black_48dp;
                break;
                case "ic_event_black_48dp":
                    id = R.drawable.ic_event_black_48dp;
                break;
            }
        return id;
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

        // Read all data for contactId
        String selection = DsoContract.Quizes.KEY_ID + " = ?";
        String[] selectionArgs = new String[]{mQuiz};

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
            cursor.moveToFirst();
            int idIndex =
                    cursor.getColumnIndex(DsoContract.Tutorials._ID);
            int stepsIndex =
                    cursor.getColumnIndex(DsoContract.Tutorials.TUTORIAL_STEPS);
            cursor.moveToNext();
        }

        if (cursor.moveToFirst()) {
            do {
                Question question = loadQuizData(cursor);
                quizList.add(question);
            } while (cursor.moveToNext());
        }
        cursor.close();

        if(quizList.size() > 0){

            currentQuestion = quizList.get(question_id);
            setQuestionView();
        }
    }

    private Question loadQuizData(Cursor cursor) {
        // Get Question ID
        int idIndex = cursor.getColumnIndex(DsoContract.Quizes.KEY_ID);
        String contactId = cursor.getString(idIndex);
        // Get Contact Name
        int questionIndex = cursor.getColumnIndex(DsoContract.Quizes.KEY_QUESTION);
        int answerIndex = cursor.getColumnIndex(DsoContract.Quizes.KEY_ANSWER);
        int optionaIndex = cursor.getColumnIndex(DsoContract.Quizes.KEY_OPTIONA);
        int optionbIndex = cursor.getColumnIndex(DsoContract.Quizes.KEY_OPTIONB);
        int optioncIndex = cursor.getColumnIndex(DsoContract.Quizes.KEY_OPTIONC);
        int optiondIndex = cursor.getColumnIndex(DsoContract.Quizes.KEY_OPTIOND);
        Question question = new Question(cursor.getString(questionIndex), cursor.getString(answerIndex), cursor.getString(optionaIndex), cursor.getString(optionbIndex), cursor.getString(optioncIndex), cursor.getString(optiondIndex));
//        fetchContactNumbers(c, contact);
//        fetchContactEmails(c, contact);
        return question;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
