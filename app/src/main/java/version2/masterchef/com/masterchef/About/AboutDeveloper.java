package version2.masterchef.com.masterchef.About;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import version2.masterchef.com.masterchef.Constants.Constants;
import version2.masterchef.com.masterchef.R;

public class AboutDeveloper extends AppCompatActivity {

    private TextView txtGmail, txtFacebook, txtInstagram, txtLinkedIn;
    private Spanned linkGmail, linkFacebook, linkInstagram, linkLinkedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_developer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
        initLinks();
        initSetMovementMethod();
        displayLinks();

    }

    private void displayLinks() {
        txtGmail.setText(linkGmail);
        txtFacebook.setText(linkFacebook);
        txtInstagram.setText(linkInstagram);
        txtLinkedIn.setText(linkLinkedIn);
    }

    private void initSetMovementMethod() {
        txtGmail.setMovementMethod(LinkMovementMethod.getInstance());
        txtFacebook.setMovementMethod(LinkMovementMethod.getInstance());
        txtInstagram.setMovementMethod(LinkMovementMethod.getInstance());
        txtLinkedIn.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void initLinks() {
        linkGmail = Html.fromHtml("Gmail: <a href = '"+Constants.GOOGLE_PLUS+Constants.DEV_GOOGLE_PLUS + "'>" + Constants.DISPLAY_DEV_EMAIL);
        linkFacebook = Html.fromHtml("Facebook: <a href = '"+Constants.FACEBOOK+Constants.DEV_FACEBOOK+ "'>" + Constants.DISPLAY_DEV_FACEBOOK);
        linkInstagram = Html.fromHtml("Instagram: <a href = '"+Constants.INSTAGRAM+Constants.DEV_INSTAGRAM+ "'>" + Constants.DISPLAY_DEV_INSTAGRAM);
        linkLinkedIn = Html.fromHtml("LinkedIn: <a href = '" +Constants.LINKED_IN+Constants.DEV_LINKED_IN+ "'>" + Constants.DISPLAY_DEV_LINKED_IN);
    }

    private void initViews() {
        txtGmail = (TextView) findViewById(R.id.link_gmail);
        txtFacebook = (TextView) findViewById(R.id.link_facebook);
        txtInstagram = (TextView) findViewById(R.id.link_instagram);
        txtLinkedIn = (TextView) findViewById(R.id.link_linked_in);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            AboutDeveloper.this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
