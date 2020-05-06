package com.example.timetoeat;

public class FoodData {

    private String itemName;
    private String itemIngredients;
    private String itemDescription;
    private int itemImage;

    public FoodData(){

    }


    public FoodData(String itemName, String itemDescription, String itemIngredients, String imageUrl) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemIngredients = itemIngredients;
        this.itemImage = itemImage;
    }

    public String getItemName(){
        return itemName;
    }

    public String getItemDescription(){return itemDescription; }

    public String getItemIngredients(){ return itemIngredients;}

    public int getItemImage(){ return itemImage; }
}
