package br.com.tisengenharia.tisapp;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

import java.util.ArrayList;
import java.util.List;

import br.com.tisengenharia.tisapp.model.PontoDeTroca;
import br.com.tisengenharia.utils.MultiDrawable;

/**
 * Copyright 2013 Google Inc.
 * Adapted class from Google Maps demo CustomMarkerClusteringDemoActivity
 * by Gabriel Lucas de Toledo Ribeiro
 */
class PontoDeTrocaRenderer extends DefaultClusterRenderer<PontoDeTroca> {

    private final IconGenerator mIconGenerator;
    private final IconGenerator mClusterIconGenerator;
    private final ImageView mImageView;
    private final ImageView mClusterImageView;
    private final int mDimension;
    private int howManyToRenderAsCluster = 15;
    private MapsActivity mapsActivity;

    public PontoDeTrocaRenderer(MapsActivity mapsActivity) {
        super(mapsActivity.getApplicationContext(), mapsActivity.getMap(), mapsActivity.mClusterManager);
        mIconGenerator = new IconGenerator(mapsActivity.getApplicationContext());
        mClusterIconGenerator = new IconGenerator(mapsActivity.getApplicationContext());
        this.mapsActivity=mapsActivity;

        Point size = new Point();
        mapsActivity.getWindowManager().getDefaultDisplay().getSize(size);
        howManyToRenderAsCluster = 3;//(int) (Math.sqrt((size.y * .8) * (size.x * .8)) / 196);

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
            Drawable drawable = mapsActivity.getResources().getDrawable(p.profilePhoto);
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
        return cluster.getSize() > howManyToRenderAsCluster;
    }

}
