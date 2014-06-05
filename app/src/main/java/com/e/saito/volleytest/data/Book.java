package com.e.saito.volleytest.data;

/**
 * Created by e.saito on 2014/06/03.
 */
public class Book {
    public final String URL_BOOK_IMG = "http://www.hanmoto.com/bd/img/";
    public final String BOOK_IMG_EXTENSION = ".jpg";

    public Book(String title,String isbn, String publisher, String kaisetu){
        this.title = title;
        this.isbn = isbn;
        this.publisher = publisher;
        this.kaisetu = kaisetu;

    };

    public Book(){

    }


    public String title;
    public String isbn;
 //   public String creator;
    public String publisher;
    public String kaisetu;
    public String detailPageURL;


 public  String getImgUrl(){
     return URL_BOOK_IMG + isbn + BOOK_IMG_EXTENSION;
 }

}

