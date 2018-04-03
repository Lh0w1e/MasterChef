package version2.masterchef.com.masterchef.Favorites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import version2.masterchef.com.masterchef.Adapters.FavoritesAdapter;
import version2.masterchef.com.masterchef.Constants.Constants;
import version2.masterchef.com.masterchef.Database.MasterChefDatabase;
import version2.masterchef.com.masterchef.DatabaseTables.FavoritesTable;
import version2.masterchef.com.masterchef.Models.FavoritesModel;
import version2.masterchef.com.masterchef.Preview.PreviewRecipe;
import version2.masterchef.com.masterchef.R;
import version2.masterchef.com.masterchef.Utils.OnTapListener;

public class Favorites extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;
    private RecyclerView mRecyclerView;

    private SQLiteDatabase mDb;

    private List<FavoritesModel> mFavoriteList = new ArrayList<>();
    private FavoritesModel favoritesModel = null;
    private FavoritesAdapter mAdapter;

    private int index = 0;

    private int[] recipe_id;
    private String[] recipe_name,
            recipe_category,
            recipe_description,
            recipe_number_of_serving,
            recipe_ingredients,
            recipe_cooking_procedure;

    private Bitmap[] recipe_image;

    private byte[] byteImage;

    private ProgressDialog progressDialog;
    private RecyclerView.LayoutManager layoutManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.favorite_coordinator_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initViews();

        initDatabase();
        //loadFavorites();
        new AsyncTaskFavorites(coordinatorLayout,mRecyclerView,progressDialog, this).execute();

    }

    /*@Override
    protected void onResume() {
        super.onResume();
        new AsyncTaskFavorites(mRecyclerView,progressDialog,this).execute();
    }*/

    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.favorite_recyclerView);
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading..");

        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
    }

    private void initDatabase() {
        mDb = MasterChefDatabase.getInstance(getApplicationContext()).getReadableDatabase();
    }

    /*private void loadFavorites() {

        String query = "SELECT * FROM " + FavoritesTable.TABLE_NAME +
                " WHERE " + FavoritesTable.FAVORITE_STATUS +
                " = " + "'" + Constants.IS_FAVORITE + "'";

        Cursor cursor = mDb.rawQuery(query, null);

        cursor.moveToFirst();

        recipe_id = new int[cursor.getCount()];
        recipe_name = new String[cursor.getCount()];
        recipe_category = new String[cursor.getCount()];
        recipe_description = new String[cursor.getCount()];
        recipe_number_of_serving = new String[cursor.getCount()];
        recipe_ingredients = new String[cursor.getCount()];
        recipe_cooking_procedure = new String[cursor.getCount()];
        recipe_image = new Bitmap[cursor.getCount()];

        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {

                recipe_id[index] = cursor.getInt(cursor.getColumnIndex(FavoritesTable.FAVORITE_COLUMN_RECIPE_ID));
                recipe_name[index] = cursor.getString(cursor.getColumnIndex(FavoritesTable.FAVORITE_COLUMN_NAME));
                recipe_category[index] = cursor.getString(cursor.getColumnIndex(FavoritesTable.FAVORITE_COLUMN_CATEGORY));
                recipe_description[index] = cursor.getString(cursor.getColumnIndex(FavoritesTable.FAVORITE_COLUMN_DESCRIPTION));
                recipe_number_of_serving[index] = cursor.getString(cursor.getColumnIndex(FavoritesTable.FAVORITE_COLUMN_NUMBER_OF_SERVINGS));
                recipe_ingredients[index] = cursor.getString(cursor.getColumnIndex(FavoritesTable.FAVORITE_COLUMN_INGREDIENTS));
                recipe_cooking_procedure[index] = cursor.getString(cursor.getColumnIndex(FavoritesTable.FAVORITE_COLUMN_COOKING_PROCEDURE));
                byteImage = cursor.getBlob(cursor.getColumnIndex(FavoritesTable.FAVORITE_COLUMN_RECIPE_IMAGE));

                recipe_image[index] = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);

                float dm = getApplicationContext().getResources().getDisplayMetrics().density;

                int h = (int) (100 * dm);
                int w = (int) (h * recipe_image[index].getWidth() / ((double) recipe_image[index].getHeight()));

                recipe_image[index] = Bitmap.createScaledBitmap(recipe_image[index], w, h, true);

                favoritesModel = new FavoritesModel(recipe_id[index], recipe_name[index], recipe_category[index], recipe_description[index],
                        recipe_number_of_serving[index], recipe_ingredients[index], recipe_cooking_procedure[index], recipe_image[index]);

                mFavoriteList.add(favoritesModel);

                cursor.moveToNext();
                index++;
            }

        } else {
            Log.e("favorites", cursor.getCount() + "");
            Snackbar.make(coordinatorLayout, "No Favorites Yet", Snackbar.LENGTH_SHORT).show();
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        mAdapter = new FavoritesAdapter(this, mFavoriteList);
        mAdapter.setOnTapListener(new OnTapListener() {
            @Override
            public void onTapView(int position) {
                //Toast.makeText(getApplicationContext(), recipe_name[position] + "", Toast.LENGTH_SHORT).show();
                Intent goPreview = new Intent(getApplicationContext(), PreviewRecipe.class);

                goPreview.putExtra(Constants.EXTRA_ID, recipe_id[position]);
                goPreview.putExtra(Constants.EXTRA_NAME, recipe_name[position]);
                goPreview.putExtra(Constants.EXTRA_IMAGE, recipe_image[position]);
                goPreview.putExtra(Constants.EXTRA_CATEGORY, recipe_category[position]);
                goPreview.putExtra(Constants.EXTRA_DESCRIPTION, recipe_description[position]);
                goPreview.putExtra(Constants.EXTRA_NUMBER_OF_SERVING, recipe_number_of_serving[position]);
                goPreview.putExtra(Constants.EXTRA_INGREDIENTS, recipe_ingredients[position]);
                goPreview.putExtra(Constants.EXTRA_COOKING_PROCEDURE, recipe_cooking_procedure[position]);

                startActivity(goPreview);
            }
        });

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_form, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            Favorites.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

}











