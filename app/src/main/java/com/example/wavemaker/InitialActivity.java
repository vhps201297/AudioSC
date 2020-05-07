package com.example.wavemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class InitialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
    }

    public void onClickPruebaVoz(View view) {
        Toast.makeText(this,"No se ha integrado este módulo", Toast.LENGTH_SHORT).show();
    }

    public void onClickPruebaAudio(View view) {
        startActivity(new Intent(this,MainActivity.class));
    }
}
