package com.example.timetoeat;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseViewHolder extends RecyclerView.ViewHolder {

    public ImageView imgUrl;
    public TextView recipeName;
    public RelativeLayout ParentLayout;


    public FirebaseViewHolder(@NonNull View itemView){
        super(itemView);

        imgUrl = itemView.findViewById(R.id.recipe_img);
        recipeName = itemView.findViewById(R.id.recipe_name);
        ParentLayout = itemView.findViewById(R.id.parent_layout);
    }


}
