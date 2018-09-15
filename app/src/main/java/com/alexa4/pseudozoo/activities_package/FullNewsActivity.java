package com.alexa4.pseudozoo.activities_package;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alexa4.pseudozoo.R;
import com.alexa4.pseudozoo.adapters.BitmapAdapter;
import com.alexa4.pseudozoo.models.ModelNews;
import com.alexa4.pseudozoo.presenter.PresenterFullNews;
import com.alexa4.pseudozoo.presenter.ViewInterfaceFullNews;
import com.alexa4.pseudozoo.user_data.FullNews;

import java.util.ArrayList;

public class FullNewsActivity extends AppCompatActivity implements ViewInterfaceFullNews {

    private final int RECYCLERVIEW_SPACING = 24;
    private PresenterFullNews presenterNews;

    private ConstraintLayout activityFullNews;
    private ConstraintLayout toolbar;

    private RecyclerView imagesRecyclerView;
    private TextView toolbarTitle;
    private TextView fullTextOfNews;
    private ImageView imageOfNews;


    private FullNews fullNews = new FullNews(null, null, null);

    private String newsUrl;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullnews);

        final ScrollView scrollView = (ScrollView) findViewById(R.id.fullnews_scrollview);
        scrollView.scrollTo(0, 0);

        newsUrl = getIntent().getStringExtra("NewsUrl");

        activityFullNews = (ConstraintLayout) findViewById(R.id.activity_fullnews);

        toolbar = (ConstraintLayout) findViewById(R.id.fullnews_toolbar);
        toolbarTitle = (TextView) findViewById(R.id.fullnews_toolbar_text);
        fullTextOfNews = (TextView) findViewById(R.id.fullnews_text);
        imageOfNews = (ImageView) findViewById(R.id.fullnews_image_of_news);

        presenterNews = new PresenterFullNews(new ModelNews());
        presenterNews.setView(this);
        this.setPresenter(presenterNews);

        ImageView toolbarBackArrow = (ImageView) findViewById(R.id.fullnews_toolbar_back);
        toolbarBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imagesRecyclerView = (RecyclerView) findViewById(R.id.fullnews_image_container);
        GridLayoutManager manager = new GridLayoutManager(this, 2,
                LinearLayoutManager.VERTICAL, false);
        imagesRecyclerView.setLayoutManager(manager);
        imagesRecyclerView.setHasFixedSize(true);
        imagesRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @SuppressLint("ResourceType")
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.left = RECYCLERVIEW_SPACING;
                outRect.right = RECYCLERVIEW_SPACING;
                outRect.bottom = RECYCLERVIEW_SPACING;

                if (parent.getChildLayoutPosition(view) == 0)
                    outRect.top = RECYCLERVIEW_SPACING;
                else outRect.top = 0;
            }
        });
    }


    @SuppressLint("ResourceAsColor")
    public void startDownloading(){
    }


    public void stopDownloading(){
    }


    /**
     * Updating activity by content which contains into fullNews
     * @param fullNews container of news activity
     */
    public void updateNews(FullNews fullNews){
        this.fullNews = fullNews;

        toolbarTitle.setText(fullNews.getTitle());
        BitmapAdapter.decodeBitmapFromUrl(
                fullNews.getImgUrl(), getResources(),
                new BitmapAdapter.DownloadImageCallback() {
                    @Override
                    public void onDownloadFinished(Bitmap bitmap) {
                        imageOfNews.setImageBitmap(bitmap);
                    }
                });
        fullTextOfNews.setText(fullNews.getFullText());

        if (fullNews.getListUrlOfImages() != null){
            imagesRecyclerView.setAdapter(new FullNewsAdapter(fullNews.getListUrlOfImages()));
        }
    }

    public void errorWhileDownloading(int progress){
        switch (progress) {
            case ModelNews.Progress.CONNECTING_ERROR:   Toast.makeText(this,
                    R.string.check_internet, Toast.LENGTH_SHORT).show();
                break;
            case ModelNews.Progress.DOWNLOADING_ERROR: Toast.makeText(this,
                    R.string.downloading_error, Toast.LENGTH_SHORT).show();
                break;
        }
        Toast.makeText(this, R.string.check_internet, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void setPresenter(PresenterFullNews presenter) {
        this.presenterNews = presenter;
    }


    /**
     * Custom adapter to represent list of news images
     */
    private class FullNewsAdapter extends RecyclerView.Adapter<FullNewsAdapter.FullNewsHolder> {

        private ArrayList<String> urls;

        public class FullNewsHolder extends RecyclerView.ViewHolder{
            public ImageView imageView;
            public FullNewsHolder(@NonNull ImageView itemView) {
                super(itemView);
                this.imageView = itemView;
            }
        }

        public FullNewsAdapter(ArrayList<String> urls){
            this.urls = urls;
        }



        @NonNull
        @Override
        public FullNewsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            ImageView imageView = (ImageView) LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.news_image_item, viewGroup, false);

            FullNewsHolder holder = new FullNewsHolder(imageView);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull FullNewsHolder fullNewsHolder, int i) {
            BitmapAdapter.decodeBitmapFromUrl(urls.get(i), getResources(),
                    new BitmapAdapter.DownloadImageCallback() {
                        @Override
                        public void onDownloadFinished(Bitmap bitmap) {
                            fullNewsHolder.imageView.setImageBitmap(bitmap);
                            fullNewsHolder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                            fullNewsHolder.imageView.setMaxWidth(imagesRecyclerView.getWidth() / 2
                                    - RECYCLERVIEW_SPACING);
                            fullNewsHolder.imageView.setMaxHeight(fullNewsHolder.imageView.getMaxWidth());

                            fullNewsHolder.imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(FullNewsActivity.this,urls.get(i),
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
        }



        @Override
        public int getItemCount() {
            return urls.size();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        setColors();
        presenterNews.updateFullNews(newsUrl);
    }

    @Override
    protected void onDestroy() {
        presenterNews.detachView();
        super.onDestroy();
    }

    /**
     * Setting colors which depends of nightMode variable
     */
    private void setColors() {
        if (nightMode.getMode()) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryNight));
            activityFullNews.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLightNight));
        }
    }
}
