package com.example.timetoeat;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class RecipeRecommend extends AppCompatActivity {
    public FirebaseAuth.AuthStateListener authListener;
    public FirebaseUser user;
    public String CurrentUser;
    private ArrayList<String> mCuisines = new ArrayList<>();
    private ArrayList<String> mAllergies = new ArrayList<>();
    private DatabaseReference mDatabase;

    private ArrayList<String> mRecipeItems = new ArrayList<>();
    private ArrayList<String> mRecipeURLs = new ArrayList<>();
    private ArrayList<String> mRecipeImageURLs = new ArrayList<>();

    private TextView HowAbout;
    private TextView ExclIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_recommend);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        HowAbout = (TextView) findViewById(R.id.title_howabout);
        ExclIngredients = (TextView) findViewById(R.id.title_excluding);

        authListener = new FirebaseAuth.AuthStateListener() {
            //@Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    CurrentUser = firebaseAuth.getUid();

                    //Get preferences
                    getSaved(); // this must be placed within the auth state as this has to wait until the listener has completed initialisation
                } else {
                    // User is signed out
                }
            }
        };
        FirebaseAuth.getInstance().addAuthStateListener(authListener);
    }

    private void getSaved() {
        final DatabaseReference refPreferences = mDatabase.child("preferences").child(CurrentUser);

        refPreferences.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot Snap : dataSnapshot.child("cuisines").getChildren()) {
                    String isKeyIncluded = Snap.getValue(String.class);
                    if (isKeyIncluded != null) {
                        if (Snap.getValue(String.class).equals("True")) {
                            String key = Snap.getKey();
                            mCuisines.add(key);
                        }
                    }
                }

                for (DataSnapshot Snap : dataSnapshot.child("allergies").getChildren()) {
                    String isKeyIncluded = Snap.getValue(String.class);
                    if (isKeyIncluded != null) {
                        if (Snap.getValue(String.class).equals("True")) {
                            String key = Snap.getKey();
                            mAllergies.add(key);
                        }
                    }
                }

                //Only run after all ArrayLists have been populated above
                getSearchResultsRecommended();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(RecipeRecommend.this, "Retrieval of preferences failed. Please try again.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getSearchResultsRecommended() {
        Document doc = null;

        new Thread() {
            @Override
            public void run() {
                try {
                    Random rand = new Random();
                    final int i = rand.nextInt(mCuisines.size());
                    String SearchString;
                    final StringBuilder SearchStringAllergy = new StringBuilder();
                    final int TotalAllergies = mAllergies.size();

                    Log.d("# of prefs to choose", String.valueOf(mCuisines.size()));
                    Log.d("Chosen cuisine", mCuisines.get(i));
                    Log.d("# of allergies", String.valueOf(mAllergies.size()));

                    final StringBuilder ExclIngredientsBuilder = new StringBuilder();

                    switch (TotalAllergies) {
                        case 0:
                            SearchString = "https://www.allrecipes.com/search/results/?wt=" +
                                    mCuisines.get(i) + "&sort=re";
                            break;
                        default:
                            SearchStringAllergy.append("https://www.allrecipes.com/search/results/?wt=").append(mCuisines.get(i)).append("&ingExcl=");

                            int j = 1; //Since ArrayList.size does not count from zero
                            for (String mAllergy : mAllergies) {
                                if (j < TotalAllergies) {
                                    //With comma
                                    SearchStringAllergy.append(mAllergy).append(",");

                                    //Separate StringBuilder for UI display
                                    ExclIngredientsBuilder.append(mAllergy).append(", ");
                                    j++;
                                } else {
                                    //Without comma
                                    SearchStringAllergy.append(mAllergy);
                                    ExclIngredientsBuilder.append(mAllergy);
                                }
                            }
                            SearchStringAllergy.append("&sort=re");

                            SearchString = SearchStringAllergy.toString();
                            break;
                    }

                    String url = SearchString;
                    Log.d("URL", SearchString);

                    final Document doc = Jsoup.connect(url).get();
                    final Elements search_items = doc.getElementsByClass("fixed-recipe-card");
                    /*final Element search_title = search_items.select("span.fixed-recipe-card__title-link");
                    This can be used extract elements which are within search_items*/

                    //Recipe name
                    for (Element search_item : search_items) {
                        mRecipeItems.add(search_item.select("span.fixed-recipe-card__title-link").text());
                        mRecipeURLs.add(search_item.select("a").first().attr("href"));
                        mRecipeImageURLs.add(search_item.select("img.fixed-recipe-card__img").attr("data-original-src"));

                        //Log.d("CapturedURL",search_item.select("a").first().attr("href"));
                        //Log.d("RecipeImageURL",search_item.select("img.fixed-recipe-card__img").attr("data-original-src"));
                        //Log.d("RecipeImages", mRecipeImageURLs.toString());
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String HowAboutString = "How about..." + mCuisines.get(i) + "?";
                            String ExclIngredientsString;

                            if (TotalAllergies != 0){
                                ExclIngredientsString = "Excluding: " + ExclIngredientsBuilder.toString();
                            } else
                            {
                                ExclIngredientsString = "";
                            }

                            HowAbout.setText(HowAboutString);
                            ExclIngredients.setText(ExclIngredientsString);
                            initRecyclerView();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    //Error
                }
            }
        }.start();

    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        //RecyclerViewAdapter adapter = new RecyclerViewAdapter (this, mRecipeItems, mRecipeImages);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mRecipeItems, mRecipeURLs, mRecipeImageURLs);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
