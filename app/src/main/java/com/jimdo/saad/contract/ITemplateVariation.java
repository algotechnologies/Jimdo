package com.jimdo.saad.contract;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.jimdo.saad.model.dto.Template;

/**
 * Created by Saad Aftab on 28/05/2018.
 */
public interface ITemplateVariation {

    interface ITemplateVariationView extends ViewPager.OnPageChangeListener {

        boolean isInternetAvailable();

        void displayTemplate(Template item, View view);

    }

    interface ITemplateVariationAdapter {

        int getBackgroundColor(int position, float positionOffset, int adapterDataSize);

    }

}
