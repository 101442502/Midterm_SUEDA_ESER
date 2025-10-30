package com.midterm.sueda;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import android.view.View;
import android.app.AlertDialog;
import android.content.Intent;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText numberInput;
    Button generateBtn, historyBtn;
    ListView listView;

    ArrayList<String> tableList = new ArrayList<>();
    ArrayAdapter<String> tableAdapter;

    // History list
    public static final ArrayList<Integer> historyNumbers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberInput = findViewById(R.id.numberInput);
        generateBtn = findViewById(R.id.generateBtn);
        historyBtn = findViewById(R.id.historyBtn);
        listView = findViewById(R.id.listView);

        tableAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, tableList);
        listView.setAdapter(tableAdapter);

        // Generate Table
        generateBtn.setOnClickListener(v -> {
            String txt = numberInput.getText().toString().trim();
            if (txt.isEmpty()) {
                Toast.makeText(this, "Enter a number", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                int n = Integer.parseInt(txt);
                tableList.clear();
                for (int i = 1; i <= 10; i++) {
                    tableList.add(n + " × " + i + " = " + (n * i));
                }
                tableAdapter.notifyDataSetChanged();
                if (!historyNumbers.contains(n)) historyNumbers.add(n);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid number", Toast.LENGTH_SHORT).show();
            }
        });

        // Click to delete row
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String row = tableList.get(position);
            new AlertDialog.Builder(this)
                    .setTitle("Delete row?")
                    .setMessage(row)
                    .setPositiveButton("Delete", (d, w) -> {
                        tableList.remove(position);
                        tableAdapter.notifyDataSetChanged();
                        Toast.makeText(this, "Deleted: " + row, Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        // Go to history screen
        historyBtn.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, HistoryActivity.class)));
    }

    // Bonus Clear All menu
    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_clear_all) {
            if (tableList.isEmpty()) {
                Toast.makeText(this, "List already empty", Toast.LENGTH_SHORT).show();
                return true;
            }
            new AlertDialog.Builder(this)
                    .setTitle("Clear all?")
                    .setMessage("Remove all rows.")
                    .setPositiveButton("Clear", (d, w) -> {
                        tableList.clear();
                        tableAdapter.notifyDataSetChanged();
                        Toast.makeText(this, "Cleared", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
