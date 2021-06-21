package dtu.gruppe04.littlebrain.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;


import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import dtu.gruppe04.littlebrain.Adapter.InputAdapter;
import dtu.gruppe04.littlebrain.ObjectDetections.DetectorActivity;
import dtu.gruppe04.littlebrain.R;
import dtu.gruppe04.littlebrain.solitaire.Klondike;
import dtu.gruppe04.littlebrain.solitaire.NodeList;
import dtu.gruppe04.littlebrain.solitaire.card.Card;

import static dtu.gruppe04.littlebrain.solitaire.card.Suit.CLUBS;
import static dtu.gruppe04.littlebrain.solitaire.card.Suit.DIAMONDS;
import static dtu.gruppe04.littlebrain.solitaire.card.Suit.HEARTS;
import static dtu.gruppe04.littlebrain.solitaire.card.Suit.SPADES;

public class playActivity extends AppCompatActivity {


    private Button openCamera, ScanButton;

    static Klondike klondike;

    InputAdapter inputMain;
    InputAdapter inputTop;

    String[] charArray;
    String[] charArrayForTop;

    int from = -1;
    int amount = -1;

    static Card scanCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        if (klondike == null)
            klondike = new Klondike();

        openCamera = findViewById(R.id.openCameraId);
        ScanButton = findViewById(R.id.ScanButtonId);


        String result = getIntent().getStringExtra("OutputOfCard");
        //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();

        if (result != null){
            cardFromDetection(result, scanCard);
        }

        initGridView();

        openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(playActivity.this, DetectorActivity.class);
                startActivity(intent);
            }
        });

        ScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanCode();
            }
        });
    }

    public void SuggestMove(View v){
        Klondike.Move[] moves = klondike.possibleMoves();

        if (moves.length == 0)
            return;

        int bestScore = Integer.MAX_VALUE;
        Klondike.Move bestMove = moves[0];

        for (Klondike.Move move : moves){
            int score = klondike.calculateValue(move);

            if (score < bestScore) {
                bestMove = move;
                bestScore = score;
            }
        }

        from = bestMove.From;
        amount = bestMove.Amount;

        if (bestMove.To < 2) {
            inputMain.setSelected(-1);
            inputTop.setSelected(bestMove.From);

            inputMain.setSuggested(-1);
            inputTop.setSuggested(bestMove.To);
        }
        else if (bestMove.To > 8){
            inputMain.setSelected(-1);
            inputTop.setSelected(bestMove.From-6);

            inputMain.setSuggested(-1);
            inputTop.setSuggested(bestMove.To-6);
        }
        else {
            inputMain.setSelected(bestMove.From+(klondike.piles[from].getCount()-amount)*7-2);
            inputTop.setSelected(-1);

            inputMain.setSuggested(bestMove.To+(klondike.piles[bestMove.To].getCount()-1)*7-2);
            inputTop.setSuggested(-1);
        }

        inputMain.notifyDataSetChanged();
        inputTop.notifyDataSetChanged();
    }

    private void cardFromDetection(String string, Card card){
        // special card
        switch(string.charAt(string.length()-1)){
            case'H':
                card.setSuit(HEARTS);
                break;
            case'D':
                card.setSuit(DIAMONDS);
                break;
            case'C':
                card.setSuit(CLUBS);
                break;
            case'S':
                card.setSuit(SPADES);
                break;
        }

        if(string.contains("A")){
            card.setVALUE(1);
        }
        if(string.contains("2")){
            card.setVALUE(2);
        }
        if(string.contains("3")){
            card.setVALUE(3);
        }
        if(string.contains("4")){
            card.setVALUE(4);
        }
        if(string.contains("5")){
            card.setVALUE(5);
        }
        if(string.contains("6")){
            card.setVALUE(6);
        }
        if(string.contains("7")){
            card.setVALUE(7);
        }
        if(string.contains("8")){
            card.setVALUE(8);
        }
        if(string.contains("9")){
            card.setVALUE(9);
        }
        if(string.contains("10")){
            card.setVALUE(10);
        }
        if(string.contains("J")){
            card.setVALUE(11);
        }
        if(string.contains("Q")){
            card.setVALUE(12);
        }
        if(string.contains("K")){
            card.setVALUE(13);
        }

        card.setUnknown(false);

        /*
        Ace - A
        Jack - J
        Queen - Q
        King - K
        2 til 10 er 2 til 10

        Hearts er H
        Diamonds er D
        Clubs er C
        Spades er S

        Queen of spades er QS
        2 of Hearts er 2H
         */
    }

    private void initGridView(){
        final GridView gridViewMain = findViewById(R.id.gridviewbutton);
        final GridView gridViewTop = findViewById(R.id.gridviewtop);

        charArray = new String[20*7];
        charArrayForTop = new String[]{"24", "", null, "♥", "♦", "♣", "♠"};

        inputMain = new InputAdapter(this, charArray);
        inputTop = new InputAdapter(this, charArrayForTop);

        gridViewMain.setAdapter(inputMain);
        gridViewTop.setAdapter(inputTop);
        
        inputTop.setPile(2, true);

        UpdateGridView(klondike.piles);

        gridViewMain.setOnItemClickListener((parent, view, position, id) -> {
            if (klondike.piles[position % 7 + 2].getCount() == 0 && from == -1){

            }
            else if (from == -1) {
                from = position % 7 + 2;
                amount = klondike.piles[from].getCount() - (position / 7);

                inputMain.setSelected(position);
                inputMain.notifyDataSetChanged();

            } else if (klondike.doMove(from, position % 7 + 2, amount)){
                from = -1;

                UpdateGridView(klondike.piles);

                inputMain.setSelected(-1);
                inputMain.notifyDataSetChanged();
                inputTop.setSelected(-1);
                inputTop.notifyDataSetChanged();
            } else if (inputMain.getSelected() == position){
                from = -1;
                amount = -1;

                inputMain.setSelected(-1);
                inputMain.notifyDataSetChanged();
            }
        });

        gridViewTop.setOnItemClickListener((parent, view, position, id) -> {
            //                              0     1       9    10   11   12
            if (klondike.piles[convertPosition(position)].getCount() == 0 && from == -1){

            }
            else if (from == -1) {
                from = convertPosition(position);
                amount = 1;

                inputTop.setSelected(position);
                inputTop.notifyDataSetChanged();
            } else if (klondike.doMove(from, convertPosition(position), amount)){
                from = -1;

                UpdateGridView(klondike.piles);

                inputMain.setSelected(-1);
                inputMain.notifyDataSetChanged();
                inputTop.setSelected(-1);
                inputTop.notifyDataSetChanged();
            }else if (inputTop.getSelected() == position){
                from = -1;
                amount = -1;

                inputTop.setSelected(-1);
                inputTop.notifyDataSetChanged();
            }
        });
    }
    private int convertPosition(int position){
        int convert = 0;
        switch (position){
            case 0:
                convert = 0;
                break;
            case 1:
                convert = 1;
                break;
            case 3:
                convert = 9;
                break;
            case 4:
                convert = 10;
                break;
            case 5:
                convert = 11;
                break;
            case 6:
                convert = 12;
                break;
        }
        return convert;
    }
    private void UpdateGridView(NodeList<Card>[] piles){
        inputMain.setSuggested(-1);
        inputTop.setSuggested(-1);

        if (
                piles[0].getCount() == 0
                && piles[1].getCount() == 0
                && (piles[2].getCount() == 0 || !piles[2].peek(0).isHidden())
                && (piles[3].getCount() == 0 || !piles[3].peek(0).isHidden())
                && (piles[4].getCount() == 0 || !piles[4].peek(0).isHidden())
                && (piles[5].getCount() == 0 || !piles[5].peek(0).isHidden())
                && (piles[6].getCount() == 0 || !piles[6].peek(0).isHidden())
                && (piles[7].getCount() == 0 || !piles[7].peek(0).isHidden())
                && (piles[8].getCount() == 0 || !piles[8].peek(0).isHidden())
        )
            gameOver(true);

        for (int i = 2; i < 9; i++) {
            int j = 0;

            for (Card card : piles[i]) {
                if (j == 0)
                    inputMain.setPile(i-2, false);

                if (card.isHidden())
                    charArray[i+j*7-2] = "";
                else if (card.isUnknown()) {
                    charArray[i+j*7-2] = "";
                    inputMain.setHighlighted(i+j*7-2);
                    inputTop.setHighlighted(-1);
                    scanCard = card;
                }
                else
                    charArray[i+j*7-2] = card.toToon();

                j++;
            }

            if (j == 0){
                inputMain.setPile(i-2, true);
                charArray[i-2] = "";
                j++;
            }

            for (; j < 20; j++) {
                charArray[i+j*7-2] = null;
            }
        }

        charArrayForTop[0] = String.valueOf(piles[0].getCount());

        if (piles[0].getCount() == 0)
            inputTop.setPile(0, true);
        else
            inputTop.setPile(0, false);

        if(piles[1].getCount() == 0) {
            charArrayForTop[1] = "";
            inputTop.setPile(1, true);
        } else {

            inputTop.setPile(1, false);

            if (piles[1].peek(-1).isUnknown()) {
                charArrayForTop[1] = "";
                inputMain.setHighlighted(-1);
                inputTop.setHighlighted(1);
                scanCard = piles[1].peek(-1);
            }
            else
                charArrayForTop[1] = piles[1].peek(-1).toToon();
        }

        if(piles[9].getCount() == 0) {
            charArrayForTop[3] = "♠";
            inputTop.setPile(3, true);
        } else {
            charArrayForTop[3] = piles[9].peek(-1).toToon();
            inputTop.setPile(3, false);
        }

        if(piles[10].getCount() == 0) {
            charArrayForTop[4] = "♦";
            inputTop.setPile(4, true);
        } else {
            charArrayForTop[4] = piles[10].peek(-1).toToon();
            inputTop.setPile(4, false);
        }

        if(piles[11].getCount() == 0) {
            charArrayForTop[5] = "♣";
            inputTop.setPile(5, true);
        } else {
            charArrayForTop[5] = piles[11].peek(-1).toToon();
            inputTop.setPile(5, false);
        }

        if(piles[12].getCount() == 0) {
            charArrayForTop[6] = "♥";
            inputTop.setPile(6, true);
        } else {
            charArrayForTop[6] = piles[12].peek(-1).toToon();
            inputTop.setPile(6, false);
        }
    }

    private void ScanCode() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setCaptureActivity(Capturece.class);
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setPrompt("Scanning code");
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (intentResult != null){
            Toast.makeText(this,intentResult.getContents(),Toast.LENGTH_LONG).show();
 /*
            if (intentResult.getContents() != null){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(intentResult.getContents());
                builder.setTitle("Scanning result");
                builder.setPositiveButton("Scan again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ScanCode();
                    }
                }).setNegativeButton("finish", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            } else {
                Toast.makeText(this,"No Results",Toast.LENGTH_LONG).show();
            }

  */



        }else {
            super.onActivityResult(requestCode, resultCode, data);

        }

    }
    private void gameOver(boolean winCondition){
        klondike = null;
        from = -1;
        amount = -1;

        Intent intent = new Intent(getBaseContext(),  MainActivity.class);
        intent.putExtra("winCondition", winCondition);
        startActivity(intent);
    }


}