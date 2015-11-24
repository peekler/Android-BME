package demo.android.ait.hu.recyclerviewdemo.data;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import demo.android.ait.hu.recyclerviewdemo.R;

/**
 * Created by Peter on 2014.11.11..
 */
public class TodoRecyclerAdapter  extends RecyclerView.Adapter<TodoRecyclerAdapter.ViewHolder> {

    private final List<Todo> todos =  new ArrayList<Todo>();

    public TodoRecyclerAdapter() {
        for (int j = 0; j < 20; j++) {
            todos.add(new Todo("Todo "+j));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parentViewGroup, int i) {
        View rowView = LayoutInflater.from(parentViewGroup.getContext())
                .inflate(R.layout.row_todo, parentViewGroup, false);

        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        final Todo todo = todos.get(position);
        viewHolder.tvTodo.setText(todo.getTodo());

        viewHolder.itemView.setTag(todo);
    }


    @Override
    public int getItemCount() {
        return todos.size();
    }

    public void removeData (int position) {
        todos.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(int positionToAdd) {
        todos.add(positionToAdd, new Todo("New todo "+System.currentTimeMillis()));
        notifyItemInserted(positionToAdd);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTodo;

        public ViewHolder(View itemView) {
            super(itemView);

            tvTodo = (TextView) itemView.findViewById(
                    R.id.tvTodo);
        }
    }

}
