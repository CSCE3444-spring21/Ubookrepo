//model class for the book information
package com.example.profilehomepage;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

//class implements Serializable
public class model implements Serializable {

    /*
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Exclude
    private String id;


     */

    //declaring the necessary variables
    String bookname, author, isbn, price, year, sellerId;


    model() {
    }

    //parameterized constructor
    public model(String bookname, String author, String isbn, String price, String year, String sellerId) {
        this.bookname = bookname;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
        this.year = year;
        this.sellerId = sellerId;
    }

    //getters and setters
    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
