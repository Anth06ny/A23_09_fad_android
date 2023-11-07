package com.amonteiro.a23_09_fad_android;

import com.amonteiro.a23_09_fad_android.beans.ISSPositionResponse;
import com.amonteiro.a23_09_fad_android.beans.WeatherBean;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import okhttp3.*;


import java.io.IOException;

public class RequestUtils {

    private static final String URL_API = "https://api.openweathermap.org/data/2.5";
    private static final String ISS_POSITION_URL = "http://api.open-notify.org/iss-now.json";


    public static LatLng getISSCurrentLocation() throws IOException {
        System.out.println(ISS_POSITION_URL);

        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();

        Request request = new Request.Builder()
                .url(ISS_POSITION_URL)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Convertir la réponse JSON en objet ISSPositionResponse
            ISSPositionResponse issResponse = gson.fromJson(response.body().string(), ISSPositionResponse.class);

            // Extraire les données de position et les convertir en LatLng
            return new LatLng(issResponse.getIss_position().getLatitude(), issResponse.getIss_position().getLongitude());
        }
    }

    public static WeatherBean loadWeather(String cityName) throws Exception {

        //requête
        String json = sendGet(URL_API + "/weather?appid=b80967f0a6bd10d23e44848547b26550&units=metric&lang=fr&q=" + cityName);

        //parser le résultat
        Gson gson = new Gson();
        return gson.fromJson(json, WeatherBean.class);
    }

    public static WeatherBean loadWeather(double latitude, double longitude) throws Exception {

        //requête
        String json = sendGet(URL_API + "/weather?appid=b80967f0a6bd10d23e44848547b26550&units=metric&lang=fr&lat=" + latitude + "&lon=" + longitude);

        //parser le résultat
        Gson gson = new Gson();
        return gson.fromJson(json, WeatherBean.class);
    }


    public static String sendGet(String url) throws Exception {
        System.out.println("url : " + url);
        OkHttpClient client = new OkHttpClient();

        //Création de la requête
        Request request = new Request.Builder().url(url).build();

        //Le try-with ressource doc ici
        //Nous permet de fermer la réponse en cas de succès ou d'échec (dans le finally)
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        }
    }

}
