package com.example.timetoeat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.Serializable;

public class RecipeDetail extends AppCompatActivity {
    private TextView RecipeTitle;
    private TextView RecipeIngredients;
    private TextView RecipeBody;
    private Button getBtn;
    private String RecipeURL;
    private Button saveRecipe;

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
        saveRecipe = (Button) findViewById(R.id.button_saverecipe);

        getRecipe();
        //getBtn = (Button) findViewById(R.id.button_getrecipe);
        //getBtn.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View view) {
//                getRecipe();
  //          }
        /*saveRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToBuyList();
            }
        });*/

        };
    /*public void sendToBuyList(ingredients){
            Intent intent = new Intent(this, ToBuyList.class);
            startActivity(intent);
            intent.putExtra("strings",ingredients);
    }*/


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
                    //+Set the image view
                    String url = RecipeURL;
                    //String url = "https://www.allrecipes.com/recipe/274419/creamy-chicken-rice-soup/?internalSource=rotd&referringContentType=Homepage&clickId=cardslot%201";
                    //8932/fruity-curry-chicken-salad/
                    //recipe/25471/jamaican-jerk-chicken/
                    final Document doc = Jsoup.connect(url).get();

                    //Recipe name
                    final Elements recipe_name_1 = doc.select("h1.recipe-summary__h1");
                    //Feature style article
                    //https://www.allrecipes.com/recipe/231030/braised-corned-beef-brisket/

                    final Elements recipe_name_2 = doc.select("h1.headline");
                    //General style article
                    //https://www.allrecipes.com/recipe/223042/chicken-parmesan/

                    //Recipe steps
                    Elements steps = doc.select("span.recipe-directions__list--item");
                    if (steps.hasText() == false) {
                        steps = doc.select(".section-body");
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

                    //Recipe steps
                    for (Element step : steps) {
                        if (StepCount < steps.size()) {
                            recipe_name_body.append(StepCount).append(". ").append(step.text()).append("\n").append("\n");
                            StepCount++;
                        }
                    }

                    //Recipe images

                    //Feature style article
                    /*img.rec-photo
                    attr("src")

                    ul.photo-strip__items
                    for each img,
                    if img class="imageBlur", exclude otherwise
                        add to list
                     */

                    //General style article
                    /*div.inner-container js-inner-container  image-overlay
                    attr("src")

                    and then retrieve all from
                    div.image-slide a.ugc-photos-link div.component.lazy-image.lazy-image-udf.aspect_1x1.rendered.image-loaded
                    attr("data-src")*/

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





                            saveRecipe.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(RecipeDetail.this, ToBuyList.class);
                                    Bundle extras = new Bundle();
                                    extras.putString("INGREDIENTS", String.valueOf(recipe_name_ingredients));
                                    intent.putExtras(extras);
                                    startActivity(intent);

                                }
                            });
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

