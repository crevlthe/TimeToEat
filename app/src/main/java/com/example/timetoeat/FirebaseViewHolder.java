package com.example.timetoeat;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

public class FirebaseViewHolder extends RecyclerView.ViewHolder {

    public ImageView recImg;
    public TextView recName;

    public FirebaseViewHolder(@NonNull View itemView){
        super(itemView);

        recImg = itemView.findViewById(R.id.card_image);
        recName = itemView.findViewById(R.id.card_text);
    }
}
