package hu.ait.android.peter.simplefragmentdemo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import hu.ait.android.peter.simplefragmentdemo.R;

/**
 * Created by Peter on 2015.03.03..
 */
public class FragmentData extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_data, null);

        Button btn = (Button) rootView.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Fragment",
                        Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}
