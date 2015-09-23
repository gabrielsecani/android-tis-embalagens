package br.com.tis.tisrecycle;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterManager;

public class MapsActivity extends FragmentActivity {

    public static final String TAG = "MapsActivity";
    public static final String LAST_LOCATION = "MyLastLocation";

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private EditText txtBusca;
    private Button btnBuscar;

    public static LatLng llMeuLugar;

    private Thread tAtualizaMeuLocal;

    private ClusterManager<MyItem> mClusterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        SharedPreferences settings = getSharedPreferences(LAST_LOCATION, 0);
        llMeuLugar = new LatLng(
                Double.parseDouble(settings.getString("LastLatitude", "-38") ),
                Double.parseDouble(settings.getString("LastLongitude", "-36") ) );

        if (GoogleApiAvailability.getInstance()==null)
            Log.d(TAG, "GoogleApiAvailability.getInstance() == null");
        else {
            int isAval = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this.getBaseContext());
            Log.d(TAG, "isAval: " + isAval);
        }

        setUpEvents();

        setUpMapIfNeeded();

        setUpMeuLocalIfNeeded();
    }

    @Override
    protected void onStop(){
        super.onStop();

        llMeuLugar = (mMap.getMyLocation()==null?new LatLng(-23.6117561,-46.6420428): new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()));

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(LAST_LOCATION, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("LastLatitude", String.valueOf(llMeuLugar.latitude));
        editor.putString("LastLongitude", String.valueOf(llMeuLugar.longitude));

        // Commit the edits!
        editor.commit();
    }

    private void setUpEvents() {
        btnBuscar = (Button)findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, txtBusca.getText().toString());
                btnBuscarOnClick(v);
            }
        });

        txtBusca = (EditText)findViewById(R.id.txtBusca);
        txtBusca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        txtBusca.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.d(TAG, "KeyCode: " + keyCode);
                Log.d(TAG, "KeyEvent: " + event.getCharacters());
                Log.d(TAG, txtBusca.getText().toString());

                btnBuscar.setEnabled(txtBusca.getText().length() > 0);

                return true;
            }
        });
    }

    private void btnBuscarOnClick(View v){

        //showNotification();

        mMap.moveCamera(CameraUpdateFactory.newLatLng(llMeuLugar));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(1));

        llMeuLugar = (mMap.getMyLocation()==null?new LatLng(-23.6117561,-46.6420428): new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()));

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();

        Log.w(TAG, "resume.");
        Log.wtf(TAG, "WTF resume.");

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
    private void setUpMeuLocalIfNeeded(){
        if (tAtualizaMeuLocal == null) {
            tAtualizaMeuLocal = new Thread(new Runnable() {

                public void run() {
                    Log.w(TAG, "Thread run.");
                    Log.wtf(TAG, "WTF Thread run.");
                    try {
                        if (mMap != null)
                            MapsActivity.llMeuLugar = (mMap.getMyLocation() == null ? new LatLng(-23.611, -46.642) : new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()));
                    } catch (Exception e) {
                        Log.w(TAG, "Thread tAtualizaMeuLocal: "+e.getMessage());
                    }
                    try{
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        Log.w(TAG, "Erro ao dormir thread tAtualizaMeuLocal: ", e);
                    }
                }
            });
            if(!tAtualizaMeuLocal.isAlive())
                tAtualizaMeuLocal.start();
        }
    }
    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

        mMap.setMyLocationEnabled(true);

        UiSettings uis = mMap.getUiSettings();
        uis.setZoomControlsEnabled(true);
        uis.setZoomGesturesEnabled(true);
        uis.setMyLocationButtonEnabled(true);
        uis.setScrollGesturesEnabled(true);
        uis.setTiltGesturesEnabled(true);
        uis.setCompassEnabled(true);
        uis.setRotateGesturesEnabled(true);
        uis.setMapToolbarEnabled(true);

        mMap.setBuildingsEnabled(false);

        setUpClusterer();

    }

    private GoogleMap getMap(){
        return mMap;
    }

    private void setUpClusterer() {

        // Position the map.
        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 10));

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<MyItem>(this, getMap());

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        getMap().setOnCameraChangeListener(mClusterManager);
        getMap().setOnMarkerClickListener(mClusterManager);

        // Add cluster items (markers) to the cluster manager.
        addItems();
    }

    private void addItems() {

        // Set some lat/lng coordinates to start with.
        double lat = -23.6117561;
        double lng = -46.6420428;

        // Add ten cluster items in close proximity, for purposes of this example.
        for (int i = 0; i < 10; i++) {
            double offset = i / 60d;
            lat = lat + offset;
            lng = lng + offset;
            MyItem offsetItem = new MyItem(lat, lng);
            mClusterManager.addItem(offsetItem);
        }
    }
}
