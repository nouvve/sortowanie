package com.example.sortowanie;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private EditText wielkosc;
    private ProgressBar progres;
    private SortingView sortingView;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wielkosc = findViewById(R.id.wielkosc);
        Button przycisk = findViewById(R.id.przycisk);
        progres = findViewById(R.id.progres);
        sortingView = findViewById(R.id.sortingView);

        mainHandler = new Handler(Looper.getMainLooper());

        przycisk.setOnClickListener(view -> {
            String input = wielkosc.getText().toString();
            if (!input.isEmpty()) {
                int size = Integer.parseInt(input);
                if (size > 0) {
                    start(size);
                } else {
                    Toast.makeText(this, "Wpisz liczbę dodatnią", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Wpisz liczbę", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void start(int size) {
        ArrayList<Integer> info = losowe(size);
        progres.setMax(size);
        progres.setProgress(0);
        sortingView.ustaw(info);

        ExecutorService es = Executors.newSingleThreadExecutor();
        es.execute(() -> {
            sortowanie(info);
            mainHandler.post(() -> {
                progres.setProgress(size);
                sortingView.invalidate();
                Toast.makeText(MainActivity.this, "Sortowanie Ukończone", Toast.LENGTH_SHORT).show();
            });
            es.shutdown();
        });
    }

    private ArrayList<Integer> losowe(int size) {
        ArrayList<Integer> data = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            data.add((int) (Math.random() * 100));
        }
        return data;
    }

    private void sortowanie(ArrayList<Integer> info) {
        int n = info.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (info.get(j) > info.get(j + 1)) {
                    Collections.swap(info, j, j + 1);
                }
            }
            int j = i;
            mainHandler.post(() -> {
                progres.setProgress(j + 1);
                sortingView.invalidate();
            });
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
