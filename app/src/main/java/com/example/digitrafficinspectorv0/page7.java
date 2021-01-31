package com.example.digitrafficinspectorv0;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class page7 extends AppCompatActivity {

    Button publicBtn, trafficBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page7);
        publicBtn = (Button) findViewById(R.id.publicBtn);

        publicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), page71.class));
            }
        });

        trafficBtn = (Button) findViewById(R.id.trafficBtn);

        trafficBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), page72.class));
            }
        });

    }
}
