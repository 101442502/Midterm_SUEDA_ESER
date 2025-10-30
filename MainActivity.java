package com.midterm.sueda;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText numberInput;
    Button generateBtn, historyBtn;
    ListView listView;
    ArrayList<String> results = new ArrayList<>();
    ArrayAdapter<String> adapter;

    public static ArrayList<Integer> historyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberInput = findViewById(R.id.numberInput);
        generateBtn = findViewById(R.id.generateBtn);
        historyBtn = findViewById(R.id.historyBtn);
        listView = findViewById(R.id.listView);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, results);
        listView.setAdapter(adapter);

        generateBtn.setOnClickListener(v -> showTable());
        historyBtn.setOnClickListener(v -> startActivity(new Intent(this, HistoryActivity.class)));

        listView.setOnItemClickListener((parent, view, position, id) -> deleteItem(position));
    }

    private void showTable() {
        String val = numberInput.getText().toString();
        if (val.isEmpty()) {
            Toast.makeText(this, "Enter number", Toast.LENGTH_SHORT).show();
            return;
        }

        int num = Integer.parseInt(val);
        results.clear();

        for (int i = 1; i <= 10; i++) {
            results.add(num + " x " + i + " = " + (num * i));
        }

        if (!historyList.contains(num)) {
            historyList.add(num);
        }

        adapter.notifyDataSetChanged();
    }

    private void deleteItem(int pos) {
        new AlertDialog.Builder(this)
                .setTitle("Delete?")
                .setMessage("Remove this row?")
                .setPositiveButton("Yes", (d, w) -> {
                    String removed = results.get(pos);
                    results.remove(pos);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(this, "Deleted: " + removed, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", null)
                .show();
    }
}
