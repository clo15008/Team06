package com.example.blank;

import android.net.http.HttpResponseCache;

import org.json.JSONArray;

import java.util.Date;

/**
 * Created by gclou on 2/26/2018.
 */

public class RecipeData {
    private Recipe main;

    /**
     * Format the Weather Data into a single string.
     *
     * @return
     */
    /**public String toString() {
        String text = "Location: " + name + "\n";
        text += "Observation: " + new Date(dt*1000).toString() + "\n";
        text += "Temp: " + main.getTemp() + " Deg F\n";
        text += "Humidity: " + main.getHumidity() + "%\n";
        text += "Pressure: " + main.getPressure() + " hPa\n";
        text += "Wind: " + wind.getSpeed() + " mph (" + wind.getDeg() + " deg)\n";
        return text;
    }**/
}
