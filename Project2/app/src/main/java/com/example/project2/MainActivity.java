package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

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

    private void loadListItem(){
        Log.i(TAG, "loadListItem: called");

        List<Integer> imageUrls = Arrays.asList(R.drawable.wonderwall, R.drawable.hey_jude,
                R.drawable.learning_to_fly,R.drawable.whats_poppin,R.drawable.if_you_want_love,
                R.drawable.time,R.drawable.used_to_me,R.drawable.conversations);
        List<String> titleNames = Arrays.asList("Wonderwall","Hey Jude", "Learning to Fly", "WHATS POPPIN","If You Want Love","Time","Used to Me","Conversations");
        List<String> artistNames = Arrays.asList("Oasis","Beatles", "Pink Floyd", "Jack Harlow","NF","NF","Cameron Dallas","Juice WRLD");
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