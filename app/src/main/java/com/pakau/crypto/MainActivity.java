package com.pakau.crypto;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(COINS)
                            .build();
                    Response response = client.newCall(request).execute();

                    final String result = response.body().string();

                    long after = System.currentTimeMillis();
                    Log.d("PAKAU", String.valueOf(after - before));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(result);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
