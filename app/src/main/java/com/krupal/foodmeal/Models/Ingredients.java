package com.krupal.foodmeal.Models;

import java.util.ArrayList;

public class Ingredients {
    String ingredients_title;
    ArrayList<String> ingredients_item_list;

    public String getIngredients_title() {
        return ingredients_title;
    }

    public void setIngredients_title(String ingredients_title) {
        this.ingredients_title = ingredients_title;
    }

    public ArrayList<String> getIngredients_item_list() {
        return ingredients_item_list;
    }

    public void setIngredients_item_list(ArrayList<String> ingredients_item_list) {
        this.ingredients_item_list = ingredients_item_list;
    }
}
