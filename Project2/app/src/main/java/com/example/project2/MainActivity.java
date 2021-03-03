package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
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

    //initially linear layout. will change based on user selection
    private String currentLayout = "LinearLayout";

    private TypedArray mImageLinks;
    public static String[] mTitles;
    public static String[] mArtists;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate: started");

        //set the stored layout if savedInstanceState is not null.
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
        return true; //display menu in host activity now
    }

    // Process clicks on Options Menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_list:
                currentLayout = "LinearLayout";
                initMyAdapter(currentLayout);
                return true; //done processing this menu selection
            case R.id.menu_grid:
                currentLayout = "GridLayout";
                initMyAdapter(currentLayout);
                return true;
            default:
                return false;
        }
    }

    //load images, song names and artist names in the Array.
    private void loadListItem(){
        Log.i(TAG, "loadListItem: called");

        //fetch song names, artist names and image files from arrays.xml
        mTitles = getResources().getStringArray(R.array.Titles);
        mArtists = getResources().getStringArray(R.array.Artists);
        mImageLinks = getResources().obtainTypedArray(R.array.Images);
    }

    //initialize the adapter, compare whether the layout to be set is grid or list.
    private void initMyAdapter(String currentLayout){
        Log.i(TAG, "initMyAdapter: called");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        if (currentLayout.equals("GridLayout")){
            Log.i(TAG, "initMyAdapter: inside initMyAdapter GridLayout");
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        else if(currentLayout.equals("LinearLayout")){
            Log.i(TAG, "initMyAdapter: inside initMyAdapter LinearLayout");
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        MyAdapter adapter = new MyAdapter(mImageLinks, mTitles, mArtists, this,recyclerView.getLayoutManager());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    //to save the layout instance during configuration change.
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putString("currentLayout",currentLayout);
        super.onSaveInstanceState(savedInstanceState);
    }
}