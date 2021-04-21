package dtu.gruppe04.littlebrain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button BTN_Play =(Button) findViewById(R.id.BTN_Play);
        BTN_Play.setOnClickListener(this);
        Button BTN_Learn =(Button) findViewById(R.id.BTN_Learn);
        BTN_Learn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BTN_Play:
                startActivity(new Intent(getApplicationContext(), playActivity.class));
                break;
            case R.id.BTN_Learn:
                startActivity(new Intent(getApplicationContext(), RulesActivity.class));
                break;
        }
    }
}