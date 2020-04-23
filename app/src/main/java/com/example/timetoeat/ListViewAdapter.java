package com.example.timetoeat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends ArrayAdapter<String> {

    private List<String> Ingredients = new ArrayList<>();
    private Context context;


    public ListViewAdapter(List<String> Ingredients, Context context){

        super(context, R.layout.row,Ingredients);
        this.context = context;
        this.Ingredients = Ingredients;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View row = inflater.inflate(R.layout.row,parent,false);
        TextView IngredientName = row.findViewById(R.id.ingredient_name);
        IngredientName.setText(Ingredients.get(position));
        return row;
    }
}
