package com.e.saito.volleytest.api;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.e.saito.volleytest.data.Book;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by e.saito on 2014/06/03.
 */
public class BookApi {
    public final String URL_BOOK = "";

    public void getBookData(RequestQueue queue , final BookListener listener){
        Response.Listener<JSONObject> resListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //data to arraylist

                listener.onLoadSucces(new ArrayList<Book>());
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


    public interface BookListener {
        public void onLoadSucces(ArrayList<Book> bookList);
        public void onLoadError();
    }

}
