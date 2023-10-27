package com.amonteiro.a23_09_fad_android;

import com.amonteiro.a23_09_fad_android.beans.WeatherBean;
import com.google.gson.Gson;
import okhttp3.*;


import java.io.IOException;

public class RequestUtils {

    private static final String URL_API = "https://api.openweathermap.org/data/2.5";

    public static WeatherBean loadWeather(String cityName) throws Exception {

        //requête
        String json = sendGet(URL_API + "/weather?appid=b80967f0a6bd10d23e44848547b26550&units=metric&lang=fr&q=" + cityName);

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
