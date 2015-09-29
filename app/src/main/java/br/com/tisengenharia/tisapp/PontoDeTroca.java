package br.com.tisengenharia.tisapp;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by Gabriel Lucas de Toledo Ribeiro on 02/09/2015.
 * baseado em: {@code https://developers.google.com/maps/documentation/android-api/utility/marker-clustering}
 */
public class PontoDeTroca implements ClusterItem {
    private final LatLng mPosition;
    private final int id;
    private final String prefixo;
    private final String cData;
    private String baloonInfo;

    public PontoDeTroca(double lat, double lng, int id, String prefixo, String cData) {
        this.mPosition = new LatLng(lat, lng);
        this.id = id;
        this.prefixo = prefixo;
        this.cData = cData;

    }

    @Override
    public LatLng getPosition() { return mPosition; }

    public int getId(){ return id; }

    public String getPrefixo() { return prefixo; }

    public String getCDATA() { return cData; }

    public String getBaloonInfo() {
        if(baloonInfo.isEmpty()){
            // TODO: Buscar informacoes desta marca
            // http://www.rotadareciclagem.com.br/siteAjax.html?method=info&id=-43&tipo=comercio
        }
        return baloonInfo;
    }

}