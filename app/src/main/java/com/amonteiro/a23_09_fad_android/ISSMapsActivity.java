package com.amonteiro.a23_09_fad_android;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.amonteiro.a23_09_fad_android.databinding.ActivityIssmapsBinding;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class ISSMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityIssmapsBinding binding;
    private Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityIssmapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }

    public void refreshISS(){
        new Thread(() -> {
            try {
                final LatLng issLocation = RequestUtils.getISSCurrentLocation();
                Geocoder geocoder = new Geocoder(ISSMapsActivity.this, Locale.getDefault());
                String locationName = "";

                try {
                    // Ici, nous faisons la requête de géocodage inverse pour obtenir l'adresse.
                    List<Address> addresses = geocoder.getFromLocation(issLocation.latitude, issLocation.longitude, 1);
                    if (addresses != null && !addresses.isEmpty()) {
                        Address address = addresses.get(0);
                        // Prenez les informations dont vous avez besoin de l'adresse. Par exemple :
                        String city = address.getLocality();
                        String country = address.getCountryName();
                        locationName = city != null ? city : "";
                        locationName += country != null ? ", " + country : "";
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String finalLocationName = locationName;
                // Maintenant que nous avons la position et le nom, nous devons mettre à jour l'UI sur le thread principal
                runOnUiThread(() -> {
                    if(mMap != null) {
                        mMap.clear();
                        // Créer un BitmapDescriptor à partir d'une ressource drawable
                        final BitmapDescriptor issIcon = BitmapDescriptorFactory.fromResource(R.drawable.ic_iss);

                        // Ajouter un marqueur à la position de l'ISS et déplacer la caméra
                        mMap.addMarker(new MarkerOptions().position(issLocation).title("ISS Location").icon(issIcon));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(issLocation, 6));

                        // Mettre à jour le TextView avec le nom de la localisation
                        binding.tvISSLocation.setText(finalLocationName.isEmpty() ? "Not over a city" : finalLocationName);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                // Gérer l'exception, par exemple en montrant un Toast
            }
        }).start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                refreshISS();
            }
        }, 0, 1000);
    }
}