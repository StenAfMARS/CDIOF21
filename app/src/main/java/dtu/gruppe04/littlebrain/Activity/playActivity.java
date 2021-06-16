package dtu.gruppe04.littlebrain.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import dtu.gruppe04.littlebrain.Adapter.InputAdapter;
import dtu.gruppe04.littlebrain.R;

public class playActivity extends AppCompatActivity {


    InputAdapter inputAdapter;
    InputAdapter inputTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        initGridView();
    }

    private void initGridView(){
        final String[] charArray = new String[]{"A♥", "", "", "", "", "", "", "", "5♥", "", "", "", "", "", "", "", "9♣", "", "", "", "", "", "", "", "10♠", "", "", "", "", "", "", "", "3♦", "", "", "", "", "", "", "", "s", "", "", "", "", "", "", "", "b"};
        final String[] charArrayForTop = new String[]{"24", "", "", "♥", "♦", "♣", "♠"};

        final GridView gridView = findViewById(R.id.gridviewbutton);
        final GridView gridViewtop = findViewById(R.id.gridviewtop);

        inputAdapter = new InputAdapter(this, charArray);
        inputTop = new InputAdapter(this, charArrayForTop);

        gridView.setAdapter(inputAdapter);
        gridViewtop.setAdapter(inputTop);
        
        inputTop.setUsed(2, true);

        for (int i = 0; i < 7; i++) {
            for (int j = i + 1; j < 7; j++) {
                inputAdapter.setUsed(i+j*7, true);
            }
        }

        inputAdapter.notifyDataSetChanged();

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            String input = charArray[position];
        });
    }
}