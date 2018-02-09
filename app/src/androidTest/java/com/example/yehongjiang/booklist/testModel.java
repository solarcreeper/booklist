package com.example.yehongjiang.booklist;

import android.util.Log;

import com.example.yehongjiang.booklist.model.Book;
import com.example.yehongjiang.booklist.model.BookList;
import com.example.yehongjiang.booklist.model.ConnectBase;
import com.example.yehongjiang.booklist.model.Data;
import com.example.yehongjiang.booklist.model.DataManage;

import org.junit.Test;

import java.util.ArrayList;

/**
 * <copyright>Copyright (c) 2018 All Right Reserved</copyright>
 * <author>Van Ye</author>
 * <date>2018/2/6</date>
 * <summary>booklist</summary>
 */
public class testModel {
    @Test
    public void test1() {
        for (int i = 0; i < 100; i++) {
            BookList bookList = DataManage.getRequiredBooks(1, 0);
            if (bookList.getBooks().size() > 0){
                ArrayList<Book> books = bookList.getBooks();
                String register_result = DataManage.register("yehongjiang" + String.valueOf(i), "yehongjiang");
                Log.d("test", register_result);
                String user_id = ConnectBase.decodeResult(DataManage.login("yehongjiang" + String.valueOf(i), "yehongjiang")).get(1).value;
                Log.d("test", user_id);

                String add_result = DataManage.addWantedBook(books.get(0), user_id);
                Log.d("test", add_result);

                ArrayList<Book> booklist = DataManage.getBookList(user_id, 0, 6);

                String remove1 = DataManage.removeWantedBook(booklist.get(0), user_id);
                Log.d("test", remove1);
            }
        }
    }


}
