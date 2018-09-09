package com.alexa4.pseudozoo.activities_package;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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


/**
 * SettingsFragment contains primitive settings which user can use
 */
public class SettingsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_settings, container, false);

        final Switch night_mode = root.findViewById(R.id.settings_switch_night_mode);
        night_mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Toast.makeText(getContext(), "Night mode enabled", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Night mode disabled", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final ImageView backArrow = (ImageView) root.findViewById(R.id.settings_fragment_toolbar_back);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).getSupportFragmentManager().popBackStack();
            }
        });
        return root;
    }
}
