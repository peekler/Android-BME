package hu.rik.android.dialogfragmentdemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import java.util.ResourceBundle;

/**
 * Created by Peter on 2015.01.15..
 */
public class MessageFragment extends DialogFragment {

    public static final String TAG = "DialogFragmentMessage";
    public static final String KEY_MSG = "KEY_MSG";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = getArguments().getString(KEY_MSG);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());
        alertDialogBuilder.setTitle(R.string.app_name);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // ha felül akarjuk irni, hogy alapba ne zárja be a dialógust, akkor
                        // ezt a függvényt hagyjuk üresen
                        // és az onStart()-ba keressük meg újra a gombot és ott
                        // állitsuk be ujra az eseménykezelőt
                    }
                });

        return alertDialogBuilder.create();
    }

}
