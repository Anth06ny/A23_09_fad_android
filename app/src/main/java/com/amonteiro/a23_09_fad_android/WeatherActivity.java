package com.amonteiro.a23_09_fad_android;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;

import com.amonteiro.a23_09_fad_android.beans.WeatherBean;
import com.amonteiro.a23_09_fad_android.databinding.ActivityWeatherBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class WeatherActivity extends AppCompatActivity {

    private ActivityWeatherBinding binding = null;

    //Outils localisation
    private FusedLocationProviderClient fusedLocationClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityWeatherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Réglages
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        //Clic sur le bouton load
        binding.btLoad.setOnClickListener(v -> {
            showWeather(null);
        });

        binding.btMyLoc.setOnClickListener(v-> {
            //On vérifie si on a la permission
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //On a la permission
                checkLocation();
            }
            else {
                //Demande la permission
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            }
        });
    }

    /**
     * Récupérer la localisation
     */
//    @RequiresPermission(
//            anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"}
//    )
    public void checkLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                       showWeather(location);
                    }
                    else {
                        binding.textView.setText("Pas de localisation");
                    }
                });
    }

    //Callback de la demande de permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //On vérifie si on a la permission
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            //On a la permission
            checkLocation();
        }
        else {
            binding.textView.setText("Il faut la permission");
        }
    }

    /**
     * Va chercher la météo en fonction de la location en paramètre et Affiche le resultat
     * @param location Si null utilise le texte de l'editText
     */
    public void showWeather(Location location){
        String city = binding.et.getText().toString();
        binding.progressBar.setVisibility(View.VISIBLE);

        //Tâche asynchrone
        new Thread(() -> {
            try {
                WeatherBean weatherBean;
                //Chercher les données
                if(location != null) {
                    weatherBean = RequestUtils.loadWeather(location.getLatitude(), location.getLongitude());
                }
                else {
                    weatherBean = RequestUtils.loadWeather(city);
                }


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