package br.com.tisengenharia.utils;

import android.support.annotation.Nullable;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

/**
 * Created by Gabriel Ribeiro on 25/09/2015.
 * Google Maps Geocoding API
 * https://developers.google.com/maps/documentation/geocoding/intro
 * <p/>
 * https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=API_KEY
 * <p/>
 * See: https://developers.google.com/maps/web-services/client-library
 */
public abstract class GeoCoding {

    final static String google_geocoding_key = "AIzaSyAHoNMSfbBTDv1ivOfHc_dTPqhIj-bydRg";
    //final String baseURL = "https://maps.googleapis.com/maps/api/geocode/json?address={0}&key="+google_geocoding_key;

    /**
     * Get Address from LatLng
     * Compatibility entry
     *
     * @param location Localização do ponto para busca do endereço
     * @return o primeiro endereço encontrado, já formatado
     */
    public static String getFirstAddressFromLatLng(com.google.android.gms.maps.model.LatLng location, @Nullable GeocodingApi.ComponentFilter components) {
        GeocodingResult[] ret = getAddressesFromLatLng(new LatLng(location.latitude, location.longitude), components);
        if (ret.length == 0)
            return "";
        else
            return getFormattedAddress(ret[0], GeocodingResultFormater.SHORT);
    }

    /**
     * Get Address from LatLng
     *
     * @param location Localização do ponto para busca do endereço
     * @return lista de endereços
     */
    public static GeocodingResult[] getAddressesFromLatLng(com.google.maps.model.LatLng location, @Nullable GeocodingApi.ComponentFilter components) {
        GeoApiContext context = new GeoApiContext().setApiKey(google_geocoding_key);
        GeocodingResult[] results = new GeocodingResult[0];
        try {
            GeocodingApiRequest apiRequest = GeocodingApi.reverseGeocode(context, location);
            if (components != null)
                apiRequest.components(components);
            results = apiRequest.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public static String getFormattedAddress(GeocodingResult result, GeocodingResultFormater formater) {
        AddressComponentType[] format = {};
        switch (formater) {
            case SHORT:
                format = new AddressComponentType[]{AddressComponentType.STREET_ADDRESS, AddressComponentType.STREET_NUMBER, AddressComponentType.LOCALITY, AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_1};
                break;
            case NATURAL:
                format = new AddressComponentType[]{AddressComponentType.STREET_ADDRESS, AddressComponentType.STREET_NUMBER, AddressComponentType.LOCALITY, AddressComponentType.NEIGHBORHOOD, AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_1, AddressComponentType.COUNTRY};
                break;
        }
        return formatAdress(result.addressComponents, format);

    }

    public static String formatAdress(AddressComponent[] addressComponents, AddressComponentType[] format) {
        StringBuilder stringBuilder = new StringBuilder();
        /*, String formattedAdrress*/
//        StringReader sr = new StringReader(formattedAdrress);
//        try {
//            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
//            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
//            parser.setInput(sr, null);
//            parser.nextTag();
//            while(parser.getEventType()!=XmlPullParser.END_DOCUMENT);
//
//            stringBuilder = xmlMapMarkerParser.parse(sr);
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        for (AddressComponentType aFormat : format) {
            AddressComponent valor = getAddressComponent(addressComponents, aFormat);
            if (valor != null) {
                stringBuilder.append(" ").append(valor.shortName);

                for (AddressComponentType at : valor.types) {
                    if (at.equals(AddressComponentType.POLITICAL))
                        stringBuilder.append(",");
                }
            }

        }
        //stringBuilder.append(getAddressComponent(addressComponents, AddressComponentType.NEIGHBORHOOD));
        return stringBuilder.toString().trim();
    }

    public static AddressComponent getAddressComponent(AddressComponent[] addressComponents, AddressComponentType contain) {
        for (AddressComponent addr : addressComponents) {
            for (AddressComponentType at : addr.types) {
                if (at.equals(contain))
                    return addr;
            }
        }
        return null;
    }

    public static com.google.android.gms.maps.model.LatLng getLatLngFromAddress(String addressText) {
        //String requestURL = String.format(baseURL, addressText);

        // Replace the API key below with a valid API key.
        GeoApiContext context = new GeoApiContext().setApiKey(google_geocoding_key);
        GeocodingResult[] results = new GeocodingResult[0];
        try {
            results = GeocodingApi.geocode(context, addressText).await();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Log.i(MapsActivity.TAG, "qtde: " + results.length);
//        Log.i(MapsActivity.TAG, results[0].formattedAddress);

//        for (int i = 0; i < results.length; i++) {
//
//            Log.i(MapsActivity.TAG,"resultado "+i+": "+ results[i].formattedAddress+
//                    "/ geometry.location: "+results[i].geometry.location.toUrlValue()+
//                    "/ geometry.locationType: "+results[i].geometry.locationType.toUrlValue());
//        }

        if(results.length>0)
            return new com.google.android.gms.maps.model.LatLng(results[0].geometry.location.lat, results[0].geometry.location.lng);
        else
            return new com.google.android.gms.maps.model.LatLng(0, 0);
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

    public enum GeocodingResultFormater {
        NATURAL, SHORT;

    }

    public static GeocodingResult[] getGeocodingResultFromAddress(String addressText) {
        GeoApiContext context = new GeoApiContext().setApiKey(google_geocoding_key);
        GeocodingResult[] results = new GeocodingResult[0];
        try {
            results = GeocodingApi.geocode(context, addressText).await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
}
