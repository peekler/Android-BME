package demo.android.app.hu.sqlitedemo;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.List;
import java.util.Random;

import demo.android.app.hu.sqlitedemo.data.Note;
import demo.android.app.hu.sqlitedemo.data.NoteDataSource;


public class NotesActivity extends ListActivity {
    private NoteDataSource datasource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        datasource = new NoteDataSource(this);
        datasource.open();

        List<Note> values = datasource.getAllNotes();

        // ArrayAdapter for displaying items easily
        ArrayAdapter<Note> adapter = new ArrayAdapter<Note>(this,
                android.R.layout.simple_selectable_list_item, values);
        setListAdapter(adapter);
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

    // XML based event handling
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Note> adapter = (ArrayAdapter<Note>) getListAdapter();
        Note note = null;
        switch (view.getId()) {
            case R.id.newnote:
                String[] notes = new String[]{"Note1", "Note2", "Note3"};
                int nextInt = new Random().nextInt(3);
                note = datasource.createNote(notes[nextInt], "desc");
                adapter.add(note);
                break;
            case R.id.delnote:
                if (getListAdapter().getCount() > 0) {
                    note = (Note) getListAdapter().getItem(0);
                    datasource.deleteNote(note);
                    adapter.remove(note);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }
}
