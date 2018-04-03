package version2.masterchef.com.masterchef.DatabaseTables;

/**
 * Created by Colinares on 10/3/2017.
 */
public class FavoritesTable {
    public static final String TABLE_NAME = "tbl_favorites";

    public static final String FAVORITE_COLUMN_ID = "column_id";
    public static final String FAVORITE_COLUMN_RECIPE_ID = "column_recipe_id";
    public static final String FAVORITE_COLUMN_NAME = "column_name";
    public static final String FAVORITE_COLUMN_CATEGORY = "column_category";
    public static final String FAVORITE_COLUMN_DESCRIPTION = "column_description";
    public static final String FAVORITE_COLUMN_NUMBER_OF_SERVINGS = "column_number_of_serving";
    public static final String FAVORITE_COLUMN_INGREDIENTS = "column_ingredients";
    public static final String FAVORITE_COLUMN_COOKING_PROCEDURE = "column_cooking_procedure";
    public static final String FAVORITE_COLUMN_RECIPE_IMAGE = "column_recipe_image";
    public static final String FAVORITE_STATUS = "status";

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                    FAVORITE_COLUMN_ID + " INTEGER PRIMARY KEY," +
                    FAVORITE_COLUMN_RECIPE_ID + " INTEGER," +
                    FAVORITE_COLUMN_NAME + " TEXT," +
                    FAVORITE_COLUMN_CATEGORY + " TEXT," +
                    FAVORITE_COLUMN_DESCRIPTION + " TEXT," +
                    FAVORITE_COLUMN_NUMBER_OF_SERVINGS + " TEXT," +
                    FAVORITE_COLUMN_INGREDIENTS + " TEXT," +
                    FAVORITE_COLUMN_COOKING_PROCEDURE + " TEXT," +
                    FAVORITE_COLUMN_RECIPE_IMAGE + " BLOB," +
                    FAVORITE_STATUS + " TEXT " +
                    ")";

}
