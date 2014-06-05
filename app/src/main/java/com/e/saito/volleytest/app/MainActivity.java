package com.e.saito.volleytest.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.e.saito.volleytest.util.StringUtil;

import java.util.ArrayList;
import java.util.List;


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
        mBookList.add(new Book("DUMMY TITLE", "978-4-7503-3994-8", "this is contents", "publisher"));
        mBookList.add(new Book("DUMMY 2", "9", "日本語表示してみる", "publisher2"));
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mListView = (ListView) findViewById(R.id.listView);
        mAdapter = new MyAdapter(this, mBookList, mRequestQueue);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Book book = mBookList.get(position);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(book.detailPageURL));
                startActivity(intent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        loadBooks();

    }

    private void loadBooks() {
        //Todo bookdata load
        showLoadingDialog();
        new BookApi().getXmlBookData(mRequestQueue, new BookApi.BookListener() {
            @Override
            public void onLoadSucccess(ArrayList<Book> bookList) {
                for (Book book : bookList) {
                    Log.d("TITLE", book.title);
                }

                mAdapter.clear();
                for (Book book : bookList) {
                    mAdapter.add(book);
                }
                String title1 = mAdapter.getItem(0).title;
                Log.d("isEUC", Boolean.toString(StringUtil.isEUC(title1)));
                Log.d("isSJIS", Boolean.toString(StringUtil.isSJIS(title1)));
                Log.d("isUTF8", Boolean.toString(StringUtil.isUTF8(title1)));
                Log.d("isWindows31j", Boolean.toString(StringUtil.isWindows31j(title1)));
                mAdapter.notifyDataSetChanged();
                dismissLoadingDialog();
            }

            @Override
            public void onLoadError() {
                Toast.makeText(MainActivity.this, "ERROR!!!!!!!!!!!!!!!!!!!", Toast.LENGTH_SHORT).show();
                dismissLoadingDialog();
            }
        });

        //dismiss dialog
    }


    private class MyAdapter extends ArrayAdapter<Book> {
        private RequestQueue mQueue;
        private ImageLoader mImgLoader;
        private LayoutInflater mInflater;

        public MyAdapter(Context context, List<Book> list, RequestQueue queue) {
            super(context, 0, list);
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mQueue = queue;
            mImgLoader = new ImageLoader(queue, new myImgCache());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null) {
                convertView = this.mInflater.inflate(R.layout.list_row, null);
                holder = new Holder();
                holder.imgView = (ImageView) convertView.findViewById(R.id.ivphoto);
                holder.contentView = (TextView) convertView.findViewById(R.id.tvContents);
                holder.titleView = (TextView) convertView.findViewById(R.id.tvTitle);
                holder.publisherView = (TextView) convertView.findViewById(R.id.tvPublsher);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            final Book book = getItem(position);


            holder.titleView.setText(book.title);
            holder.contentView.setText(book.kaisetu);
            holder.publisherView.setText(book.publisher);


            ImageLoader.ImageContainer imgContainer = (ImageLoader.ImageContainer) holder.imgView.getTag();
            if (imgContainer != null) {
                imgContainer.cancelRequest();
            }
            ImageLoader.ImageListener listener = ImageLoader.getImageListener(holder.imgView,
                    android.R.drawable.progress_horizontal,
                    android.R.drawable.ic_dialog_alert);
            holder.imgView.setTag(mImgLoader.get(book.getImgUrl(), listener));

            return convertView;
        }


        private class Holder {
            ImageView imgView;
            TextView titleView;
            TextView publisherView;
            TextView contentView;
        }


        private class myImgCache implements ImageLoader.ImageCache {
            LruCache<String, Bitmap> cache;

            myImgCache() {
                int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
                Log.d("maxMemory",""+ maxMemory);
                int cacheSize = maxMemory / 4;

                cache = new LruCache<String, Bitmap>(cacheSize) {
                    @Override
                    protected int sizeOf(String key, Bitmap value) {
                        return value.getRowBytes() * value.getHeight() / 1024;
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
