package com.program.module_wenda.utils;

import com.program.lib_common.Constants;
import com.program.module_wenda.model.WendaApi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitManager {

    private WendaApi mApi;
    private static RetrofitManager sApiManager;

    public static RetrofitManager getInstence() {
        if (sApiManager == null) {
            synchronized (RetrofitManager.class) {
                if (sApiManager == null) {
                    sApiManager = new RetrofitManager();
                }
            }
        }
        return sApiManager;
    }

    public WendaApi getApi() {
        if (mApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
            mApi =  retrofit.create(WendaApi.class);
        }
        return mApi;
    }

}
