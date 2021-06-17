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

    int from;
    int amount;

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
            if (from == -1) {
                from = position % 7 + 2;
                amount = klondike.piles[from].getCount() - (position / 7);
            } else if (klondike.doMove(from, position % 7 + 2, amount)){

            }
        });

        gridViewMain.setOnItemClickListener((parent, view, position, id) -> {
            from = position % 7 + 2;
            amount = klondike.piles[from].getCount() - (position / 7);
        });
    }

    private void UpdateGridView(NodeList<Card>[] piles){
        for (int i = 2; i < 9; i++) {
            for (int j = 0; j < 13; j++) {
                inputMain.setHidden(i+j*7-2,true);
            }

            int j = 0;

            for (Card card : piles[i]) {
                charArray[i+j*7-2] = card.toToon();
                inputMain.setHidden(i+j++*7-2, false);
            }
        }

        charArrayForTop[0] = String.valueOf(piles[0].getCount());

        if(piles[1].getCount() == 0)
            charArrayForTop[1] = "";
        else
            charArrayForTop[1] = piles[1].peek(-1).toToon();

        if(piles[9].getCount() == 0)
            charArrayForTop[3] = "♠";
        else
            charArrayForTop[3] = piles[9].peek(-1).toToon();

        if(piles[10].getCount() == 0)
            charArrayForTop[4] = "♦";
        else
            charArrayForTop[4] = piles[10].peek(-1).toToon();

        if(piles[11].getCount() == 0)
            charArrayForTop[5] = "♣";
        else
            charArrayForTop[5] = piles[11].peek(-1).toToon();

        if(piles[12].getCount() == 0)
            charArrayForTop[6] = "♥";
        else
            charArrayForTop[6] = piles[12].peek(-1).toToon();
    }
}