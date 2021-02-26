package com.example.aitd;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView log;


    private void changeMode() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        log = findViewById(R.id.log);

        changeMode();

    }

    /**
     * 进入文字
     *
     * @param response
     */
    public void fillContent(String response) {
        log.setText(response + "\n" + log.getText().toString());
    }
}