package com.amonteiro.a23_09_fad_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.amonteiro.a23_09_fad_android.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity  {



    private static final int MENU_WEATHER = 2;

    private ActivityMainBinding binding = null;


    //Callback : La création de l'écran
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Créer l'interface
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        //Afficher
        setContentView(binding.getRoot());

        //Callback : Du clic sur le bouton valider
        binding.btValidate.setOnClickListener(v -> {
            if(binding.rbLike.isChecked()) {
                binding.et.setText(binding.rbLike.getText());
            }
            else if(binding.rbDislike.isChecked()) {
                binding.et.setText(binding.rbDislike.getText());
            }
            binding.iv.setImageResource(R.drawable.baseline_delete_forever_24);
        });

        binding.btCancel.setOnClickListener(v -> {
            binding.et.setText("");
            binding.rg.clearCheck();
            binding.iv.setImageResource(R.drawable.baseline_flag_24);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,MENU_WEATHER,0,"Météo");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == MENU_WEATHER) {
            Intent intent = new Intent(this, WeatherActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}