package examples.amorg.aut.bme.hu.listfragmentdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Peter on 2014.09.30..
 */
public class NoteListAdapter extends BaseAdapter {
    private List<Note> notes;
    private Context context;

    public NoteListAdapter(Context context, List notes) {
        this.context = context;
        this.notes = notes;
    }

    static class ViewHolder {
        TextView txtTitle;
        TextView txtDate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null) {
            LayoutInflater inflater =
                    LayoutInflater.from(context);
            v = inflater.inflate(R.layout.layout_list_item, null);
            ViewHolder holder = new ViewHolder();
            holder.txtTitle = (TextView)v.findViewById(
                    R.id.txtTitle);
            holder.txtDate = (TextView)v.findViewById(
                    R.id.txtDate);
            v.setTag(holder);
        }

        Note note = notes.get(position);
        if(note != null) {
            ViewHolder holder = (ViewHolder)v.getTag();
            holder.txtTitle.setText(note.getTitle());
            holder.txtDate.setText(note.getDate());
        }
        return v;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
