package com.mozilla.hackathon.kiboko.activities;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mozilla.hackathon.kiboko.R;
import com.mozilla.hackathon.kiboko.models.Question;
import com.mozilla.hackathon.kiboko.widgets.CheckableLinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mwadime on 6/10/2016.
 */
public class IconQuizActivity extends AppCompatActivity {
    List<Question> quesList;
    int score=0;
    int qid=0;
    Question currentQ;
    TextView quizStepView;
    ImageView imageView1,imageView2,imageView3,imageView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon_quiz);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
//        DbHelper db=new DbHelper(this);
//        quesList=db.getAllQuestions();

        quesList = new ArrayList();

        Question  question  =new Question("What icon indicates battery low?", "ic_bluetooth_connected_black_48dp", "ic_phone_black_48dp", "ic_event_black_48dp", "ic_battery_20_black_48dp", String.valueOf(R.id.answer4));
        quesList.add(question);
        currentQ=quesList.get(qid);
        quizStepView=(TextView)findViewById(R.id.quizStepView);
        setQuestionView();
        //bootstrap content
        CheckableLinearLayout choice1 = (CheckableLinearLayout)findViewById(R.id.choice1);
        choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //imageView1.setImageDrawable();
                checkAnswer(imageView1);
            }
        });

        CheckableLinearLayout choice2 = (CheckableLinearLayout)findViewById(R.id.choice2);
        choice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checkAnswer(imageView2);
            }
        });
        CheckableLinearLayout choice3 = (CheckableLinearLayout)findViewById(R.id.choice3);
        choice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checkAnswer(imageView3);
            }
        });
        CheckableLinearLayout choice4 = (CheckableLinearLayout)findViewById(R.id.choice4);
        choice4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // checkAnswer(imageView4);
            }
        });
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

        if(currentQ.getANSWER().equals(String.valueOf(imageView.getId())))
        {
            score++;
            Log.d("score", "Your score"+score);
            correct=true;
        }
        if(qid<5){
            currentQ=quesList.get(qid);
            setQuestionView();
        }else{
            Intent intent = new Intent(IconQuizActivity.this, ResultActivity.class);
            Bundle b = new Bundle();
            b.putInt("score", score); //Your score
            intent.putExtras(b); //Put your score to your next Intent
            startActivity(intent);
            finish();
        }

        return correct;
    }

    private void setQuestionView()
    {
        imageView1 = (ImageView)findViewById(R.id.answer1);
        imageView1.setImageDrawable(ContextCompat.getDrawable(IconQuizActivity.this,getDrawableId(quesList.get(qid).getOPTIONA())));
        imageView2 = (ImageView)findViewById(R.id.answer2);
        imageView2.setImageDrawable(ContextCompat.getDrawable(IconQuizActivity.this,getDrawableId(quesList.get(qid).getOPTIONB())));
        imageView3 = (ImageView)findViewById(R.id.answer3);
        imageView3.setImageDrawable(ContextCompat.getDrawable(IconQuizActivity.this,getDrawableId(quesList.get(qid).getOPTIONC())));
        imageView4 = (ImageView)findViewById(R.id.answer4);
        imageView4.setImageDrawable(ContextCompat.getDrawable(IconQuizActivity.this,getDrawableId(quesList.get(qid).getOPTIOND())));


        qid++;
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
}
