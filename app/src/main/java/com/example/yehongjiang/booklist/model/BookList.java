package com.example.yehongjiang.booklist.model;

import java.util.ArrayList;

/**
 * <copyright>Copyright (c) 2018 All Right Reserved</copyright>
 * <author>Van Ye</author>
 * <date>2018/1/23</date>
 * <summary>booklist</summary>
 */
public class BookList {
    private ArrayList<Book> books;
    private int    bookStart;
    private int    bookCount;

    public BookList(){

    }

    public BookList(ArrayList<Book> books, int bookStart, int bookCount){
        this.books = books;
        this.bookStart = bookStart;
        this.bookCount = bookCount;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public int getBookStart() {
        return bookStart;
    }

    public void setBookStart(int bookStart) {
        this.bookStart = bookStart;
    }

    public int getBookCount() {
        return bookCount;
    }

    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }
}
