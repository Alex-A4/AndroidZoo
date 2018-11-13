package com.alexa4.pseudozoo.activities_package.news_views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alexa4.pseudozoo.R;
import com.alexa4.pseudozoo.activities_package.SettingsFragment;
import com.alexa4.pseudozoo.adapters.BitmapAdapter;
import com.alexa4.pseudozoo.models.ModelNews;
import com.alexa4.pseudozoo.presenter.PresenterNews;
import com.alexa4.pseudozoo.presenter.ViewInterfaceNews;
import com.alexa4.pseudozoo.user_data.news_data.News;
import com.alexa4.pseudozoo.user_data.NightMode;

import java.util.ArrayList;


/**
 * NewsFragment contains list of news, toolbar with settings button
 * It's loading when the app starts
 */
public class NewsFragment extends Fragment implements ViewInterfaceNews {

    private ListView newsList;

    private ConstraintLayout toolbar;
    private ConstraintLayout fragmentMain;
    private TextView mToolbarText;
    private ImageButton mUpdateButton;
    private ArrayList<News> newsArrayList;

    boolean mIsDownloadingError = false;

    private SettingsFragment settingsFragment;
    private PresenterNews presenterNews;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsFragment = new SettingsFragment();
        presenterNews.setView(this);
        presenterNews.updateNewsList();
    }

    @Override
    public void onResume() {
        super.onResume();
        //Getting news list if it was changed
        getNewsList();
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_news, container, false);
        newsList = (ListView) root.findViewById(R.id.news_list);
        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent newsIntent = FullNewsActivity.newIntent(getContext(),
                        newsArrayList.get(position).getFullNewsLink());
                startActivity(newsIntent);
            }
        });

        toolbar = (ConstraintLayout) root.findViewById(R.id.main_fragment_toolbar);
        fragmentMain = (ConstraintLayout) root.findViewById(R.id.fragment_main);

        //Initializing button for updating news
        mUpdateButton = (ImageButton) root.findViewById(R.id.news_fragment_button_update);
        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsDownloadingError = false;
                updateUI();
                presenterNews.updateNewsList();
            }
        });

        mToolbarText = (TextView) root.findViewById(R.id.main_fragment_toolbar_text);

        //Initializing settings button from toolbar
        final ImageView settings = (ImageView) root.findViewById(R.id.main_fragment_toolbar_settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                ft.replace(R.id.container, settingsFragment, String.valueOf(settingsFragment.getClass()));
                ft.addToBackStack(null);
                ft.setCustomAnimations(
                        android.R.animator.fade_in, android.R.animator.fade_out);
                ft.commit();
            }
        });

        //Setting color if the night mode enabled
        setColors();
        updateUI();
        setRetainInstance(true);

        return root;
    }


    /**
     * Updating the UI of some views
     */
    private void updateUI() {
        //If downloading had been broken then hide news list and show update button
        if (mIsDownloadingError) {
            mUpdateButton.setVisibility(View.VISIBLE);
            newsList.setVisibility(View.INVISIBLE);
        } else {
            mUpdateButton.setVisibility(View.INVISIBLE);
            newsList.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Show connecting text when news loading begin
     */
    public void showConnectingText(){
        if (mToolbarText != null)
            mToolbarText.setText(R.string.connecting_text);
    }

    /**
     * Hide connecting text when news loading finish
     */
    public void hideConnectingText(){
        if (mToolbarText != null)
            mToolbarText.setText(R.string.fragment_main_title);
    }



    /**
     * If error occur then show Toast with error
     * @param progress code of error
     */
    public void showErrorConnection(int progress){
        mIsDownloadingError = true;
        //Update UI
        if (mUpdateButton != null) {
            updateUI();
        }

        switch (progress) {
            case ModelNews.Progress.CONNECTING_ERROR:   Toast.makeText(getContext(),
                    R.string.check_internet, Toast.LENGTH_SHORT).show();
                break;
            case ModelNews.Progress.DOWNLOADING_ERROR: Toast.makeText(getContext(),
                    R.string.downloading_error, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * Setting news list from Presenter
     * @param list of news
     */
    public void updateNewsList(ArrayList<News> list){
        this.newsArrayList = list;
        if (newsList != null && newsList.getAdapter() == null)
            newsList.setAdapter(new NewsAdapter(getContext(), R.layout.news_item, newsArrayList));
        mIsDownloadingError = false;
    }

    /**
     * Overloaded method to get newsArrayList from model
     * if news was create
     */
    @Override
    public void getNewsList() {
        //Init list of news if it's not empty
        if (newsArrayList == null)
            presenterNews.updateNewsList();
        if (newsArrayList != null && newsArrayList.size() == 0) {
            this.newsArrayList = presenterNews.getNewsList();
        } else if (newsList != null && newsList.getAdapter() == null && newsArrayList != null)
            newsList.setAdapter(new NewsAdapter(getContext(), R.layout.news_item, newsArrayList));
    }


    /**
     * Overloaded method to set presenterNews on this Fragment
     * @param presenterNews
     */
    @Override
    public void setPresenter(PresenterNews presenterNews) {
        this.presenterNews = presenterNews;
    }


    /**
     * Custom adapter to represent list of news
     */
    private class NewsAdapter extends ArrayAdapter<News> {

        public NewsAdapter(@NonNull Context context, int textViewResourceId,
                           @NonNull ArrayList<News> objects) {
            super(context, textViewResourceId, objects);
        }


        /**
         * Setting the news_item by content of the news
         */
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
            LayoutInflater inflater = getLayoutInflater();
            View news = inflater.inflate(R.layout.news_item, parent, false);

            TextView caption = (TextView) news.findViewById(R.id.news_item_caption);
            caption.setText(newsArrayList.get(position).getCaption());

            TextView time = (TextView) news.findViewById(R.id.news_item_time);
            time.setText(newsArrayList.get(position).getTime());

            ImageView image = (ImageView) news.findViewById(R.id.news_item_image);

            //If image already downloaded then set it else download it by URL
            if (newsArrayList.get(position).getBitmap() != null)
                image.setImageBitmap(newsArrayList.get(position).getBitmap());
            else BitmapAdapter.decodeBitmapFromUrl(
                    newsArrayList.get(position).getImageUrl(), getResources(),
                    new BitmapAdapter.DownloadImageCallback() {
                        @Override
                        public void onDownloadFinished(Bitmap bitmap) {
                            image.setImageBitmap(bitmap);
                            newsArrayList.get(position).setBitmap(bitmap);
                        }
                    });

            TextView description = (TextView) news.findViewById(R.id.news_item_description);
            description.setText(newsArrayList.get(position).getDescription());

            return news;
        }


    }


    /**
     * Setting null value to presenter.view field
     */
    @Override
    public void onDetach() {
        super.onDetach();
        presenterNews.detachView();
    }

    /**
     * Setting colors which depends of nightMode variable
     */
    private void setColors(){
        if (NightMode.getNightMode().getMode()){
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryNight));
            fragmentMain.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLightNight));
        }
    }
}
