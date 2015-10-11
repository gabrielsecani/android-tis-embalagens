package br.com.tisengenharia.tisapp;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.model.GeocodingResult;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.zip.GZIPInputStream;

import br.com.tisengenharia.tisapp.model.PontoDeTroca;
import br.com.tisengenharia.utils.GeoCoding;
import br.com.tisengenharia.utils.XMLMapMarkerParser;

public class MapsActivity extends FragmentActivity
        implements ClusterManager.OnClusterClickListener<PontoDeTroca>, ClusterManager.OnClusterInfoWindowClickListener<PontoDeTroca>, ClusterManager.OnClusterItemClickListener<PontoDeTroca>, ClusterManager.OnClusterItemInfoWindowClickListener<PontoDeTroca> {

    public static final String TAG = "MapsActivity";
    private final String LAST_LOCATION = "MyLastLocation";

    // Objects from the layout
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private EditText txtBusca;
    private Button btnBuscar;
    private RelativeLayout pnlBuscar;
    private Spinner spnLista;
    //

    private LatLng llMeuLugar;

    protected static long LoadItemsTaskControle = new Date().getTime();
    private double latMax = 0;
    private double latMin = 0;
    private double lngMax = 0;
    private double lngMin = 0;

    public ClusterManager<PontoDeTroca> mClusterManager;

    // Whether the display should be refreshed.
    public static boolean refreshDisplay = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        refreshDisplay = true;
        setContentView(R.layout.activity_maps);

        SharedPreferences settings = getSharedPreferences(LAST_LOCATION, 0);
        llMeuLugar = new LatLng(
                Double.parseDouble(settings.getString("LastLatitude", "-23.6117561")),
                Double.parseDouble(settings.getString("LastLongitude", "-46.6420428")));

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

        if (getMap() != null)
            if (getMap().getMyLocation() != null)
                llMeuLugar = (getMap().getMyLocation() == null ? new LatLng(-23.6117561, -46.6420428) : new LatLng(getMap().getMyLocation().getLatitude(), getMap().getMyLocation().getLongitude()));

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(LAST_LOCATION, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("LastLatitude", String.valueOf(llMeuLugar.latitude));
        editor.putString("LastLongitude", String.valueOf(llMeuLugar.longitude));

        editor.apply();
        // Commit the edits!
        editor.commit();

        super.onStop();

    }

    private double zoom = 0;

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

        spnLista = (Spinner) findViewById(R.id.spnLista);
        spnLista.setVisibility(View.GONE);
        spnLista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                GeocodingResult result = (GeocodingResult) parent.getAdapter().getItem(position);
                txtBusca.setText(result.formattedAddress);
                getMap().moveCamera(CameraUpdateFactory.newLatLng(
                        new LatLng(result.geometry.location.lat, result.geometry.location.lng)));
                pnlBuscar.setVisibility(View.VISIBLE);
                spnLista.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                pnlBuscar.setVisibility(View.VISIBLE);
                spnLista.setVisibility(View.GONE);
            }
        });
