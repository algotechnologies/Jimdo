package com.jimdo.saad.contract;

/**
 * Created by Saad Aftab on 25/05/2018.
 */
public interface ISplash {

    interface ISplashView {

        boolean isInternetAvailable();

        void onSuccess(String response);

        void onFailure();

    }

    interface ISplashPresenter {

        void loadData();

        void onSuccess(String response);

        void onFailure();

    }

}
