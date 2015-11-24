package demo.android.app.hu.sqlitedemo.data;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class NoteTable {
    public static final String TABLE_NOTE = "note";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESC = "desc";

    private static final String DATABASE_CREATE = "create table " + TABLE_NOTE
            + "(" + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null, " + COLUMN_DESC
            + " text not null" + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(NoteTable.class.getName(), "Upgrading from version " + oldVersion
                + " to " + newVersion);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);
        onCreate(database);
    }
}

