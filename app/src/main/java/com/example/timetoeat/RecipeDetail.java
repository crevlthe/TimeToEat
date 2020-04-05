package com.example.timetoeat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class RecipeDetail extends AppCompatActivity {
    private TextView RecipeTitle;
    private TextView RecipeIngredients;
    private TextView RecipeBody;
    private Button getBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        RecipeTitle = (TextView) findViewById(R.id.text_recipe_title);
        RecipeIngredients = (TextView) findViewById(R.id.text_recipe_ingredients);
        RecipeBody = (TextView) findViewById(R.id.text_recipe_body);
        getBtn = (Button) findViewById(R.id.button_getrecipe);
        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRecipe();
            }
        });
    }

    private void getRecipe() {
        Document doc = null;
        new Thread() {
            @Override
            public void run() {
                final StringBuilder recipe_name_ingredients = new StringBuilder();
                final StringBuilder recipe_name_body = new StringBuilder();
                int IngredientCount = 1;
                int StepCount = 1;

                try {
                    Document doc = Jsoup.connect("https://www.allrecipes.com/recipe/84270/slow-cooker-corned-beef-and-cabbage/").get();
                    final Element recipe_name = doc.select("h1.recipe-summary__h1").first();
                    Elements ingredients = doc.select("li.checkList__line label[title]");
                    Elements steps = doc.select("span.recipe-directions__list--item");

                    for (Element ingredient : ingredients) {
                        recipe_name_ingredients.append("\u25AA ").append(ingredient.attr("title"));

                        if (IngredientCount < ingredients.size()) {
                            recipe_name_ingredients.append("\n");
                        }

                        IngredientCount++;
                    }

                    for (Element step : steps) {
                        if (StepCount < steps.size()) {
                            recipe_name_body.append(StepCount).append(". ").append(step.text()).append("\n").append("\n");
                            StepCount++;
                        }
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            RecipeTitle.setText(recipe_name.text());
                            RecipeIngredients.setText(recipe_name_ingredients.toString());
                            RecipeBody.setText(recipe_name_body.toString());
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    // Error
                }
            }
        }.start();
    }
}
