package com.example.timetoeat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.ArrayList;

public class RecipeSearch extends AppCompatActivity {

    private TextView RecipeTitle;
    private TextView RecipeDescription;
    private TextView SearchBar;
    private Button searchBtn;

    private ArrayList<String> mRecipeItems = new ArrayList<>();
    private ArrayList<String> mRecipeURLs = new ArrayList<>();
    private ArrayList<String> mRecipeImageURLs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_search);
        //RecipeTitle = (TextView) findViewById(R.id.text_search_title);
        //RecipeDescription = (TextView) findViewById(R.id.text_search_description);
        SearchBar = (TextView) findViewById(R.id.text_searchbar);
        searchBtn = (Button) findViewById(R.id.button_search);
        searchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //used to clear the ArrayLists if a new search is initiated (without proceeding
                //to another activity)
                mRecipeItems.clear();
                mRecipeURLs.clear();
                mRecipeImageURLs.clear();
                getSearchResults();
            }
        });


    }

    private void getSearchResults() {
        Document doc = null;
        final String SearchString = "https://www.allrecipes.com/search/results/?wt=" +
                SearchBar.getText().toString().replaceAll(" ","%20") +
                "&sort=re";

        /*https://www.allrecipes.com/search/results/?wt=chicken%20recipe%20hello123&sort=re
        Sample link, sorted by best match*/

        new Thread(){
            @Override
            public void run(){
                try {
                    String url = SearchString;
                    Log.d("SearchString", SearchString);

                    final Document doc = Jsoup.connect(url).get();
                    final Elements search_items = doc.getElementsByClass("fixed-recipe-card");
                    /*final Element search_title = search_items.select("span.fixed-recipe-card__title-link");
                    This can be used extract elements which are within search_items*/

                    //Recipe name
                    for (Element search_item : search_items){
                        mRecipeItems.add(search_item.select("span.fixed-recipe-card__title-link").text());
                        mRecipeURLs.add(search_item.select("a").first().attr("href"));
                        mRecipeImageURLs.add(search_item.select("img.fixed-recipe-card__img").attr("data-original-src"));

                        //Log.d("CapturedURL",search_item.select("a").first().attr("href"));
                        //Log.d("RecipeImageURL",search_item.select("img.fixed-recipe-card__img").attr("data-original-src"));
                        //Log.d("RecipeImages", mRecipeImageURLs.toString());
                        }

                    runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            initRecyclerView();
                        }
                    });
                } catch (IOException e){
                    e.printStackTrace();
                        // Error
                }

            }

        }.start();

    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        //RecyclerViewAdapter adapter = new RecyclerViewAdapter (this, mRecipeItems, mRecipeImages);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter (this, mRecipeItems, mRecipeURLs, mRecipeImageURLs);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
