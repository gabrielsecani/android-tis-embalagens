package br.com.tisengenharia.tisapp;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLFilterImpl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.com.tisengenharia.utils.GeoCoding;
import br.com.tisengenharia.tisapp.model.PontoDeTroca;
import br.com.tisengenharia.utils.MultiDrawable;
import br.com.tisengenharia.utils.PontoDeTrocaClusterRenderer;

public class MapsActivity extends FragmentActivity
        implements ClusterManager.OnClusterClickListener<PontoDeTroca>, ClusterManager.OnClusterInfoWindowClickListener<PontoDeTroca>, ClusterManager.OnClusterItemClickListener<PontoDeTroca>, ClusterManager.OnClusterItemInfoWindowClickListener<PontoDeTroca> {
    @Override
    public boolean onClusterClick(Cluster<PontoDeTroca> cluster) {

        // Show a toast with some info when the cluster is clicked.
        String firstName = cluster.getItems().iterator().next().getCDATA();
        Toast.makeText(this, cluster.getSize() + " (including " + firstName + ")", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onClusterInfoWindowClick(Cluster<PontoDeTroca> cluster) {
        // Does nothing, but you could go to a list of the users.
    }

    @Override
    public boolean onClusterItemClick(PontoDeTroca item) {
        // Does nothing, but you could go into the user's profile page, for example.
        return false;
    }

    @Override
    public void onClusterItemInfoWindowClick(PontoDeTroca item) {
        // Does nothing, but you could go into the user's profile page, for example.
    }

    public static final String TAG = "MapsActivity";
    private final String LAST_LOCATION = "MyLastLocation";

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private EditText txtBusca;
    private Button btnBuscar;

    private LatLng llMeuLugar;

    private ClusterManager<PontoDeTroca> mClusterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        SharedPreferences settings = getSharedPreferences(LAST_LOCATION, 0);
        llMeuLugar = new LatLng(
                Double.parseDouble(settings.getString("LastLatitude", "-38")),
                Double.parseDouble(settings.getString("LastLongitude", "-36")));

        if (GoogleApiAvailability.getInstance() == null)
            Log.d(TAG, "GoogleApiAvailability.getInstance() == null");
        else {
            int isAval = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
            Log.d(TAG, "isAval: " + isAval);
        }

        setUpEvents();

        setUpMapIfNeeded();

        setUpMeuLocalIfNeeded();
    }

    @Override
    protected void onStop() {
        super.onStop();

        llMeuLugar = (getMap().getMyLocation() == null ? new LatLng(-23.6117561, -46.6420428) : new LatLng(getMap().getMyLocation().getLatitude(), getMap().getMyLocation().getLongitude()));

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
        btnBuscar = (Button) findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Buscando: " + String.valueOf(txtBusca.getText()));

                btnBuscarOnClick(v);
            }
        });

        txtBusca = (EditText) findViewById(R.id.txtBusca);
        txtBusca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "Buscar por: " + s);
                btnBuscar.setEnabled(s.length() > 2);
            }
        });
    }

    private void setMapZoomSmooth(float zoom) {
        while (getMap().getCameraPosition().zoom != zoom) {
            float zoomAtual = getMap().getCameraPosition().zoom;
            float zoomNovo = zoomAtual;
            Log.i(TAG, "ZoomAtual: " + zoomAtual);
            if (Math.abs(zoomAtual - zoom) <= 1)
                zoomNovo = zoom;
                //else if (zoomAtual > zoom)
                //    zoomNovo = zoomAtual - (zoomAtual - zoom) * 0.30f;
            else// if (zoomAtual < zoom)
                zoomNovo = zoomAtual - (zoomAtual - zoom) * 0.30f;

            getMap().moveCamera(CameraUpdateFactory.zoomTo(zoomNovo));
            try {
                //Thread.yield();
                Thread.sleep(100, 0);
            } catch (InterruptedException e) {
                Log.e(TAG, "aguardando animação de zoomSmooth:: " + e.getMessage());
            }
        }
    }

    private class SearchAddressTask extends AsyncTask<String, Integer, CameraUpdate> {

        protected void onPreExecute() {
            super.onPreExecute();
            setMapZoomSmooth(getMap().getCameraPosition().zoom - 2);
        }

        protected CameraUpdate doInBackground(String... SearchAddresses) {
            int count = SearchAddresses.length;
            long totalSize = 0;
            CameraUpdate newCamera = null;
            for (int i = 0; i < count; i++) {
                LatLng found = new GeoCoding().getLatLngFromAddress(SearchAddresses[i]);
                newCamera = CameraUpdateFactory.newLatLng(found);
                //totalSize += Downloader.downloadFile(urls[i]);
                publishProgress((int) ((i / (float) count) * 100));
                // Escape early if cancel() is called
                if (isCancelled()) break;
            }
            return newCamera;
        }

        protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        protected void onPostExecute(CameraUpdate result) {
            getMap().moveCamera(result);
            setMapZoomSmooth(getMap().getMaxZoomLevel() - 6);
            //Notificacao.showAlert(getApplicationContext(), "Search " + (result != null ? "done" : "error") + ".").show();
        }
    }

    private void btnBuscarOnClick(View v) {
        //showNotification();
        //getMap().moveCamera(CameraUpdateFactory.zoomTo(getMap().getMinZoomLevel()));
        //getMap().moveCamera(CameraUpdateFactory.newLatLng(new GeoCoding().getLatLngFromAddress(String.valueOf(txtBusca.getText()))));
        //llMeuLugar = (getMap().getMyLocation() == null ? new LatLng(-23.6117561, -46.6420428) : new LatLng(getMap().getMyLocation().getLatitude(), getMap().getMyLocation().getLongitude()));
        //getMap().moveCamera(CameraUpdateFactory.zoomTo(getMap().getMaxZoomLevel()));

        new SearchAddressTask().execute(String.valueOf(txtBusca.getText()));
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
        if (getMap() == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.
            if (getMap() != null) {
                setUpMap();
            }
        }
    }

    private void setUpMeuLocalIfNeeded() {
        try {
            if (getMap() != null)
                llMeuLugar = (getMap().getMyLocation() == null ? new LatLng(-23.611, -46.642) : new LatLng(getMap().getMyLocation().getLatitude(), getMap().getMyLocation().getLongitude()));
        } catch (Exception e) {
            Log.e(TAG, "setUpMeuLocalIfNeeded():: exception message: " + e.getMessage());
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

        getMap().setMyLocationEnabled(true);

        UiSettings uis = getMap().getUiSettings();
        uis.setZoomControlsEnabled(true);
        uis.setZoomGesturesEnabled(true);
        uis.setMyLocationButtonEnabled(true);
        uis.setScrollGesturesEnabled(true);
        uis.setTiltGesturesEnabled(true);
        uis.setCompassEnabled(true);
        uis.setRotateGesturesEnabled(true);
        uis.setMapToolbarEnabled(true);

        getMap().setBuildingsEnabled(false);


        setUpClusterer();

    }

    private GoogleMap getMap() {
        return mMap;
    }

    private void setUpClusterer() {

        // Position the map.
        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-23.6117561, -46.6420428), 8));


        mClusterManager.setRenderer(new PontoDeTrocaRenderer());
        getMap().setOnCameraChangeListener(mClusterManager);
        getMap().setOnMarkerClickListener(mClusterManager);
        getMap().setOnInfoWindowClickListener(mClusterManager);
        mClusterManager.setOnClusterClickListener(this);
        mClusterManager.setOnClusterInfoWindowClickListener(this);
        mClusterManager.setOnClusterItemClickListener(this);
        mClusterManager.setOnClusterItemInfoWindowClickListener(this);

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<PontoDeTroca>(this, getMap());
        mClusterManager.setOnClusterInfoWindowClickListener(new ClusterManager.OnClusterInfoWindowClickListener<PontoDeTroca>() {
            @Override
            public void onClusterInfoWindowClick(Cluster<PontoDeTroca> cluster) {
                Log.e(TAG, "onClusterInfoWindowClick:" + cluster.getPosition().latitude);
            }
        });

