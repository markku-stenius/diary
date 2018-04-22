package com.diary.diary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DiaryView extends SuperActivity {
    private TextView title;
    private TextView content;
    private String date;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton edit = findViewById(R.id.edit);

        title = findViewById(R.id.view_title);
        content = findViewById(R.id.view_content);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent = new Intent(Intent.ACTION_INSERT_OR_EDIT);
                editIntent.putExtra("id", id);
                editIntent.putExtra("title", title.getText());
                editIntent.putExtra("content", content.getText());
                editIntent.putExtra("date", date);
                startActivityForResult(editIntent, 101);
            }
        });
        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            this.id = extras.getInt("id");
            this.date = extras.getString("date");
            this.title.setText(extras.getString("title"));
            this.content.setText(extras.getString("content"));
            getSupportActionBar().setTitle(this.date);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                Entry updateEntry = new Entry(
                        extras.getInt("id"),
                        this.date,
                        extras.getString("title"),
                        dateFormat.format(date),
                        extras.getString("content")
                );
                db.UpdateEntry(updateEntry);
                title.setText(extras.getString("title"));
                content.setText(extras.getString("content"));
                Snackbar.make(findViewById(R.id.diary_view), R.string.success_edited,
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            case R.id.action_delete:
                db.DeleteEntry(this.id);
                finish();
                return true;
        }

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_diary, menu);
        return true;
    }

}
