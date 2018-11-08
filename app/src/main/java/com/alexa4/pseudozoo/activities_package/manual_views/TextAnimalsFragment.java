package com.alexa4.pseudozoo.activities_package.manual_views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 *
 */
public class TextAnimalsFragment extends Fragment {
    private static final String ANIMALS_TEXT = "ANIMALS_TEXT";
    private String mText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mText = getArguments().getString(ANIMALS_TEXT);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView text = new TextView(getContext());
        text.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        text.setText(mText);

        return text;
    }

    /**
     * Initializing fragment with text
     * @param text the text which need set
     * @return the instance of fragment
     */
    public static TextAnimalsFragment newInstance(String text) {
        TextAnimalsFragment fragment = new TextAnimalsFragment();
        Bundle args = new Bundle();
        args.putString(ANIMALS_TEXT, text);

        fragment.setArguments(args);
        return fragment;
    }
}
