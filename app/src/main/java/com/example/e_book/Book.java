package com.example.e_book;

import android.widget.TextView;

public class Book {
    String bookname;
    String discription;
    String authorname;
    String timestamp;

    public Book() {
    }

    public Book(String bookname, String discription, String authorname, String timestamp) {
        this.bookname = bookname;
        this.discription = discription;
        this.authorname = authorname;
        this.timestamp = timestamp;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
