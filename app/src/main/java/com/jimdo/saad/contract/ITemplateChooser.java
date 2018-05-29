package com.jimdo.saad.contract;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.jimdo.saad.model.dto.Template;

import java.util.List;

/**
 * Created by Saad Aftab on 27/05/2018.
 */
public interface ITemplateChooser {

    interface ITemplateChooserView extends ViewPager.OnPageChangeListener {

        boolean isInternetAvailable();

        void onSuccess(Template template);

        void onFailure();

        void displayTemplate(Template item, View view);

        void hideProgress();

        void onTemplateChosen();

        void openTemplateVariationActivity(List<Template> variationsData);
    }

    interface ITemplateChooserPresenter {

        void parseData(String data);

        void onSuccess(String response);

        void onFailure();

    }

    interface ITemplateChooserAdapter {

        List<Template> getVariationData();

        int getBackgroundColor(int position, float positionOffset, int adapterDataSize);

    }

}
