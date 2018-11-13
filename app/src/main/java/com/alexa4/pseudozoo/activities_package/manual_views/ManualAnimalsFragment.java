package com.alexa4.pseudozoo.activities_package.manual_views;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alexa4.pseudozoo.R;
import com.alexa4.pseudozoo.adapters.ImageCompressor;
import com.alexa4.pseudozoo.models.ModelManual;
import com.alexa4.pseudozoo.user_data.manual_data.ManualAnimalItem;
import com.alexa4.pseudozoo.user_data.manual_data.ManualAnimalsStore;
import com.alexa4.pseudozoo.user_data.manual_data.ManualItem;
import com.alexa4.pseudozoo.user_data.manual_data.ManualItemStore;

import java.util.ArrayList;


public class ManualAnimalsFragment extends Fragment {
    private static final String ANIMALS_URL = "ANIMALS_URL";
    //The url on the page of animals type
    private ManualItem mAnimalsTypeItem;
    //List of animals on the page of selected type
    private ArrayList<ManualAnimalItem> mAnimalsList;
    private RecyclerView mAnimalsRV;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String mUrl = getArguments().getString(ANIMALS_URL);
        mAnimalsTypeItem = ManualItemStore.getStore().getItemByUrl(mUrl);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_manual_animals, container, false);

        mAnimalsRV = (RecyclerView) root.findViewById(R.id.manual_animals_list);
        mAnimalsRV.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));

        //Text of page in toolbar
        TextView toolbarText = (TextView) root.findViewById(R.id.manual_animals_fragment_toolbar_text);
        toolbarText.setText(mAnimalsTypeItem.getTitle().replace("\n", "\\"));

        //Back arrow in toolbar
        ImageView backArrow = (ImageView) root.findViewById(R.id.manual_animals_toolbar_back);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        //Downloading data of page
        ModelManual.downloadAnimalsPage(mAnimalsTypeItem.getUrl(), new ModelManual.DownloadAnimalsCallback() {
            @Override
            public void sendResult(boolean response) {
                mAnimalsList = ManualAnimalsStore.getStore().getItems();
                mAnimalsRV.setAdapter(new AnimalsAdapter(mAnimalsList));
            }
        });

        setRetainInstance(true);

        return root;
    }

    private class AnimalsAdapter extends RecyclerView.Adapter<AnimalsAdapter.AnimalsViewHolder> {
        private ArrayList<ManualAnimalItem> mItems;

        public AnimalsAdapter(ArrayList<ManualAnimalItem> items) {
            mItems = items;
        }

        @NonNull
        @Override
        public AnimalsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View root = LayoutInflater.from(getContext()).inflate(R.layout.manual_list_item,
                    viewGroup, false);

            return new AnimalsViewHolder(root);
        }

        @Override
        public void onBindViewHolder(@NonNull AnimalsViewHolder holder,
                                     @SuppressLint("RecyclerView") int position) {
            holder.mTextView.setText(mItems.get(position).getTitle());

            if (mItems.get(position).getImage() != null)
                holder.mImageView.setImageBitmap(mItems.get(position).getImage());
            else {
                ImageCompressor.getCompressedImage(getContext(),
                        mItems.get(position).getImageSrc(),
                        new ImageCompressor.BitmapCompressorCallback() {
                            @Override
                            public void sendCompressedBmp(Bitmap bmp) {
                                mItems.get(position).setImage(bmp);
                                holder.mImageView.setImageBitmap(bmp);
                            }
                        });
            }

            //TODO: change logic onClickListener
            holder.mImageView.getRootView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AnimalsFragment fragment = AnimalsFragment.newInstance(mItems.get(position).getUrl());
                    getFragmentManager().beginTransaction()
                            .replace(R.id.manual_animals_fragment_container, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public class AnimalsViewHolder extends RecyclerView.ViewHolder {
            private ImageView mImageView;
            private TextView mTextView;
            public AnimalsViewHolder(@NonNull View itemView) {
                super(itemView);
                mImageView = (ImageView) itemView.findViewById(R.id.manual_list_image);
                mTextView = (TextView) itemView.findViewById(R.id.manual_list_text);
            }
        }
    }

    /**
     * Initializing fragment by url with selected animals type
     * @param url the url of selected animals type
     * @return the instance of the fragment
     */
    public static ManualAnimalsFragment initFragment(@NonNull String url) {
        ManualAnimalsFragment fragment = new ManualAnimalsFragment();

        Bundle args = new Bundle();
        args.putString(ANIMALS_URL, url);
        fragment.setArguments(args);

        return fragment;
    }
}
