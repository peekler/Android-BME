package demo.android.app.hu.dialogdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPopUp:
                showPopUp(v);
                //Toast toast = Toast.makeText(this, "hello", Toast.LENGTH_LONG);
                //toast.setView(R.layout.popuptest);
                break;
            case R.id.btnAlertDialog:
                showAlertDialog("Ez az üzenet!");
                break;
        }
    }

    private void showPopUp(View v) {
        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.popuptest, null);
        final PopupWindow popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        Button button = (Button) popupView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        popupWindow.showAsDropDown(v,50,50);
    }

    private void showAlertDialog(String msg) {
        AlertDialog.Builder alertbox =
                new AlertDialog.Builder(this);
        alertbox.setTitle("Ez a cím");
        alertbox.setMessage(msg);
        alertbox.setNeutralButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0,
                                        int arg1) {
                        // Ok kiválasztva
                    }
                });
        alertbox.show();
    }

}
