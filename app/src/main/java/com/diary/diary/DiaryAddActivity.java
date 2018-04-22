package com.diary.diary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class DiaryAddActivity extends AppCompatActivity {
    private EditText title;
    private EditText content;
    private String edit_flag = null;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_add);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        title = findViewById(R.id.form_title);
        content = findViewById(R.id.form_content);
        FloatingActionButton send = findViewById(R.id.add_new);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            try {
                if (title.getText().toString().length() == 0) {
                    Snackbar.make(view, R.string.failed_title,
                            Snackbar.LENGTH_SHORT)
                            .show();
                } else if(content.getText().toString().length() == 0) {
                    Snackbar.make(view, R.string.failed_content,
                            Snackbar.LENGTH_SHORT)
                            .show();
                } else {
                    Intent sendIntent = new Intent();
                    sendIntent.putExtra("edit_flag", edit_flag);
                    sendIntent.putExtra("id", id);
                    sendIntent.putExtra("title", title.getText().toString());
                    sendIntent.putExtra("content", content.getText().toString());
                    setResult(RESULT_OK, sendIntent);
                    finish();
                }
            } catch (Exception e) {
                Snackbar.make(view, R.string.failed_general,
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
            }
        });
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            this.edit_flag = "edit";
            this.id = extras.getInt("id");
            this.title.setText(extras.getString("title"));
            this.content.setText(extras.getString("content"));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
