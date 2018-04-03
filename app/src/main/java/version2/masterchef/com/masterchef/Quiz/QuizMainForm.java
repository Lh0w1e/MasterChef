package version2.masterchef.com.masterchef.Quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import version2.masterchef.com.masterchef.R;

public class QuizMainForm extends AppCompatActivity {

    private CardView cardMultiChoice, cardIdentification, cardGuessImage;

    private Animation goScaleViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_main_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
        initAnimation();
        initCardViewOnClickEvent();

    }

    private void initCardViewOnClickEvent() {
        cardMultiChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(goScaleViews);
                startActivity(new Intent(getApplicationContext(), MultipleChoiceQuiz.class));

            }
        });

        cardIdentification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(goScaleViews);
                startActivity(new Intent(getApplicationContext(), IdentificationQuiz.class));
            }
        });

        cardGuessImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(goScaleViews);
                startActivity(new Intent(getApplicationContext(), GuessTheImageQuiz.class));
            }
        });
    }

    private void initAnimation() {
        goScaleViews = AnimationUtils.loadAnimation(this, R.anim.scale);
    }

    private void initViews() {
        cardMultiChoice = (CardView) findViewById(R.id.card_multiple_choice);
        cardIdentification = (CardView) findViewById(R.id.card_identification);
        cardGuessImage = (CardView) findViewById(R.id.card_guess_image);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
