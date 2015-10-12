package br.com.tisengenharia.tisapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.model.GeocodingResult;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.GZIPInputStream;

import br.com.tisengenharia.tisapp.model.PontoDeTroca;
import br.com.tisengenharia.utils.GeoCoding;
import br.com.tisengenharia.utils.XMLMapMarkerParser;

public class MapsActivity extends FragmentActivity {

    public static final String TAG = "MapsActivity";
    private final String LAST_LOCATION = "MyLastLocation";

    // Objects from the layout
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private EditText txtBusca;
    private Button btnBuscar;

    //

    private LatLng llMeuLugar;
    public static long LoadItemsTaskControle = new Date().getTime();
    float zoom = 0;
    private double latMax = 0;
    private double latMin = 0;
    private double lngMax = 0;
    private double lngMin = 0;
    private Marker myMarker;
    private Marker mySearch;

    public ClusterManager<PontoDeTroca> mClusterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


        setUpMapIfNeeded();

        setUpMeuLocalIfNeeded();

        setUpEvents();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");

    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop()");

        if (getMap() != null)
            if (getMap().getMyLocation() != null)
                //llMeuLugar = (getMap().getMyLocation() == null ? new LatLng(-23.6117561, -46.6420428) : new LatLng(getMap().getMyLocation().getLatitude(), getMap().getMyLocation().getLongitude()));
                llMeuLugar = getMap().getCameraPosition().target;
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(LAST_LOCATION, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("LastLatitude", String.valueOf(llMeuLugar.latitude));
        editor.putString("LastLongitude", String.valueOf(llMeuLugar.longitude));
        editor.putString("LastZoom", String.valueOf(getMap().getCameraPosition().zoom));

        editor.apply();
        // Commit the edits!
        editor.commit();

        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
        setUpMapIfNeeded();
        SharedPreferences settings = getSharedPreferences(LAST_LOCATION, 0);
        llMeuLugar = new LatLng(
                Double.parseDouble(settings.getString("LastLatitude", "-23.6117561")),
                Double.parseDouble(settings.getString("LastLongitude", "-46.6420428")));
        float zmm = Float.parseFloat(settings.getString("LastZoom", "10"));
        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(llMeuLugar, zmm));

        new LoadItemsTask(true).execute(2);
    }

    private void setUpEvents() {

        btnBuscar = (Button) findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Buscando: " + String.valueOf(txtBusca.getText()));
                new SearchAddressTask().execute(String.valueOf(txtBusca.getText()));
            }
        });

        txtBusca = (EditText) findViewById(R.id.txtBusca);
        txtBusca.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return btnBuscar.callOnClick();
            }
        });
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


            myMarker = getMap().addMarker(new MarkerOptions()
                            .position(llMeuLugar)
                            .title(getString(R.string.mymarker_title))
                            .snippet(getString(R.string.mymarker_snippet))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            );
            mySearch = getMap().addMarker(new MarkerOptions()
                            .position(llMeuLugar)
                            .title(getString(R.string.searchmarker_title))
                            .snippet(getString(R.string.searchmarker_snippet))
                            .visible(false)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            );

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


        getMap().setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                myMarker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
                //getMap().moveCamera(CameraUpdateFactory.newLatLng(myMarker.getPosition()));

            }
        });

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
        mClusterManager = new ClusterManager<>(this, getMap());
        PontoDeTrocaRenderer pontoDeTrocaRenderer = new PontoDeTrocaRenderer(this);
        mClusterManager.setRenderer(pontoDeTrocaRenderer);
        getMap().setOnCameraChangeListener(mClusterManager);
        getMap().setOnMarkerClickListener(mClusterManager);
        getMap().setOnInfoWindowClickListener(mClusterManager);
