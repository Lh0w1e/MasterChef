package version2.masterchef.com.masterchef.SplashScreen;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import version2.masterchef.com.masterchef.MainForm.MainForm;
import version2.masterchef.com.masterchef.R;

public class SplashScreen extends AppCompatActivity {

    private int progressStatus = 0;

    protected ProgressBar progressBar;
    private TextView txtTitle, txtVersion, txtEdition, txtLoading;

    private Animation animLeft, animRight, fadeIn, moveUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initViews();
        initAnimations();
        goAnimateViews();
        goSplash();
    }

    private void goSplash() {
        progressStatus = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 20;
                    try {
                        Thread.sleep(1000);
                        progressBar.setProgress(progressStatus);
                        if (progressStatus == 100) {
                            //Toast.makeText(SplashScreen.this, "Test", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainForm.class));
                            finish();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    private void goAnimateViews() {
        txtTitle.startAnimation(fadeIn);
        txtVersion.startAnimation(animRight);
        txtEdition.startAnimation(animLeft);
        txtLoading.startAnimation(moveUp);
    }

    private void initAnimations() {
        fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        animRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_right);
        animLeft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_left);
        moveUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_up);
    }

    private void initViews() {
        progressBar = (ProgressBar) findViewById(R.id.pb);
        txtTitle = (TextView) findViewById(R.id.tvMasterChef);
        txtVersion = (TextView) findViewById(R.id.tvVersion2);
        txtEdition = (TextView) findViewById(R.id.tvFDE);
        txtLoading = (TextView) findViewById(R.id.tv);
    }
}
