package com.mozilla.hackathon.kiboko.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.mozilla.hackathon.kiboko.R;

/**
 * Created by mwadime on 6/10/2016.
 */
public class ResultActivity extends AppCompatActivity {
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
        //display score
//        switch (score)
//        {
//            case 1:
//            case 2: t.setText("Opps, try again bro, keep learning");
//                break;
//            case 3:
//            case 4:t.setText("Nice Try");
//                break;
//            case 5:t.setText("Who are you? A student in JP???");
//                break;
//        }
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
}
