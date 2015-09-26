package br.com.tisengenharia.tisapp;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by Gabriel Lucas de Toledo Ribeiro on 02/09/2015.
 * baseado em: {@code https://developers.google.com/maps/documentation/android-api/utility/marker-clustering}
 */
public class MyItem implements ClusterItem {
    private final LatLng mPosition;

    public MyItem(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

}