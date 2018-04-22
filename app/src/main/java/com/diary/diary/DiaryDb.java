package com.diary.diary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DiaryDb {
    private SQLiteDatabase db;
    private Context context;
    private BaseDBOpenHelper dbHelper;

    static final String ENTRY = "entries";
    static final String ID = "id";
    static final String DATE = "date";
    static final String TITLE = "title";
    static final String CONTENT = "content";
    static final String UPDATED = "updated_at";

    public DiaryDb(Context context) {
        this.context = context;
        dbHelper = new BaseDBOpenHelper(context);
    }

    public void close() {
        db.close();
    }

    public void open() throws SQLiteException {
        try {
            db = dbHelper.getWritableDatabase();
        } catch(SQLiteException ex) {
            db = dbHelper.getReadableDatabase();
        }
    }

    public List<Entry> selectAll(String orderColumn, String orderDir) {
        List<Entry> data = new ArrayList<>();
        Cursor cursor = db.query(ENTRY,null,null,
                null, null,null,orderColumn + " " + orderDir);
        if(cursor != null) {
            if(cursor.moveToFirst()) {
                do {
                    Entry entry = new Entry(cursor.getInt(cursor.getColumnIndex(ID)),
                            cursor.getString(cursor.getColumnIndex(DATE)),
                            cursor.getString(cursor.getColumnIndex(TITLE)),
                            cursor.getString(cursor.getColumnIndex(UPDATED)),
                            cursor.getString(cursor.getColumnIndex(CONTENT))
                    );
                    data.add(entry);
                } while(cursor.moveToNext());
            }
        }
        return data;
    }

    public void InsertEntry(Entry _entry) {
        ContentValues entry = new ContentValues();
        entry.put(TITLE, _entry.getTitle());
        entry.put(DATE, _entry.getDate());
        entry.put(CONTENT, _entry.getContent());
        entry.put(UPDATED, _entry.getUpdatedAt());
        db.insert(ENTRY, null, entry);
    }

    public void UpdateEntry(Entry _entry) {
        ContentValues entry = new ContentValues();
        entry.put(TITLE, _entry.getTitle());
        entry.put(DATE, _entry.getDate());
        entry.put(CONTENT, _entry.getContent());
        entry.put(UPDATED, _entry.getUpdatedAt());
        db.update(ENTRY, entry, "id=" + _entry.getId(), null);
    }

    public void DeleteEntry(long id) {
        db.delete(ENTRY, "id="+id, null);
    }

    static class BaseDBOpenHelper extends SQLiteOpenHelper {

        public BaseDBOpenHelper(Context context) {
            super(context, "entries.db", null, 1);
        }
        private static final String CREATE_ENTRY = "create table " + ENTRY +
                " (" + ID + " integer primary key autoincrement, " + DATE + " timestamp not null, " +
                TITLE + " text not null, " + CONTENT + " text not null, " +
                UPDATED + " timestamp not null);";

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_ENTRY);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + ENTRY);
            onCreate(db);
        }
    }
}