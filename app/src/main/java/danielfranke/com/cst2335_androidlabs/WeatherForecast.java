package danielfranke.com.cst2335_androidlabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends Activity {

    protected static final String ACTIVITY_NAME = "WeatherForecast";
    private TextView tvCurrentTemp, tvMinTemp, tvMaxTemp;
    private ImageView imageView;
    private ProgressBar progressBar;
    private static final String URL="http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        imageView = findViewById(R.id.imageViewWeatherForecast);
        tvCurrentTemp = findViewById(R.id.textViewCurrentTemp);
        tvMinTemp = findViewById(R.id.textViewMinTemp);
        tvMaxTemp = findViewById(R.id.textViewMaxTemp);

        progressBar = findViewById(R.id.progressBarWeatherForecast);
        progressBar.setVisibility(View.VISIBLE);

        ForecastQuery forecastQuery = new ForecastQuery();
        forecastQuery.execute(URL);
    }

     class ForecastQuery extends AsyncTask<String, Integer, String> {

        private String maxTemp;
        private String minTemp;
        private String currentTemp;
        private String weatherIcon;
        private Bitmap bitmap;

        @Override
        protected String doInBackground(String... args) {
            try {
                URL url = new URL(args[0]);
                HttpURLConnection web= (HttpURLConnection) url.openConnection();
                web.setReadTimeout(10000 /* milliseconds */);
                web.setConnectTimeout(15000 /* milliseconds */);
                web.setRequestMethod("GET");
                web.setDoInput(true);
                web.connect();

                XmlPullParser parser= Xml.newPullParser();
                InputStream inputStream = web.getInputStream();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(inputStream, null);

                //int event = parser.getEventType();
                while(parser.next()!=XmlPullParser.END_DOCUMENT){
                    if(parser.getEventType()!=XmlPullParser.START_TAG){
                        continue;
                    }
                    String name = parser.getName();
                    //Starts by looking for the entry tag
                    if(name.equals("temperature")){
                        currentTemp=parser.getAttributeValue(null, "value");
                        publishProgress(25);
                        minTemp=parser.getAttributeValue(null, "min");
                        publishProgress(50);
                        maxTemp=parser.getAttributeValue(null, "max");
                        publishProgress(75);

                    }
                    if(name.equals("weather")) {
                        weatherIcon = parser.getAttributeValue(null, "icon");

                        if (fileExistance(weatherIcon + ".png")) {

                            try {
                                inputStream=openFileInput(weatherIcon + ".png");
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            bitmap = BitmapFactory.decodeStream(inputStream);
                            Log.i(ACTIVITY_NAME+weatherIcon + ".png", "Image icon found locally" );

                        }
                        else {
                            URL urlIcon=new URL("http://openweathermap.org/img/w/"+weatherIcon+".png");
                            bitmap=getImage(urlIcon);
                            FileOutputStream outputStream=openFileOutput(weatherIcon+".png", Context.MODE_PRIVATE);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 80 , outputStream);
                            outputStream.flush();
                            outputStream.close();
                            Log.i(ACTIVITY_NAME+weatherIcon + ".png", "Image icon downloaded");
                        }

                        publishProgress(100);
                    }
                }

            } catch(Exception e){
                e.printStackTrace();
            }
            return null;

        }

        private Bitmap getImage(URL url) {
            HttpURLConnection connection = null;

            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());

                }
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }

            Log.i(ACTIVITY_NAME, "Get weather image icon from website");
            return bitmap;
        }

        public Bitmap readImage(String imagefile) {
            Bitmap bm=null;
            try {
                FileInputStream fis;
                fis = openFileInput(imagefile);

                bm = BitmapFactory.decodeStream(fis);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Log.i(ACTIVITY_NAME, "Get weather image icon from local directory");
            return bm;
        }

        @Override
        protected void onProgressUpdate(Integer... value) {
            Log.i(ACTIVITY_NAME, "In onProgressUpdate()");
            progressBar.setProgress(value[0]);
        }

        private boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        @Override
        protected void onPostExecute(String result) {

            tvCurrentTemp.setText(String.format("%s",currentTemp));
            tvMaxTemp.setText(String.format("%s %s %s", "High", maxTemp, "\u2103"));
            tvMinTemp.setText(String.format("%s %s %s", "Low", minTemp, "\u2103"));
            imageView.setImageBitmap(this.bitmap);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}