package com.alexa4.pseudozoo.activities_package;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexa4.pseudozoo.R;
import com.alexa4.pseudozoo.user_data.ApiKey;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;

public class MapContainer extends Fragment {
    private MapView mapview;

    //Don't forget to change ApiKey to API_KEY before commit
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapKitFactory.setApiKey (ApiKey.API_KEY);
        MapKitFactory.initialize(this.getContext());
    }


    /**
     * Creating map due to Yandex mapkit
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.mapfragment, container, false);

        mapview = (MapView)root.findViewById (R. id.mapview);
        mapview.getMap ().move(
                new CameraPosition(new Point(57.677282, 39.90008), 20.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);

        return root;
    }

    @Override
    public void onStop() {
        super.onStop();
        mapview.onStop();
        System.out.println("OnStop");
        MapKitFactory.getInstance().onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapview.onStart();
        System.out.println("OnStart");
        MapKitFactory.getInstance().onStart();
    }
}
