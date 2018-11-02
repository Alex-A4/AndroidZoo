package com.alexa4.pseudozoo.presenter;

import com.alexa4.pseudozoo.activities_package.ManualFragment;
import com.alexa4.pseudozoo.models.ModelManual;

public class PresenterManual {
    private ManualFragment mView;
    private ModelManual mModel;


    public PresenterManual(ManualFragment fragment) {
        mView = fragment;
        mModel = ModelManual.getManual(mView.getContext());
    }
}
