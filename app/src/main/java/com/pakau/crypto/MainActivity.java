package com.pakau.crypto;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import okio.BufferedSource;
import okio.Okio;
import okio.Source;

public class MainActivity extends Activity {
    private static final String IPIFY = "https://api.ipify.org?format=json";
    private static final String COINS = "https://min-api.cryptocompare.com/data/top/totalvolfull?limit=10&tsym=USD";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView = new TextView(this);
        ScrollView scrollView = new ScrollView(this);
        scrollView.addView(textView);

        setContentView(scrollView);

        new Thread() {
            @Override
            public void run() {
                try {
                    //String coins = "https://min-api.cryptocompare.com/data/all/coinlist"
                    long before = System.currentTimeMillis();
                    URL url = new URL(COINS);
                    URLConnection urlConnection = url.openConnection();
                    InputStream inputStream = urlConnection.getInputStream();

                    Source source = Okio.source(inputStream);
                    BufferedSource bufferedSource = Okio.buffer(source);
                    final String result = bufferedSource.readUtf8();

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