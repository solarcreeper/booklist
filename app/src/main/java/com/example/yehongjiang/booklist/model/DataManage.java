package com.example.yehongjiang.booklist.model;

import android.util.Log;

import java.util.ArrayList;

/**
 * <copyright>Copyright (c) 2018 All Right Reserved</copyright>
 * <author>Van Ye</author>
 * <date>2018/1/23</date>
 * <summary>booklist</summary>
 */

public class DataManage {
    public static final String TAG = "DataManage JSON parse";

    public static BookList getRequiredBooks(int num, int start){
        String type = "tag";
        String value = "test";
        int total = ConnectBase.getSearchTotal(type, value);
        ArrayList<Book> books = new ArrayList<>();

        if (total < num){
            ArrayList<Book> searchBooks = ConnectBase.searchNet(type, value, start, total);
            for (int index = 0; index<searchBooks.size(); index++){
                if (Integer.valueOf(searchBooks.get(index).getRating().get(2)) > 9){
                    books.add(searchBooks.get(index));
                }
            }
        }
        else{
            while(start < total && books.size() < num){
                ArrayList<Book> searchBooks = ConnectBase.searchNet(type, value, start, num);
                for (int index = 0; index<searchBooks.size(); index++){
                    if (Float.valueOf(searchBooks.get(index).getRating().get(2)) > 9){
                        books.add(searchBooks.get(index));
                    }
                }
                start = start + num;
                Log.d("start is", String.valueOf(start));
            }
        }
        Log.d("book search finished", String.valueOf(books.size()));
        BookList bookList = new BookList(books, start, total);
        return bookList;
    }

}
