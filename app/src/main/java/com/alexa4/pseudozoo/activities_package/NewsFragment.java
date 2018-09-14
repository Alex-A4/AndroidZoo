package com.alexa4.pseudozoo.activities_package;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alexa4.pseudozoo.R;
import com.alexa4.pseudozoo.adapters.BitmapAdapter;
import com.alexa4.pseudozoo.models.ModelNews;
import com.alexa4.pseudozoo.presenter.PresenterNews;
import com.alexa4.pseudozoo.presenter.ViewInterfaceNews;
import com.alexa4.pseudozoo.user_data.News;

import java.util.ArrayList;


/**
 * NewsFragment contains list of news, toolbar with settings button
 * It's loading when the app starts
 */
public class NewsFragment extends Fragment implements ViewInterfaceNews {
    private ListView newsList;

    private ConstraintLayout toolbar;
    private ConstraintLayout fragmentMain;
    private TextView connectingText;
    private ArrayList<News> newsArrayList;

    private PresenterNews presenterNews;

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_news, container, false);

        newsList = (ListView) root.findViewById(R.id.news_list);
        toolbar = (ConstraintLayout) root.findViewById(R.id.main_fragment_toolbar);
        fragmentMain = (ConstraintLayout) root.findViewById(R.id.fragment_main);
        connectingText = (TextView) root.findViewById(R.id.main_fragment_text_connecting);

        //Initializing settings button from toolbar
        final ImageView settings = (ImageView) root.findViewById(R.id.main_fragment_toolbar_settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                SettingsFragment sf = new SettingsFragment();
                ft.replace(R.id.container, sf, "fragment_settings");
                ft.addToBackStack(null);
                ft.setCustomAnimations(
                        android.R.animator.fade_in, android.R.animator.fade_out);
                ft.commit();
                System.out.println("Settings opened");
            }
        });


        return root;
    }


    /**
     * Show connecting text when news loading begin
     */
    public void showConnectingText(){
        connectingText.setVisibility(View.VISIBLE);
    }

    /**
     * Hide connecting text when news loading finish
     */
    public void hideConnectingText(){
        connectingText.setVisibility(View.INVISIBLE);
    }


    /**
     * If error occur then show Toast with error
     * @param progress code of error
     */
    public void showErrorConnection(int progress){
        connectingText.setVisibility(View.INVISIBLE);
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
        System.out.println("List had been updated");
        this.newsArrayList = list;
        newsList.setAdapter(new NewsAdapter(getContext(), R.layout.news_item, newsArrayList));
    }

    /**
     * Overloaded method to update newsArrayList from model
     */
    @Override
    public void createNewsList() {
        //Init list of news if it's not empty
        this.newsArrayList = presenterNews.getNewsList();
        System.out.println("List had been created");

        System.out.println("Adapter running");
        newsList.setAdapter(new NewsAdapter(getContext(), R.layout.news_item, newsArrayList));
        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getContext(), newsArrayList.get(position).getCaption(),
                        Toast.LENGTH_SHORT).show();
            }
        });
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
    public void onStop() {
        super.onStop();
        presenterNews.detachView();
    }


    /**
     * Setting this view to presenter, setting fragment colors, getting list of news from model
     */
    @Override
    public void onStart() {
        super.onStart();
        presenterNews.setView(this);
        setColors();
        this.newsArrayList = new ArrayList<>();
        createNewsList();
    }


    /**
     * Later will be added scroll updating, now update news if size is 0
     */
    @Override
    public void onResume() {
        super.onResume();
        if (presenterNews.getNewsList().size() == 0)
            presenterNews.updateNewsList();
    }

    /**
     * Setting colors which depends of nightMode variable
     */
    private void setColors(){
        if (nightMode.getMode()){
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryNight));
            fragmentMain.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLightNight));
        }
    }
}
