package com.example.yehongjiang.booklist.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

/**
 * <copyright>Copyright (c) 2018 All Right Reserved</copyright>
 * <author>Van Ye</author>
 * <date>2018/1/23</date>
 * <summary>booklist</summary>
 */

public class DataManage {
    public static final String TAG = "DataManage JSON parse";

    public static String register(String username, String password) {
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        names.add("username");
        names.add("password");
        names.add("action");
        values.add(username);
        values.add(password);
        values.add("register");
        return ConnectBase.httpConnectionPost(names, values);
    }

    public static String login(String username, String password) {
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        names.add("username");
        names.add("password");
        names.add("action");
        values.add(username);
        values.add(password);
        values.add("login");
        return ConnectBase.httpConnectionPost(names, values);
    }

    public static String addWantedBook(Book book, String userId) {
        JSONObject object = book.encodeJson();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        names.add("book");
        names.add("user_id");
        names.add("action");
        values.add(object.toString());
        values.add(userId);
        values.add("addWantedBook");
        return ConnectBase.httpConnectionPost(names, values);
    }

    public static String removeWantedBook(Book book, String userId) {
        JSONObject object = book.encodeJson();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        names.add("book");
        names.add("user_id");
        names.add("action");
        values.add(object.toString());
        values.add(userId);
        values.add("removeWantedBook");
        return ConnectBase.httpConnectionPost(names, values);
    }

    public static BookList getBookList(String userId, int start, int count) {
        ArrayList<Book> bookList = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        names.add("user_id");
        names.add("start");
        names.add("count");
        names.add("action");
        values.add(userId);
        values.add(String.valueOf(start));
        values.add(String.valueOf(count));
        values.add("getBookList");
        String result = ConnectBase.httpConnectionPost(names, values);
        try {
            JSONTokener tokener = new JSONTokener(result);
            JSONArray books = (JSONArray) tokener.nextValue();
            for (int i = 0; i < books.length(); i++) {
                JSONObject book = (JSONObject) books.get(i);
                String isbn10 = book.getString("isbn10");
                String isbn13 = book.getString("isbn13");
                String title = book.getString("title");
                String originTitle = book.getString("origin_title");
                String altTitle = book.getString("alt_title");
                String subTitle = book.getString("sub_title");
                String url = book.getString("url");
                String alt = book.getString("alt");
                String image = book.getString("image");

                ArrayList<String> images = new ArrayList<>();
                JSONObject imagesObj = book.getJSONObject("images");
                images.add(imagesObj.getString("small"));
                images.add(imagesObj.getString("large"));
                images.add(imagesObj.getString("medium"));

                ArrayList<String> author = new ArrayList<>();
                JSONArray authorList = book.getJSONArray("author");
                for (int index = 0; index < authorList.length(); index++) {
                    author.add(authorList.get(index).toString());
                }

                ArrayList<String> translator = new ArrayList<>();
                JSONArray translatorList = book.getJSONArray("translator");
                for (int index = 0; index < translatorList.length(); index++) {
                    translator.add(translatorList.get(index).toString());
                }

                String publisher = book.getString("publisher");
                String pubdate = book.getString("pubdate");

                ArrayList<String> rating = new ArrayList<>();
                JSONObject ratingObj = book.getJSONObject("rating");
                rating.add(ratingObj.getString("max"));
                rating.add(ratingObj.getString("num_raters"));
                rating.add(ratingObj.getString("average"));
                rating.add(ratingObj.getString("min"));

                String binding = book.getString("binding");
                String price = book.getString("price");

                String pages = book.getString("pages");
                String authorIntro = book.getString("author_intro");
                String summary = book.getString("summary");
                String catalog = book.getString("catalog");
                boolean isWanted = true;

                Book newBook = new Book(isbn10, isbn13, title, originTitle, altTitle, subTitle,
                        url, alt, image, images, author, translator, publisher, pubdate, rating,
                        binding, price, pages, authorIntro, summary, catalog, isWanted);
                bookList.add(newBook);
            }
        } catch (JSONException e) {
            Log.d(TAG, "function:searchNet error:json parse error");
            e.printStackTrace();
        }
        BookList bookResult = new BookList(bookList, start, bookList.size());

        return bookResult;
    }


    public static BookList getRequiredBooks(int num, int start) {
        String type = "q";
        String[] keys = {"java", "python", "C++", "C#", "JavaScript", "JSON", "MySql", "DataBase", "Php", "Ruby"};
        String value = keys[(int) (Math.random() * 10)];

        int total = ConnectBase.getSearchTotal(type, value);
        ArrayList<Book> books = new ArrayList<>();

        if (total < num) {
            ArrayList<Book> searchBooks = ConnectBase.searchNet(type, value, start, total);
            for (int index = 0; index < searchBooks.size(); index++) {
                if (Float.valueOf(searchBooks.get(index).getRating().get(2)) > 1) {
                    books.add(searchBooks.get(index));
                }
            }
        } else {
            while (start < total && books.size() < num) {
                ArrayList<Book> searchBooks = ConnectBase.searchNet(type, value, start, num);
                for (int index = 0; index < searchBooks.size(); index++) {
                    if (Float.valueOf(searchBooks.get(index).getRating().get(2)) > 1) {
                        books.add(searchBooks.get(index));
                    }
                }
                start = start + num;
                Log.d("start is", String.valueOf(start));
            }
        }
        Log.d("book search finished", String.valueOf(books.size()));
        for (int i = 0; i < books.size(); i++) {
            Log.d("book" + String.valueOf(i), books.get(i).getTitle() + "_" + books.get(i).getImages().get(2));
        }
        BookList bookList = new BookList(books, start, total);
        return bookList;
    }

    public static BookList getRequiredBooks(int num, int start, String value) {
        String type = "q";
        int total = ConnectBase.getSearchTotal(type, value);
        ArrayList<Book> books = new ArrayList<>();

        if (total < num) {
            ArrayList<Book> searchBooks = ConnectBase.searchNet(type, value, start, total);
            for (int index = 0; index < searchBooks.size(); index++) {
                if (Float.valueOf(searchBooks.get(index).getRating().get(2)) > 0) {
                    books.add(searchBooks.get(index));
                }
            }
        } else {
            while (start < total && books.size() < num) {
                ArrayList<Book> searchBooks = ConnectBase.searchNet(type, value, start, num);
                for (int index = 0; index < searchBooks.size(); index++) {
                    if (Float.valueOf(searchBooks.get(index).getRating().get(2)) > 0) {
                        books.add(searchBooks.get(index));
                    }
                }
                start = start + num;
                Log.d("start is", String.valueOf(start));
            }
        }
        Log.d("book search finished", String.valueOf(books.size()));
        for (int i = 0; i < books.size(); i++) {
            Log.d("book" + String.valueOf(i), books.get(i).getTitle() + "_" + books.get(i).getImages().get(2));
        }
        BookList bookList = new BookList(books, start, total);
        return bookList;
    }


}
