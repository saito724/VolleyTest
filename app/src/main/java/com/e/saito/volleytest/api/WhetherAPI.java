package com.e.saito.volleytest.api;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class WhetherApi {
    private final String BASE_URL = "http://weather.livedoor.com/forecast/webservice/json/v1?city=";
    private static RequestQueue mReqestQueue;

    public static RequestQueue getRequestQueueInstance(Context context){
        if(mReqestQueue == null){
            mReqestQueue = Volley.newRequestQueue(context);
        }
        return  mReqestQueue;
    }


}
