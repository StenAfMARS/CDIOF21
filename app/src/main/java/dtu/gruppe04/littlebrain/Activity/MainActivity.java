package dtu.gruppe04.littlebrain.Activity;
import org.tensorflow.lite.Interpreter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import dtu.gruppe04.littlebrain.R;

// Group nr. 4

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    Interpreter tflite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            tflite = new Interpreter(loadModelFile());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        boolean sessionId = getIntent().getBooleanExtra("winCondition",false);
        TextView txt_Header = (TextView) findViewById(R.id.TXT_Header);
        if(sessionId == true){
            txt_Header.setText("You Won");
        }

        Button BTN_Play =(Button) findViewById(R.id.BTN_Play);
        BTN_Play.setOnClickListener(this);
        Button BTN_Learn =(Button) findViewById(R.id.BTN_Learn);
        BTN_Learn.setOnClickListener(this);
    }

    private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor = this.getAssets().openFd("raw/detect.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
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