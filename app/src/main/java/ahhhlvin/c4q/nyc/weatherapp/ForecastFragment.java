package ahhhlvin.c4q.nyc.weatherapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ForecastFragment extends Fragment {

    private ArrayList<DayForecastObject> forecastList;
    private WeatherViewAdapter adapter;
    public static final String API_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?id=4670592&cnt=6&units=imperial&APPID=5ce3af43784cd035386cb1fe3ee4bd60";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_forecast,
                container, false);

        RecyclerView weekRecyclerView = (RecyclerView) view.findViewById(R.id.weekRecyclerView);
        weekRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new WeatherViewAdapter(forecastList);
        weekRecyclerView.setAdapter(adapter);

        new WeatherTask().execute();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        forecastList = new ArrayList<>(5);
    }

    class WeatherTask extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... voids) {
            return fetchWeeklyWeather(API_URL);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s.equalsIgnoreCase("Exception caught")) {
                Toast.makeText(getContext(), "No Results Found", Toast.LENGTH_SHORT).show();
            } else {
                adapter.notifyDataSetChanged();
                System.out.println("SETTING UPDATED ADAPTER AND GIF LIST");
            }        }
    }

    public String fetchWeeklyWeather(String url) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response responses;

            try {
                DayForecastObject dayForecast;
                responses = client.newCall(request).execute();
                forecastList.clear();

                String jsonData = responses.body().string();
                JSONObject jsonObj = new JSONObject(jsonData);
                JSONArray listArray = jsonObj.getJSONArray("list");

                for (int i = 0; i < listArray.length(); i++) {
                    if (i != 0) {
                        JSONObject dayObj = listArray.getJSONObject(i);
                        JSONObject tempObj = dayObj.getJSONObject("temp");
                        JSONArray weatherArr = dayObj.getJSONArray("weather");

                        dayForecast = new DayForecastObject();
                        long time = Long.parseLong(dayObj.getString("dt"));
                        Date date = new Date(time * 1000);

                        dayForecast.setDt(new SimpleDateFormat("EEE, M/dd/yyyy", Locale.US).format(date));
                        dayForecast.setMax(tempObj.getInt("max"));
                        dayForecast.setMin(tempObj.getInt("min"));
                        dayForecast.setMain(weatherArr.getJSONObject(0).getString("main"));
                        dayForecast.setSpeed(dayObj.getInt("speed"));
                        dayForecast.setHumidity(dayObj.getInt("humidity"));
                        forecastList.add(dayForecast);
                    }
                }

                responses.body().close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return ("OK");

        } catch (JSONException e) {
            e.printStackTrace();
            return ("Exception Caught");
        }
    }
}


