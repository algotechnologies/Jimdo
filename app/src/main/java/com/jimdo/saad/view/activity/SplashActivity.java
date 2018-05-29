package com.jimdo.saad.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jimdo.saad.R;
import com.jimdo.saad.contract.ISplash.*;
import com.jimdo.saad.presenter.SplashPresenter;
import com.jimdo.saad.util.Constant;
import com.jimdo.saad.util.Helper;

/**
 * Created by Saad Aftab on 24/05/2018.
 */
public class SplashActivity extends AppCompatActivity implements ISplashView {

    private ISplashPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (isInternetAvailable() ) {
            // Instantiating SplashActivity's presenter
            presenter = new SplashPresenter(this);
            presenter.loadData();
        }
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
    public void onSuccess(String response) {
        openTemplateChooserActivity(response);
        destroyMe();
    }

    @Override
    public void onFailure() {
        Helper.displayMessage(getApplicationContext(), Constant.SOMETHING_WRONG_MSG);
    }

    // Go to next screen
    private void openTemplateChooserActivity(String json) {
        Intent intent = new Intent(SplashActivity.this, TemplateChooserActivity.class);
        intent.putExtra(Constant.EXTRA_TEMPLATES_DATA, json);
        startActivity(intent);
    }

    // Destroy this Activity
    private void destroyMe() {
        finish();
    }

}
