package com.example.sakur.postservice;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener,View.OnClickListener{
    // フィールド
    private LocationManager locationManager;
    final double [] lat = {0.0};
    final double [] lng = {0.0};
//    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);


        locationStart();
        Button button = (Button)findViewById(R.id.urlbutton);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
//        String message = "緯度:"+String.valueOf(lat[0])+"軽度:"+String.valueOf(lng[0]);
//        Toast toast =  new Toast.makeText(this, message, Toast.LENGTH_SHORT);
//        toast.show();

        Toast.makeText(this,"緯度:"+lat[0]+"軽度:"+lng[0],Toast.LENGTH_SHORT).show();
        AsyncHttp post = new AsyncHttp(lat[0],lng[0]); //("Android", 10.11);
        post.execute();
    }

    private void locationStart(){
        // LocationManagerインスタンス生成
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(!gpsEnabled){
            // GPS設定するように促す
            Intent settingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingIntent);
            Log.d("debug","gpsEnable,startActivity");
        }else{
            Log.d("debug","gpsEnabled");
        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED){
            Log.d("debug","checkSelfPermission false");
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 50, (LocationListener)this);
    }

    @Override
    public void onLocationChanged(Location location){
        //緯度の表示
        lat[0] = location.getLatitude();
        lng[0] = location.getLongitude();
        Log.d("Debug", String.valueOf(lat[0]));
        Log.d("Debug", String.valueOf(lng[0]));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
