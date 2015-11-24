package examples.amorg.aut.bme.hu.listfragmentdemo;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Peter on 2014.09.30..
 */
public class MyListFragment extends ListFragment {

    public interface IListSelector {
        public void onItemSelected(int position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_note_list,
                container, false);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        List<Note> notes = new ArrayList<Note>();
        for (int i=0; i<100; i++) {
            notes.add(new Note("title"+i,
                    new Date(System.currentTimeMillis()).toString()));
        }

        NoteListAdapter noteListAdapter = new NoteListAdapter(activity,notes);

        setListAdapter(noteListAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        ((IListSelector)getActivity()).onItemSelected(position);
    }
}
