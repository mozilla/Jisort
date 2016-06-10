package com.mozilla.hackathon.kiboko.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mozilla.hackathon.kiboko.R;

/**
 * Created by mwadime on 6/10/2016.
 */
public class ResultActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //get rating bar object
        RatingBar bar=(RatingBar)findViewById(R.id.ratingBar1);
        bar.setNumStars(5);
        bar.setStepSize(0.5f);
        //get text view
        TextView t=(TextView)findViewById(R.id.textResult);
        //get score
        Bundle b = getIntent().getExtras();
        int score= b.getInt("score");
        //display score
        bar.setRating(score);
        switch (score)
        {
            case 1:
            case 2: t.setText("Opps, try again bro, keep learning");
                break;
            case 3:
            case 4:t.setText("Nice Try");
                break;
            case 5:t.setText("Who are you? A student in JP???");
                break;
        }
    }
}
