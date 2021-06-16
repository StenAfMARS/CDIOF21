package dtu.gruppe04.littlebrain.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import dtu.gruppe04.littlebrain.Adapter.InputAdapter;
import dtu.gruppe04.littlebrain.R;

public class playActivity extends AppCompatActivity {


    InputAdapter inputAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        initGridView();
    }

    private void initGridView(){
        final String[] charArray = new String[]{"24", "", "", "♥", "♦", "♣", "♠","", "", "", "", "", "", "","A♥", "", "", "", "", "", "", "", "5♥", "", "", "", "", "", "", "", "9♣", "", "", "", "", "", "", "", "10♠", "", "", "", "", "", "", "", "3♦", "", "", "", "", "", "", "", "s", "", "", "", "", "", "", "", "b"};


        final GridView gridView = findViewById(R.id.gridview);
        inputAdapter = new InputAdapter(this, charArray);
        gridView.setAdapter(inputAdapter);

        inputAdapter.setUsed(2, true);

        for (int i = 7; i < 14; i++) {
            inputAdapter.setUsed(i, true);
        }

        for (int i = 0; i < 7; i++) {
            for (int j = i + 1; j < 7; j++) {
                inputAdapter.setUsed(i+j*7+14, true);
            }
        }

        inputAdapter.notifyDataSetChanged();

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            String input = charArray[position];
        });
    }
}