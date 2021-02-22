package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private String currentLayout = "LinearLayout";
    private ArrayList<Integer> mImageLinks = new ArrayList<>();
    private ArrayList<String> mTitles = new ArrayList<>();
    private ArrayList<String> mArtists = new ArrayList<>();

//    private enum LayoutManagerType {
//        GRID_LAYOUT_MANAGER,
//        LINEAR_LAYOUT_MANAGER
//    }

//    private LayoutManagerType mCurrentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate: started");

        if (savedInstanceState != null){
            Log.i(TAG, "onCreate: savedInstanceState is not null");
            currentLayout = savedInstanceState.getString("currentLayout");
        }

        loadListItem();
        initMyAdapter(currentLayout);
    }

    // Create Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG, "onCreateOptionsMenu: before inflating");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }
    // Process clicks on Options Menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_list:

                currentLayout = "LinearLayout";
                initMyAdapter(currentLayout);
                return true;
            case R.id.menu_grid:
                currentLayout = "GridLayout";
                initMyAdapter(currentLayout);
                return true;
            default:
                return false;
        }
    }

    private void loadListItem(){
        Log.i(TAG, "loadListItem: called");

        List<Integer> imageUrls = Arrays.asList(R.drawable.wonderwall, R.drawable.hey_jude,
                R.drawable.learning_to_fly,R.drawable.whats_poppin,
                R.drawable.time,R.drawable.conversations, R.drawable.lose_yourself,
                R.drawable.bohemian_rhapsody,R.drawable.heroes);
        List<String> titleNames = Arrays.asList("Wonderwall","Hey Jude", "Learning to Fly",
                "WHATS POPPIN","Time","Conversations","Lose Yourself","Bohemian Rhapsody","Heroes");
        List<String> artistNames = Arrays.asList("Oasis","Beatles", "Pink Floyd",
                "Jack Harlow","NF","Juice WRLD","Eminem","Queen","David Bowie");
        mTitles.addAll(titleNames);
        mArtists.addAll(artistNames);
        mImageLinks.addAll(imageUrls);
    }

    private void initMyAdapter(String currentLayout){
        Log.i(TAG, "initMyAdapter: called");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        MyAdapter adapter = new MyAdapter(mImageLinks, mTitles, mArtists, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        if (currentLayout.equals("GridLayout")){
            Log.i(TAG, "initMyAdapter: inside initMyAdapter GridLayout");
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        else if(currentLayout.equals("LinearLayout")){
            Log.i(TAG, "initMyAdapter: inside initMyAdapter LinearLayout");
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
//        savedInstanceState.putSerializable(CURRENT_LAYOUT, currentLayout);
        savedInstanceState.putString("currentLayout",currentLayout);
        super.onSaveInstanceState(savedInstanceState);
    }
}