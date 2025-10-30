package com.midterm.sueda;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> tableList = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        String number = getIntent().getStringExtra("number");

        if (number != null && !number.isEmpty()) {
            int n = Integer.parseInt(number);

            for (int i = 1; i <= 10; i++) {
                tableList.add(n + " x " + i + " = " + (n * i));
            }
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tableList);
        listView.setAdapter(adapter);
    }
}

