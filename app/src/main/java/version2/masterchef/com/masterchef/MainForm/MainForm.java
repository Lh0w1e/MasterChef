package version2.masterchef.com.masterchef.MainForm;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterViewFlipper;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import version2.masterchef.com.masterchef.About.AboutDeveloper;
import version2.masterchef.com.masterchef.About.AboutMasterChef;
import version2.masterchef.com.masterchef.Adapters.ImageFlipperAdapter;
import version2.masterchef.com.masterchef.Adapters.TabLayoutAdapter;
import version2.masterchef.com.masterchef.Constants.Constants;
import version2.masterchef.com.masterchef.Database.MasterChefDatabaseFromAssets;
import version2.masterchef.com.masterchef.Favorites.Favorites;
import version2.masterchef.com.masterchef.Preview.PreviewRecipe;
import version2.masterchef.com.masterchef.Quiz.QuizMainForm;
import version2.masterchef.com.masterchef.R;

public class MainForm extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Toolbar toolbar;

    private ViewPager viewPager;
    private TabLayout tabLayout;

    //for double back press
    private boolean doubleBackPressed = false;

    //for image animation
    private AdapterViewFlipper adapterViewFlipper;
    private String[] getRecipeNameToFlip;
    private Bitmap[] getImageToFlip;
    private byte[] byteImage;

    private MasterChefDatabaseFromAssets databaseFromAssets;

    private ArrayList<Uri> arrayListApkFilepath;

    private int search_id;
    private String search_name,
            search_category,
            search_description,
            search_number_of_serving,
            search_ingredients,
            search_cooking_procedure;

    private Bitmap search_image;

    private byte[] search_byteImage;

    private Animation goLeft, goRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_form);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initDatabase();
        initDrawer();
        initViews();
        initAnimations();

        getImagesFromDatabase();

        initAdapters();

    }

    private void initAnimations() {
        goLeft = AnimationUtils.loadAnimation(this,R.anim.rapid_move_left);
        goRight = AnimationUtils.loadAnimation(this,R.anim.rapid_move_right);
    }

    private void initDatabase() {
        try {
            databaseFromAssets = new MasterChefDatabaseFromAssets(getApplicationContext());
            databaseFromAssets.checkAndCopyDatabase();
            databaseFromAssets.openDatabase();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

    }

    private void initAdapters() {
        TabLayoutAdapter tabLayoutAdapter = new TabLayoutAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabLayoutAdapter);

        tabLayout.setupWithViewPager(viewPager);

        ImageFlipperAdapter imageFlipperAdapter = new ImageFlipperAdapter(this, getImageToFlip, getRecipeNameToFlip, goLeft, goRight);
        adapterViewFlipper.setAdapter(imageFlipperAdapter);
        adapterViewFlipper.setAutoStart(true);

    }

    private void getImagesFromDatabase() {

        int index = 0;

        String query = "SELECT * FROM " + Constants.TBL_DEFAULT_RECIPES;

        Cursor cursor = databaseFromAssets.selectQuery(query);
        cursor.moveToFirst();

        getRecipeNameToFlip = new String[cursor.getCount()];
        getImageToFlip = new Bitmap[cursor.getCount()];

        if (cursor.getCount() == 0) {
            Log.e("data count", "0");
        } else {
            while (!cursor.isAfterLast()) {
                getRecipeNameToFlip[index] = cursor.getString(cursor.getColumnIndex(Constants.RECIPE_NAME));
                byteImage = cursor.getBlob(cursor.getColumnIndex(Constants.RECIPE_IMAGE));

                getImageToFlip[index] = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);

                float dm = getApplicationContext().getResources().getDisplayMetrics().density;

                int h = (int) (100 * dm);
                int w = (int) (h * getImageToFlip[index].getWidth() / ((double) getImageToFlip[index].getHeight()));

                getImageToFlip[index] = Bitmap.createScaledBitmap(getImageToFlip[index], w, h, true);

                cursor.moveToNext();
                index++;

            }
        }
    }

    private void initViews() {
        adapterViewFlipper = (AdapterViewFlipper) findViewById(R.id.adapter_view_flipper);
        viewPager = (ViewPager) findViewById(R.id.viewPagerHome);
        tabLayout = (TabLayout) findViewById(R.id.tableLayoutHome);
    }

    private void initDrawer() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        if (doubleBackPressed) {
            super.onBackPressed();
            return;
        }

        doubleBackPressed = true;
        Snackbar.make(drawer, "Press back again to exit.", Snackbar.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackPressed = false;
            }
        }, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        doubleBackPressed = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.menuSearch);

        final SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        final SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete)
                mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, getRecipeNameToFlip);
        searchAutoComplete.setAdapter(adapter);
        searchAutoComplete.setDropDownBackgroundResource(R.color.colorDefault);

        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.clearFocus();

        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String searchedRecipeName = (String) parent.getItemAtPosition(position);

                //Toast.makeText(MainForm.this, "you clicked " + searchedRecipeName, Toast.LENGTH_SHORT).show();


                mSearchView.setIconified(true);
                mSearchView.clearFocus();

                getInfoOfRecipeName(searchedRecipeName);
                goPreview();

            }
        });

        return true;
    }

    private void goPreview() {
        Intent goPreview = new Intent(getApplicationContext(), PreviewRecipe.class);

        goPreview.putExtra(Constants.EXTRA_ID, search_id);
        goPreview.putExtra(Constants.EXTRA_NAME, search_name);
        goPreview.putExtra(Constants.EXTRA_IMAGE, search_image);
        goPreview.putExtra(Constants.EXTRA_CATEGORY, search_category);
        goPreview.putExtra(Constants.EXTRA_DESCRIPTION, search_description);
        goPreview.putExtra(Constants.EXTRA_NUMBER_OF_SERVING, search_number_of_serving);
        goPreview.putExtra(Constants.EXTRA_INGREDIENTS, search_ingredients);
        goPreview.putExtra(Constants.EXTRA_COOKING_PROCEDURE, search_cooking_procedure);

        startActivity(goPreview);
    }

    private void getInfoOfRecipeName(String searchedRecipeName) {
        String query = "SELECT * FROM " + Constants.TBL_DEFAULT_RECIPES +
                " WHERE " + Constants.RECIPE_NAME +
                " = " + "'"+searchedRecipeName+"'" +
                " LIMIT 1";

        Log.e("search query", query);

        Cursor cursor = databaseFromAssets.selectQuery(query);
        cursor.moveToFirst();

        search_id = cursor.getInt(cursor.getColumnIndex(Constants.RECIPE_ID));
        search_name = cursor.getString(cursor.getColumnIndex(Constants.RECIPE_NAME));
        search_category = cursor.getString(cursor.getColumnIndex(Constants.RECIPE_CATEGORY));
        search_description = cursor.getString(cursor.getColumnIndex(Constants.RECIPE_DESCRIPTION));
        search_number_of_serving = cursor.getString(cursor.getColumnIndex(Constants.RECIPE_NUMBER_OF_SERVING));
        search_ingredients = cursor.getString(cursor.getColumnIndex(Constants.RECIPE_INGREDIENTS));
        search_cooking_procedure = cursor.getString(cursor.getColumnIndex(Constants.RECIPE_COOKING_PROCEDURE));
        search_byteImage = cursor.getBlob(cursor.getColumnIndex(Constants.RECIPE_IMAGE));

        search_image = BitmapFactory.decodeByteArray(search_byteImage, 0, search_byteImage.length);

        cursor.close();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_favorites) {
            startActivity(new Intent(getApplicationContext(), Favorites.class));
        }
        /*else if (id == R.id.nav_quiz) {
            startActivity(new Intent(getApplicationContext(), QuizMainForm.class));
        }*/
        else if (id == R.id.nav_about_masterchef) {
            startActivity(new Intent(getApplicationContext(), AboutMasterChef.class));
        } else if (id == R.id.nav_about_developer) {
            startActivity(new Intent(getApplicationContext(), AboutDeveloper.class));
        } else if (id == R.id.nav_feedback) {
            goFeedback();
        } else if (id == R.id.nav_share) {
            goShare();
            //goShareApk();
        } else if (id == R.id.nav_moreApps) {
            goPlayStore(this.getApplicationContext());

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void goShareApk() {
        arrayListApkFilepath = new ArrayList<Uri>();

        apkShareFunction(getPackageName());
        // you can pass bundle id of installed app in your device instead of getPackageName()
        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        intent.setType("application/vnd.android.package-archive");
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, arrayListApkFilepath);
        startActivity(Intent.createChooser(intent, "Share " + arrayListApkFilepath.size() + " Files Via"));

    }

    private void apkShareFunction(String bundle_id) {
        File f1;
        File f2 = null;

        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        final List pkgAppsList = getPackageManager().queryIntentActivities(mainIntent, 0);
        int z = 0;
        for (Object object : pkgAppsList) {

            ResolveInfo info = (ResolveInfo) object;
            if (info.activityInfo.packageName.equals(bundle_id)) {

                f1 = new File(info.activityInfo.applicationInfo.publicSourceDir);

                Log.e("file--", " " + f1.getName() + "----" + info.loadLabel(getPackageManager()));
                try {

                    String file_name = info.loadLabel(getPackageManager()).toString();
                    Log.e("file_name--", " " + file_name);

                    f2 = new File(Environment.getExternalStorageDirectory().toString() + "/Folder");
                    f2.mkdirs();
                    f2 = new File(f2.getPath() + "/" + file_name + ".apk");
                    f2.createNewFile();

                    InputStream in = new FileInputStream(f1);

                    OutputStream out = new FileOutputStream(f2);

                    byte[] buf = new byte[4096];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    in.close();
                    out.close();
                    System.out.println("File copied.");
                } catch (FileNotFoundException ex) {
                    System.out.println(ex.getMessage() + " in the specified directory.");
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        arrayListApkFilepath.add(Uri.fromFile(new File(f2.getAbsolutePath())));

    }

    private void goShare() {

        ApplicationInfo info = getApplicationContext().getApplicationInfo();
        String apkPath = info.publicSourceDir;

        Log.e("apk name", apkPath);

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("application/vnd.android.package-archive");
        //share.setType("*/*");

        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(apkPath)));

        startActivity(Intent.createChooser(share, "Share App Via"));

    }

    private void goPlayStore(Context context) {
        // you can also use BuildConfig.APPLICATION_ID
        String appId = context.getPackageName();
        Intent rateIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("market://details?id=" + appId));
        boolean marketFound = false;

        // find all applications able to handle our rateIntent
        final List<ResolveInfo> otherApps = context.getPackageManager().queryIntentActivities(rateIntent, 0);
        for (ResolveInfo otherApp : otherApps) {
            // look for Google Play application
            if (otherApp.activityInfo.applicationInfo.packageName.equals(Constants.GOOGLE_PLAY_PACKAGE_NAME)) {

                ActivityInfo otherAppActivity = otherApp.activityInfo;
                ComponentName componentName = new ComponentName(
                        otherAppActivity.applicationInfo.packageName,
                        otherAppActivity.name
                );
                // make sure it does NOT open in the stack of your activity
                rateIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // task reparenting if needed
                rateIntent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                // if the Google Play was already open in a search result
                //  this make sure it still go to the app page you requested
                rateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // this make sure only the Google Play app is allowed to
                // intercept the intent
                rateIntent.setComponent(componentName);
                startActivity(rateIntent);
                marketFound = true;
                break;

            }
        }
        // if GP not present on device, open web browser
        if (!marketFound) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(Constants.GOOGLE_PLAY_WEB_URL_LINK + appId));
            startActivity(webIntent);
        }
    }

    private void goFeedback() {
        Intent feedback = new Intent(Intent.ACTION_SEND);

        feedback.setData(Uri.parse("email"));

        feedback.putExtra(Intent.EXTRA_EMAIL, new String[]{Constants.DISPLAY_DEV_EMAIL});
        feedback.putExtra(Intent.EXTRA_SUBJECT, "MasterChef : Feedback/Suggestions");
        feedback.putExtra(Intent.EXTRA_TEXT, "");

        feedback.setType("message/rfc822");
        //feedback.setType("*/*");

        Intent chooser = Intent.createChooser(feedback, "Send Feedback using");
        startActivity(chooser);

    }


}













