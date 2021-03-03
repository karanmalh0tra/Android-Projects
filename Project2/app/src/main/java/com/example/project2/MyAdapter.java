package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private static final String TAG = "MyAdapter";
    public static String[] mSongUrls;
    public static String[] mSongWikiUrls;
    public static String[] mArtistWikiUrls;

    private final TypedArray mImages;
    private final String[] mTitleNames;
    private final String[] mArtistNames;
    private final Context mContext;
    private final RecyclerView.LayoutManager mLayoutManager;
    private RVClickListener RVlistener;

    public MyAdapter(TypedArray mImages, String[] mTitleNames, String[] mArtistNames, Context mContext,RecyclerView.LayoutManager layoutManager) {
        this.mImages = mImages;
        this.mTitleNames = mTitleNames;
        this.mArtistNames = mArtistNames;
        this.mContext = mContext;
        this.mLayoutManager = layoutManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //inflating song_item layout file. checking for GridLayout instance and altering sizes
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View listView = inflater.inflate(R.layout.song_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listView, RVlistener);
        if (mLayoutManager instanceof GridLayoutManager){
            Log.i(TAG, "onCreateViewHolder: layoutmanager is "+mLayoutManager);

            //to alter the padding for grid layout.
            float paddingDp = 8;
            float density = context.getResources().getDisplayMetrics().density;
            int paddingPixel = (int)(paddingDp * density);
            listView.setPadding(paddingPixel,2*paddingPixel,0,3*paddingPixel);
            ImageView image = (ImageView) listView.findViewById(R.id.layout_image);
            image.getLayoutParams().height = 200;
            image.getLayoutParams().width = 200;
            image.requestLayout();

            //to lower the song title text size for gridlayout
            TextView title = (TextView) listView.findViewById(R.id.layout_title);
            title.setTextSize(16);
            title.requestLayout();

            //to lower the artist name text size for gridlayout
            TextView author = (TextView) listView.findViewById(R.id.layout_artist);
            author.setTextSize(12);
            author.requestLayout();

        }

        // Retrieve links from arrays.xml file
        mSongUrls = context.getResources().getStringArray(R.array.songURLs);
        mSongWikiUrls = context.getResources().getStringArray(R.array.songWikiURLs);
        mArtistWikiUrls = context.getResources().getStringArray(R.array.ArtistWikiURLs);

        // listener to play the song.
        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: clicked on "+mTitleNames[viewHolder.getAdapterPosition()]);
                Uri uri = Uri.parse(mSongUrls[viewHolder.getAdapterPosition()]); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });

        return viewHolder;
    }


    //pass data to the viewholder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: called.");
        holder.image.setImageResource(mImages.getResourceId(position,0));
        holder.titleName.setText(mTitleNames[position]);
        holder.artistName.setText(mArtistNames[position]);

    }

    //return length of songs
    @Override
    public int getItemCount() {
        return mTitleNames.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public TextView titleName;
        public TextView artistName;
        public ImageView image;
        public RelativeLayout parentLayout;
        public View itemView;

        private RVClickListener listener;


        public ViewHolder(@NonNull View itemView, RVClickListener passedListener) {
            super(itemView);
            titleName = (TextView) itemView.findViewById(R.id.layout_title);
            artistName = (TextView) itemView.findViewById(R.id.layout_artist);
            image = (ImageView) itemView.findViewById(R.id.layout_image);
            parentLayout = itemView.findViewById(R.id.parent_layout);

            this.itemView = itemView;
            itemView.setOnCreateContextMenuListener(this);
            this.listener = passedListener;
        }


        //set up context menu
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            menu.setHeaderTitle("Song Menu");
            menu.add(0,v.getId(),0,"Read more about "+mTitleNames[getAdapterPosition()]).setOnMenuItemClickListener(onMenu);
            menu.add(1,v.getId(),1,"Read more about "+mArtistNames[getAdapterPosition()]).setOnMenuItemClickListener(onMenu);
            menu.add(2,v.getId(),2,"Hear "+mTitleNames[getAdapterPosition()]).setOnMenuItemClickListener(onMenu);

        }

        //listener from menu items.
        private final MenuItem.OnMenuItemClickListener onMenu = new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item){
                Log.i(TAG, "onMenuItemClick: called "+item.getGroupId());
                switch (item.getGroupId()){
                    case 0:
                        Log.i(TAG, "onMenuItemClick: "+item.getGroupId());
                        startMenuActivity(mSongWikiUrls,getAdapterPosition());
                        break;
                    case 1:
                        Log.i(TAG, "onMenuItemClick: "+item.getGroupId());
                        startMenuActivity(mArtistWikiUrls,getAdapterPosition());
                        break;
                    case 2:
                        Log.i(TAG, "onMenuItemClick: "+item.getGroupId());
                        startMenuActivity(mSongUrls,getAdapterPosition());
                        break;
                }
                return true;
            }
        };

        //starts an activity in browser/youtube based on selection from menu item click listener
        public void startMenuActivity(String[] links,int position){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(links[position]));
            Intent chooser = Intent.createChooser(intent, "Select a browser!");
            mContext.startActivity(chooser);
        }
    }
}
