package ahhhlvin.c4q.nyc.weatherapp;

import java.io.Serializable;

/**
 * Created by alvin2 on 1/18/17.
 */

public class DayForecastObject implements Serializable{

    private String dt;
    private String main;
    private int max;
    private int min;
    private int speed;
    private int humidity;

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}
