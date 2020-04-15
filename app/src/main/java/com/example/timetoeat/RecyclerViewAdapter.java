package com.example.timetoeat;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<String> mRecipeItem = new ArrayList<>();
    private ArrayList<String> mRecipeURL = new ArrayList<>();
    //private ArrayList<String> mRecipeImage = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(Context context, ArrayList<String> RecipeItem, ArrayList<String> RecipeURL) {
        mRecipeItem = RecipeItem;
        mRecipeURL = RecipeURL;
        //mRecipeImage = RecipeImage;
        mContext = context;
    }

    //used to inflate the view
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_searchitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    //used to add items to the RecyclerView (from the respective ArrayLists)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
/*        Glide.with(mContext)
                .asBitmap()
                .load(mRecipeImage.get(position))
                .into(holder.RecipeImage);*/
        holder.RecipeItem.setText(mRecipeItem.get(position));

        holder.ParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View View) {
                //start new activity based on that passed from the search page
                Intent intent = new Intent(mContext, RecipeDetail.class);
                intent.putExtra("URL", mRecipeURL.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    //retrieves the number of items in the list prior to refreshing RecyclerView
    @Override
    public int getItemCount() {
        return mRecipeItem.size();
    }

    //outlines what will be set in the new View (within the RecyclerView)
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView RecipeItem;
        //ImageView RecipeImage;
        RelativeLayout ParentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            RecipeItem = itemView.findViewById(R.id.card_text);
            //RecipeImage = itemView.findViewById(R.id.card_image);
            ParentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}