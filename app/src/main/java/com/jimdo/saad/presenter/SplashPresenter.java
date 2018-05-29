package com.jimdo.saad.presenter;

import android.util.Log;

import com.jimdo.saad.contract.ISplash.*;
import com.jimdo.saad.model.network.SplashModel;
import com.jimdo.saad.util.Constant;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Saad Aftab on 24/05/2018.
 */
public class SplashPresenter implements ISplashPresenter {

    private static final String TAG = "SplashPresenter";

    private ISplashView view;
    private SplashModel model;

    public SplashPresenter(ISplashView view) {
        this.view = view;
    }

    // Calling design templates data from server
    @Override
    public void loadData() {
        model = new SplashModel(this);
        model.request(Constant.GET_PUBLISHED_DESIGNS);
    }

    @Override
    public void onFailure() {
        view.onFailure();
    }

    @Override
    public void onSuccess(String response) {
        parseJson(response);
    }

    private void parseJson(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);

            // Check if the response has data otherwise stop the app from jumping on next screen.
            if (jsonArray.length() > 0) {
                view.onSuccess(json);
            } else {
                view.onFailure();
            }
        } catch (JSONException e) {
            Log.d(TAG, "JSONException while parsing data: ", e);
        }
    }

}
