package demo.android.ait.hu.recyclerviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import demo.android.ait.hu.recyclerviewdemo.data.TodoRecyclerAdapter;


public class MainActivity extends Activity {

    private FrameLayout deleteBar;
    private TodoRecyclerAdapter todoRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deleteBar = (FrameLayout) findViewById(R.id.deleteBar);

        todoRecyclerAdapter = new TodoRecyclerAdapter();
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        // RecyclerView layout manager
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(todoRecyclerAdapter);

        deleteBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                todoRecyclerAdapter.removeData(0);
                hideDeleteBar();
            }
        });

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {

                    if (deleteBar.getVisibility() == View.VISIBLE)
                        hideDeleteBar();

                } else {

                    if (deleteBar.getVisibility() == View.GONE)
                        showDeleteBar();
                }
            }
        });
    }

    private void showDeleteBar() {

        deleteBar.startAnimation(AnimationUtils.loadAnimation(this,
                R.anim.translate_up_on));

        deleteBar.setVisibility(View.VISIBLE);
    }

    private void hideDeleteBar() {

        deleteBar.startAnimation(AnimationUtils.loadAnimation(this,
                R.anim.translate_up_off));

        deleteBar.setVisibility(View.GONE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
