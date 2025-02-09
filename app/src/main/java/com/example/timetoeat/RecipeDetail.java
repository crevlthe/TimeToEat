package com.example.timetoeat;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RecipeDetail extends AppCompatActivity {
    private TextView RecipeTitle;
    private TextView RecipeIngredients;
    private TextView RecipeBody;
    private ImageView RecipeImage;
    private Button getBtn;
    private String RecipeURL;
    private String RecipeImageURL;
    private Button saveRecipe;
    private TextView t;

    private DatabaseReference RootRef;

    final String myCurrentDateTime = DateFormat.getDateTimeInstance()
            .format(Calendar.getInstance().getTime());


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
        RecipeImage = (ImageView) findViewById(R.id.image_recipe);
        saveRecipe = (Button) findViewById(R.id.button_saverecipe);

        t = (TextView) findViewById(R.id.text_recipe_ingredients);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/Playball-Regular.ttf");
        t.setTypeface(myCustomFont);

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
                    //https://www.allrecipes.com/recipe/274419/creamy-chicken-rice-soup/

                    final Elements recipe_name_2 = doc.select("h1.headline");
                    //General style article
                    //https://www.allrecipes.com/recipe/223042/chicken-parmesan/

                    //Set filtering based on Feature style article or General style article template
                    //if "span.recipe-directions__list--item" exists, then it is a Feature style article
                    Elements steps = doc.select("span.recipe-directions__list--item");
                    if (steps.hasText() == false) {
                        steps = doc.select(".section-body");
                        RecipeImageURL = doc.select("div.lead-content-wrapper.two-col-style > aside.primary-media-section.primary-media-with-filmstrip > div.image-container > div.component.lazy-image").attr("data-src");
                    } else RecipeImageURL = doc.select("img.rec-photo").attr("src");

                    //Recipe images
                    //Feature style article
                    /*img.rec-photo
                    attr("src")

                    a class="video-play"
                    https://www.allrecipes.com/recipe/258494/chinese-barbeque-pork-char-siu/?internalSource=hub%20recipe&referringContentType=Search&clickId=cardslot%201

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

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (recipe_name_1.hasText() == false) {
                                //use Glide to load image
                                Glide.with(RecipeImage.getContext())
                                        .asBitmap()
                                        .load(RecipeImageURL)
                                        //.override(Target.SIZE_ORIGINAL, 350)
                                        .fitCenter()
                                        .into(RecipeImage);

                                RecipeTitle.setText(recipe_name_2.first().text());
                                sendToFirebaseTitle2(recipe_name_2, recipe_name_ingredients, recipe_name_body, RecipeImageURL);


                            } else {
                                RecipeTitle.setText(recipe_name_1.first().text());
                                sendToFirebaseTitle1(recipe_name_1, recipe_name_ingredients, recipe_name_body, RecipeImageURL);
                            };

                            RecipeIngredients.setText(recipe_name_ingredients.toString());
                            RecipeBody.setText(recipe_name_body.toString());

                            saveRecipe.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    //send ingredients to the list
                                    Intent intent = new Intent(RecipeDetail.this, ToBuyList.class);
                                    Bundle extras = new Bundle();
                                    extras.putString("INGREDIENTS", String.valueOf(recipe_name_ingredients));
                                    intent.putExtras(extras);
                                    startActivity(intent);

                                    //We need to send data to Firebase depending on title_1 or title_2, therefore we create to identical methods



                                }

                            });



                                }

                        private void sendToFirebaseTitle1(Elements recipe_name_1,
                                                          StringBuilder recipe_name_ingredients,
                                                          StringBuilder recipe_name_body,
                                                          String recipeImageURL) {

                            Map<String, Object> savedR = new HashMap<>();
                            savedR.put("recipeName", String.valueOf(recipe_name_1));
                            savedR.put("recipeIngredients", String.valueOf(recipe_name_ingredients));
                            savedR.put("recipeDescription", String.valueOf(recipe_name_body));
                            savedR.put("imgUrl", RecipeImageURL);

                            RootRef = FirebaseDatabase.getInstance().getReference();


                            RootRef.child("SavedRecipes").child(myCurrentDateTime)
                                    .setValue(savedR)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                //startActivity(new Intent(getApplicationContext(), PersonalRecipe.class));
                                                //Toast.makeText(RecipeDetail.this, "Recipe to the Database", Toast.LENGTH_SHORT).show();
                                            } else {
                                                String message = task.getException().toString();
                                                Toast.makeText(RecipeDetail.this, "Error" + message, Toast.LENGTH_SHORT);
                                            }
                                        }
                                    });


                        }


                        private void sendToFirebaseTitle2(Elements recipe_name_2,
                                                          StringBuilder recipe_name_ingredients,
                                                          StringBuilder recipe_name_body, String recipeImageURL) {




                            //Log.i("PARSE NAME", name);


                            Map<String, Object> savedR = new HashMap<>();
                            savedR.put("recipeName", String.valueOf(recipe_name_2));
                            savedR.put("recipeIngredients", String.valueOf(recipe_name_ingredients));
                            savedR.put("recipeDescription", String.valueOf(recipe_name_body));
                            savedR.put("imgUrl", RecipeImageURL);

                            RootRef = FirebaseDatabase.getInstance().getReference();


                            RootRef.child("SavedRecipes").child(myCurrentDateTime)
                                    .setValue(savedR)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                //startActivity(new Intent(getApplicationContext(), PersonalRecipe.class));
                                                //Toast.makeText(RecipeDetail.this, "Recipe to the Database", Toast.LENGTH_SHORT).show();
                                            } else {
                                                String message = task.getException().toString();
                                                Toast.makeText(RecipeDetail.this, "Error" + message, Toast.LENGTH_SHORT);
                                            }
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

