package com.example.sakur.postservice;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by sakur on 2017/02/08.
 */

public class AsyncHttp extends AsyncTask<String, Integer, Boolean> {

    HttpURLConnection urlConnection = null;
    Boolean flg = false; // データ通信可否のフラグ
    double latitude;// 送る緯度データ
    double longitude;// 送る経度データ

    // コンストラクタで初期化
    public AsyncHttp(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // 非同期処理
    @Override
    protected Boolean doInBackground(String... contents) {
        String urlinput = "http://10.250.6.14/upload/post.php";
        // ローカル環境の場合10.0.2.2
        try{
            URL url = new URL(urlinput);
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            // POST用パラメータ
            // DB上のテーブルにあるカラム名'latitude'と'longitude'に値を入れたい
            String postDataSample = "latitude="+latitude+"&longitude="+longitude;

            // POSTパラメータ設定
            OutputStream out = urlConnection.getOutputStream();
            out.write(postDataSample.getBytes());
            out.flush();
            out.close();

            // レスポンスを受け取る
            urlConnection.getInputStream();

            flg = true;
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return flg;
    }
}
