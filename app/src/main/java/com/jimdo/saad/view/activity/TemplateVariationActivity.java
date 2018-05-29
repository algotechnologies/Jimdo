package com.jimdo.saad.view.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jimdo.saad.R;
import com.jimdo.saad.contract.ITemplateVariation.*;
import com.jimdo.saad.model.dto.Template;
import com.jimdo.saad.presenter.adapter.TemplateVariationAdapter;
import com.jimdo.saad.util.Constant;
import com.jimdo.saad.util.Helper;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Saad Aftab on 28/05/2018.
 */
public class TemplateVariationActivity extends AppCompatActivity implements ITemplateVariationView {

    @BindView(R.id.bg_template_variation)
    RelativeLayout background;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private TemplateVariationAdapter templateVariationAdapter;
    private List<Template> extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_variation);

        ButterKnife.bind(this);

        extras = getExtras();

        if (extras != null) {
            if (isInternetAvailable()) {
                initObjects();
                setuoViewPager();
            }
        } else {
            Helper.displayMessage(getApplicationContext(), Constant.PROBLEM_GETTING_EXTRAS);
        }

    }

    private void initObjects() {
        templateVariationAdapter = new TemplateVariationAdapter(this, extras);
    }

    private void setuoViewPager() {
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(templateVariationAdapter);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public boolean isInternetAvailable() {
        if (!Helper.checkNetworkAvailablity(getApplicationContext())) {
            Helper.displayMessage(getApplicationContext(), Constant.NO_INTERNET_MSG);
            return false;
        }
        return true;
    }

    private List<Template> getExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            return (List<Template>) extras.getSerializable(Constant.EXTRA_VARIATIONS_DATA);
        }
        return null;
    }

    @Override
    public void displayTemplate(Template item, View view) {
        TextView txtName = (TextView) view.findViewById(R.id.tv_name);
        ImageView ivScreenshot = (ImageView) view.findViewById(R.id.iv_screenshot);

        txtName.setText(item.getName());
        Picasso.with(this)
                .load(item.getImage())
                .resize(512, 512)
                .centerInside()
                .placeholder(R.drawable.template_placeholder)
                .error(R.drawable.template_placeholder)
                .into(ivScreenshot);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int backgroundColor = templateVariationAdapter.getBackgroundColor(position, positionOffset, templateVariationAdapter.getCount());
        background.setBackgroundColor(backgroundColor);
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

}