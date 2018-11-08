package com.alexa4.pseudozoo.activities_package.manual_views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexa4.pseudozoo.R;

public class AnimalsFragment extends Fragment {
    private static final String ANIMAL_URL = "ANIMAL_URL";
    private String mAnimalUrl;
    private TextView mPageText;
    private ImageView mPageImage;
    private ViewPager mPager;
    private FragmentPagerAdapter mAdapter;

    /**
     * Getting web url from args
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAnimalUrl = getArguments().getString(ANIMAL_URL);
    }

    /**
     * Initializing fragment by the web url with animal
     * @param url the web url of page
     * @return the instance of fragment
     */
    public static AnimalsFragment newInstance(String url) {
        AnimalsFragment fragment = new AnimalsFragment();
        Bundle args = new Bundle();
        args.putString(ANIMAL_URL, url);

        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_animals, container, false);

        mPageImage = (ImageView) root.findViewById(R.id.fragment_animals_image);
        mPageImage.setImageDrawable(getResources().getDrawable(R.drawable.instagram));

        mPageText = (TextView) root.findViewById(R.id.fragment_animals_text);
        mPageText.setText("There are text");

        mPager = (ViewPager) root.findViewById(R.id.fragment_animals_view_pager);
        mPager.setAdapter(new AnimalsPagerAdapter(getFragmentManager()));

        return root;
    }


    /**
     * Adapter for ViewPager. It creates instance of TextAnimalsFragment which contains text
     */
    class AnimalsPagerAdapter extends FragmentPagerAdapter {

        public AnimalsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        //TODO: add insert parameter
        @Override
        public Fragment getItem(int position) {
            return TextAnimalsFragment.newInstance("Hi " + position);
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }
    }
}
