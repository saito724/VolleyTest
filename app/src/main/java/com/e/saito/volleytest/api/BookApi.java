package com.e.saito.volleytest.api;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.e.saito.volleytest.data.Book;
import com.e.saito.volleytest.util.XmlUtil;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by e.saito on 2014/06/03.
 */
public class BookApi {
    public final String URL_BOOK = "http://www.hanmoto.com/api/Operation=ItemSearch&SearchIndex=Books&Keywords=Android 開発";


    public void getJsonBookData(RequestQueue queue , final BookListener listener){
        Response.Listener<JSONObject> resListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //data to arraylist

                listener.onLoadSucccess(new ArrayList<Book>());
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onLoadError();
            }
        };

        Request request = new JsonObjectRequest(Request.Method.GET, URL_BOOK,null,resListener,errorListener);
        queue.add(request);
    }

    public void getXmlBookData(RequestQueue queue , final BookListener listener){
        Request request = new StringRequest(
                Request.Method.GET,
                URL_BOOK,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        XmlUtil.parseTest(response);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Response.ErrorListener","error!!!!!!!!");
                        listener.onLoadError();
                        error.printStackTrace();
                    }
                }
        );
        queue.add(request);


    }


    public interface BookListener {
        public void onLoadSucccess(ArrayList<Book> bookList);
        public void onLoadError();
    }

}
