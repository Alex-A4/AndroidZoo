package com.alexa4.pseudozoo.presenter;

import com.alexa4.pseudozoo.activities_package.manual_views.ManualFragment;
import com.alexa4.pseudozoo.models.ModelManual;

public class PresenterManual {
    private ManualFragment mView;
    private ModelManual mModel;


    public PresenterManual(ManualFragment fragment) {
        mView = fragment;
        mModel = ModelManual.getManual(mView.getContext());
    }


    /**
     * Downloading manual from Zoo-web, then set result to view
     */
    public void downloadManual() {
        mModel.downloadManual(new ModelManual.DownloadManualCallback() {
            @Override
            public void sendResult(boolean result) {
                mView.setResultOfDownloading(result);
            }
        });
    }
}
