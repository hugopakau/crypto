package com.pakau.crypto;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends Activity {
    private static final String IPIFY = "https://api.ipify.org?format=json";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView = new TextView(this);


        setContentView(textView);

        new Thread() {
            @Override
            public void run() {
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    //String coins = "https://min-api.cryptocompare.com/data/all/coinlist"
                    long before = System.currentTimeMillis();
                    URL url = new URL(IPIFY);
                    URLConnection urlConnection = url.openConnection();
                    InputStream inputStream = urlConnection.getInputStream();
                    BufferedReader bufferedReader =
                            new BufferedReader(new InputStreamReader(inputStream));
                    String s = bufferedReader.readLine();
                    while (s != null) {
                        stringBuilder.append(s);
                        s = bufferedReader.readLine();
                    }
                    final String result = stringBuilder.toString();
                    long after = System.currentTimeMillis();
                    Log.d("PAKAU", String.valueOf(after-before));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(result);
                        }
                    });



                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }



            }
        }.start();

    }
}
//https://min-api.cryptocompare.com/data/all/coinlist