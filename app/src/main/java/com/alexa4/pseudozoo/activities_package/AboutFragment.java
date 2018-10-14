package com.alexa4.pseudozoo.activities_package;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alexa4.pseudozoo.R;
import com.alexa4.pseudozoo.user_data.NightMode;


/**
 * AboutFragment represent information about zoo, and communication methods,
 * toolbar with title and icon of zoo
 */
public class AboutFragment extends Fragment {

    private final String VK_LINK = "https://vk.com/zooyar";
    private final String INSTAGRAM_LINK = "https://www.instagram.com/yaroslavlzoo/";
    private final String FACEBOOK_LINK = "https://www.facebook.com/yaroslavlzoo";
    private final String YOUTUBE_LINK = "https://www.youtube.com/channel/UC_2z1FBwooyqMcUTYrzjyiw/about";

    private ConstraintLayout toolbar;
    private ConstraintLayout fragmentAbout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_about, container, false);

        toolbar = (ConstraintLayout) root.findViewById(R.id.about_fragment_toolbar);
        fragmentAbout = (ConstraintLayout) root.findViewById(R.id.about_fragment_parent);

        //VK social network image
        final ImageView networks_vk = (ImageView) root.findViewById(R.id.networks_vk);
        networks_vk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(VK_LINK));
                startActivity(i);
            }
        });

        //Facebook social network image
        final ImageView networks_facebook = (ImageView) root.findViewById(R.id.networks_facebook);
        networks_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(FACEBOOK_LINK));
                startActivity(i);
            }
        });

        //Instagram social network image
        final ImageView networks_instagram = (ImageView) root.findViewById(R.id.networks_instagram);
        networks_instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(INSTAGRAM_LINK));
                startActivity(i);
            }
        });

        //Youtube social network image
        final ImageView networks_youtube = (ImageView) root.findViewById(R.id.networks_youtube);
        networks_youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(YOUTUBE_LINK));
                startActivity(i);
            }
        });

        return root;
    }


    /**
     * Setting colors of fragment
     */
    @Override
    public void onStart() {
        super.onStart();
        setColors();
    }


    /**
     * Setting colors which depends of nightMode variable
     */
    private void setColors(){
        if (NightMode.getNightMode().getMode()){
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryNight));
            fragmentAbout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLightNight));
        }
    }
}
