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
    public List<String> songUrls = Arrays.asList(
            "https://www.youtube.com/watch?v=bx1Bh8ZvH84",
            "https://www.youtube.com/watch?v=A_MjCqQoLLA",
            "https://www.youtube.com/watch?v=nVhNCTH8pDs",
            "https://www.youtube.com/watch?v=HIwAI05Y1fU",
            "https://www.youtube.com/watch?v=E1ZVSFfCk9g",
            "https://www.youtube.com/watch?v=1VSZtyenNlA",
            "https://www.youtube.com/watch?v=_Yhyp-_hX2s",
            "https://www.youtube.com/watch?v=fJ9rUzIMcZQ",
            "https://www.youtube.com/watch?v=lXgkuM2NhYI",
            "https://www.youtube.com/watch?v=83xBPCw5hh4",
            "https://www.youtube.com/watch?v=yKNxeF4KMsY",
            "https://www.youtube.com/watch?v=MzCLLHscMOw",
            "https://www.youtube.com/watch?v=uGcsIdGOuZY",
            "https://www.youtube.com/watch?v=MU8B4XDI3Uw",
            "https://www.youtube.com/watch?v=09R8_2nJtjg");
    public List<String> songWikipediaUrls = Arrays.asList(
            "https://en.wikipedia.org/wiki/Wonderwall_(song)",
            "https://en.wikipedia.org/wiki/Hey_Jude",
            "https://en.wikipedia.org/wiki/Learning_to_Fly_(Pink_Floyd_song)",
            "https://en.wikipedia.org/wiki/Whats_Poppin",
            "https://en.wikipedia.org/wiki/Time_(NF_song)",
            "https://en.wikipedia.org/wiki/Conversations_(song)",
            "https://en.wikipedia.org/wiki/Lose_Yourself",
            "https://en.wikipedia.org/wiki/Bohemian_Rhapsody",
            "https://en.wikipedia.org/wiki/%22Heroes%22_(David_Bowie_song)",
            "https://en.wikipedia.org/wiki/Rockstar_(DaBaby_song)",
            "https://en.wikipedia.org/wiki/Yellow_(Coldplay_song)",
            "https://en.wikipedia.org/wiki/Breakeven_(song)",
            "https://en.wikipedia.org/wiki/Hero_(Skillet_song)",
            "https://en.wikipedia.org/wiki/Love_Somebody_(Maroon_5_song)",
            "https://en.wikipedia.org/wiki/Sugar_(Maroon_5_song)");
    public List<String> artistWikipediaUrls = Arrays.asList(
            "https://en.wikipedia.org/wiki/Oasis_(band)",
            "https://en.wikipedia.org/wiki/The_Beatles",
            "https://en.wikipedia.org/wiki/Pink_Floyd",
            "https://en.wikipedia.org/wiki/Jack_Harlow",
            "https://en.wikipedia.org/wiki/NF_(rapper)",
            "https://en.wikipedia.org/wiki/Juice_Wrld",
            "https://en.wikipedia.org/wiki/Eminem",
            "https://en.wikipedia.org/wiki/Queen_(band)",
            "https://en.wikipedia.org/wiki/David_Bowie",
            "https://en.wikipedia.org/wiki/DaBaby",
            "https://en.wikipedia.org/wiki/Coldplay",
            "https://en.wikipedia.org/wiki/The_Script",
            "https://en.wikipedia.org/wiki/Skillet_(band)",
            "https://en.wikipedia.org/wiki/Maroon_5",
            "https://en.wikipedia.org/wiki/Maroon_5");

    public ArrayList<String> mSongUrls = new ArrayList<>();
    public ArrayList<String> mSongWikiUrls = new ArrayList<>();
    public ArrayList<String> mArtistWikiUrls = new ArrayList<>();
    private ArrayList<Integer> mImages;
    private ArrayList<String> mTitleNames;
    private ArrayList<String> mArtistNames;
    private Context mContext;
    private RecyclerView.LayoutManager mLayoutManager;
    private RVClickListener RVlistener;

    public MyAdapter(ArrayList<Integer> mImages, ArrayList<String> mTitleNames, ArrayList<String> mArtistNames, Context mContext,RecyclerView.LayoutManager layoutManager) {
        this.mImages = mImages;
        this.mTitleNames = mTitleNames;
        this.mArtistNames = mArtistNames;
        this.mContext = mContext;
        this.mLayoutManager = layoutManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View listView = inflater.inflate(R.layout.song_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listView, RVlistener);
        if (mLayoutManager instanceof GridLayoutManager){
            Log.i(TAG, "onCreateViewHolder: layoutmanager is "+mLayoutManager);

            //to alter the padding for grid layout.
            float paddingDp = 10;
//            int imageDp = 70;
            float density = context.getResources().getDisplayMetrics().density;
            int paddingPixel = (int)(paddingDp * density);
            listView.setPadding(paddingPixel,2*paddingPixel,0,3*paddingPixel);
            ImageView image = (ImageView) listView.findViewById(R.id.layout_image);
            image.getLayoutParams().height = 180;
            image.getLayoutParams().width = 180;
            image.requestLayout();

            //to lower the song title textsize for gridlayout
            TextView title = (TextView) listView.findViewById(R.id.layout_title);
            title.setTextSize(18);
            title.requestLayout();

            //to lower the artist name textsize for gridlayout
            TextView author = (TextView) listView.findViewById(R.id.layout_artist);
            author.setTextSize(14);
            author.requestLayout();

        }
        mSongUrls.addAll(songUrls);
        mSongWikiUrls.addAll(songWikipediaUrls);
        mArtistWikiUrls.addAll(artistWikipediaUrls);
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

        //listener from menu items.
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

        //starts an activity in browser/youtube based on selection from menu item click listener
        public void startMenuActivity(ArrayList<String> links,int position){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(links.get(position)));
            Intent chooser = Intent.createChooser(intent, "Open in browser");
            mContext.startActivity(chooser);
        }
    }
}
