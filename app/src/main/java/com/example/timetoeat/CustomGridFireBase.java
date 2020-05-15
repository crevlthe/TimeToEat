package com.example.timetoeat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomGridFireBase extends BaseAdapter {
    Context context;
    String[] fin;
    private final int[] ImageId;

    public CustomGridFireBase(Context context, List<String> fin, int[] imageId) {
        this.ImageId = imageId;
        this.context = context;
        this.fin = fin.toArray(new String[0]);
    }

    @Override
    public int getCount() {
        return fin.length;
    }

    @Override
    public Object getItem(int position) {
        return fin[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            grid = new View(context);
            grid = inflater.inflate(R.layout.fb_single_grid, null);
            TextView textView = (TextView) grid.findViewById(R.id.grid_text);
            ImageView imageView = (ImageView) grid.findViewById(R.id.grid_image);
            textView.setText(fin[position]);
            imageView.setImageResource(ImageId[position]);
        } else {
            grid = (View) convertView;
        }
        return grid;
    }

    /*public void removeItem(String el) {
        List<String> list = new ArrayList<>(Arrays.asList(fin));
        /*for (int i = 0; i < list.size(); i++) {
            if (el.equals(list.get(i))) {
                list.remove(el);
            }
        if (list.contains(el)){
            list.remove(el);
        }

        fin = list.toArray(new String[0]);
        notifyDataSetChanged();
    }*/
}
