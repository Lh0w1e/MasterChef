package version2.masterchef.com.masterchef.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import version2.masterchef.com.masterchef.Adapters.FriedAdapter;
import version2.masterchef.com.masterchef.Constants.Constants;
import version2.masterchef.com.masterchef.Database.MasterChefDatabaseFromAssets;
import version2.masterchef.com.masterchef.Models.FriedModel;
import version2.masterchef.com.masterchef.Preview.PreviewRecipe;
import version2.masterchef.com.masterchef.R;

/**
 * Created by Colinares on 9/27/2017.
 */
public class FragmentFried extends Fragment {
    private View mView;

    private MasterChefDatabaseFromAssets databaseFromAssets;

    private FriedAdapter mAdapter;
    private List<FriedModel> mRecipeList;
    private FriedModel mRecipesModel = null;

    private ListView mListView;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_fried, container, false);

        initDatabase();
        initListView();
        loadAppetizersInListView();
        initListViewOnClick();

        return mView;
    }

    private void initDatabase() {
        try{
            databaseFromAssets = new MasterChefDatabaseFromAssets(getActivity());
            databaseFromAssets.checkAndCopyDatabase();
            databaseFromAssets.openDatabase();
        }catch (SQLiteException e){
            e.printStackTrace();
        }

    }

    private void loadAppetizersInListView() {

        mRecipesModel= null;
        mRecipeList = new ArrayList<>();

        String query = "SELECT * FROM " + Constants.TBL_DEFAULT_RECIPES +
                " WHERE " + Constants.RECIPE_CATEGORY +
                " = " + "'"+ Constants.MENU_FRIED +"'";

        Cursor cursor = databaseFromAssets.selectQuery(query);
        cursor.moveToFirst();

        recipe_id = new int[cursor.getCount()];
        recipe_name = new String[cursor.getCount()];
        recipe_category = new String[cursor.getCount()];
        recipe_description = new String[cursor.getCount()];
        recipe_number_of_serving = new String[cursor.getCount()];
        recipe_ingredients = new String[cursor.getCount()];
        recipe_cooking_procedure = new String[cursor.getCount()];
        recipe_image = new Bitmap[cursor.getCount()];

        while (!cursor.isAfterLast()){

            recipe_id[index] = cursor.getInt(cursor.getColumnIndex(Constants.RECIPE_ID));
            recipe_name[index] = cursor.getString(cursor.getColumnIndex(Constants.RECIPE_NAME));
            recipe_category[index] = cursor.getString(cursor.getColumnIndex(Constants.RECIPE_CATEGORY));
            recipe_description[index] = cursor.getString(cursor.getColumnIndex(Constants.RECIPE_DESCRIPTION));
            recipe_number_of_serving[index] = cursor.getString(cursor.getColumnIndex(Constants.RECIPE_NUMBER_OF_SERVING));
            recipe_ingredients[index] = cursor.getString(cursor.getColumnIndex(Constants.RECIPE_INGREDIENTS));
            recipe_cooking_procedure[index] = cursor.getString(cursor.getColumnIndex(Constants.RECIPE_COOKING_PROCEDURE));
            byteImage = cursor.getBlob(cursor.getColumnIndex(Constants.RECIPE_IMAGE));

            recipe_image[index] = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);

            float dm = getContext().getResources().getDisplayMetrics().density;

            int h = (int) (100 * dm);
            int w = (int) (h * recipe_image[index].getWidth() / ((double) recipe_image[index].getHeight()));

            recipe_image[index] = Bitmap.createScaledBitmap(recipe_image[index], w, h, true);

            mRecipesModel = new FriedModel(recipe_id[index], recipe_name[index],recipe_category[index],recipe_description[index],
                    recipe_number_of_serving[index],recipe_ingredients[index],recipe_cooking_procedure[index],recipe_image[index]);

            mRecipeList.add(mRecipesModel);

            cursor.moveToNext();
            index++;

        }

        mAdapter = new FriedAdapter(getContext(), R.layout.custom_fried, mRecipeList);
        mListView.setAdapter(mAdapter);
    }

    private void initListViewOnClick() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent goPreview = new Intent(getContext(), PreviewRecipe.class);

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
    }

    private void initListView() {
        mListView = (ListView) mView.findViewById(R.id.fried_listView);
    }


}
