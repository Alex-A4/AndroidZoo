package com.alexa4.pseudozoo.activities_package;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.alexa4.pseudozoo.R;
import com.alexa4.pseudozoo.presenter.ViewInterfaceParent;


/**
 * SettingsFragment contains primitive settings which user can use
 */
public class SettingsFragment extends Fragment implements ViewInterfaceParent {

    private ConstraintLayout toolbar;
    private ConstraintLayout settingsFragment;

    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_settings, container, false);

        toolbar = (ConstraintLayout) root.findViewById(R.id.settings_fragment_toolbar);
        settingsFragment = (ConstraintLayout) root.findViewById(R.id.settings_fragment_parent);


        //Night mode switcher
        final Switch night_mode = root.findViewById(R.id.settings_switch_night_mode);
        night_mode.setChecked(nightMode.getMode());
        night_mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Toast.makeText(getContext(), "Night mode enabled", Toast.LENGTH_SHORT).show();
                    nightMode.setMode(true);
                    setColors();
                } else {
                    Toast.makeText(getContext(), "Night mode disabled", Toast.LENGTH_SHORT).show();
                    nightMode.setMode(false);
                    setColors();
                }
            }
        });


        //toolbar's button which is closing current window
        final ImageView backArrow = (ImageView) root.findViewById(R.id.settings_fragment_toolbar_back);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).getSupportFragmentManager().popBackStack();
            }
        });


        return root;
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
            settingsFragment.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLightNight));
        } else {
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            settingsFragment.setBackgroundColor(getResources().getColor(R.color.colorScreenBackground));
        }
        ((MainActivity) getContext()).setColors();
    }
}