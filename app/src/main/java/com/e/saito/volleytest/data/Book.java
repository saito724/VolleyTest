package com.e.saito.volleytest.data;

/**
 * Created by e.saito on 2014/06/03.
 */
public class Book {
    public final String URL_BOOK_IMG = "http://www.hanmoto.com/bd/img/";
    public final String BOOK_IMG_EXTENSION = ".jpg";


    public int id;
    public String title;
    public String isbn;
    public String creator;
    public String publisher;
    public String Kaisetsu;
    public String DetailPageURL;


    public class  GenreInfo{
        public Genre genre1;
        public Genre genre2;
        public Genre genre3;

        private class Genre{
            public int id;
            public String name;
        }
    }

 public  String getImgUrl(){
     return URL_BOOK_IMG + isbn + BOOK_IMG_EXTENSION;
 }

}

