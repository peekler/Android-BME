package hu.ait.android.peter.fragmentexample;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Peter on 2015.03.03..
 */
public class FragmentTwo extends Fragment {

    public static final String TAG = "FragmentTwo";
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_two, null);
        return rootView;
    }
}
