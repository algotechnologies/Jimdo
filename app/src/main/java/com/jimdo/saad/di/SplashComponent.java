package com.jimdo.saad.di;

import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * Created by Saad Aftab on 13/01/2019.
 */

@Component(modules = OkHttpClientModule.class)
public interface SplashComponent {

    OkHttpClient getOkHttpClient();

}
