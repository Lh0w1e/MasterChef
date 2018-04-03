package version2.masterchef.com.masterchef.Favorites;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

import version2.masterchef.com.masterchef.Adapters.FavoritesAdapter;
import version2.masterchef.com.masterchef.Adapters.FavoritesAdapterV2;
import version2.masterchef.com.masterchef.Constants.Constants;
import version2.masterchef.com.masterchef.Database.MasterChefDatabase;
import version2.masterchef.com.masterchef.DatabaseTables.FavoritesTable;
import version2.masterchef.com.masterchef.Models.FavoritesModel;
import version2.masterchef.com.masterchef.Preview.PreviewRecipe;
import version2.masterchef.com.masterchef.R;
import version2.masterchef.com.masterchef.Utils.OnTapListener;

/**
 * Created by Colinares on 10/14/2017.
 */
public class AsyncTaskFavorites extends AsyncTask<Void, FavoritesModel, Void> {

    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private Activity mActivity;
    private FavoritesAdapterV2 adapter;
    private ArrayList<FavoritesModel> favoritesModels = new ArrayList<>();

    private SQLiteDatabase mDb;

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

    public AsyncTaskFavorites(CoordinatorLayout coordinatorLayout,RecyclerView recyclerView, ProgressDialog progressDialog, Activity activity) {
        this.coordinatorLayout = coordinatorLayout;
        this.recyclerView = recyclerView;
        this.progressDialog = progressDialog;
        this.mActivity = activity;
    }

    @Override
    protected void onPreExecute() {

        adapter = new FavoritesAdapterV2(favoritesModels);
        recyclerView.setAdapter(adapter);
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {

        initDatabase();
        loadFavorites();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void loadFavorites() {
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

        if(cursor.getCount() == 0){
            Snackbar.make(coordinatorLayout, "No Favorites", Snackbar.LENGTH_LONG).show();
        }else{
            while (!cursor.isAfterLast()){
                recipe_id[index] = cursor.getInt(cursor.getColumnIndex(FavoritesTable.FAVORITE_COLUMN_RECIPE_ID));
                recipe_name[index] = cursor.getString(cursor.getColumnIndex(FavoritesTable.FAVORITE_COLUMN_NAME));
                recipe_category[index] = cursor.getString(cursor.getColumnIndex(FavoritesTable.FAVORITE_COLUMN_CATEGORY));
                recipe_description[index] = cursor.getString(cursor.getColumnIndex(FavoritesTable.FAVORITE_COLUMN_DESCRIPTION));
                recipe_number_of_serving[index] = cursor.getString(cursor.getColumnIndex(FavoritesTable.FAVORITE_COLUMN_NUMBER_OF_SERVINGS));
                recipe_ingredients[index] = cursor.getString(cursor.getColumnIndex(FavoritesTable.FAVORITE_COLUMN_INGREDIENTS));
                recipe_cooking_procedure[index] = cursor.getString(cursor.getColumnIndex(FavoritesTable.FAVORITE_COLUMN_COOKING_PROCEDURE));
                byteImage = cursor.getBlob(cursor.getColumnIndex(FavoritesTable.FAVORITE_COLUMN_RECIPE_IMAGE));

                recipe_image[index] = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);

                float dm = mActivity.getResources().getDisplayMetrics().density;

                int h = (int) (100 * dm);
                int w = (int) (h * recipe_image[index].getWidth() / ((double) recipe_image[index].getHeight()));

                recipe_image[index] = Bitmap.createScaledBitmap(recipe_image[index], w, h, true);

                publishProgress(new FavoritesModel(recipe_id[index], recipe_name[index], recipe_category[index], recipe_description[index],
                        recipe_number_of_serving[index], recipe_ingredients[index], recipe_cooking_procedure[index], recipe_image[index]));

                cursor.moveToNext();
                index++;
            }
        }

        cursor.close();
        mDb.close();

    }

    @Override
    protected void onProgressUpdate(FavoritesModel... values) {

        favoritesModels.add(values[0]);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPostExecute(final Void aVoid) {

        progressDialog.dismiss();
        adapter.setOnTapListener(new OnTapListener() {
            @Override
            public void onTapView(int position) {
                //Snackbar.make(coordinatorLayout, recipe_name[position], Snackbar.LENGTH_LONG).show();
                Intent goPreview = new Intent(mActivity, PreviewRecipe.class);
                goPreview.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                goPreview.putExtra(Constants.EXTRA_ID, recipe_id[position]);
                goPreview.putExtra(Constants.EXTRA_NAME, recipe_name[position]);
                goPreview.putExtra(Constants.EXTRA_IMAGE, recipe_image[position]);
                goPreview.putExtra(Constants.EXTRA_CATEGORY, recipe_category[position]);
                goPreview.putExtra(Constants.EXTRA_DESCRIPTION, recipe_description[position]);
                goPreview.putExtra(Constants.EXTRA_NUMBER_OF_SERVING, recipe_number_of_serving[position]);
                goPreview.putExtra(Constants.EXTRA_INGREDIENTS, recipe_ingredients[position]);
                goPreview.putExtra(Constants.EXTRA_COOKING_PROCEDURE, recipe_cooking_procedure[position]);

                goPreview.putExtra(Constants.FAVORITE_EXTRAS_ID, Constants.FAVORITE_EXTRAS_VALUE);

                mActivity.startActivity(goPreview);
                mActivity.finish();

            }
        });

    }

    private void initDatabase() {
        mDb = MasterChefDatabase.getInstance(mActivity).getReadableDatabase();
    }
}
