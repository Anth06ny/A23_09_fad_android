package com.amonteiro.a23_09_fad_android.beans;

import java.util.List;

public class WeatherBean {

    private String name;
    private TempBean main;
    private WindBean wind;

    private List<DescriptionBean> weather;

    @Override
    public String toString() {
        return "WeatherBean{" +
                "name='" + name + '\'' +
                ", main=" + main +
                ", wind=" + wind +
                ", weather=" + weather +
                '}';
    }

    public List<DescriptionBean> getWeather() {
        return weather;
    }

    public void setWeather(List<DescriptionBean> weather) {
        this.weather = weather;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TempBean getMain() {
        return main;
    }

    public void setMain(TempBean main) {
        this.main = main;
    }

    public WindBean getWind() {
        return wind;
    }

    public void setWind(WindBean wind) {
        this.wind = wind;
    }
}
