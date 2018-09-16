package com.alexa4.pseudozoo.activities_package;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alexa4.pseudozoo.R;
import com.alexa4.pseudozoo.presenter.ViewInterfaceParent;

import java.net.InetAddress;


/**
 * MapFragment represent map where zoo is, which place visitor can visit
 */
public class ZooMapFragment extends Fragment implements ViewInterfaceParent {

    private ConstraintLayout toolbar;
    private ConstraintLayout fragmentMap;

    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_map, container, false);

        toolbar = (ConstraintLayout) root.findViewById(R.id.map_fragment_toolbar);
        fragmentMap = (ConstraintLayout) root.findViewById(R.id.map_fragment_parent);

        return root;
    }


    /**
     * Returns true if internet connection exists
     * @return internet connection status
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    /**
     * If internet connection exists, then show map else throw the Toast
     */
    @Override
    public void onResume() {
        super.onResume();
        if (isNetworkAvailable()) {
            final MapContainer mc = new MapContainer();
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            ft.replace(R.id.map_container, mc);
            ft.setCustomAnimations(
                    android.R.animator.fade_in, android.R.animator.fade_out);
            ft.commit();
        } else {
            Toast toast =  Toast.makeText(getContext(), R.string.check_internet, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        setColors();
    }


    /**
     * Setting colors which depends of nightMode variable
     */
    private void setColors(){
        if (nightMode.getMode()){
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryNight));
            fragmentMap.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLightNight));
        }
    }
}
