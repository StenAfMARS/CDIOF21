package dtu.gruppe04.littlebrain.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import dtu.gruppe04.littlebrain.Adapter.InputAdapter;
import dtu.gruppe04.littlebrain.R;
import dtu.gruppe04.littlebrain.solitaire.Klondike;
import dtu.gruppe04.littlebrain.solitaire.NodeList;
import dtu.gruppe04.littlebrain.solitaire.card.Card;

public class playActivity extends AppCompatActivity {

    Klondike klondike;

    InputAdapter inputMain;
    InputAdapter inputTop;

    String[] charArray;
    String[] charArrayForTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        klondike = new Klondike();

        initGridView();
    }

    private void initGridView(){
        final GridView gridViewMain = findViewById(R.id.gridviewbutton);
        final GridView gridViewTop = findViewById(R.id.gridviewtop);

        charArray = new String[13*7];
        charArrayForTop = new String[]{"24", "", "", "♥", "♦", "♣", "♠"};

        inputMain = new InputAdapter(this, charArray);
        inputTop = new InputAdapter(this, charArrayForTop);

        gridViewMain.setAdapter(inputMain);
        gridViewTop.setAdapter(inputTop);
        
        inputTop.setHidden(2, true);

        UpdateGridView(klondike.piles);

        gridViewMain.setOnItemClickListener((parent, view, position, id) -> {
            String input = charArray[position];
        });
    }

    private void UpdateGridView(NodeList<Card>[] piles){
        for (int i = 2; i < 9; i++) {
            for (int j = 0; j < 13; j++) {
                inputMain.setHidden(i+j*7-2,true);
            }

            int j = 0;

            for (Card card : piles[i]) {
                charArray[i+j*7-2] = card.getValue() +  card.getSuit().toString();
                inputMain.setHidden(i+j++*7-2, false);
            }
        }


    }
}