package br.com.tisengenharia.tisapp.model;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;

import br.com.tisengenharia.utils.GeoCoding;

/**
 * Created by Gabriel Lucas de Toledo Ribeiro on 02/09/2015.
 * baseado em: {@code https://developers.google.com/maps/documentation/android-api/utility/marker-clustering}
 */
public class PontoDeTroca implements ClusterItem {
    private final LatLng mPosition;
    private final int id;
    private final String prefixo;
    private final String cData;
    public final int profilePhoto;

    private String baloonInfo;
    private String shortAddress;

    public PontoDeTroca(double lat, double lng, int id, String prefixo, String cData, int profilePhoto) {
        this.mPosition = new LatLng(lat, lng);
        this.id = id;
        this.prefixo = prefixo;
        this.cData = cData;
        this.profilePhoto = profilePhoto;
        this.baloonInfo = "";
        this.shortAddress = "";
        //   getBaloonInfo();
    }

    // convert InputStream to String
    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is, Charset.forName("iso-8859-1")));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    public int getId() {
        return id;
    }

    public String getPrefixo() {
        return prefixo;
    }

    public String getCDATA() {
        return cData;
    }

    public String getBaloonInfo() {
        if (baloonInfo.isEmpty()) {
            new LoadBaloonInfoTask(this).execute(0);
        }
        return baloonInfo;
    }

    @Override
    public String toString() {
        return "PontoDeTroca (lat:" + this.getPosition().latitude + ", lng:" + this.getPosition().longitude + ", id:" + getId() + ", prefixo:" + getPrefixo() + ", cData:" + cData + ", profilePhoto:" + profilePhoto + ")";
    }

    public String getShortAddress() {
        if (this.shortAddress.isEmpty())
            this.shortAddress = GeoCoding.getFirstAddressFromLatLng(getPosition(), null);
        return this.shortAddress;
    }

    private class LoadBaloonInfoTask extends AsyncTask<Integer, Integer, Integer> {
        String sURL;
        PontoDeTroca pontoDeTroca;

        public LoadBaloonInfoTask(PontoDeTroca pontoDeTroca) {
            this.pontoDeTroca = pontoDeTroca;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (pontoDeTroca.getId() < 0) {
                cancel(true);
                return;
            }

            sURL = "http://www.rotadareciclagem.com.br/siteAjax.html?method=info&id=" + pontoDeTroca.getId() + "&tipo=" + pontoDeTroca.getPrefixo();

            Log.d("LoadBaloonInfoTask", "ID: " + pontoDeTroca.getId() + " rota URL: " + sURL);

        }

        @Override
        protected void onCancelled() {
            Log.d("LoadBaloonInfoTask", "ID: " + pontoDeTroca.getId() + " Cancelado.");
            super.onCancelled();
        }

        @Override
        protected Integer doInBackground(Integer... params) {

            if (!pontoDeTroca.baloonInfo.isEmpty()) {
                cancel(true);
                return 1;
            }
            if (isCancelled()) return null;

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
                        String str = getStringFromInputStream(zip);
                        //str.indexOf("</head>")+7,
                        try {
                            str = str.replace("\n", "").replace("\t", "");
                            str = str.substring(
                                    str.indexOf("<h4>"), str.indexOf("<a href=\"#\" ")
                            );
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        pontoDeTroca.baloonInfo = str;
//                                str.substring(str.indexOf("<h4>") + 4, str.indexOf("</h4>"));
//                        pontoDeTroca.baloonInfo += str.substring(str.indexOf("</h4><br>")+9, str.indexOf("<br>", str.indexOf("</h4><br>")));
//                        try {
//                            XMLBaloonParser xmlBaloonParser = new XMLBaloonParser();
//
//                            pontoDeTroca.baloonInfo = xmlBaloonParser.parse(zip);
//                        } catch (XmlPullParserException e) {
//                            e.printStackTrace();
//                        }
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
            return 0;
        }

        @Override
        protected void onPostExecute(Integer returnValue) {

            Log.i("LoadBaloonInfoTask", "ID: " + pontoDeTroca.getId() + " Resultado: " + this.pontoDeTroca.getBaloonInfo().length());
        }
    }
}