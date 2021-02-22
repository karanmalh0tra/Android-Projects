package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private static final String TAG = "MyAdapter";
    public List<String> songUrls = Arrays.asList(
            "https://www.youtube.com/watch?v=bx1Bh8ZvH84",
            "https://www.youtube.com/watch?v=A_MjCqQoLLA",
            "https://www.youtube.com/watch?v=nVhNCTH8pDs",
            "https://www.youtube.com/watch?v=HIwAI05Y1fU",
            "https://www.youtube.com/watch?v=E1ZVSFfCk9g",
            "https://www.youtube.com/watch?v=1VSZtyenNlA",
            "https://www.youtube.com/watch?v=_Yhyp-_hX2s",
            "https://www.youtube.com/watch?v=fJ9rUzIMcZQ",
            "https://www.youtube.com/watch?v=lXgkuM2NhYI");
    public List<String> songWikipediaUrls = Arrays.asList(
            "https://en.wikipedia.org/wiki/Wonderwall_(song)",
            "https://en.wikipedia.org/wiki/Hey_Jude",
            "https://en.wikipedia.org/wiki/Learning_to_Fly_(Pink_Floyd_song)",
            "https://en.wikipedia.org/wiki/Whats_Poppin",
            "https://en.wikipedia.org/wiki/Time_(NF_song)",
            "https://en.wikipedia.org/wiki/Conversations_(song)",
            "https://en.wikipedia.org/wiki/Lose_Yourself",
            "https://en.wikipedia.org/wiki/Bohemian_Rhapsody",
            "https://en.wikipedia.org/wiki/%22Heroes%22_(David_Bowie_song)");
    public List<String> artistWikipediaUrls = Arrays.asList(
            "https://en.wikipedia.org/wiki/Oasis_(band)",
            "https://en.wikipedia.org/wiki/The_Beatles",
            "https://en.wikipedia.org/wiki/Pink_Floyd",
            "https://en.wikipedia.org/wiki/Jack_Harlow",
            "https://en.wikipedia.org/wiki/NF_(rapper)",
            "https://en.wikipedia.org/wiki/Juice_Wrld",
            "https://en.wikipedia.org/wiki/Eminem",
            "https://en.wikipedia.org/wiki/Queen_(band)",
            "https://en.wikipedia.org/wiki/David_Bowie");

    public ArrayList<String> mSongUrls = new ArrayList<>();
    public ArrayList<String> mSongWikiUrls = new ArrayList<>();
    public ArrayList<String> mArtistWikiUrls = new ArrayList<>();
    private ArrayList<Integer> mImages;
    private ArrayList<String> mTitleNames;
    private ArrayList<String> mArtistNames;
    private Context mContext;
    private RVClickListener RVlistener;

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
        ViewHolder viewHolder = new ViewHolder(listView, RVlistener);
        mSongUrls.addAll(songUrls); //validate
        mSongWikiUrls.addAll(songWikipediaUrls); //validate
        mArtistWikiUrls.addAll(artistWikipediaUrls); //validate
        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: clicked on "+mTitleNames.get(viewHolder.getAdapterPosition()));
                Uri uri = Uri.parse(mSongUrls.get(viewHolder.getAdapterPosition())); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });

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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public TextView titleName;
        public TextView artistName;
        public ImageView image;
        public RelativeLayout parentLayout; //not sure why yet
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


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            //inflate menu from xml
            MenuInflater inflater = new MenuInflater(v.getContext());
            inflater.inflate(R.menu.context_menu, menu);
            menu.setHeaderTitle("Song Menu");
            menu.getItem(0).setOnMenuItemClickListener(onMenu);
            menu.getItem(1).setOnMenuItemClickListener(onMenu);
            menu.getItem(2).setOnMenuItemClickListener(onMenu);

        }

        private final MenuItem.OnMenuItemClickListener onMenu = new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item){
                Log.i(TAG, "onMenuItemClick: called "+item);
                switch (item.getItemId()){
                    case R.id.menu1:
                        Log.i(TAG, "onMenuItemClick: "+R.id.menu1);
                        startMenuActivity(mSongWikiUrls,getAdapterPosition());
                        break;
                    case R.id.menu2:
                        Log.i(TAG, "onMenuItemClick: "+R.id.menu1);
                        startMenuActivity(mArtistWikiUrls,getAdapterPosition());
                        break;
                    case R.id.menu3:
                        Log.i(TAG, "onMenuItemClick: "+R.id.menu1);
                        startMenuActivity(mSongUrls,getAdapterPosition());
                        break;
                }
                return true;
            }
        };

        public void startMenuActivity(ArrayList<String> links,int position){
            Uri uri = Uri.parse(links.get(position));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            mContext.startActivity(intent);
        }

    }
}
