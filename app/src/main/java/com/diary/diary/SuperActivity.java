package com.diary.diary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class SuperActivity extends AppCompatActivity {
    DiaryDb db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DiaryDb(this);
        db.open();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
