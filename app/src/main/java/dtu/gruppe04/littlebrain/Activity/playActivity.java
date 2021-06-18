package dtu.gruppe04.littlebrain.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;


import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.Console;

import dtu.gruppe04.littlebrain.Adapter.InputAdapter;
import dtu.gruppe04.littlebrain.ObjectDetections.DetectorActivity;
import dtu.gruppe04.littlebrain.R;
import dtu.gruppe04.littlebrain.solitaire.Klondike;
import dtu.gruppe04.littlebrain.solitaire.NodeList;
import dtu.gruppe04.littlebrain.solitaire.card.Card;

public class playActivity extends AppCompatActivity {


    private Button openCamera, ScanButton;

    Klondike klondike;

    InputAdapter inputMain;
    InputAdapter inputTop;

    String[] charArray;
    String[] charArrayForTop;
    String result;

    int from;
    int amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        klondike = new Klondike();
        openCamera = findViewById(R.id.openCameraId);
        ScanButton = findViewById(R.id.ScanButtonId);
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
        result = getIntent().getStringExtra("OutputOfCard");
        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
    }



    private void initGridView(){
        final GridView gridViewMain = findViewById(R.id.gridviewbutton);
        final GridView gridViewTop = findViewById(R.id.gridviewtop);

        from = -1;
        amount = -1;

        charArray = new String[13*7];
        charArrayForTop = new String[]{"24", "", null, "♥", "♦", "♣", "♠"};

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
            if (from == -1) {
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

            if (intentResult.getContents() != null){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(intentResult.getContents());
                builder.setTitle("Scanning result");
                builder.setPositiveButton("Scan agin", new DialogInterface.OnClickListener() {
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



        }else {
            super.onActivityResult(requestCode, resultCode, data);

        }

    }


}