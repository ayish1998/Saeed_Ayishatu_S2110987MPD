package com.example.saeed_ayishatu_s2110987mpd.Adapters;
//Name: Ayishatu Saeed
//Student ID:S2110987
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.saeed_ayishatu_s2110987mpd.Domains.ForecastData;
import com.example.saeed_ayishatu_s2110987mpd.R;

public class ForecastDataAdapter extends RecyclerView.Adapter<ForecastDataAdapter.ViewHolder> {

    private List<ForecastData> forecastDataList;
    private static OnForecastItemClickListener listener;

    public ForecastDataAdapter(List<ForecastData> forecastDataList) {
        this.forecastDataList = forecastDataList;
    }

    public void setForecastDataList(List<ForecastData> forecastDataList) {
        this.forecastDataList = forecastDataList;
        notifyDataSetChanged();

        if (forecastDataList != null) {
            for (ForecastData forecastData : forecastDataList) {
                Log.d("ForecastDataAdapter", "Forecast Data: " + forecastData.toString());
            }
        } else {
            Log.d("ForecastDataAdapter", "Forecast Data List is null");
        }
    }

    public interface OnForecastItemClickListener {
        void onForecastItemClick(int position);
    }

    public void setOnForecastItemClickListener(OnForecastItemClickListener listener) {
        ForecastDataAdapter.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_data_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (forecastDataList != null) {
            ForecastData forecastData = forecastDataList.get(position);
            holder.bind(forecastData);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MotionLayout motionLayout = (MotionLayout) holder.itemView;
                    if (motionLayout.getCurrentState() == R.id.start) {
                        motionLayout.transitionToState(R.id.end);
                    } else {
                        motionLayout.transitionToState(R.id.start);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return forecastDataList != null ? forecastDataList.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewForecastDay;
        ImageView imageViewWeatherCondition;
        View forecastDetailsLayout;
        TextView textViewMinTemperature;
        TextView textViewMaxTemperature;
        TextView textViewWindDirection;
        TextView textViewWindSpeed;
        TextView textViewVisibility;
        TextView textViewPressure;
        TextView textViewHumidity;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewForecastDay = itemView.findViewById(R.id.textViewForecastDay);
            imageViewWeatherCondition = itemView.findViewById(R.id.imageViewWeatherCondition);
            forecastDetailsLayout = itemView.findViewById(R.id.forecast_details);
            textViewMinTemperature = itemView.findViewById(R.id.textViewMinTemperature);
            textViewMaxTemperature = itemView.findViewById(R.id.textViewMaxTemperature);
            textViewWindDirection = itemView.findViewById(R.id.textViewWindDirection);
            textViewWindSpeed = itemView.findViewById(R.id.textViewWindSpeed);
            textViewVisibility = itemView.findViewById(R.id.textViewVisibility);
            textViewPressure = itemView.findViewById(R.id.textViewPressure);
            textViewHumidity = itemView.findViewById(R.id.textViewHumidity);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleDetails();
                }
            });
        }

        void bind(ForecastData forecastData) {
            textViewForecastDay.setText(forecastData.getDay());
            imageViewWeatherCondition.setImageResource(getWeatherConditionImage(forecastData.getWeatherCondition()));
            textViewMinTemperature.setText("Min Temperature: " + forecastData.getMinTemperature());
            textViewMaxTemperature.setText("Max Temperature: " + forecastData.getMaxTemperature());
            textViewWindDirection.setText("Wind Direction: " + forecastData.getWindDirection());
            textViewWindSpeed.setText("Wind Speed: " + forecastData.getWindSpeed());
            textViewVisibility.setText("Visibility: " + forecastData.getVisibility());
            textViewPressure.setText("Pressure: " + forecastData.getPressure());
            textViewHumidity.setText("Humidity: " + forecastData.getHumidity());
        }

        void toggleDetails() {
            if (forecastDetailsLayout.getVisibility() == View.GONE) {
                forecastDetailsLayout.setVisibility(View.VISIBLE);
            } else {
                forecastDetailsLayout.setVisibility(View.GONE);
            }
        }

        private int getWeatherConditionImage(String weatherCondition) {
            if ("Sunny".equals(weatherCondition)) {
                return R.drawable.sunny;
            } else if ("Cloudy".equals(weatherCondition)) {
                return R.drawable.cloudyy;
            } else if ("Rain".equals(weatherCondition)) {
                return R.drawable.rain;
            } else if ("Snow".equals(weatherCondition)) {
                return R.drawable.snow;
            } else if ("Thunderstorm".equals(weatherCondition)) {
                return R.drawable.thunder;
            } else {
                return R.drawable.partly_cloudy; // Default image for unknown conditions
            }
        }
    }
}