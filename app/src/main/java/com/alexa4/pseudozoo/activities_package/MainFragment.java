package com.alexa4.pseudozoo.activities_package;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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
import com.alexa4.pseudozoo.presenter.PresenterNews;
import com.alexa4.pseudozoo.presenter.ViewInterface;
import com.alexa4.pseudozoo.user_data.News;

import java.util.ArrayList;


/**
 * MainFragment contains list of news, toolbar with settings button
 * It's loading when the app starts
 */
public class MainFragment extends Fragment implements ViewInterface {
    private ListView newsList;

    private ArrayList<News> newsArrayList;

    private PresenterNews presenterNews;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_main, container, false);

        //Initializing list of news
        newsList = (ListView) root.findViewById(R.id.news_list);


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
     * Setting news list from Presenter
     * @param list of news
     */
    public void updateNewsList(ArrayList<News> list){
        System.out.println("This is my list" +list);
        this.newsArrayList.addAll(list);
        System.out.println("List after process" + newsArrayList);
    }

    /**
     * Overloaded method to update newsArrayList from model
     */
    @Override
    public void createNewsList() {
        presenterNews.getNewsList();

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
            image.setImageResource(newsArrayList.get(position).getImage());
            TextView description = (TextView) news.findViewById(R.id.news_item_description);
            description.setText(newsArrayList.get(position).getDescription());

            return news;
        }


    }


    @Override
    public void onStop() {
        super.onStop();
        presenterNews.detachView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenterNews.setView(this);
        this.newsArrayList = new ArrayList<>();
        createNewsList();
    }
}
