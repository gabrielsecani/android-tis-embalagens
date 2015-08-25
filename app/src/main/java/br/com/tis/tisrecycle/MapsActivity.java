package br.com.tis.tisrecycle;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.util.Date;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    private LatLng llMeuLugar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMap.getMyLocation();
        setContentView(R.layout.activity_maps);

        int isAval = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this.getBaseContext());
        System.out.println("isAval: "+isAval);

        setUpMapIfNeeded();
    }

    private void showNotification(int isAval){
        NotificationCompat.Builder builder = new android.support.v4.app.NotificationCompat.Builder(this);

        //Create Intent to launch this Activity again if the notification is clicked.
        Intent i = new Intent(this, MapsActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(this, 0, i,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(intent);

        // Sets the ticker text
        builder.setTicker("isAval: " + String.valueOf(isAval));

        // Sets the small icon for the ticker
//        builder.setSmallIcon(R.drawable.ic_stat_custom);

        // Cancel the notification when clicked
        builder.setAutoCancel(true);

        // Build the notification
        Notification notification = builder.build();

        // Inflate the notification layout as RemoteViews
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification);

        // Set text on a TextView in the RemoteViews programmatically.
        final String time = DateFormat.getTimeInstance().format(new Date()).toString();
        final String text = getResources().getString(R.string.collapsed, time);
        contentView.setTextViewText(R.id.textView,text);

            /* Workaround: Need to set the content view here directly on the notification.
             * NotificationCompatBuilder contains a bug that prevents this from working on platform
             * versions HoneyComb.
             * See https://code.google.com/p/android/issues/detail?id=30495
             */
        notification.contentView=contentView;

        // Add a big content view to the notification if supported.
        // Support for expanded notifications was added in API level 16.
        // (The normal contentView is shown when the notification is collapsed, when expanded the
        // big content view set here is displayed.)
//        if(Build.VERSION.SDK_INT>=16)
//        {
//             Inflate and set the layout for the expanded notification view
//            RemoteViews expandedView =
//                    new RemoteViews(getPackageName(), R.layout.notification_expanded);
//            notification.bigContentView = expandedView;
//        }

        // START_INCLUDE(notify)
        // Use the NotificationManager to show the notification
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(0,notification);
    }
    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

        UiSettings uis = mMap.getUiSettings();
        uis.setZoomControlsEnabled(true);
        uis.setMyLocationButtonEnabled(true);
        uis.setZoomGesturesEnabled(true);

        mMap.setMyLocationEnabled(true);

        LatLng itemLatLng1 = new LatLng(-23.544180, -46.634458),
               itemLatLng2 = new LatLng(-23.545121, -46.635629),
               itemLatLng3 = (mMap.getMyLocation()==null?new LatLng(-23.6117561,-46.6420428): new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()));

        mMap.addMarker(new MarkerOptions().position(itemLatLng1).title("Marca 1"));
        mMap.addMarker(new MarkerOptions().position(itemLatLng2).title("Marca 2"));
        mMap.addMarker(new MarkerOptions().position(itemLatLng3).title("Onde estou"));
        llMeuLugar = (mMap.getMyLocation()==null?new LatLng(-23.6117561,-46.6420428): new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()));
        mMap.addMarker(new MarkerOptions().position(llMeuLugar).title("Minha posicao"));

        mMap.getProjection().toScreenLocation(itemLatLng3);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Projection p = mMap.getProjection();
                System.out.println(p.getVisibleRegion().describeContents());
                System.out.println(p.getVisibleRegion().farLeft.latitude);
                System.out.println(p.getVisibleRegion().farLeft.longitude);
                llMeuLugar = (mMap.getMyLocation()==null?new LatLng(-23.611,-46.642): new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()));
                if(mMap.getMyLocation()!=null) {
                    System.out.println(mMap.getMyLocation().getLatitude());
                    System.out.println(mMap.getMyLocation().getLongitude());
                }
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(llMeuLugar).title("Minha posicao"));

            }
        });
    }
}
