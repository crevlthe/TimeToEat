package com.example.timetoeat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
    private String RecipeURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Intent intent = getIntent();
        //always check that there are intent extras before retrieving as
        //retrieving null intent extras will lead to an app crash
        //to append more checks e.g. if(getIntent().hasExtra("xxx") && getIntent().hasExtra("xxx"))
        if(getIntent().hasExtra("URL")){
            RecipeURL = getIntent().getStringExtra("URL");
        }

        RecipeTitle = (TextView) findViewById(R.id.text_recipe_title);
        RecipeIngredients = (TextView) findViewById(R.id.text_recipe_ingredients);
        RecipeBody = (TextView) findViewById(R.id.text_recipe_body);

        getRecipe();
        //getBtn = (Button) findViewById(R.id.button_getrecipe);
        //getBtn.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View view) {
//                getRecipe();
  //          }
        };


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
                    String url = RecipeURL;
                    //String url = "https://www.allrecipes.com/recipe/274419/creamy-chicken-rice-soup/?internalSource=rotd&referringContentType=Homepage&clickId=cardslot%201";
                    //8932/fruity-curry-chicken-salad/
                    //recipe/25471/jamaican-jerk-chicken/
                    final Document doc = Jsoup.connect(url).get();

                    //Recipe name
                    final Elements recipe_name_1 = doc.select("h1.recipe-summary__h1");
                    final Elements recipe_name_2 = doc.select("h1.headline");

                    //Recipe steps
                    Elements steps_1 = doc.select("span.recipe-directions__list--item");
                    if (steps_1.hasText() == false) {
                        steps_1 = doc.select(".section-body");
                    }

                    //Recipe ingredients
                    Elements ingredients = doc.select(".ingredients-item");


                    for (Element ingredient : ingredients) {
                        //recipe_name_ingredients.append("\u25AA ").append(ingredient.attr("value"));
                        recipe_name_ingredients.append("\u25AA ").append(ingredient.text()).append("\n");

                        if (IngredientCount < ingredients.size()) {
                            recipe_name_ingredients.append("\n");
                        }

                        IngredientCount++;
                    }

                    for (Element step : steps_1) {
                        if (StepCount < steps_1.size()) {
                            recipe_name_body.append(StepCount).append(". ").append(step.text()).append("\n").append("\n");
                            StepCount++;
                        }
                    }


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (recipe_name_1.hasText() == false) {
                                RecipeTitle.setText(recipe_name_2.first().text());
                            } else {
                                RecipeTitle.setText(recipe_name_1.first().text());
                            }
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