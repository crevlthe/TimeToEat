package com.example.timetoeat;

public class PersonalRecipeInfo {

    private String recipeName;
    private String recipeIngredients;
    private String recipeDescription;
    private String imgUrl;

    public PersonalRecipeInfo(){
    }

    public PersonalRecipeInfo(String recipeName, String recipeIngredients, String recipeDescription, String imgUrl){
        this.recipeName = recipeName;
        this.recipeIngredients = recipeIngredients;
        this.recipeDescription = recipeDescription;
        this.imgUrl = imgUrl;
    }

    public String getRecipeName(){return recipeName;}

    public String getRecipeIngredients(){return recipeIngredients;}

    public String getRecipeDescription(){return recipeDescription;}

    public String getImgUrl(){return imgUrl;}

}
