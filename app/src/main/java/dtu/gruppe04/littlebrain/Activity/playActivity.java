package dtu.gruppe04.littlebrain.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import dtu.gruppe04.littlebrain.Adapter.InputAdapter;
import dtu.gruppe04.littlebrain.R;
import dtu.gruppe04.littlebrain.solitaire.card.Card;

public class playActivity extends AppCompatActivity {


    InputAdapter inputMain;
    InputAdapter inputTop;

    String[] charArray;
    String[] charArrayForTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        initGridView();
    }

    private void initGridView(){
        final GridView gridViewMain = findViewById(R.id.gridviewbutton);
        final GridView gridViewTop = findViewById(R.id.gridviewtop);

        inputMain = new InputAdapter(this, charArray);
        inputTop = new InputAdapter(this, charArrayForTop);

        gridViewMain.setAdapter(inputMain);
        gridViewTop.setAdapter(inputTop);
        
        inputTop.setUsed(2, true);

        for (int i = 0; i < 7; i++) {
            for (int j = i + 1; j < 7; j++) {
                inputMain.setUsed(i+j*7, true);
            }
        }

        gridViewMain.setOnItemClickListener((parent, view, position, id) -> {
            String input = charArray[position];
        });
    }

    private void UpdateGridView(Card[][] piles, int [] topPos;){

        charArray = new String[]{"A♥", "", "", "", "", "", "", "", "5♥", "", "", "", "", "", "", "", "9♣", "", "", "", "", "", "", "", "10♠", "", "", "", "", "", "", "", "3♦", "", "", "", "", "", "", "", "s", "", "", "", "", "", "", "", "b"};


        charArrayForTop = new String[]{"24", "", "", "♥", "♦", "♣", "♠"};
    }
}