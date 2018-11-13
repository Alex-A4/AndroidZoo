package com.alexa4.pseudozoo.activities_package.manual_views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alexa4.pseudozoo.R;
import com.alexa4.pseudozoo.adapters.ImageCompressor;
import com.alexa4.pseudozoo.presenter.PresenterManual;
import com.alexa4.pseudozoo.user_data.manual_data.ManualItem;
import com.alexa4.pseudozoo.user_data.manual_data.ManualItemStore;
import com.alexa4.pseudozoo.user_data.NightMode;

import java.util.ArrayList;

public class ManualFragment extends Fragment {
    private RecyclerView mManualList;
    private PresenterManual mPresenter;
    private ImageButton mUpdateButton;
    private TextView mToolbarText;
    private ConstraintLayout mToolbar;
    private ConstraintLayout mManualFragment;
    private ArrayList<ManualItem> mManualItemsList;

    boolean mIsDownloadingError = false;

    /**
     * Setting connection between fragment and its presenter
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterManual(this);
        mPresenter.downloadManual();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_manual, container, false);

        mManualList = (RecyclerView) root.findViewById(R.id.manual_list);
        mManualList.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));

        mToolbar = (ConstraintLayout) root.findViewById(R.id.manual_fragment_toolbar);
        mManualFragment = (ConstraintLayout) root.findViewById(R.id.fragment_manual_layout);

        mToolbarText = (TextView) root.findViewById(R.id.manual_fragment_toolbar_text);

        //Initializing button to update downloading
        mUpdateButton = (ImageButton) root.findViewById(R.id.manual_fragment_button_update);
        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsDownloadingError = false;
                updateUI();
                mPresenter.downloadManual();
            }
        });

        //Check if the manual downloaded but adapter is null
        if (mManualItemsList != null && mManualList.getAdapter() == null)
            mManualList.setAdapter(new ManualListAdapter(mManualItemsList));

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
            mManualList.setVisibility(View.INVISIBLE);
        } else {
            mUpdateButton.setVisibility(View.INVISIBLE);
            mManualList.setVisibility(View.VISIBLE);
        }
    }


    /**
     * The method which make actions after downloading of manuals
     * @param result the result of downloading
     */
    public void setResultOfDownloading(boolean result) {
        if (result) {
            mManualItemsList = ManualItemStore.getStore().getItems();
            if (mManualList != null)
                mManualList.setAdapter(new ManualListAdapter(mManualItemsList));

        } else {
            //If the downloading had been broken
            mIsDownloadingError = true;

            //Update UI
            if (mUpdateButton != null) {
                updateUI();
            }
            Toast.makeText(getContext(), R.string.check_internet, Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Adapter for recycler view. Each element of adapter it's manual_list_item instance
     */
    private class ManualListAdapter extends RecyclerView.Adapter<ManualListAdapter.ManualViewHolder> {
        private ArrayList<ManualItem> mItems;

        public ManualListAdapter(ArrayList<ManualItem> items) {
            mItems = items;
        }

        public class ManualViewHolder extends RecyclerView.ViewHolder {
            private ImageView mItemImage;
            private TextView mItemText;
            public ManualViewHolder(@NonNull View itemView) {
                super(itemView);
                mItemImage = (ImageView) itemView.findViewById(R.id.manual_list_image);
                mItemText = (TextView) itemView.findViewById(R.id.manual_list_text);
            }
        }


        @NonNull
        @Override
        public ManualViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View root = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.manual_list_item, viewGroup,false);

            return new ManualViewHolder(root);
        }

        @Override
        public void onBindViewHolder(@NonNull ManualViewHolder holder, int position) {
            ManualItem item = mItems.get(position);
            holder.mItemText.setText(item.getTitle());

            //Setting compressed image
            if (item.getImage() != null)
                holder.mItemImage.setImageBitmap(item.getImage());
            else {
                ImageCompressor.getCompressedImage(holder.mItemImage.getContext(),
                        item.getImageSrc(),
                        new ImageCompressor.BitmapCompressorCallback() {
                            @Override
                            public void sendCompressedBmp(Bitmap bmp) {
                                item.setImage(bmp);
                                holder.mItemImage.setImageBitmap(bmp);
                            }
                        });
            }
            //Setting click listener on the root element
            holder.mItemImage.getRootView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = ManualAnimalsActivity.newIntent(ManualFragment.this.getContext(),
                            mItems.get(position).getUrl());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }


    /**
     * Setting colors which depends of nightMode variable
     */
    private void setColors(){
        if (NightMode.getNightMode().getMode()){
            mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryNight));
            mManualFragment.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLightNight));
        }
    }
}