//        DefaultClusterRenderer<PontoDeTroca> defRenderer = new DefaultClusterRenderer<PontoDeTroca>(this, getMap(), mClusterManager);
//
//        defRenderer.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<PontoDeTroca>() {
//            @Override
//            public boolean onClusterItemClick(PontoDeTroca pontoDeTroca) {
//                Log.d(TAG, "setOnClusterItemClickListener: "+pontoDeTroca.getPosition().latitude);
//                return false;
//            }
//        });
//
//        mClusterManager.setRenderer(defRenderer);

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        getMap().setOnCameraChangeListener(mClusterManager);

        /*getMap().setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

                new LoadItemsTask().execute(cameraPosition);

            }
        });
        */
        getMap().setOnMarkerClickListener(mClusterManager);

        // Add cluster items (markers) to the cluster manager.
        addItems();
    }

    private class LoadItemsTask extends AsyncTask<CameraPosition, Integer, CameraPosition> {

        @Override
        protected CameraPosition doInBackground(CameraPosition... cameraPosition) {
            //TODO: Buscar pontos no mapa de acordo com a movimentacao de tela.
            // http://www.rotadareciclagem.com.br/site.html?method=carregaEntidades&latMax=-18.808692664927005&lngMax=-31.80579899520876&latMin=-25.010725932824087&lngMin=-54.17868717880251&zoomAtual=7
            LatLngBounds bounds = null;

            while (bounds == null) {
                try {
                    bounds = getMap().getProjection().getVisibleRegion().latLngBounds;
                } catch (Exception e) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    Log.i(TAG, "Map Client not ready yet. waiting for 100ms.");
                }
            }
            double latMax = bounds.northeast.latitude;
            double lngMax = bounds.northeast.longitude;
            double latMin = bounds.southwest.latitude;
            double lngMin = bounds.southwest.longitude;

            try {
                String sURL = "http://www.rotadareciclagem.com.br/site.html?method=carregaEntidades&" +
                        "latMax=" + latMax + "&lngMax=" + lngMax + "&latMin=" + latMin + "&lngMin=" + lngMin + "&zoomAtual=" + cameraPosition[0].zoom;
                Log.i(TAG, "requesting URL: " + sURL);

                URL url = new URL(sURL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    InputSource is = new InputSource(in);
                    XMLFilterImpl xml = new XMLFilterImpl();
                    try {
                        xml.parse(is);

                    } catch (SAXException e) {
                        e.printStackTrace();
                    }
                } finally {
                    urlConnection.disconnect();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return cameraPosition[0];
        }

        @Override
        protected void onPostExecute(CameraPosition cameraPosition) {
            mClusterManager.onCameraChange(cameraPosition);
            mClusterManager.cluster();
        }
    }

    private void addItems() {

        // Set some lat/lng coordinates to start with.
        double lat = -23.6117561;
        double lng = -46.6420428;

        mClusterManager.clearItems();

        // Add ten cluster items in close proximity, for purposes of this example.
        for (int i = 0; i < 30; i++) {
            double offseta = (i / 420d) - (i - 3 % 6) / 90d;
            double offsetb = (i * i * ((i % 2 == 0) ? -0.3 : 0.4)) / 660d;
            lat = lat - offseta;
            lng = lng + offsetb;
            int id = 6819;
            String prefixo = "cooperativa",
                     cData = "Cooperlix "+i+" - Cooperativa de Trabalho, Produção e Reciclagem de Presidente Prudente";

            PontoDeTroca offsetItem = new PontoDeTroca(lat, lng, id, prefixo, cData, R.drawable.marcadorpadrao);

            mClusterManager.addItem(offsetItem);
        }

    }


    /**
     * Copyright 2013 Google Inc.
     * Adapted class from Google Maps demo CustomMarkerClusteringDemoActivity
     */
    private class PontoDeTrocaRenderer extends DefaultClusterRenderer<PontoDeTroca> {
        private final IconGenerator mIconGenerator = new IconGenerator(getApplicationContext());
        private final IconGenerator mClusterIconGenerator = new IconGenerator(getApplicationContext());
        private final ImageView mImageView;
        private final ImageView mClusterImageView;
        private final int mDimension;

        public PontoDeTrocaRenderer() {
            super(getApplicationContext(), getMap(), mClusterManager);

            View multiProfile = getLayoutInflater().inflate(R.layout.multi_profile, null);
            mClusterIconGenerator.setContentView(multiProfile);
            mClusterImageView = (ImageView) multiProfile.findViewById(R.id.image);

            mImageView = new ImageView(getApplicationContext());
            mDimension = (int) getResources().getDimension(R.dimen.custom_profile_image);
            mImageView.setLayoutParams(new ViewGroup.LayoutParams(mDimension, mDimension));
            int padding = (int) getResources().getDimension(R.dimen.custom_profile_padding);
            mImageView.setPadding(padding, padding, padding, padding);
            mIconGenerator.setContentView(mImageView);
        }

        @Override
        protected void onBeforeClusterItemRendered(PontoDeTroca PontoDeTroca, MarkerOptions markerOptions) {
            // Draw a single PontoDeTroca.
            // Set the info window to show their name.
            mImageView.setImageResource(PontoDeTroca.profilePhoto);
            Bitmap icon = mIconGenerator.makeIcon();
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(PontoDeTroca.getCDATA());
        }

        @Override
        protected void onBeforeClusterRendered(Cluster<PontoDeTroca> cluster, MarkerOptions markerOptions) {
            // Draw multiple people.
            // Note: this method runs on the UI thread. Don't spend too much time in here (like in this example).
            List<Drawable> profilePhotos = new ArrayList<Drawable>(Math.min(4, cluster.getSize()));
            int width = mDimension;
            int height = mDimension;

            for (PontoDeTroca p : cluster.getItems()) {
                // Draw 4 at most.
                if (profilePhotos.size() == 4) break;
                Drawable drawable = getResources().getDrawable(p.profilePhoto);
                drawable.setBounds(0, 0, width, height);
                profilePhotos.add(drawable);
            }
            MultiDrawable multiDrawable = new MultiDrawable(profilePhotos);
            multiDrawable.setBounds(0, 0, width, height);

            mClusterImageView.setImageDrawable(multiDrawable);
            Bitmap icon = mClusterIconGenerator.makeIcon(String.valueOf(cluster.getSize()));
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
        }

        @Override
        protected boolean shouldRenderAsCluster(Cluster cluster) {
            // Always render clusters.
            return cluster.getSize() > 1;
        }
    }
}