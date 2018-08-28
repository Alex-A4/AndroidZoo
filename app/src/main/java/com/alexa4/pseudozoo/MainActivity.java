package com.alexa4.pseudozoo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alexa4.pseudozoo.viewElements.BottomNavigation;

public class MainActivity extends AppCompatActivity {

    News newsArrayList[] = new News[]{
            new News("1 и 2 сентября - День знаний в Ярославском зоопарке!","02 августа 2018",R.drawable.firstseptember,"1 и 2 сентября 2018 года мы приглашаем мальчишек и девчонок, а так же их родителей на День знаний в Ярославский зоопарк! Для Вас - интерактивная экскурсия, лекторий, а так же возможность познаокмиться с нашим учебным центром!"),
            new News("Выездная выставка зоопарка в Левцово!", "02 августа 2018", R.drawable.levcovo,"3 августа 2018 года Ярославский зоопарк представил свою экспозицию на \"Дне ярославского поля\", а 4 августа мы ждем всех в \"Левцово\" на фестивале \"ТехноTravel\", где Вы сможете покататься верхом на верблюде!")
    };

    private ListView newsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsList = (ListView) findViewById(R.id.news_list);
        newsList.setAdapter(new NewsAdapter(this, R.layout.news_item, newsArrayList));

        System.out.println("Main opened");
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(BottomNavigation.getNavigation(this));
        navigation.setSelectedItemId(R.id.navigation_home);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        Intent intent;
        intent = new Intent("com.alexa4.pseudozoo.SettingsActivity");
        System.out.println("Settings opened");
        startActivity(intent);
        return super.onOptionsItemSelected(menuItem);
    }


    private class News{
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

    private class NewsAdapter extends ArrayAdapter<News>{

        public NewsAdapter(@NonNull Context context, int textViewResourceId,
                           @NonNull News[] objects) {
            super(context, textViewResourceId, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
            LayoutInflater inflater = getLayoutInflater();
            View news = inflater.inflate(R.layout.news_item, parent, false);

            TextView caption = (TextView) news.findViewById(R.id.news_item_caption);
            caption.setText(newsArrayList[position].getCaption());
            TextView time = (TextView) news.findViewById(R.id.news_item_time);
            time.setText(newsArrayList[position].getTime());
            ImageView image = (ImageView) news.findViewById(R.id.news_item_image);
            image.setImageResource(newsArrayList[position].getImage());
            TextView description = (TextView) news.findViewById(R.id.news_item_description);
            description.setText(newsArrayList[position].getDescription());

            return news;
        }


    }

}
