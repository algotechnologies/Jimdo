package com.jimdo.saad.presenter.adapter;

import android.animation.ArgbEvaluator;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jimdo.saad.R;
import com.jimdo.saad.contract.ITemplateChooser.*;
import com.jimdo.saad.model.dto.Template;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saad Aftab on 26/05/2018.
 */
public class TemplateChooserAdapter extends PagerAdapter implements ITemplateChooserAdapter {

    private ITemplateChooserView view;

    private List<Template> templatesData;
    private ArgbEvaluator argbEvaluator;
    private int currentPosition = 0;

    public TemplateChooserAdapter(ITemplateChooserView view) {
        this.view = view;

        initObjects();
    }

    private void initObjects() {
        templatesData = new ArrayList<>();
        argbEvaluator = new ArgbEvaluator();
    }

    public void addCardItem(Template template) {
        templatesData.add(template);
    }

    @Override
    public int getCount() {
        return templatesData.size();
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
        view.displayTemplate(templatesData.get(position), templateView);

        return templateView;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        // Update current view position
        currentPosition = position;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getBackgroundColor(int position, float positionOffset, int adapterDataSize) {
        if (position < (adapterDataSize - 1) && position < (templatesData.size() - 1)) {
            return (int) argbEvaluator.evaluate(positionOffset, templatesData.get(position).getColor(), templatesData.get(position + 1).getColor());
        }
        return templatesData.get(templatesData.size() - 1).getColor();
    }

    @Override
    public List<Template> getVariationData() {
        return templatesData.get(currentPosition).getVariations();
    }

}