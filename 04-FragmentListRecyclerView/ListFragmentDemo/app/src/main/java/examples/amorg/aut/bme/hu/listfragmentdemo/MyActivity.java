package examples.amorg.aut.bme.hu.listfragmentdemo;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Toast;


public class MyActivity extends Activity implements MyListFragment.IListSelector {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container,
                            new MyListFragment())
                    .commit();
        }
    }

    @Override
    public void onItemSelected(int position) {
        Toast.makeText(this, "POSITION: "+position, Toast.LENGTH_SHORT).show();

    }
}
