package com.cs310.notebook;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import java.util.List;

/**
 * Created by erdembocugoz on 17/05/16.
 */
public class MyLocation {
    private double longitude;
    private double latitude;
    private LocationManager locationManager;
    private Context context;
    private LocationListener locationListener;

    public MyLocation(Context context) {
        this.context = context;
        this.locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
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
        };
    }

    public void findLocation() {
        String location_context = context.LOCATION_SERVICE;
        locationManager = (LocationManager) context.getSystemService(location_context);

        List<String> providers = locationManager.getProviders(true);

        for (String provider : providers) {
            try {
                Location location = locationManager.getLastKnownLocation(provider);
                if (location != null) {
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                }
                locationManager.requestLocationUpdates(provider, 1000, 0, locationListener);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}