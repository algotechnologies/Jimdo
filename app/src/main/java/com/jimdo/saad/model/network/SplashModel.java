package com.jimdo.saad.model.network;

import com.jimdo.saad.contract.ISplash.*;
import com.jimdo.saad.di.DaggerSplashComponent;
import com.jimdo.saad.di.SplashComponent;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Saad Aftab on 24/05/2018.
 */
public class SplashModel implements Callback {

    private ISplashPresenter presenter;

    OkHttpClient client;

    public SplashModel(ISplashPresenter presenter) {
        this.presenter = presenter;

        SplashComponent component = DaggerSplashComponent.create();
        client = component.getOkHttpClient();
    }

    public void request(String url) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Call call = client.newCall(request);
        call.enqueue(this);
    }

    @Override
    public void onFailure(Call call, IOException e) {
        presenter.onFailure();
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response.isSuccessful()) {
            String responseStr = response.body().string();
            presenter.onSuccess(responseStr);
        } else {
            presenter.onFailure();
        }
    }
}
