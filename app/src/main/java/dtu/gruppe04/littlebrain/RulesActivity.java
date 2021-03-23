package dtu.gruppe04.littlebrain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RulesActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        Button BTN_Back =(Button) findViewById(R.id.BTN_Back);
        BTN_Back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BTN_Back:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
        }
    }
}