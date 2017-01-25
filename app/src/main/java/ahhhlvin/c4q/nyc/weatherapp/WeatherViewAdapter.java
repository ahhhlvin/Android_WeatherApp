package ahhhlvin.c4q.nyc.weatherapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by alvin2 on 1/18/17.
 */

public class WeatherViewAdapter extends RecyclerView.Adapter<WeatherViewAdapter.WeatherViewHolder> {

    private ArrayList<DayForecastObject> forecastList;

    WeatherViewAdapter(ArrayList<DayForecastObject> forecastList) {
        this.forecastList = forecastList;
    }

    @Override
    public WeatherViewAdapter.WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_weekly_forecast, null);
        return new WeatherViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(WeatherViewHolder holder, int position) {
        if (forecastList.size() > 0) {
            DayForecastObject currentObj = forecastList.get(position);

            holder.dateTV.setText(currentObj.getDt());
            holder.weatherDescriptionTV.setText(currentObj.getMain());
            holder.maxTempTV.setText("MAX: " + currentObj.getMax() + " °F");
            holder.minTempTV.setText("MIN: " + currentObj.getMin() + " °F");
        }
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }


    class WeatherViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {

        TextView dateTV;
        TextView weatherDescriptionTV;
        TextView maxTempTV;
        TextView minTempTV;

        WeatherViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            dateTV = (TextView) itemView.findViewById(R.id.dt);
            weatherDescriptionTV = (TextView) itemView.findViewById(R.id.main);
            maxTempTV = (TextView) itemView.findViewById(R.id.maxTemp);
            minTempTV = (TextView) itemView.findViewById(R.id.minTemp);
        }

        @Override
        public void onClick(View view) {
            FragmentActivity activity = (FragmentActivity) view.getContext();
            DailyFragment dailyFragment = new DailyFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("dayForecast", forecastList.get(getAdapterPosition()));
            dailyFragment.setArguments(bundle);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutContainer, dailyFragment).addToBackStack(null).commit();
        }
    }
}

