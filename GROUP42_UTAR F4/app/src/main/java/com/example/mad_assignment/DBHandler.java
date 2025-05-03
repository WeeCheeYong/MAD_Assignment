package com.example.mad_assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "flashcard.db";
    private static final int DATABASE_VERSION = 1;

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SET = "CREATE TABLE sets(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT NOT NULL, " +
                "description TEXT, " +
                "last_result TEXT)";

        String CREATE_CARD = "CREATE TABLE cards (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "setId INTEGER NOT NULL, " +
                "question TEXT NOT NULL, " +
                "answer TEXT NOT NULL, " +
                "FOREIGN KEY (setId) REFERENCES sets(id))";

        db.execSQL(CREATE_SET);
        db.execSQL(CREATE_CARD);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS sets");
        db.execSQL("DROP TABLE IF EXISTS cards");
        onCreate(db);
    }

    public void updateSet(String id, String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("description", description);
        db.update("sets", values, "id = ?", new String[]{id});
        db.close();
    }

    public void deleteSet(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("sets", "id = ?", new String[]{id});
        db.close();
    }
}
