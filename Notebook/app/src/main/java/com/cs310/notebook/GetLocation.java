package com.cs310.notebook;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by erdembocugoz on 17/05/16.
 */
public class GetLocation implements LocationListener {

    public static double latitude;
    public static double longitude;


    @Override
    public void onLocationChanged(Location loc)
    {
        loc.getLatitude();

        loc.getLongitude();
        latitude=loc.getLatitude();
        longitude=loc.getLongitude();
        Log.d("Longitute latitude",""+longitude+""+latitude);
    }

    @Override
    public void onProviderDisabled(String provider)
    {
        //print "Currently GPS is Disabled";
    }
    @Override
    public void onProviderEnabled(String provider)
    {
        //print "GPS got Enabled";
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {
    }
}
