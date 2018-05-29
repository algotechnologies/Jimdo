package com.jimdo.saad.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jimdo.saad.R;
import com.jimdo.saad.contract.ITemplateChooser.*;
import com.jimdo.saad.model.dto.Template;
import com.jimdo.saad.presenter.TemplateChooserPresenter;
import com.jimdo.saad.presenter.adapter.TemplateChooserAdapter;
import com.jimdo.saad.util.Constant;
import com.jimdo.saad.util.Helper;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Saad Aftab on 25/05/2018.
 */
public class TemplateChooserActivity extends AppCompatActivity implements ITemplateChooserView {

    @BindView(R.id.bg_template_chooser)
    RelativeLayout background;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.progress_circle)
    ProgressBar progress;

    private ITemplateChooserPresenter presenter;

    private TemplateChooserAdapter templateChooserAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_chooser);

        ButterKnife.bind(this);

        initObjects();
        setuoViewPager();

        String extras = getExtras();

        if (!extras.isEmpty()) {
            if (isInternetAvailable()) {
                // Instantiating TemplateChooserActivity's presenter
                presenter = new TemplateChooserPresenter(this);
                presenter.parseData(extras);
            }
        } else {
            Helper.displayMessage(getApplicationContext(), Constant.PROBLEM_GETTING_EXTRAS);
        }

    }

    private void initObjects() {
        templateChooserAdapter = new TemplateChooserAdapter(this);
    }

    private void setuoViewPager() {
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(templateChooserAdapter);
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

    @Override
    public void onSuccess(Template template) {
        templateChooserAdapter.addCardItem(template);
        templateChooserAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure() {
        Helper.displayMessage(getApplicationContext(), Constant.SOMETHING_WRONG_MSG);
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
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int backgroundColor = templateChooserAdapter.getBackgroundColor(position, positionOffset, templateChooserAdapter.getCount());
        background.setBackgroundColor(backgroundColor);
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private String getExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            return extras.getString(Constant.EXTRA_TEMPLATES_DATA);
        }
        return "";
    }

    @Override
    @OnClick(R.id.btn_choose_template)
    public void onTemplateChosen() {
        List<Template> variationsData = templateChooserAdapter.getVariationData();
        openTemplateVariationActivity(variationsData);
    }

    // Go to next screen
    @Override
    public void openTemplateVariationActivity(List<Template> variationsData) {
        Intent intent = new Intent(TemplateChooserActivity.this, TemplateVariationActivity.class);
        intent.putExtra(Constant.EXTRA_VARIATIONS_DATA, (Serializable) variationsData);
        startActivity(intent);
    }

}
