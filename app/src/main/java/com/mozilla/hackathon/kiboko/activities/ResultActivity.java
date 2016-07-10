package com.mozilla.hackathon.kiboko.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mozilla.hackathon.kiboko.R;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by mwadime on 6/10/2016.
 */
public class ResultActivity extends AppCompatActivity {
    private static int[] imageResources = new int[]{
        R.drawable.bmo,
        R.drawable.carlton,
        R.drawable.beach,
        R.drawable.ok,
        R.drawable.rainbow,
        R.drawable.party
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        TextView txtPoints = (TextView) findViewById(R.id.quizResult);
        //get score
        Bundle b = getIntent().getExtras();
        int score= b.getInt("score");
        txtPoints.setText(getString(R.string.quiz_template_points, score));

        GifImageView gifImageView = (GifImageView) findViewById((R.id.result_image));
        int randomIndex = new Double(Math.random() * imageResources.length).intValue();
        gifImageView.setImageResource(imageResources[randomIndex]);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void playAgain(View view){
        Intent intent = new Intent(ResultActivity.this, IconQuizActivity.class);
        startActivity(intent);
        finish();
    }
}
