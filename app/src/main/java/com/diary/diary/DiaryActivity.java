package com.diary.diary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DiaryActivity extends SuperActivity {
    List<Entry> entryList = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private EntryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        // open database connection
        entryList = db.selectAll();

        // set up view
        mRecyclerView = findViewById(R.id.entryList);
        mRecyclerView.setHasFixedSize(true);

        // then layout
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // add adapter to the recycler view
        mAdapter = new EntryAdapter(entryList);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.i("TAG", "REMOVED!");
                return false;
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton add = findViewById(R.id.add_new);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent();
                addIntent.setAction(Intent.ACTION_INSERT_OR_EDIT);
                if (addIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(addIntent, 101);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // refresh the data
        mAdapter.updateList(db.selectAll());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("EVENT", "bakc");
        if(resultCode == RESULT_OK) {
            if(data != null) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                Entry newEntry = new Entry(
                        0,
                        dateFormat.format(date),
                        data.getStringExtra("title"),
                        dateFormat.format(date),
                        data.getStringExtra("content")
                );
                db.InsertEntry(newEntry);
                Snackbar.make(findViewById(R.id.main_view), R.string.success_added,
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
        }
    }
}
