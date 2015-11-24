package hu.ait.android.sugarormdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import hu.ait.android.sugarormdemo.data.DayItem;

public class MainActivity extends AppCompatActivity {

    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = (TextView) findViewById(R.id.tvStatus);

        Button btnCreate = (Button) findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewDayItem();
                listAllDayItems();
            }
        });

        Button btnSelect = (Button) findViewById(R.id.btnSelect);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listAllDayItems();
            }
        });

        Button btnDeleteAll = (Button) findViewById(R.id.btnDeleteAll);
        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAllDayItems();
                listAllDayItems();
            }
        });
    }

    private void deleteAllDayItems() {
        DayItem.deleteAll(DayItem.class);
    }

    private void listAllDayItems() {
        List<DayItem> items = DayItem.listAll(DayItem.class);
        tvStatus.setText("");
        for (DayItem item : items) {
            tvStatus.append(item.getTitle() + ", " +
                    item.getDueDate().toString() + "\n");
        }
    }

    private void createNewDayItem() {
        DayItem item = new DayItem("todo",
                new Date(System.currentTimeMillis()));
        item.save();
    }
}
