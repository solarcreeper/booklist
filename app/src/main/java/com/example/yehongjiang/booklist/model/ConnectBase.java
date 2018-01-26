package com.example.yehongjiang.booklist.model;


import android.util.Log;

import com.example.yehongjiang.booklist.config.GlobalSetting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL;

/**
 * <copyright>Copyright (c) 2018 All Right Reserved</copyright>
 * <author>Van Ye</author>
 * <date>2018/1/23</date>
 * <summary>booklist</summary>
 */

public class ConnectBase {
    public static final String TAG = "ConnectBase JSON parse";

    public static int getSearchTotal(String type, String value) {
        int total = 0;
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        names.add(type);
        names.add("start");
        names.add("count");
        values.add(value);
        values.add(Integer.toString(0));
        values.add(Integer.toString(1));
        String result = ConnectBase.httpConnectionGet(GlobalSetting.REQUEST_GET_URL, names, values);

        try {
            JSONTokener jsonTokener = new JSONTokener(result);
            JSONObject object = (JSONObject) jsonTokener.nextValue();
            total = object.getInt("total");
        } catch (JSONException e) {
            Log.d(TAG, "function:getSearchTotal error:json parse error");
            total = -1;
            e.printStackTrace();
        }
        return total;
    }

    public static ArrayList<Book> searchNet(String type, String value, int start, int count) {
        ArrayList<Book> bookArrayList = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        names.add(type);
        names.add("start");
        names.add("count");
        values.add(value);
        values.add(Integer.toString(start));
        values.add(Integer.toString(count));
        String result = ConnectBase.httpConnectionGet(GlobalSetting.REQUEST_GET_URL, names, values);

        try {
            JSONTokener tokener = new JSONTokener(result);
            JSONObject jsonObject = (JSONObject) tokener.nextValue();
            JSONArray books = jsonObject.getJSONArray("books");
            for (int i = 0; i < books.length(); i++) {
                JSONObject book = (JSONObject) books.get(i);
                String isbn10 = book.getString("isbn10");
                String isbn13 = book.getString("isbn13");
                String title = book.getString("title");
                String originTitle = book.getString("origin_title");
                String altTitle = book.getString("alt_title");
                String subTitle = book.getString("subtitle");
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
                rating.add(ratingObj.getString("numRaters"));
                rating.add(ratingObj.getString("average"));
                rating.add(ratingObj.getString("min"));

                ArrayList<String> tags = new ArrayList<>();
                JSONArray tagsList = book.getJSONArray("tags");
                for (int index = 0; index < tagsList.length(); index++) {
                    JSONObject object = (JSONObject) tagsList.get(index);
                    tags.add(object.getString("name"));
                }

                String binding = book.getString("binding");
                String price = book.getString("price");

                String pages = book.getString("pages");
                String authorIntro = book.getString("author_intro");
                String summary = book.getString("summary");
                String catalog = book.getString("catalog");
                boolean isWanted = false;

                Book newBook = new Book(isbn10, isbn13, title, originTitle, altTitle, subTitle,
                        url, alt, image, images, author, translator, publisher, pubdate, rating,
                        tags, binding, price, pages, authorIntro, summary, catalog, isWanted);
                bookArrayList.add(newBook);
            }
        } catch (JSONException e) {
            Log.d(TAG, "function:searchNet error:json parse error");
            e.printStackTrace();
        }
        return bookArrayList;
    }

    public static String httpConnectionPost(ArrayList<String> keys, ArrayList<String> values) {
        String result = "";
        String keyValues = ConnectBase.encodeParams(keys, values);
        URL url;
        try {
            url = new URL(GlobalSetting.REQUEST_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setConnectTimeout(GlobalSetting.TIMEOUT);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(keyValues);
            outputStream.flush();
            outputStream.close();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader in = new InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(in);
                String inputLine = null;
                while ((inputLine = reader.readLine()) != null) {
                    result = result + inputLine + "\n";
                }
                in.close();
            } else {
                result = "{\"error_code\":\"" + String.valueOf(GlobalSetting.NETWORK_ERROR) + "\"}";
            }
            connection.disconnect();
        } catch (IOException e) {
            result = "{\"error_code\":\"" + String.valueOf(GlobalSetting.UNKNOWN_ERROR) + "\"}";
            e.printStackTrace();
        }
        Log.d(TAG, "function:httpConnectionPost error:" + result);
        return result;
    }

    public static String httpConnectionGet(String urlAddress, ArrayList<String> keys, ArrayList<String> values) {
        String result = "";
//        String content = ConnectBase.encodeParams(keys, values);
//        URL url;
//        try {
//            url = new URL(urlAddress + "?" + content);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            connection.setConnectTimeout(GlobalSetting.TIMEOUT);
//            connection.setDoInput(true);
//            connection.setDoOutput(true);
//            connection.setUseCaches(false);
//            connection.connect();
//            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
//            out.writeBytes(content);
//            out.flush();
//            out.close();
//            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                InputStreamReader in = new InputStreamReader(connection.getInputStream());
//                BufferedReader reader = new BufferedReader(in);
//                String inputLine = null;
//                while ((inputLine = reader.readLine()) != null) {
//                    result = result + inputLine + "\n";
//                }
//                in.close();
//            } else {
//                result = "{\"error_code\":\"" + String.valueOf(GlobalSetting.NETWORK_ERROR) + "\"}";
//            }
//            connection.disconnect();
//        } catch (IOException e) {
//            result = "{\"error_code\":\"" + String.valueOf(GlobalSetting.NETWORK_ERROR) + "\"}";
//            e.printStackTrace();
//        }
//        Log.d(TAG, "function:httpConnectionGet error:" + result);
        result = GlobalSetting.FADE_JSON;
        return result;
    }

    private static String encodeParams(ArrayList<String> keys, ArrayList<String> values) {
        String result = "";
        try {
            result = result + keys.get(0) + "=" + URLEncoder.encode(values.get(0), "utf-8");
            for (int i = 1; i < keys.size(); i++) {
                result = result + "&" + keys.get(i) + "=" + URLEncoder.encode(values.get(i), "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            Log.d(TAG, "function:encodeParams error: UnsupportedEncodingException");
            e.printStackTrace();
        }
        return result;
    }
}