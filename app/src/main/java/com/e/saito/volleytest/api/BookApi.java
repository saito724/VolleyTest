package com.e.saito.volleytest.api;

import android.util.Log;
import android.util.Xml;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.e.saito.volleytest.data.Book;
import com.e.saito.volleytest.util.StringUtil;
import com.e.saito.volleytest.util.XmlUtil;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by e.saito on 2014/06/03.
 */
public class BookApi {
    public final String URL_BOOK = "http://www.hanmoto.com/api/Operation=ItemSearch&SearchIndex=Books&Keywords=the";

    public static final String TAG_ITEM = "Item";
    public static final String TAG_TITLE = "Title";
    public static final String TAG_KAISETU = "Kaisetsu";
    public static final String TAG_PUBLISHER = "Publisher";
    public static final String TAG_DETAIL_PAGE_URL = "DetailPageURL";
    public static final String TAG_ISBN_13 = "ISBN13wh";

    public void getJsonBookData(RequestQueue queue, final BookListener listener) {
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

        Request request = new JsonObjectRequest(Request.Method.GET, URL_BOOK, null, resListener, errorListener);
        queue.add(request);
    }

    public void getXmlBookData(RequestQueue queue, final BookListener listener) {
        Request request = new StringRequest(
                Request.Method.GET,
                URL_BOOK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            listener.onLoadSucccess(parseXmlBook(response));
                            Log.d("onLoadSucccess", "onLoadSucccess");
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                          // XmlUtil.parseTest(response);
                    }
                    //   XmlUtil.parseTest(response);
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Response.ErrorListener", "error!!!!!!!!");
                        Log.d("Response.ErrorListener", error.getLocalizedMessage());
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


    public static ArrayList<Book> parseXmlBook(String xmlString) throws XmlPullParserException, IOException {

        XmlPullParser xmlPullParser = Xml.newPullParser();
        xmlPullParser.setInput(new StringReader(xmlString));
        int eventType = xmlPullParser.getEventType();
        Log.d("getColumnNumber", ":" + xmlPullParser.getColumnNumber());
        Log.d("getInputEncoding",":" + xmlPullParser.getInputEncoding());
        Log.d("getDepth",":" + xmlPullParser.getDepth());
        if (eventType != XmlPullParser.START_DOCUMENT) {
            return null;
        }

        ArrayList<Book> bookList = new ArrayList<Book>();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG && xmlPullParser.getName().equals(TAG_ITEM) ){
                eventType = xmlPullParser.next();
                Book book = new Book();
                while (true) {
                    if(eventType == XmlPullParser.END_DOCUMENT
                            || (eventType == XmlPullParser.END_TAG   &&  xmlPullParser.getName().equals(TAG_ITEM))){
                        Log.d("1:", Boolean.toString(eventType != XmlPullParser.END_DOCUMENT));
                        Log.d("2:", Boolean.toString( eventType == XmlPullParser.END_TAG ));
                        Log.d("3:", Boolean.toString(xmlPullParser.getName().equals(TAG_ITEM)));
                        break;
                    }
                    if(eventType == XmlPullParser.START_TAG) {
                        Log.d("bigin inner while",xmlPullParser.getName());
                        String name = xmlPullParser.getName();
                        if (name.equals(TAG_TITLE)) {
                            book.title = xmlPullParser.nextText();
                            Log.d("xmlparse title:",book.title);
                        } else if (name.equals(TAG_ISBN_13)) {
                            book.isbn = xmlPullParser.nextText();
                            Log.d("xmlparse isbn:",book.isbn);
                        } else if (name.equals(TAG_KAISETU)) {
                            book.kaisetu = xmlPullParser.nextText();
                            Log.d("xmlparse Kaisetsu:",book.kaisetu);
                        } else if (name.equals(TAG_PUBLISHER)) {
                            book.publisher = xmlPullParser.nextText();
                            Log.d("xmlparse publisher:",book.publisher);
                        } else if (name.equals(TAG_DETAIL_PAGE_URL)) {
                            book.detailPageURL = xmlPullParser.nextText();
                            Log.d("xmlparse publisher:",book.detailPageURL);
                        }

                    }
                    eventType = xmlPullParser.next();

                }//end while
                bookList.add(book);
            }//end if
            eventType = xmlPullParser.next();

        }
        Log.d("parse end:","SIze:" + bookList.size());
        return bookList;
    }

}
