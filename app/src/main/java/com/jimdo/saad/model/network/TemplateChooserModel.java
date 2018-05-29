package com.jimdo.saad.model.network;

import com.jimdo.saad.contract.ITemplateChooser.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Saad Aftab on 27/05/2018.
 */
public class TemplateChooserModel implements Callback {

    private ITemplateChooserPresenter presenter;
    private OkHttpClient client;

    public TemplateChooserModel(ITemplateChooserPresenter presenter) {
        this.presenter = presenter;

        client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build();
    }

    public void request(String url) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Accept", "application/json")
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
