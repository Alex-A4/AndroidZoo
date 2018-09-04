package com.alexa4.pseudozoo;

import android.content.Context;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alexa4.pseudozoo.interfaces.OnSetToolBarListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * MainFragment contains list of news, toolbar with settings button
 * It's loading when the app starts
 */
public class MainFragment extends Fragment {
    private ListView newsList;

    private static final List<News> newsArrayList = new ArrayList<News>(Arrays.asList(
            new News("1 и 2 сентября - День знаний в Ярославском зоопарке!","02 августа 2018",R.drawable.firstseptember,"1 и 2 сентября 2018 года мы приглашаем мальчишек и девчонок, а так же их родителей на День знаний в Ярославский зоопарк! Для Вас - интерактивная экскурсия, лекторий, а так же возможность познаокмиться с нашим учебным центром!"),
            new News("Выездная выставка зоопарка в Левцово!", "02 августа 2018", R.drawable.levcovo,"3 августа 2018 года Ярославский зоопарк представил свою экспозицию на \"Дне ярославского поля\", а 4 августа мы ждем всех в \"Левцово\" на фестивале \"ТехноTravel\", где Вы сможете покататься верхом на верблюде!"),
            new News("Вкусное лакомство для обитаталей зоопарка", "01 июня 2018",R.drawable.lakomstvo,"Во время жаркой погоды сотрудники Ярославского зоопарка балуют обитаталей вкусным мороженым. В пятницу, 27.07.2018, все желающие наблюдали, как лакомятся вкусняшками наши Ума и Топа")
    ));


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_main, container, false);

        //Initializing list of news
        newsList = (ListView) root.findViewById(R.id.news_list);
        newsList.setAdapter(new NewsAdapter(getContext(), R.layout.news_item, newsArrayList));
        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getContext(), newsArrayList.get(position).getCaption(),
                        Toast.LENGTH_SHORT).show();
            }
        });


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

        //implementation interface between fragment and activity, without direct links
        //OnSetToolBarListener listener = (OnSetToolBarListener) getActivity();
        //listener.onToolbarSet(toolbar);

        return root;
    }

    /**
     * Custom class for using it in Adapter
     */
    private static final class News{
        private String caption;
        private String time;
        private int imageId;
        private String description;

        public News(String caption, String time,int imageId, String description){
            this.caption = caption;
            this.time = time;
            this.imageId = imageId;
            this.description = description;
        }

        public String getCaption() {
            return caption;
        }

        public int getImage() {
            return imageId;
        }

        public String getDescription() {
            return description;
        }

        public String getTime() {
            return time;
        }
    }


    /**
     * Custom adapter to represent list of news
     */
    private class NewsAdapter extends ArrayAdapter<News> {

        public NewsAdapter(@NonNull Context context, int textViewResourceId,
                           @NonNull List<News> objects) {
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
}
