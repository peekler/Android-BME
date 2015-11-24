package hu.ait.android.peter.fragmentexample;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showFragmentByTag(FragmentOne.TAG);
    }

    private void showFragmentByTag(String tag) {
        Fragment fragment = getFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            if (FragmentOne.TAG.equals(tag)) {
                fragment = new FragmentOne();
            } else if (FragmentTwo.TAG.equals(tag)) {
                fragment = new FragmentTwo();
            } else if (FragmentThree.TAG.equals(tag)) {
                fragment = new FragmentThree();
            }
        }

        if (fragment != null) {
            FragmentTransaction ft = getFragmentManager()
                    .beginTransaction();
            //ft.replace(R.id.layoutContainer, fragment, tag);
            ft.add(R.id.layoutContainer, fragment, tag);
            // 3. tag paraméter, replace vs add
            ft.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
            ft.addToBackStack(null);
            ft.commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 1){ // ActionBarActivity-nél kell
            getFragmentManager().popBackStackImmediate();
            getFragmentManager().beginTransaction().commit();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_one:
                showFragmentByTag(FragmentOne.TAG);
                return true;
            case R.id.action_two:
                showFragmentByTag(FragmentTwo.TAG);
                return true;
            case R.id.action_three:
                showFragmentByTag(FragmentThree.TAG);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
