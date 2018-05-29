package com.jimdo.saad.presenter;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.jimdo.saad.contract.ITemplateChooser.ITemplateChooserPresenter;
import com.jimdo.saad.contract.ITemplateChooser.ITemplateChooserView;
import com.jimdo.saad.model.dto.Template;
import com.jimdo.saad.model.network.TemplateChooserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saad Aftab on 27/05/2018.
 */
public class TemplateChooserPresenter implements ITemplateChooserPresenter {

    private static final String TAG = "TemplateChooserPresente";

    private ITemplateChooserView view;
    private TemplateChooserModel model;

    public TemplateChooserPresenter(ITemplateChooserView view) {
        this.view = view;

        initObjects();
    }

    private void initObjects() {
        model = new TemplateChooserModel(this);
    }

    @Override
    public void parseData(String data) {
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                model.request(jsonArray.getString(i));
            }
        } catch (JSONException e) {
            Log.d(TAG, "JSONException while parsing data: ", e);
        }
    }

    @Override
    public void onSuccess(String response) {
        parseJson(response);
    }

    @Override
    public void onFailure() {
        view.onFailure();
    }

    private void parseJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);

            final String name = jsonObject.getString("name");
            final String image = jsonObject.getJSONObject("screenshots").getString("medium");
            String color = jsonObject.getJSONObject("meta").getString("color");
            final int colorInt = getCorrectColorFormat(color);

            JSONArray variationsArray = jsonObject.getJSONArray("variations");
            final List<Template> variations = new ArrayList<>();

            for (int i = 0; i < variationsArray.length(); i++) {
                String variationName = variationsArray.getJSONObject(i).getString("name");
                String variationColor = variationsArray.getJSONObject(i).getString("icon");
                String variationImage = variationsArray.getJSONObject(i).getJSONObject("screenshots").getString("medium");
                int variationColorInt = getCorrectColorFormat(variationColor);

                Template variation = new Template(variationName, variationImage, variationColorInt);
                variations.add(variation);
            }

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Template template = new Template(name, image, colorInt);
                    template.setVariations(variations);

                    view.onSuccess(template);
                    view.hideProgress();
                }
            });
        } catch (JSONException e) {
            Log.d(TAG, "JSONException while parsing data: ", e);
        }
    }

    private int getCorrectColorFormat(String color) {
        // Check if color is in the correct format or not, that's required by Color.parseColor()
        if (color.length() < 7) {
            color = color.substring(1);
            color = "#" + color.concat(color);
        }
        return Color.parseColor(color);
    }

}
