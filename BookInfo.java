//book info class
package com.example.profilehomepage;
import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class BookInfo implements Serializable {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Exclude
    private String id;


    //declaring variables
    private String bookName;
    private String author;
    private String isbn;
    private String price;
    private String year;
    private String sellerId;

    public BookInfo() {

    }

    //parameterized constructor
    public BookInfo(String bookName, String author, String isbn, String price, String year, String sellerId){
        this.bookName=bookName;
        this.author=author;
        this.isbn=isbn;
        this.price=price;
        this.year=year;
        this.sellerId=sellerId;
    }

    //setters and getters
    public String getSellerId() {
        return sellerId;
    }
    public void setSellerId(String sellerId){
        this.sellerId=sellerId;
    }

    public String getBookname(){
        return bookName;
    }

    public void setBookname(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor(){
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn(){
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPrice(){
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getYear(){
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
