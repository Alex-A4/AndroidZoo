package com.alexa4.pseudozoo.activities_package.manual_views;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexa4.pseudozoo.R;
import com.alexa4.pseudozoo.adapters.BitmapAdapter;
import com.alexa4.pseudozoo.adapters.ImageCompressor;
import com.alexa4.pseudozoo.models.ModelManual;
import com.alexa4.pseudozoo.user_data.manual_data.Animal;

public class AnimalsFragment extends Fragment {
    private static final String ANIMAL_URL = "ANIMAL_URL";
    private String mAnimalUrl;
    private TextView mPageText;
    private TextView toolbarText;
    private ImageView mPageImage;
    private ViewPager mPager;
    private Animal mAnimal;
    private AnimalsPagerAdapter mAdapter;

    /**
     * Getting web url from args
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAnimalUrl = getArguments().getString(ANIMAL_URL);

        //Downloading info
        ModelManual.downloadAnimalInfo(mAnimalUrl, new ModelManual.DownloadAnimalInfoCallback() {
            @Override
            public void sendResult(Animal animal) {
                mAnimal = animal;
                updateData();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        updateData();
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

        mPageText = (TextView) root.findViewById(R.id.fragment_animals_text);

        mPager = (ViewPager) root.findViewById(R.id.fragment_animals_view_pager);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            /**
             * Updating Pager height to accommodate whole text into the fragment
             * TODO: change the call of method if not works
             * @param position
             */
            @Override
            public void onPageSelected(int position) {
                mPager.invalidate();
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

        toolbarText = (TextView) root.findViewById(R.id.fragment_animals_toolbar_text);

        ImageView backArrow = (ImageView) root.findViewById(R.id.fragment_animals_toolbar_image);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        return root;
    }


    /**
     * Adapter for ViewPager. It creates instance of TextAnimalsFragment which contains text
     */
    private class AnimalsPagerAdapter extends FragmentPagerAdapter {

        public AnimalsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return TextAnimalsFragment.newInstance(mAnimal.getParamText().get(position));
        }

        @Override
        public int getCount() {
            return mAnimal.getParamNames().size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mAnimal.getParamNames().get(position);
        }
    }

    /**
     * Update data about animal
     */
    private void updateData() {
        if (mAdapter == null && mAnimal != null) {
            mAdapter = new AnimalsPagerAdapter(getFragmentManager());
            mPager.setAdapter(mAdapter);
            mPageText.setText(mAnimal.getDescription());
            toolbarText.setText(mAnimal.getName());
            BitmapAdapter.decodeBitmapFromUrl(mAnimal.getImageUrl(), getResources(), true,
                    new BitmapAdapter.DownloadImageCallback() {
                        @Override
                        public void onDownloadFinished(Bitmap bitmap) {
                            mPageImage.setImageBitmap(bitmap);
                        }
                    });
        }
    }
}
