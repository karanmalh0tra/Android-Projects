package com.example.project2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private static final String TAG = "MyAdapter";

    private ArrayList<Integer> mImages;
    private ArrayList<String> mTitleNames;
    private ArrayList<String> mArtistNames;
    private Context mContext;

    public MyAdapter(ArrayList<Integer> mImages, ArrayList<String> mTitleNames, ArrayList<String> mArtistNames, Context mContext) {
        this.mImages = mImages;
        this.mTitleNames = mTitleNames;
        this.mArtistNames = mArtistNames;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View listView = inflater.inflate(R.layout.song_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: called.");

        holder.image.setImageResource(mImages.get(position));
        holder.titleName.setText(mTitleNames.get(position));
        holder.artistName.setText(mArtistNames.get(position));
    }

    @Override
    public int getItemCount() {
        return mTitleNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView titleName;
        public TextView artistName;
        public ImageView image;
        public RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleName = (TextView) itemView.findViewById(R.id.layout_title);
            artistName = (TextView) itemView.findViewById(R.id.layout_artist);
            image = (ImageView) itemView.findViewById(R.id.layout_image);
            parentLayout = itemView.findViewById(R.id.parent_layout);


        }
    }
}
