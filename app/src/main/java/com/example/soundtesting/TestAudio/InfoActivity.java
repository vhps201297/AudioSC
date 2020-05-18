package com.example.soundtesting.TestAudio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.soundtesting.R;

public class InfoActivity extends AppCompatActivity {

    private TextView textMinEarLeft;
    private TextView textMaxEarLeft;
    private TextView textMinEarRight;
    private TextView textMaxEarRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Ear left = (Ear) getIntent().getSerializableExtra("left_ear");
        Ear right = (Ear) getIntent().getSerializableExtra("right_ear");
        textMinEarLeft = findViewById(R.id.txt_min_left_ear);
        textMaxEarLeft = findViewById(R.id.txt_max_left_ear);
        textMinEarRight = findViewById(R.id.txt_min_right_ear);
        textMaxEarRight = findViewById(R.id.txt_max_right_ear);


        textMinEarLeft.setText(getString(R.string.str_freq_minima,String.valueOf(left.getMinFrequencyLeftEar())));
        textMaxEarLeft.setText(getString(R.string.str_freq_max,String.valueOf(left.getMaxFrequencyLeftEar())));
        textMinEarRight.setText(getString(R.string.str_freq_minima,String.valueOf(right.getMinFrequencyRightEar())));
        textMaxEarRight.setText(getString(R.string.str_freq_max,String.valueOf(right.getMaxFrequencyRightEar())));

    }

    public void onClickAccept(View view) {
        finish();
    }
}
