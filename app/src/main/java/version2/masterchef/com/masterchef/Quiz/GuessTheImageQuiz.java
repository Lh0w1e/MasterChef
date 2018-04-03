package version2.masterchef.com.masterchef.Quiz;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import version2.masterchef.com.masterchef.Constants.Constants;
import version2.masterchef.com.masterchef.R;

public class GuessTheImageQuiz extends AppCompatActivity {

    //for custom instruction dialog
    private View custom_view;
    private Button customBtnOk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_the_image_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        displayInstructionDialog();
    }

    private void displayInstructionDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        custom_view = layoutInflater.inflate(R.layout.guess_the_recipe_instruction, null);

        customBtnOk = (Button) custom_view.findViewById(R.id.custom_guess_the_recipe_btn_ok);

        final AlertDialog.Builder instruction = new AlertDialog.Builder(this);

        instruction.setView(custom_view);
        instruction.setCancelable(true);

        final AlertDialog dialog = instruction.create();
        dialog.show();

        customBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GuessTheImageQuiz.this, Constants.THIS_FUNCTION_IS_UNDER_CONSTRUCTION, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });



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
