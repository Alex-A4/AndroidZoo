package com.alexa4.pseudozoo.activities_package;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
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

public class FullNewsActivity extends FragmentActivity implements ViewInterfaceFullNews {

    private final int RECYCLERVIEW_SPACING = 24;
    private PresenterFullNews presenterFullNews;

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

        //Getting news url from the intent data
        newsUrl = getIntent().getStringExtra("NewsUrl");

        final ScrollView scrollView = (ScrollView) findViewById(R.id.fullnews_scrollview);
        scrollView.scrollTo(0, 0);


        activityFullNews = (ConstraintLayout) findViewById(R.id.activity_fullnews);

        toolbar = (ConstraintLayout) findViewById(R.id.fullnews_toolbar);
        toolbarTitle = (TextView) findViewById(R.id.fullnews_toolbar_text);
        fullTextOfNews = (TextView) findViewById(R.id.fullnews_text);
        imageOfNews = (ImageView) findViewById(R.id.fullnews_image_of_news);

        presenterFullNews = new PresenterFullNews(new ModelNews());
        presenterFullNews.setView(this);
        this.setPresenter(presenterFullNews);

        ImageView toolbarBackArrow = (ImageView) findViewById(R.id.fullnews_toolbar_back);
        toolbarBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Initializing recyclerview which contains images
        imagesRecyclerView = (RecyclerView) findViewById(R.id.fullnews_image_container);
        GridLayoutManager manager = new GridLayoutManager (this, 2,
                LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

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

        if (fullNews.getListUrlOfImagesLowQuality() != null){
            imagesRecyclerView.setAdapter(new FullNewsAdapter(fullNews.getListUrlOfImagesLowQuality(),
                    fullNews.getListUrlOfImagesHighQuality()));
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
        getFragmentManager().popBackStack();
    }

    /**
     * Setting presenter
     * @param presenter
     */
    @Override
    public void setPresenter(PresenterFullNews presenter) {
        this.presenterFullNews = presenter;
    }


    /**
     * Custom adapter to represent list of news images
     */
    private class FullNewsAdapter extends RecyclerView.Adapter<FullNewsAdapter.FullNewsHolder> {

        private ArrayList<String> urlsLowQuality;
        private ArrayList<String> urlsHighQuality;


        public class FullNewsHolder extends RecyclerView.ViewHolder{
            public ImageView imageView;
            public FullNewsHolder(@NonNull ImageView itemView) {
                super(itemView);
                this.imageView = itemView;
            }
        }

        public FullNewsAdapter(ArrayList<String> urlsLowQuality, ArrayList<String> urlsHighQuality){
            this.urlsLowQuality = urlsLowQuality;
            this.urlsHighQuality = urlsHighQuality;
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
            if (fullNews.getListOfBitmap() != null && fullNews.getListOfBitmap().size() > i)
                fullNewsHolder.imageView.setImageBitmap(fullNews.getListOfBitmap().get(i));
            else BitmapAdapter.decodeBitmapFromUrl(urlsLowQuality.get(i), getResources(),
                    new BitmapAdapter.DownloadImageCallback() {
                        @Override
                        public void onDownloadFinished(Bitmap bitmap) {
                            fullNewsHolder.imageView.setImageBitmap(bitmap);
                            fullNews.getListOfBitmap().add(i, bitmap);
                            fullNewsHolder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                            fullNewsHolder.imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent("com.alexa4.pseudozoo.ImageViewerActivity");
                                    intent.putExtra("HighNewsUrl", urlsHighQuality.get(i));
                                    startActivity(intent);
                                }
                            });
                        }
                    });
        }



        @Override
        public int getItemCount() {
            return urlsLowQuality.size();
        }
    }


    /**
     * Start downloading of news data
     */
    @Override
    protected void onStart() {
        super.onStart();
        setColors();
        presenterFullNews.updateFullNews(newsUrl);
    }

    /**
     * Detach
     */
    @Override
    protected void onDestroy() {
        presenterFullNews.detachView();
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
