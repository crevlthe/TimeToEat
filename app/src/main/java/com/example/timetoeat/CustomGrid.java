package com.example.timetoeat;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomGrid extends BaseAdapter {

    private Context mContext;
    private String[] GroceryList;
    private final int[] Imageid;

    public CustomGrid(Context c, ArrayList<String> GroceryList, int[] Imageid){
        mContext = c;
        this.Imageid = Imageid;
        this.GroceryList = GroceryList.toArray(new String[0]);
    }

    @Override
    public int getCount(){ return GroceryList.length;}

    @Override
    public Object getItem(int position){return null;}

    @Override
    public long getItemId(int position){ return 0;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null){
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_single, null);
            TextView textView = (TextView) grid.findViewById(R.id.grid_text);
            ImageView imageView = (ImageView) grid.findViewById(R.id.grid_image);
            textView.setText(GroceryList[position]);
            imageView.setImageResource(Imageid[position]);
        }else{
            grid = (View) convertView;
        }
        return grid;
    }

    public void removeItem(String el){
        List<String> list = new ArrayList<String>(Arrays.asList(GroceryList));
        for(int i=0; i<list.size();i++){
            if (el.equals(list.get(i))){
                list.remove(list.get(i));
                if(list.isEmpty()){
                    Log.i("TEEEST", "List is Empty");
                }
            }
        }
        GroceryList = list.toArray(new String[0]);
        notifyDataSetChanged();

    }

}

