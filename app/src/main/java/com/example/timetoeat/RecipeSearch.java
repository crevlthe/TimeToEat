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
import java.util.List;

public class RecipeSearch extends AppCompatActivity {

    private TextView RecipeTitle;
    private TextView RecipeDescription;
    private TextView SearchBar;
    private Button searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_search);
        RecipeTitle = (TextView) findViewById(R.id.text_search_title);
        //RecipeDescription = (TextView) findViewById(R.id.text_search_description);
        SearchBar = (TextView) findViewById(R.id.text_searchbar);
        searchBtn = (Button) findViewById(R.id.button_search);
        searchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
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
                final StringBuilder recipe_search_titles = new StringBuilder();

                try {
                    String url = SearchString;
                    Log.d("SearchString", SearchString);

                    final Document doc = Jsoup.connect(url).get();
                    final Elements search_items = doc.getElementsByClass("fixed-recipe-card");
                    /*final Element search_title = search_items.select("span.fixed-recipe-card__title-link");
                    This can be used extract elements which are within search_items*/

                    //Recipe name
                    for (Element search_item : search_items){
                        recipe_search_titles.append(search_item.select("span.fixed-recipe-card__title-link").text()).append("\n");
                        }

                    Log.d("Result", recipe_search_titles.toString());

                    runOnUiThread(new Runnable(){
                        @Override
                        public void run(){

                        }
                    });
                } catch (IOException e){
                    e.printStackTrace();
                        // Error
                }

            }

        }.start();


    }
}
