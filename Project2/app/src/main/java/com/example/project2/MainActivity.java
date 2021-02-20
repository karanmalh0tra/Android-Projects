package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
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

    private ArrayList<Integer> mImageLinks = new ArrayList<>();
    private ArrayList<String> mTitles = new ArrayList<>();
    private ArrayList<String> mArtists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate: started");

        loadListItem();
    }

    // Create Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }
    // Process clicks on Options Menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_list:
                Toast.makeText(getApplicationContext(), "you've been helped",
                        Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_grid:
                Toast.makeText(getApplicationContext(), "you've been helped more",
                        Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

    private void loadListItem(){
        Log.i(TAG, "loadListItem: called");

        List<Integer> imageUrls = Arrays.asList(R.drawable.wonderwall, R.drawable.hey_jude,
                R.drawable.learning_to_fly,R.drawable.whats_poppin,
                R.drawable.time,R.drawable.used_to_me,R.drawable.conversations,R.drawable.lose_yourself);
        List<String> titleNames = Arrays.asList("Wonderwall","Hey Jude", "Learning to Fly",
                "WHATS POPPIN","Time","Conversations","Lose Yourself");
        List<String> artistNames = Arrays.asList("Oasis","Beatles", "Pink Floyd",
                "Jack Harlow","NF","Juice WRLD","Eminem");
        mTitles.addAll(titleNames);
        mArtists.addAll(artistNames);
        mImageLinks.addAll(imageUrls);

        initMyAdapter();
    }

    private void initMyAdapter(){
        Log.i(TAG, "initMyAdapter: called");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        MyAdapter adapter = new MyAdapter(mImageLinks, mTitles, mArtists, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}