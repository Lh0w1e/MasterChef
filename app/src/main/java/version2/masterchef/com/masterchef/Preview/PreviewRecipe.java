package version2.masterchef.com.masterchef.Preview;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

import version2.masterchef.com.masterchef.Constants.Constants;
import version2.masterchef.com.masterchef.Database.MasterChefDatabase;
import version2.masterchef.com.masterchef.Database.MasterChefDatabaseFunction;
import version2.masterchef.com.masterchef.DatabaseTables.FavoritesTable;
import version2.masterchef.com.masterchef.Favorites.Favorites;
import version2.masterchef.com.masterchef.R;

/**
 * Created by Colinares on 10/1/2017.
 */
public class PreviewRecipe extends AppCompatActivity {

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    private ImageView previewRecipeImage;
    private TextView previewRecipeDescription, previewRecipeIngredients, previewRecipeServings, previewRecipeProcedure;

    private int getRecipe_id;
    private Bitmap getImage;
    private String getRecipeName, getCategory, getDescription, getIngredients, getServing, getProcedure;
    private String getFavoriteExtras;

    private boolean isFavorite = false;

    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_recipe);

        initDatabase();

        initViews();
        getIntents();

        previewAll();

        checkIfRecipeIsAlreadyOnFavorites();

    }

    private void initDatabase() {
        mDb = MasterChefDatabase.getInstance(getApplicationContext()).getReadableDatabase();
        MasterChefDatabaseFunction.init(getApplicationContext());
    }

    private void previewAll() {
        collapsingToolbarLayout.setTitle(getRecipeName);
        collapsingToolbarLayout.setCollapsedTitleTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        previewRecipeImage.setImageBitmap(getImage);

        previewRecipeDescription.setText(getDescription);
        previewRecipeServings.setText(getServing);
        previewRecipeIngredients.setText(getIngredients);
        previewRecipeProcedure.setText(getProcedure);
    }

    private void getIntents() {

        getFavoriteExtras = getIntent().getExtras().getString(Constants.FAVORITE_EXTRAS_ID);

        getRecipe_id = getIntent().getExtras().getInt(Constants.EXTRA_ID);
        getRecipeName = getIntent().getExtras().getString(Constants.EXTRA_NAME);
        getImage = (Bitmap) getIntent().getExtras().get(Constants.EXTRA_IMAGE);

        getCategory = getIntent().getExtras().getString(Constants.EXTRA_CATEGORY);
        getDescription = getIntent().getExtras().getString(Constants.EXTRA_DESCRIPTION);
        getServing = getIntent().getExtras().getString(Constants.EXTRA_NUMBER_OF_SERVING);
        getIngredients = getIntent().getExtras().getString(Constants.EXTRA_INGREDIENTS);
        getProcedure = getIntent().getExtras().getString(Constants.EXTRA_COOKING_PROCEDURE);

    }

    private void initViews() {
        toolbar = (Toolbar)findViewById(R.id.preview_toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapseTB);
        previewRecipeImage = (ImageView) findViewById(R.id.preview_collapse_image);

        previewRecipeDescription = (TextView) findViewById(R.id.preview_recipe_description);
        previewRecipeServings = (TextView) findViewById(R.id.preview_recipe_serving);
        previewRecipeIngredients = (TextView) findViewById(R.id.preview_recipe_ingredients);
        previewRecipeProcedure = (TextView) findViewById(R.id.preview_recipe_procedure);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_favorites, menu);

        MenuItem item = menu.findItem(R.id.menu_favorite);

        checkIfRecipeIsAlreadyOnFavorites();

        if(isFavorite){
            item.setIcon(R.drawable.red_favorite);
        }else{
            item.setIcon(R.drawable.white_favorite);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home){

            boolean hasExtra = true;

            if(getFavoriteExtras == null || getFavoriteExtras.equals("")){
                hasExtra = false;
            }

            if(!hasExtra){
                finish();
            }else{
                startActivity(new Intent(getApplicationContext(),Favorites.class));
                finish();
            }

        }else if (id == R.id.menu_favorite){

            checkIfRecipeIsAlreadyOnFavorites();

            if(!isFavorite){
                insertNewFavorite(item);
            }else{
                updateFavoriteRecipe(item);
            }

        }

        return super.onOptionsItemSelected(item);
    }



    private void insertNewFavorite(MenuItem item) {
        int saveId = getRecipe_id;
        String saveRecipeName = getRecipeName;
        String saveCategory = getCategory;
        String saveDescription = getDescription;
        String saveServing = getServing;
        String saveIngredients = getIngredients;
        String saveProcedure = getProcedure;
        Bitmap saveImage = getImage;

        ContentValues insertValues = new ContentValues();

        insertValues.put(FavoritesTable.FAVORITE_COLUMN_RECIPE_ID, saveId);
        insertValues.put(FavoritesTable.FAVORITE_COLUMN_NAME, saveRecipeName);
        insertValues.put(FavoritesTable.FAVORITE_COLUMN_CATEGORY, saveCategory);
        insertValues.put(FavoritesTable.FAVORITE_COLUMN_DESCRIPTION, saveDescription);
        insertValues.put(FavoritesTable.FAVORITE_COLUMN_NUMBER_OF_SERVINGS, saveServing);
        insertValues.put(FavoritesTable.FAVORITE_COLUMN_INGREDIENTS, saveIngredients);
        insertValues.put(FavoritesTable.FAVORITE_COLUMN_COOKING_PROCEDURE, saveProcedure);
        insertValues.put(FavoritesTable.FAVORITE_COLUMN_RECIPE_IMAGE, bitmapToByte(saveImage));
        insertValues.put(FavoritesTable.FAVORITE_STATUS, Constants.IS_FAVORITE);

        MasterChefDatabaseFunction.insert(FavoritesTable.TABLE_NAME, insertValues);

        Log.e("favorites","insert success");

        item.setIcon(R.drawable.red_favorite);

    }

    private void updateFavoriteRecipe(MenuItem item) {
        int idToDelete = getRecipe_id;

        boolean result = MasterChefDatabaseFunction.update(idToDelete, Constants.IS_NOT_FAVORITE);

        if(result){
            item.setIcon(R.drawable.white_favorite);
            Log.e("favorites", "item updated");
        }else{
            Log.e("favorites", "error updating item");
        }

    }


    private void checkIfRecipeIsAlreadyOnFavorites() {
        String query = "SELECT * FROM " + FavoritesTable.TABLE_NAME +
                " WHERE " + FavoritesTable.FAVORITE_COLUMN_RECIPE_ID +
                " = " + getRecipe_id +
                " AND " + FavoritesTable.FAVORITE_STATUS +
                " = " + "'"+Constants.IS_FAVORITE+"'";

        Log.e("status query", query);

        Cursor cursor = mDb.rawQuery(query, null);
        cursor.moveToFirst();

        if(cursor.getCount() > 0){
            Log.e("has favorite", cursor.getCount()+"");
            isFavorite = true;
            Log.e("isFavorite", isFavorite + "");

        }else{
            Log.e("no favorite", cursor.getCount()+"");
            isFavorite = false;
            Log.e("isFavorite", isFavorite + "");
        }
    }

    private byte[] bitmapToByte(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] byteArray = outputStream.toByteArray();
        return byteArray;
    }
}






