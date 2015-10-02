package br.com.tisengenharia.utils;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

/**
 * Created by Gabriel Lucas de Toledo Ribeiro on 29/09/2015.
 */
public class PontoDeTrocaClusterRenderer extends DefaultClusterRenderer<PontoDeTroca>{
    public PontoDeTrocaClusterRenderer(Context applicationContext, GoogleMap map, ClusterManager<PontoDeTroca> lClusterManager) {
        super(applicationContext, map, lClusterManager);
    }

/*
    private final IconGenerator mIconGenerator = new IconGenerator(getApplicationContext());
    private final IconGenerator mClusterIconGenerator = new IconGenerator(getApplicationContext());
    private final ImageView mImageView;
    private final ImageView mClusterImageView;
    private final int mDimension;

    public PontoDeTrocaClusterRenderer() {
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
    protected void onBeforeClusterItemRendered(Person person, MarkerOptions markerOptions) {
        // Draw a single person.
        // Set the info window to show their name.
        mImageView.setImageResource(person.profilePhoto);
        Bitmap icon = mIconGenerator.makeIcon();
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(person.name);
    }

    @Override
    protected void onBeforeClusterRendered(Cluster<Person> cluster, MarkerOptions markerOptions) {
        // Draw multiple people.
        // Note: this method runs on the UI thread. Don't spend too much time in here (like in this example).
        List<Drawable> profilePhotos = new ArrayList<Drawable>(Math.min(4, cluster.getSize()));
        int width = mDimension;
        int height = mDimension;

        for (Person p : cluster.getItems()) {
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


*/
}
