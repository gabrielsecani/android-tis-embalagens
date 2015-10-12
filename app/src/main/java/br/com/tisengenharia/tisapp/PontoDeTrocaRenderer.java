package br.com.tisengenharia.tisapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import br.com.tisengenharia.tisapp.model.PontoDeTroca;
import br.com.tisengenharia.utils.MultiDrawable;

/**
 * Copyright 2013 Google Inc.
 * Adapted class from Google Maps demo CustomMarkerClusteringDemoActivity
 * by Gabriel Lucas de Toledo Ribeiro
 */
class PontoDeTrocaRenderer extends DefaultClusterRenderer<PontoDeTroca>
        implements ClusterManager.OnClusterClickListener<PontoDeTroca>, ClusterManager.OnClusterInfoWindowClickListener<PontoDeTroca>, ClusterManager.OnClusterItemClickListener<PontoDeTroca>, ClusterManager.OnClusterItemInfoWindowClickListener<PontoDeTroca> {

    private final IconGenerator mIconGenerator;
    private final IconGenerator mClusterIconGenerator;
    private final ImageView mImageView;
    private final ImageView mClusterImageView;
    private final int mDimension;
    private final MapsActivity mapsActivity;

    public PontoDeTrocaRenderer(MapsActivity mapsActivity) {
        super(mapsActivity.getApplicationContext(), mapsActivity.getMap(), mapsActivity.mClusterManager);
        mIconGenerator = new IconGenerator(mapsActivity.getApplicationContext());
        mClusterIconGenerator = new IconGenerator(mapsActivity.getApplicationContext());
        this.mapsActivity=mapsActivity;

//        Point size = new Point();
//        mapsActivity.getWindowManager().getDefaultDisplay().getSize(size);
        //howManyToRenderAsCluster = (int) (Math.sqrt((size.y * .8) * (size.x * .8)) / 196);

        View multiProfile = mapsActivity.getLayoutInflater().inflate(R.layout.multi_profile, null);
        mClusterIconGenerator.setContentView(multiProfile);
        mClusterIconGenerator.setStyle(android.R.style.Theme_Translucent);

        mClusterImageView = (ImageView) multiProfile.findViewById(R.id.image);

        mImageView = new ImageView(mapsActivity.getApplicationContext());
        mDimension = (int) mapsActivity.getResources().getDimension(R.dimen.custom_profile_image);
        mImageView.setLayoutParams(new ViewGroup.LayoutParams(mDimension, mDimension));
        int padding = (int) mapsActivity.getResources().getDimension(R.dimen.custom_profile_padding);
        mImageView.setPadding(padding, padding, padding, padding);
        mIconGenerator.setContentView(mImageView);

    }

    @Override
    protected void onBeforeClusterItemRendered(PontoDeTroca pontoDeTroca, MarkerOptions markerOptions) {
        // Draw a single PontoDeTroca.
        // Set the info window to show their name.
        if (pontoDeTroca.getId() > 0)
            mImageView.setImageResource(pontoDeTroca.profilePhoto);
        else
            mImageView.setImageResource(R.drawable.marcadorpadraoplus);
        Bitmap icon = mIconGenerator.makeIcon();
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(pontoDeTroca.getCDATA()).snippet(pontoDeTroca.getBaloonInfo());
    }

    @Override
    protected void onBeforeClusterRendered(Cluster<PontoDeTroca> cluster, MarkerOptions markerOptions) {
        // Draw multiple people.
        // Note: this method runs on the UI thread. Don't spend too much time in here (like in this example).
        List<Drawable> profilePhotos = new ArrayList<>(Math.min(4, cluster.getSize()));
        int width = mDimension;
        int height = mDimension;

        for (PontoDeTroca p : cluster.getItems()) {
            // Draw 4 at most.
            if (profilePhotos.size() == 4) break;
            Drawable drawable = null;
            drawable = mapsActivity.getResources().getDrawable(p.profilePhoto);
            assert drawable != null;
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
        return cluster.getSize() > 6;
    }

    /// ClusterManager Events implemented
    @Override
    public boolean onClusterClick(Cluster<PontoDeTroca> cluster) {
        // Click event when this is a Cluster. Show dialog to choose which one to zoom.

        // Show a toast with some info when the cluster is clicked.
//        String firstName = cluster.getItems().iterator().next().getCDATA();
//        Toast.makeText(this, "(" + firstName + ", ...)", Toast.LENGTH_SHORT).show();
//        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(cluster.getPosition(), getMap().getCameraPosition().zoom + 1));
        //Toast.makeText(mapsActivity, "PontodeTrocaRednde.onClusterClick size:" + cluster.getSize() + " id:" + cluster.getItems().iterator().next().getId(), Toast.LENGTH_SHORT);

        final Collection<PontoDeTroca> array_cluster = cluster.getItems();
        ArrayList<CharSequence> listaCharSequences = new ArrayList<>(array_cluster.size());
        for (PontoDeTroca pdt : array_cluster) {
            listaCharSequences.add(pdt.getCDATA() + " (" + pdt.getShortAddress() + ")");
        }

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mapsActivity).setTitle(mapsActivity.getString(R.string.info_window_cluster_tittle))
                .setAdapter(new ArrayAdapter(mapsActivity, android.R.layout.simple_list_item_1, listaCharSequences), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PontoDeTroca pt = (PontoDeTroca) array_cluster.toArray()[which];
                        mapsActivity.zoom = mapsActivity.getMap().getCameraPosition().zoom - ((pt.getCDATA().isEmpty()) ? 1 : 2);

                        mapsActivity.getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(pt.getPosition(), mapsActivity.zoom));
                        mapsActivity.mClusterManager.cluster();
                    }
                })
                .setNegativeButton(R.string.info_window_negative_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.create().show();

        return true;
    }

    @Override
    public void onClusterInfoWindowClick(Cluster<PontoDeTroca> cluster) {
        // Does nothing, but you could go to a list of the cluster item
//        StringBuilder lista = new StringBuilder(cluster.getSize());
//        for (PontoDeTroca pt : cluster.getItems()) {
//            lista.append(pt.getCDATA() + "<br> ");
//        }
//
//        Toast.makeText(mapsActivity, lista.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onClusterItemClick(PontoDeTroca item) {
        // Click event when is not a cluster. Should render InfoWindow about item.
        //Toast.makeText(mapsActivity, "onClusterItemClick " + item.getPrefixo() + item.getBaloonInfo(), Toast.LENGTH_LONG).show();
        MapsActivity.LoadItemsTaskControle = new Date().getTime();
        if (item.getId() < 0) {
            Toast.makeText(mapsActivity, "Grupo de " + Math.abs(item.getId()) + " " + item.getPrefixo() + "s", Toast.LENGTH_SHORT).show();
//            mapsActivity.getMap().moveCamera(
//                    CameraUpdateFactory.newLatLngZoom(getMarker(item).getPosition(),
//                            mapsActivity.getMap().getCameraPosition().zoom + 1));
        } else {
            View infoView = mapsActivity.getLayoutInflater().inflate(R.layout.custominfoview, null);
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mapsActivity)
                    //.setTitle(mapsActivity.getString(R.string.info_window_cluster_item_tittle))
                    .setView(infoView)
                    .setNegativeButton(R.string.info_window_negative_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

//            ImageView icon = (ImageView) infoView.findViewById(R.id.custominfoview_icon);
//            icon.setImageResource(item.profilePhoto);
//            TextView tvTitle = ((TextView) infoView.findViewById(R.id.custominfoview_title));
//            tvTitle.setText(item.getCDATA());
//            TextView tvStreetAddress = ((TextView) infoView.findViewById(R.id.custominfoview_streetAddress));
//            tvStreetAddress.setText(item.getShortAddress());
            TextView tvBallon = ((TextView) infoView.findViewById(R.id.custominfoview_baloon));
            tvBallon.setText(Html.fromHtml(item.getBaloonInfo()));
            alertDialog.create().show();

        }
        return false;
    }

    @Override
    public void onClusterItemInfoWindowClick(PontoDeTroca item) {
        //Toast.makeText(mapsActivity, "onClusterItemInfoWindowClick " + item.getPrefixo() + item.getBaloonInfo(), Toast.LENGTH_LONG).show();
        MapsActivity.LoadItemsTaskControle = new Date().getTime();

    }
}
