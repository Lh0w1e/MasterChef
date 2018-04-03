package version2.masterchef.com.masterchef.Models;

import android.graphics.Bitmap;

/**
 * Created by Colinares on 10/1/2017.
 */
public class AppetizerModel {

    private int recipe_id;
    private String recipe_name,
            recipe_category,
            recipe_description,
            recipe_number_of_serving,
            recipe_ingredients,
            recipe_cooking_procedure;

    private Bitmap recipe_image;

    public AppetizerModel(int recipe_id, String recipe_name, String recipe_category, String recipe_description,
                          String recipe_number_of_serving, String recipe_ingredients, String recipe_cooking_procedure,
                          Bitmap recipe_image) {
        this.recipe_id = recipe_id;
        this.recipe_name = recipe_name;
        this.recipe_category = recipe_category;
        this.recipe_description = recipe_description;
        this.recipe_number_of_serving = recipe_number_of_serving;
        this.recipe_ingredients = recipe_ingredients;
        this.recipe_cooking_procedure = recipe_cooking_procedure;
        this.recipe_image = recipe_image;
    }

    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getRecipe_name() {
        return recipe_name;
    }

    public void setRecipe_name(String recipe_name) {
        this.recipe_name = recipe_name;
    }

    public String getRecipe_category() {
        return recipe_category;
    }

    public void setRecipe_category(String recipe_category) {
        this.recipe_category = recipe_category;
    }

    public String getRecipe_description() {
        return recipe_description;
    }

    public void setRecipe_description(String recipe_description) {
        this.recipe_description = recipe_description;
    }

    public String getRecipe_number_of_serving() {
        return recipe_number_of_serving;
    }

    public void setRecipe_number_of_serving(String recipe_number_of_serving) {
        this.recipe_number_of_serving = recipe_number_of_serving;
    }

    public String getRecipe_ingredients() {
        return recipe_ingredients;
    }

    public void setRecipe_ingredients(String recipe_ingredients) {
        this.recipe_ingredients = recipe_ingredients;
    }

    public String getRecipe_cooking_procedure() {
        return recipe_cooking_procedure;
    }

    public void setRecipe_cooking_procedure(String recipe_cooking_procedure) {
        this.recipe_cooking_procedure = recipe_cooking_procedure;
    }

    public Bitmap getRecipe_image() {
        return recipe_image;
    }

    public void setRecipe_image(Bitmap recipe_image) {
        this.recipe_image = recipe_image;
    }
}