//        getMap().setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
//            @Override
//            public View getInfoWindow(Marker marker) {
//                return null;
//            }
//
//            @Override
//            public View getInfoContents(Marker marker) {
//                View myContentView = getLayoutInflater().inflate(R.layout.custommarker, null);
//                TextView tvTitle = ((TextView) myContentView.findViewById(R.id.custommarker_title));
//                tvTitle.setText(marker.getTitle());
//                TextView tvSnippet = ((TextView) myContentView.findViewById(R.id.custommarker_snippet));
//                tvSnippet.setText(marker.getSnippet());
//                ImageView icon = (ImageView) myContentView.findViewById(R.id.custommarker_icon);
//
//                if (mClusterManager.getClusterMarkerCollection().getMarkers().contains(marker)) {
//                    icon.setImageResource(R.drawable.marcadorpadraoplus);
//                }
//
//                return myContentView;
//            }
//        });

        mClusterManager.setOnClusterClickListener(pontoDeTrocaRenderer);
        mClusterManager.setOnClusterItemClickListener(pontoDeTrocaRenderer);
        mClusterManager.setOnClusterItemInfoWindowClickListener(pontoDeTrocaRenderer);
        mClusterManager.setOnClusterInfoWindowClickListener(pontoDeTrocaRenderer);

        getMap().setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

                new LoadItemsTask().execute(1);
                mClusterManager.onCameraChange(cameraPosition);
            }
        });

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

    @Deprecated
    private void setMapZoomSmooth(float zoom) {
        while (getMap().getCameraPosition().zoom != zoom) {
            LoadItemsTaskControle = new Date().getTime();
            float zoomAtual = getMap().getCameraPosition().zoom;
            float zoomNovo;
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

    private class SearchAddressTask extends AsyncTask<String, Integer, GeocodingResult[]> {
        ProgressDialog pd;

        protected void onPreExecute() {
            super.onPreExecute();
//            setMapZoomSmooth(getMap().getCameraPosition().zoom - 2);
            pd = new ProgressDialog(MapsActivity.this);
            pd.setIndeterminate(true);
            pd.setMessage(getString(R.string.searchingFor) + " " + String.valueOf(txtBusca.getText()));
            pd.show();
            mySearch.setVisible(true);
        }

        protected GeocodingResult[] doInBackground(String... SearchAddresses) {
            if (SearchAddresses.length > 0)
                return GeoCoding.getGeocodingResultFromAddress(SearchAddresses[0]);
            else
                return null;
        }

        protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        protected void onPostExecute(final GeocodingResult[] results) {

            try {

                if (results != null) {
                    if (results.length == 0) {
                        pd.setMessage(getString(R.string.search_nodatafound));
                        try {
                            Thread.sleep(700, 1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        pd.dismiss();
                    } else if (results.length == 1) {
                        mySearch.setPosition(new LatLng(results[0].geometry.location.lat, results[0].geometry.location.lng));
                        mySearch.setVisible(true);
                        CameraUpdate newCamera = CameraUpdateFactory.newLatLngZoom(
                                mySearch.getPosition(), getMap().getMaxZoomLevel() - 2);
                        getMap().moveCamera(newCamera);
                    } else if (results.length > 1) {
                        pd.setMessage(getString(R.string.search_toomanyfound));

                        ArrayList<CharSequence> SearchAddressTaskResults = new ArrayList<>(results.length);
                        for (GeocodingResult res : results)
                            SearchAddressTaskResults.add(res.formattedAddress);

                        ArrayAdapter<CharSequence> arrayAdapter = new ArrayAdapter<>(MapsActivity.this,
                                android.R.layout.simple_list_item_1, SearchAddressTaskResults);

                        //SearchAddressTaskResults = (GeocodingResultForPickList[]) lista.toArray();
                        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                        builder.setTitle(R.string.pick_one)
                                .setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        GeocodingResult result = results[which];
                                        txtBusca.setText(result.formattedAddress);
                                        CameraUpdate newCamera = CameraUpdateFactory.newLatLngZoom(
                                                new LatLng(results[which].geometry.location.lat, result.geometry.location.lng),
                                                getMap().getMaxZoomLevel()-4);
                                        getMap().moveCamera(newCamera);
                                    }
                                }).setNegativeButton(R.string.info_window_negative_button, null);
//                            .setItems((CharSequence[]) lista.toArray(), new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    newCamera = CameraUpdateFactory.newLatLng(
//                                            new LatLng(results[0].geometry.location.lat, results[0].geometry.location.lng));
//                                    getMap().moveCamera(newCamera);
//                                }
//                            });
                        AlertDialog pick = builder.create();
                        pick.show();
                        //spnLista.setAdapter(arrayAdapter);

//                    pnlBuscar.setVisibility(View.GONE);
//                    spnLista.setVisibility(View.VISIBLE);
                    }
                } else
                    pd.setMessage(getString(R.string.search_error));
//            getMap().moveCamera(newCamera);
//            setMapZoomSmooth(getMap().getMaxZoomLevel() - 6);
//            getMap().moveCamera(newCamera);

            } finally {
                pd.dismiss();

            }
        }
    }

    private class LoadItemsTask extends AsyncTask<Integer, Integer, List<PontoDeTroca>> {
        String sURL;
        private final boolean blForceRun;

        public LoadItemsTask() {
            this.blForceRun = false;
        }

        public LoadItemsTask(boolean blForceRun) {
            this.blForceRun = blForceRun;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //TODO: Check to get access do Intenet
            float lzoom = getMap().getCameraPosition().zoom;

            if (!blForceRun) {
                if ((new Date().getTime() - LoadItemsTaskControle <= 2000)) {
                    Log.v("LoadItemsTask", "rerun time control to dont run over and over again successively: " + (new Date().getTime() - LoadItemsTaskControle));
                    cancel(true);
                    return;
                }
                if (Math.abs(lzoom - zoom) < 0.6 && lzoom != zoom) {
                    Log.v("LoadItemsTask", "zoom was not enough to reload: " + lzoom + "/" + zoom);
                    this.cancel(true);
                    return;
                }
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
            // Buscar pontos no mapa de acordo com a movimentacao de tela.
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

            if (returnValue != null) {
                Log.i("LoadItemsTask", "Foram encontrados " + returnValue.size() + " marcadores");
                if (returnValue.size() > 0) {
                    mClusterManager.clearItems();
                    mClusterManager.addItems(returnValue);
                    Log.d("LoadItemsTask", "itens adicionados ao cluster manager. Re-clustering...");
                    mClusterManager.cluster();

                }
            } else {
                Log.i("LoadItemsTask", "Reiniciando busca de marcadores dentro de alguns instantes");
                try {
                    Thread.sleep(100, 0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.d("LoadItemsTask", "tentando executar novamente");
                }
                new LoadItemsTask().execute(0);
            }
            LoadItemsTaskControle = 0;
        }
    }

}