package com.jimdo.saad.presenter.adapter;

import android.animation.ArgbEvaluator;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jimdo.saad.R;
import com.jimdo.saad.contract.ITemplateVariation.*;
import com.jimdo.saad.model.dto.Template;

import java.util.List;

/**
 * Created by Saad Aftab on 28/05/2018.
 */
public class TemplateVariationAdapter extends PagerAdapter implements ITemplateVariationAdapter {

    private ITemplateVariationView view;
    private List<Template> variationsData;
    private ArgbEvaluator argbEvaluator;

    public TemplateVariationAdapter(ITemplateVariationView view, List<Template> variationsData) {
        this.view = view;
        this.variationsData = variationsData;

        argbEvaluator = new ArgbEvaluator();
    }

    @Override
    public int getCount() {
        return variationsData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View templateView = LayoutInflater.from(container.getContext()).inflate(R.layout.template_view, container, false);
        container.addView(templateView);

        // Binding view
        view.displayTemplate(variationsData.get(position), templateView);

        return templateView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getBackgroundColor(int position, float positionOffset, int adapterDataSize) {
        if (position < (adapterDataSize - 1) && position < (variationsData.size() - 1)) {
            return (int) argbEvaluator.evaluate(positionOffset, variationsData.get(position).getColor(), variationsData.get(position + 1).getColor());
        }
        return variationsData.get(variationsData.size() - 1).getColor();
    }

}
