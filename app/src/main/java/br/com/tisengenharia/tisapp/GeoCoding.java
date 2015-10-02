package br.com.tisengenharia.tisapp;

import android.provider.Settings;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

import br.com.tisengenharia.tisapp.MapsActivity;

/**
 * Created by Gabriel Ribeiro on 25/09/2015.
 * Google Maps Geocoding API
 * https://developers.google.com/maps/documentation/geocoding/intro
 *
 * https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=API_KEY
 *
 * See: https://developers.google.com/maps/web-services/client-library
 */
public class GeoCoding {

    final String google_geocoding_key="AIzaSyAHoNMSfbBTDv1ivOfHc_dTPqhIj-bydRg";
    final String baseURL = "https://maps.googleapis.com/maps/api/geocode/json?address={0}&key="+google_geocoding_key;

    public LatLng getLatLngFromAddress(String addressText){
        String requestURL = String.format(baseURL, addressText);

        // Replace the API key below with a valid API key.
        GeoApiContext context = new GeoApiContext().setApiKey(google_geocoding_key);
        GeocodingResult[] results = new GeocodingResult[0];
        try {
            results = GeocodingApi.geocode(context, addressText).await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i(MapsActivity.TAG, "qtde: " + results.length);
        Log.i(MapsActivity.TAG, results[0].formattedAddress);

        for (int i = 0; i < results.length; i++) {

            Log.i(MapsActivity.TAG,"resultado "+i+": "+ results[i].formattedAddress+
                    "/ geometry.location: "+results[i].geometry.location.toUrlValue()+
                    "/ geometry.locationType: "+results[i].geometry.locationType.toUrlValue());
        }

        if(results.length>0)
            return new LatLng(results[0].geometry.location.lat,results[0].geometry.location.lng);
        else
            return new LatLng(0,0);
/*
        Source Code

        You can access the source code for the Java Client for Google Maps Services at https://github.com/googlemaps/google-maps-services-java/

        Usage (Java)

        This example uses the Google Maps Geocoding API.

// Replace the API key below with a valid API key.
        GeoApiContext context = new GeoApiContext().setApiKey("API_KEY");
        GeocodingResult[] results =  GeocodingApi.geocode(context,
                "1600 Amphitheatre Parkway Mountain View, CA 94043").await();
        System.out.println(results[0].formattedAddress);

        Synchronous requests

        GeocodingApiRequest req = GeocodingApi.newRequest(context).address("Sydney");

        try {
            req.await();
            // Handle successful request.
        } catch (Exception e) {
            // Handle error
        }

        req.awaitIgnoreError(); // No checked exception.

        ////Asynchronous requests

        req.setCallback(new PendingResult.Callback<GeocodingResult[]>() {
            @Override
            public void onResult(GeocodingResult[] result) {
                // Handle successful request.
            }

            @Override
            public void onFailure(Throwable e) {
                // Handle error.
            }
        });

        */
    }
}
