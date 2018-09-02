package com.alexa4.pseudozoo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * MapFragment represent map where zoo is, which place visitor can visit
 */
public class ZooMapFragment extends Fragment {


 /*   GoogleMap map;

    SupportMapFragment mMapFragment;*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_map, container, false);

        /*FragmentManager fm = getChildFragmentManager();

        mMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map_container);


        if (mMapFragment == null) {
            mMapFragment = SupportMapFragment.newInstance();
        }

        mMapFragment.getMapAsync(new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            map = googleMap;
            map.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Marker"));
            }
        });

        fm.beginTransaction().replace(R.id.map_container, mMapFragment).commit();*/



        return root;
    }
}