//        spnLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });

        pnlBuscar = (RelativeLayout) findViewById(R.id.pnlBuscar);

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

    public GoogleMap getMap() {
        return mMap;
    }

    private void setUpClusterer() {

        // Position the map.
        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-23.6117561, -46.6420428), 8));


        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<PontoDeTroca>(this, getMap());

        mClusterManager.setRenderer(new PontoDeTrocaRenderer(this));
        getMap().setOnCameraChangeListener(mClusterManager);
        getMap().setOnMarkerClickListener(mClusterManager);
        getMap().setOnInfoWindowClickListener(mClusterManager);
        mClusterManager.setOnClusterClickListener(this);
        mClusterManager.setOnClusterItemClickListener(this);
        mClusterManager.setOnClusterItemInfoWindowClickListener(this);
        mClusterManager.setOnClusterInfoWindowClickListener(this);

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
//        getMap().setOnCameraChangeListener(mClusterManager);
//
        getMap().setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

                new LoadItemsTask().execute(1);
                mClusterManager.onCameraChange(cameraPosition);
            }
        });

        getMap().setOnMarkerClickListener(mClusterManager);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (refreshDisplay) {
            new LoadItemsTask().execute(0);
        }
    }

    @Override
    public boolean onClusterClick(Cluster<PontoDeTroca> cluster) {

        // Show a toast with some info when the cluster is clicked.
        String firstName = cluster.getItems().iterator().next().getCDATA();
        Toast.makeText(this, cluster.getSize() + " (" + firstName + ", ...)", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onClusterInfoWindowClick(Cluster<PontoDeTroca> cluster) {
        // Does nothing, but you could go to a list of the users.
        StringBuilder lista = new StringBuilder(cluster.getSize());
        for (PontoDeTroca pt : cluster.getItems()) {
            lista.append(pt.getCDATA() + "<br> ");
        }
        Toast.makeText(MapsActivity.this, lista.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onClusterItemClick(PontoDeTroca item) {
        Toast.makeText(MapsActivity.this, "clitem "+ item.getPrefixo()+item.getBaloonInfo(), Toast.LENGTH_LONG).show();

        return true;
    }

    @Override
    public void onClusterItemInfoWindowClick(PontoDeTroca item) {
        Toast.makeText(MapsActivity.this, "clitem-infownd "+ item.getPrefixo()+item.getBaloonInfo(), Toast.LENGTH_LONG).show();

    }

    @Deprecated
    private void addItems() {

        // Set some lat/lng coordinates to start with.
        double lat = -23.6117561;
        double lng = -46.6420428;

        mClusterManager.clearItems();

        // Add ten cluster items in close proximity, for purposes of this example.
        for (int i = 0; i < 10; i++) {
            double offseta = (i / 420d) - (i - 3 % 6) / 90d;
            double offsetb = (i * i * ((i % 2 == 0) ? -0.3 : 0.4)) / 660d;
            lat = lat - offseta;
            lng = lng + offsetb;
            int id = 6819;
            String prefixo = "cooperativa",
                    cData = "Demo " + i + ": " + new Date().getTime() % 10000;

            PontoDeTroca offsetItem = new PontoDeTroca(lat, lng, id, prefixo, cData, R.drawable.marcadorpadrao);

            mClusterManager.addItem(offsetItem);
        }

    }

    private class SearchAddressTask extends AsyncTask<String, Integer, GeocodingResult[]> {

        protected void onPreExecute() {
            super.onPreExecute();
            setMapZoomSmooth(getMap().getCameraPosition().zoom - 2);

        }

        protected GeocodingResult[] doInBackground(String... SearchAddresses) {
            int count = SearchAddresses.length;
            CameraUpdate newCamera = null;
            if (SearchAddresses.length > 0)
                return GeoCoding.getGeocodingResultFromAddress(SearchAddresses[0]);
            else
                return null;
        }

        protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        protected void onPostExecute(GeocodingResult[] results) {
            Toast msg = Toast.makeText(MapsActivity.this, ".", Toast.LENGTH_SHORT);
            ;
            msg.show();

            CameraUpdate newCamera = CameraUpdateFactory.scrollBy(0, 0);
            if (results != null) {
                msg.setText("..");
                if (results.length == 1) {
                    newCamera = CameraUpdateFactory.newLatLng(
                            new LatLng(results[0].geometry.location.lat, results[0].geometry.location.lng));
                } else {
                    spnLista.setAdapter(
                            new ArrayAdapter<GeocodingResult>(MapsActivity.this,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    results));

                    msg.setText("...");
                    pnlBuscar.setVisibility(View.GONE);
                    spnLista.setVisibility(View.VISIBLE);
                    msg.setText("....");
                }
            } else
                msg.setText("Nenhum resultado encotrado...");
            getMap().moveCamera(newCamera);
            setMapZoomSmooth(getMap().getMaxZoomLevel() - 6);
            getMap().moveCamera(newCamera);
        }
    }

    private class LoadItemsTask extends AsyncTask<Integer, Integer, List<PontoDeTroca>> {
        String sURL;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            double lzoom = getMap().getCameraPosition().zoom;

            if ((new Date().getTime() - LoadItemsTaskControle <= 4000) &&
                    (lzoom == zoom)) {
                Log.v("LoadItemsTask", "rerun time control to dont run over and over again successively: " + (new Date().getTime() - LoadItemsTaskControle));
                cancel(true);
                return;
            } else if (Math.abs(lzoom - zoom) < 0.6 && lzoom != zoom) {
                Log.v("LoadItemsTask", "zoom was not enough to reload: " + lzoom + "/" + zoom);
                this.cancel(true);
                return;
            }

            // https://developers.google.com/android/reference/com/google/android/gms/maps/model/VisibleRegion
            LatLngBounds vb = getMap().getProjection().getVisibleRegion().latLngBounds;

//            if ((Math.abs(latMax - vb.northeast.latitude) < latMax*0.2) ||
//                    (Math.abs(lngMin - vb.southwest.longitude) < lngMin*0.2) ||
//                    (Math.abs(latMin - vb.southwest.latitude) < latMin*0.2) ||
//                    (Math.abs(lngMax - vb.northeast.longitude) < lngMax*0.2)) {
//                Log.v("LoadItemsTask", "Visible Region not changed too much: latMax:" + Math.abs(latMax - vb.northeast.latitude) +
//                        "lngMin:" + Math.abs(lngMin - vb.southwest.longitude) +
//                        "latMin:" + Math.abs(latMin - vb.southwest.latitude) +
//                        "lngMax:" + Math.abs(lngMax - vb.northeast.longitude));
//                this.cancel(true);
//                return;
//            }
            LoadItemsTaskControle = new Date().getTime();
            zoom = lzoom;


            latMax = vb.northeast.latitude;
            lngMin = vb.southwest.longitude;
            latMin = vb.southwest.latitude;
            lngMax = vb.northeast.longitude;

            // adiciona um margem para os pontos carregados na tela
            double margem = 0.15;
            latMax = latMax + (latMax - latMin) * margem;
            latMin = latMin - (latMax - latMin) * margem;
            lngMax = lngMax + (lngMax - lngMin) * margem;
            lngMin = lngMin - (lngMax - lngMin) * margem;

            sURL = "http://www.rotadareciclagem.com.br/site.html?method=carregaEntidades&" +
                    "latMax=" + latMax + "&lngMax=" + lngMax + "&latMin=" + latMin + "&lngMin=" + lngMin + "&zoomAtual=" + String.valueOf(zoom).replace(".0", "");
            //sURL = "http://www.rotadareciclagem.com.br/site.html?method=carregaEntidades&latMax=-23.03870267611196&lngMax=-46.288739297683264&latMin=-24.224626557802793&lngMin=-47.37381772785905&zoomAtual=12";

            Log.d("LoadItemsTask", " rota URL: " + sURL);

        }

        @Override
        protected void onCancelled() {
            Log.d("LoadItemsTask", "Cancelado.");
            super.onCancelled();
        }

        @Override
        protected List<PontoDeTroca> doInBackground(Integer... params) {
            //TODO: Buscar pontos no mapa de acordo com a movimentacao de tela.
            // http://www.rotadareciclagem.com.br/site.html?method=carregaEntidades&latMax=-18.808692664927005&lngMax=-31.80579899520876&latMin=-25.010725932824087&lngMin=-54.17868717880251&zoomAtual=7

            List<PontoDeTroca> items = null;
            if (isCancelled()) return null;
            //items = XMLMapMarkerParser.makeATest();
//            StringReader sr = new StringReader(getString(R.string.markersxml));
//            XMLMapMarkerParser xmlMapMarkerParser = new XMLMapMarkerParser();
//            try {
//                items = xmlMapMarkerParser.parse(sr);
//            } catch (XmlPullParserException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            try {

                URL url = new URL(sURL);
                // Given a string representation of a URL, sets up a connection and gets
                // an input stream.
                HttpURLConnection urlConnection = null;
                try {
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setReadTimeout(10000 /* milliseconds */);
                    urlConnection.setConnectTimeout(15000 /* milliseconds */);
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setDoInput(true);
                    // Starts the query
                    urlConnection.connect();
                    if (isCancelled()) return null;
                    InputStream zip = null;
                    try {
                        zip = (GZIPInputStream) urlConnection.getContent();
                        try {
                            XMLMapMarkerParser xmlMapMarkerParser = new XMLMapMarkerParser();

                            items = xmlMapMarkerParser.parse(zip);

                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        }
                    } finally {
                        if (zip != null)
                            zip.close();
                    }

                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return items;
        }

        @Override
        protected void onPostExecute(List<PontoDeTroca> returnValue) {

            Log.i("LoadItemsTask", "Foram encontrados " + returnValue.size() + " marcadores");
            if (returnValue != null) {
                if (returnValue.size() > 0) {
                    mClusterManager.clearItems();
                    mClusterManager.addItems(returnValue);
                    Log.d("LoadItemsTask", "itens adicionados ao cluster manager. Re-clustering...");
                    mClusterManager.cluster();
                }
            } else {
                try {
                    Thread.sleep(100, 0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("LoadItemsTask", "tentando executar novamente");
                new LoadItemsTask().execute(0);
            }
            LoadItemsTaskControle = 0;
        }
    }

}