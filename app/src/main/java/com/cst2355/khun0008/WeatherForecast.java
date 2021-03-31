package com.cst2355.khun0008;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class WeatherForecast extends AppCompatActivity {

    private ProgressBar pb;
    private ImageView iView;
    private TextView currentTemp;
    private TextView minTemp;
    private TextView maxTemp;
    private TextView uvRat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        iView = findViewById(R.id.imageViewCW);
        currentTemp = findViewById(R.id.tvCurrentTemp);
        minTemp = findViewById(R.id.tvMinTemp);
        maxTemp = findViewById(R.id.tvMaxTemp);
        uvRat = findViewById(R.id.tvUvRating);
        pb = findViewById(R.id.pBar);
        pb.setVisibility(View.VISIBLE);

        ForecastQuery req = new ForecastQuery();
        req.execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric",
                "http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389");
    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {

        private String UV,min,max,cTemperature,icon;
        private Bitmap cWeather = null;
        private double uvRating;

        protected String doInBackground(String... args) {
            try {

                //String URL = URLEncoder.encode(args[0],"UTF-8");

                //create a URL object of what server to contact:
                URL url = new URL(args[0]);

                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //wait for data:
                InputStream response = urlConnection.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(response, "UTF-8");


                int eventType = xpp.getEventType(); //The parser is currently at START_DOCUMENT

                while(eventType != XmlPullParser.END_DOCUMENT)
                {

                    if(eventType == XmlPullParser.START_TAG)
                    {
                        if (xpp.getName().equals("temperature")) {
                            cTemperature = xpp.getAttributeValue(null, "value");
                            publishProgress(25);
                            min = xpp.getAttributeValue(null, "min");
                            publishProgress(50);
                            max = xpp.getAttributeValue(null, "max");
                            publishProgress(75);
                        }

                        else if (xpp.getName().equals("weather")) {
                            icon = xpp.getAttributeValue(null, "icon"); // this will run for <AMessage message="parameter" >
                        }
                    }
                    eventType = xpp.next(); //move to the next xml event and store it in a variable
                }


                String urlString = "https://openweathermap.org/img/w/" + icon + ".png";
                URL url1 = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    cWeather = BitmapFactory.decodeStream(connection.getInputStream());
                }

                if (fileExistance(icon + ".png")) {
                    Log.i("check","photo should be downloaded");
                    FileOutputStream outputStream = openFileOutput( icon + ".png", Context.MODE_PRIVATE);
                    cWeather.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    publishProgress(100);
                }

                else {
                    Log.i("check","Photo is already downloaded");
                    FileInputStream fis = null;
                    try {
                        fis = openFileInput(icon+".png");
                    }
                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap bm = BitmapFactory.decodeStream(fis);
                }
            } catch (Exception e) {

            }

            try {

                //create a URL object of what server to contact:
                URL url = new URL(args[1]);

                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //wait for data:
                InputStream response = urlConnection.getInputStream();

                //JSON reading:   Look at slide 26
                //Build the entire string response:
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String result = sb.toString(); //result is the whole string


                // convert string to JSON: Look at slide 27:
                JSONObject uvReport = new JSONObject(result);

                //get the double associated with "value"
                uvRating = uvReport.getDouble("value");

                Log.i("MainActivity", "The uv is now: " + uvRating);

            } catch (Exception e) {

            }

            return "done";
        }

        public void onProgressUpdate(Integer... args) {
            pb.setVisibility(View.VISIBLE);
            pb.setProgress(args[0]);
        }

        //Type3
        public void onPostExecute(String fromDoInBackground) {
            currentTemp.setText("Temperature: " + cTemperature);
            minTemp.setText("Min: " + min);
            maxTemp.setText("Max: " + max);
            iView.setImageBitmap(cWeather);
            uvRat.setText("UV Rating: "+uvRating);
            pb.setVisibility(View.INVISIBLE);
        }
    }

    public boolean fileExistance(String fname) {
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }
}