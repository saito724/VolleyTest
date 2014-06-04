package com.e.saito.volleytest.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.e.saito.volleytest.api.BookApi;
import com.e.saito.volleytest.data.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;


public class MainActivity extends BaseActivity {
    private ArrayList<Book> mBookList;
    private ListView mListView;
    private RequestQueue mRequestQueue;
    private MyAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBookList = new ArrayList<Book>();

        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mListView = (ListView)findViewById(R.id.listView);
        mAdapter = new MyAdapter(this, mBookList,mRequestQueue);
        mListView.setAdapter(mAdapter);

        loadBooks();

    }

    private void loadBooks(){
        //Todo bookdata load
        showLoadingDialog();
       new BookApi().getXmlBookData(mRequestQueue, new BookApi.BookListener() {
           @Override
           public void onLoadSucccess(ArrayList<Book> bookList) {
                mBookList = bookList;
                mAdapter.notifyDataSetChanged();
           }
           @Override
           public void onLoadError() {
               Toast.makeText(MainActivity.this,"ERROR!!!!!!!!!!!!!!!!!!!",Toast.LENGTH_SHORT).show();

           }
       });

        //dismiss dialog
    }


    private class MyAdapter extends ArrayAdapter<Book>{
        private RequestQueue mQueue;
        private ImageLoader mImgLoader;
        private LayoutInflater mInflater;

        public MyAdapter(Context context, List<Book> list, RequestQueue queue){
            super(context,0,list);
            mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mQueue = queue;
            mImgLoader = new ImageLoader(queue, new myImgCache());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if(convertView == null){
                convertView = mInflater.inflate(R.layout.list_row, parent);
                holder = new Holder();
                holder.imgView = (ImageView)convertView.findViewById(R.id.ivphoto);
                holder.contentView = (TextView)convertView.findViewById(R.id.tvContents);
                convertView.setTag(holder);
            }else{
                holder = (Holder)convertView.getTag();
            }
             Book  book = getItem(position);

             holder.contentView.setText(book.Kaisetsu);

            ImageLoader.ImageListener listener = ImageLoader.getImageListener(holder.imgView,
                    android.R.drawable.spinner_background,
                    android.R.drawable.ic_dialog_alert);

            mImgLoader.get(book.getImgUrl(),listener);
            return  convertView;
        }


        private class Holder{
            ImageView imgView;
            TextView contentView;
        }


        private class myImgCache implements ImageLoader.ImageCache{
            LruCache<String,Bitmap> cache;
             myImgCache(){
                 int maxMemory = (int)(Runtime.getRuntime().maxMemory() /1024);
                 int cacheSize = maxMemory / 8;

                 cache = new LruCache<String, Bitmap>(cacheSize){
                     @Override
                     protected int sizeOf(String key, Bitmap value) {
                         return value.getByteCount() /1024;
                     }
                 };
             }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }
        }

    }

}
