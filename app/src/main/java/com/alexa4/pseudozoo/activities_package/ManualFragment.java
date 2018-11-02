package com.alexa4.pseudozoo.activities_package;

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

import com.alexa4.pseudozoo.R;
import com.alexa4.pseudozoo.presenter.PresenterManual;

public class ManualFragment extends Fragment {
    private RecyclerView mManualList;
    private PresenterManual mPresenter;


    /**
     * Setting connection between fragment and its presenter
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterManual(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_manual, container, false);

        mManualList = (RecyclerView) root.findViewById(R.id.manual_list);
        mManualList.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));

        return root;
    }


    /**
     * Adapter for recycler view. Each element of adapter it's manual_list_item instance
     */
    private static class ManualListAdapter extends RecyclerView.Adapter<ManualListAdapter.ManualViewHolder> {


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
        public void onBindViewHolder(@NonNull ManualViewHolder manualViewHolder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
}
