package com.example.timetoeat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
        final TextView IngredientName = row.findViewById(R.id.ingredient_name);
        IngredientName.setText(Ingredients.get(position));

        CheckBox checkBox = row.findViewById(R.id.checkBox);
        checkBox.setTag(position);
        if(ToBuyList.isActionMode){
            checkBox.setVisibility((View.VISIBLE));
        }
        else{
            checkBox.setVisibility(View.GONE);
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                int position = (int) buttonView.getTag();

                if(ToBuyList.UserSelection.contains(Ingredients.get(position))){
                    ToBuyList.UserSelection.remove(Ingredients.get(position));
                } else{
                    ToBuyList.UserSelection.add(Ingredients.get(position));
                }

                ToBuyList.actionMode.setTitle(ToBuyList.UserSelection.size() + "  items selected...");

            }
        });
        return row;
    }

    public void removeItems(List<String> items){
        for(String item: items){
            Ingredients.remove(item);
        }
        notifyDataSetChanged();
    }

}
