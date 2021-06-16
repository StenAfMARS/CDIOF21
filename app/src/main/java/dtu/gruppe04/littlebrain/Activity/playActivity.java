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

    private void UpdateGridView(Card[][] piles, int [] topPos){
        // 0 1 9 10 11 12

        charArray = new String[]{"A♥", "", "", "", "", "", "", "", "5♥", "", "", "", "", "", "", "", "9♣", "", "", "", "", "", "", "", "10♠", "", "", "", "", "", "", "", "3♦", "", "", "", "", "", "", "", "s", "", "", "", "", "", "", "", "b"};


        charArrayForTop = new String[]{"24", "", "", "♥", "♦", "♣", "♠"};
        //                              0     1       9    10   11   12
        charArrayForTop[0] = String.valueOf(topPos[0]);
        if(topPos[1] == -1 ){
            charArrayForTop[1] = "";
        }
        else{
            charArrayForTop[1] = String.valueOf(topPos[1]);
        }
        if(topPos[9] == -1 ){
            charArrayForTop[3] = "";
        }
        else{
            charArrayForTop[3] = String.valueOf(topPos[9]);
        }
        if(topPos[10] == -1 ){
            charArrayForTop[4] = "";
        }
        else{
            charArrayForTop[4] = String.valueOf(topPos[10]);
        }
        if(topPos[11] == -1 ){
            charArrayForTop[5] = "";
        }
        else{
            charArrayForTop[5] = String.valueOf(topPos[11]);
        }
        if(topPos[11] == -1 ){
            charArrayForTop[5] = "";
        }
        else{
            charArrayForTop[6] = String.valueOf(topPos[12]);
        }
    }
}