package com.amonteiro.a23_09_fad_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.amonteiro.a23_09_fad_android.beans.WindBean;
import com.amonteiro.a23_09_fad_android.databinding.ActivityWeatherAroundBinding;
import com.amonteiro.a23_09_fad_android.databinding.ActivityWeatherBinding;

import java.util.ArrayList;

public class WeatherAroundActivity extends AppCompatActivity {

    private ActivityWeatherAroundBinding binding = null;

    private ArrayList<WindBean> list = new ArrayList<>();
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityWeatherAroundBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btAdd.setOnClickListener(v -> {
            list.add(new WindBean(count++));
            refreshScreen();
        });

        binding.btDelete.setOnClickListener(v -> {
            if(!list.isEmpty()) {
                list.remove(0);
                refreshScreen();
            }
        });
    }

    public void refreshScreen(){
        String texte = "";
        for (WindBean windBean : list) {
            texte += "-" +  windBean.getSpeed() + "\n";
        }

        binding.textView2.setText(texte);
    }
}