package com.example.saeed_ayishatu_s2110987mpd;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.saeed_ayishatu_s2110987mpd.Domains.ForecastData;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;


public class WeatherDataParser {

    @NonNull
    public static ObservationData parseObservationData(String xmlData) {
        ObservationData observationData = new ObservationData();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xmlData));

            int eventType = parser.getEventType();
            String title = "";
            String description = "";
            String pubDate = "";
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    String tagName = parser.getName();
                    if ("title".equals(tagName)) {
                        title = parser.nextText();
                    } else if ("description".equals(tagName)) {
                        description = parser.nextText();
                    } else if ("pubDate".equals(tagName)) {
                        pubDate = parser.nextText();
                    }
                } else if (eventType == XmlPullParser.END_TAG && "item".equals(parser.getName())) {
                    break; // Exit loop when </item> is encountered
                }
                eventType = parser.next();
            }

            // Extract data from title
            String[] titleParts = title.split(" - ");
            String day = titleParts[0]; // Correctly extract the day
            String[] weatherParts = titleParts[1].split(": ");
            String time = weatherParts[0];
            String weatherCondition = weatherParts[1].split(", ")[0]; // Extract only the weather condition

            // Extract data from description
            String[] descriptionParts = description.split(", ");
            String temperature = extractValue(descriptionParts[0]);
            String windDirection = extractValue(descriptionParts[1]);
            String windSpeed = extractValue(descriptionParts[2]);
            String humidity = extractValue(descriptionParts[3]);
            String pressure = extractValue(descriptionParts[4]);
            // Correctly extract visibility
            String visibility = "";
            for (String part : descriptionParts) {
                if (part.startsWith("Visibility: ")) {
                    visibility = part.substring("Visibility: ".length());
                    break;
                }
            }

            // Extract date and time from pubDate
            SimpleDateFormat pubDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
            Date pubDateTime = pubDateFormat.parse(pubDate);

            if (pubDateTime != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy", Locale.ENGLISH);
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss z", Locale.ENGLISH);
                String formattedDate = dateFormat.format(pubDateTime);
                String formattedTime = timeFormat.format(pubDateTime);

                observationData.setDate(formattedDate);
                observationData.setTime(formattedTime);
            }

            observationData.setDay(day);
            observationData.setTime(time);
            observationData.setWeatherCondition(weatherCondition);
            observationData.setTemperature(temperature);
            observationData.setWindDirection(windDirection);
            observationData.setWindSpeed(windSpeed);
            observationData.setHumidity(humidity);
            observationData.setPressure(pressure);
            observationData.setVisibility(visibility); // Set the correctly extracted visibility

            // Log the observation data
            Log.d("WeatherDataParser", "Observation Data: " + observationData);

        } catch (XmlPullParserException | IOException | ParseException e) {
            e.printStackTrace();
        }
        return observationData;
    }



    private static String extractValue(String input) {
        // Split the input string by colon and trim the result
        String[] parts = input.split(":");
        if (parts.length > 1) {
            return parts[1].trim();
        }
        return "";
    }


    @NonNull
    public static List<ForecastData> parseForecastData(String xmlData) {
        List<ForecastData> forecastDataList = new ArrayList<>();
        ForecastData forecastData = null; // Initialize outside the loop
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xmlData));

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && "item".equals(parser.getName())) {
                    forecastData = new ForecastData(); // Create a new ForecastData object
                } else if (eventType == XmlPullParser.END_TAG && "item".equals(parser.getName())) {
                    if (forecastData != null) {
                        forecastDataList.add(forecastData); // Add the forecastData object to the list
                        Log.d("ForecastData", forecastData.toString()); // Log the forecastData object
                    }
                } else if (forecastData != null) { // Ensure forecastData is not null
                    if (eventType == XmlPullParser.START_TAG && "title".equals(parser.getName())) {
                        String title = parser.nextText();
                        // Correctly extract the day and weather conditions
                        String[] titleParts = title.split(":", 2); // Split by the first colon
                        if (titleParts.length >= 2) {
                            forecastData.setDay(titleParts[0].trim());
                            // Further split the weather conditions part by the first comma to remove additional info
                            String[] weatherParts = titleParts[1].split(",", 2);
                            forecastData.setWeatherCondition(weatherParts[0].trim());
                        }
                    }
                    else if (eventType == XmlPullParser.START_TAG && "description".equals(parser.getName())) {
                        String description = parser.nextText();
                        // Extract UV Risk
                        String uvRisk = extractValue(description, "UV Risk");
                        forecastData.setUvRisk(uvRisk);
                        // Extract other data as before
                        String[] descriptionParts = description.split(", ");
                        for (String part : descriptionParts) {
                            if (part.contains("Minimum Temperature")) {
                                forecastData.setMinTemperature(part.split(": ")[1].trim());
                            } else if (part.contains("Maximum Temperature")) {
                                forecastData.setMaxTemperature(part.split(": ")[1].trim());
                            } else if (part.contains("Wind Direction")) {
                                forecastData.setWindDirection(part.split(": ")[1].trim());
                            } else if (part.contains("Wind Speed")) {
                                forecastData.setWindSpeed(part.split(": ")[1].trim());
                            } else if (part.contains("Visibility")) {
                                forecastData.setVisibility(part.split(": ")[1].trim());
                            } else if (part.contains("Pressure")) {
                                forecastData.setPressure(part.split(": ")[1].trim());
                            } else if (part.contains("Humidity")) {
                                forecastData.setHumidity(part.split(": ")[1].trim());
                            }
                        }
                    }
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return forecastDataList;
    }

    private static String extractValue(String input, String key) {
        // Split the input string by the key and trim the result
        String[] parts = input.split(key + ": ");
        if (parts.length > 1) {
            return parts[1].trim();
        }
        return "";
    }



    @Nullable
    public static ObservationData fetchAndParseObservationData(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            String xmlData = scanner.hasNext() ? scanner.next() : "";
            return parseObservationData(xmlData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }




    public static List<ForecastData> fetchAndParseForecastData(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            String xmlData = scanner.hasNext() ? scanner.next() : "";
            return parseForecastData(xmlData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

