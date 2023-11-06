package com.amonteiro.a23_09_fad_android;

import android.os.Bundle;
import android.view.View;

import com.amonteiro.a23_09_fad_android.beans.WeatherBean;
import com.amonteiro.a23_09_fad_android.databinding.ActivityWeatherBinding;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class WeatherActivity extends AppCompatActivity {

    private ActivityWeatherBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityWeatherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Clic sur le bouton load
        binding.btLoad.setOnClickListener(v -> {
            showWeather();
        });
    }

    public void showWeather(){
        String city = binding.et.getText().toString();
        binding.progressBar.setVisibility(View.VISIBLE);

        //Tâche asynchrone
        new Thread(() -> {
            try {
                //Chercher les données
                WeatherBean weatherBean = RequestUtils.loadWeather(city);

                //Retourne sur le thread graphique
                runOnUiThread(() -> {
                    binding.textView.setText("Il fait " + weatherBean.getMain().getTemp() + "° à " + weatherBean.getName() + " avec un vent de " + weatherBean.getWind().getSpeed() + " m/s");
                    binding.progressBar.setVisibility(View.GONE);

                    //Image
                    if(!weatherBean.getWeather().isEmpty()) {
                        Picasso.get().load("https://openweathermap.org/img/wn/" + weatherBean.getWeather().get(0).getIcon() + "@4x.png").into(binding.imageView);
                    }
                });
            }
            catch (Exception e) {
                e.printStackTrace();
                //Retourne sur le thread graphique
                runOnUiThread(() -> {
                    binding.textView.setText("Une erreur est survenue : " + e.getMessage());
                    binding.progressBar.setVisibility(View.GONE);
                });
            }
        }).start();
    }
}