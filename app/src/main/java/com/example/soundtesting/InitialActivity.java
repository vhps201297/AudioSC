package com.example.soundtesting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.soundtesting.TestAudio.MainActivity;
import com.example.soundtesting.TestVoice.VoiceMainActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class InitialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
    }

    public void onClickPruebaVoz(View view) {
        //Toast.makeText(this,"No se ha integrado este módulo", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, VoiceMainActivity.class));
    }

    public void onClickPruebaAudio(View view) {
        new MaterialAlertDialogBuilder(this)
                .setMessage(R.string.str_instruccion_audio)
                .setTitle("Instrucciones")
                .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(InitialActivity.this, MainActivity.class));
                    }
                })
                .show();

    }

}
