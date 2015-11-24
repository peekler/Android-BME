package demo.android.app.hu.sqlitedemo.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class NoteDataSource {

    // Database fields
    private SQLiteDatabase database;
    private NoteDBHelper dbHelper;
    private String[] allColumns = { NoteTable.COLUMN_ID,
            NoteTable.COLUMN_TITLE, NoteTable.COLUMN_DESC };

    public NoteDataSource(Context context) {
        dbHelper = new NoteDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Note createNote(String aTitle, String aDesc) {
        ContentValues values = new ContentValues();
        values.put(NoteTable.COLUMN_TITLE, aTitle);
        values.put(NoteTable.COLUMN_DESC, aDesc);
        long insertId = database.insert(NoteTable.TABLE_NOTE, null,
                values);
        Cursor cursor = database.query(NoteTable.TABLE_NOTE,
                allColumns, NoteTable.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Note newNote = cursorToNote(cursor);
        cursor.close();
        return newNote;
    }

    private Note cursorToNote(Cursor cursor) {
        Note note = new Note();
        note.setId(cursor.getLong(0));
        note.setTitle(cursor.getString(1));
        note.setDesc(cursor.getString(2));
        return note;
    }

    public void deleteNote(Note note) {
        long id = note.getId();
        database.delete(NoteTable.TABLE_NOTE, NoteTable.COLUMN_ID
                + " = " + id, null);
    }

    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<Note>();

        Cursor cursor = database.query(NoteTable.TABLE_NOTE,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Note note = cursorToNote(cursor);
            notes.add(note);
            cursor.moveToNext();
        }
        // Do not forget to close the cursor
        cursor.close();
        return notes;
    }
}

