package demo.android.app.hu.sqlitedemo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NoteDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "notetable.db";
    private static final int DATABASE_VERSION = 1;

    public NoteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        NoteTable.onCreate(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        NoteTable.onUpgrade(database, oldVersion, newVersion);
    }
}
