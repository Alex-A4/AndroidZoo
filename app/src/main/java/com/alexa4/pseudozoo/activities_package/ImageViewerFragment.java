package com.alexa4.pseudozoo.activities_package;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexa4.pseudozoo.R;
import com.alexa4.pseudozoo.adapters.BitmapAdapter;
import com.alexa4.pseudozoo.user_data.ImagesStore;

public class ImageViewerFragment extends Fragment {
    private static final String IMAGE_URL = "IMAGE_URL";
    private String url;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getArguments().getString(IMAGE_URL);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.image_viewer, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getActivity().getWindow().setStatusBarColor(Color.argb(255, 0, 0, 0));

        ImageView image = root.findViewById(R.id.image_viewer);
        BitmapAdapter.decodeBitmapFromUrl(url, getResources(), new BitmapAdapter.DownloadImageCallback() {
            @Override
            public void onDownloadFinished(Bitmap bitmap) {
                image.setImageBitmap(bitmap);
            }
        });

        int position = 0;
        int count = ImagesStore.getStore().getUrls().size();
        for (int i = 0; i < count; i++)
            if (ImagesStore.getStore().getUrls().get(i).equals(url)) {
                position = i;
                break;
            }

        TextView toolbarTitle = (TextView) root.findViewById(R.id.image_viewer_toolbar_text);
        toolbarTitle.setText((position+1)+ " " +getString(R.string.image_viewer_toolbar_of) + " " + count);

        ImageView backArrow = root.findViewById(R.id.image_viewer_back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return root;
    }

    /**
     * Initializing intent for ImageViewer with the url
     * @param context the context of app
     * @param url the url of image
     * @return intent for ImageViewer
     */
    public static ImageViewerFragment instanceViewer(Context context, String url) {
        Bundle args = new Bundle();
        args.putString(IMAGE_URL, url);

        ImageViewerFragment fragment = new ImageViewerFragment();
        fragment.setArguments(args);
        return fragment;
    }


}
