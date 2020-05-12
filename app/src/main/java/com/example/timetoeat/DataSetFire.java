package com.example.timetoeat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataSetFire {
    private String imgUrl;
    private String recipeDescription;
    private String recipeIngredients;
    private String recipeName;




    public DataSetFire(String recipeName, String recipeIngredients, String recipeDescription, String imgUrl ){
        this.recipeName = recipeName;
        this.recipeIngredients = recipeIngredients;
        this.recipeDescription = recipeDescription;
        this.imgUrl = imgUrl;
    }

    public DataSetFire(){

    }


    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(String recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public void setRecipeDescription(String recipeDescription) {
        this.recipeDescription = recipeDescription;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
