package com.example.yehongjiang.booklist.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.io.Serializable;

/**
 * <copyright>Copyright (c) 2018 All Right Reserved</copyright>
 * <author>Van Ye</author>
 * <date>2018/1/23</date>
 * <summary>booklist</summary>
 */


public class Book implements Serializable {
    private String isbn10;
    private String isbn13;
    private String title;
    private String originTitle;
    private String altTitle;
    private String subTitle;
    private String url;
    private String alt;
    private String image;
    private ArrayList<String> images;
    private ArrayList<String> author = new ArrayList<>();
    private ArrayList<String> translator = new ArrayList<>();
    private String publisher;
    private String pubdate;
    private ArrayList<String> rating = new ArrayList<>();
    private String binding;
    private String price;
    private String pages;
    private String authorIntro;
    private String summary;
    private String catalog;

    private boolean isWanted;

    public Book() {
    }

    public Book(String isbn10, String isbn13, String title, String originTitle, String altTitle,
                String subTitle, String url, String alt, String image, ArrayList<String> images,
                ArrayList<String> author, ArrayList<String> translator, String publisher,
                String pubdate, ArrayList<String> rating,
                String binding, String price, String pages, String authorIntro,
                String summary, String catalog, boolean isWanted) {
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
        this.title = title.replace("'", "\\\'");
        this.originTitle = originTitle.replace("'", "\\\'");
        this.altTitle = altTitle.replace("'", "\\\'");
        this.subTitle = subTitle.replace("'", "\\\'");
        this.url = url;
        this.alt = alt;
        this.image = image;
        this.images = images;
        this.author = author;
        this.translator = translator;
        this.publisher = publisher;
        this.pubdate = pubdate;
        for (int i = 0; i < rating.size(); i++){
            if (rating.get(i).equals("")){
                rating.set(i, "0");
            }
        }
        this.rating = rating;
        this.binding = binding;
        this.price = price;
        this.pages = pages;
        this.authorIntro = authorIntro.replace("'", "\\\'");
        this.summary = summary.replace("'", "\\\'");
        this.catalog = catalog.replace("'", "\\\'");
        this.isWanted = isWanted;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginTitle() {
        return originTitle;
    }

    public void setOriginTitle(String originTitle) {
        this.originTitle = originTitle;
    }

    public String getAltTitle() {
        return altTitle;
    }

    public void setAltTitle(String altTitle) {
        this.altTitle = altTitle;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public ArrayList<String> getAuthor() {
        return author;
    }

    public void setAuthor(ArrayList<String> author) {
        this.author = author;
    }

    public ArrayList<String> getTranslator() {
        return translator;
    }

    public void setTranslator(ArrayList<String> translator) {
        this.translator = translator;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public ArrayList<String> getRating() {
        return rating;
    }

    public void setRating(ArrayList<String> rating) {
        this.rating = rating;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getAuthorIntro() {
        return authorIntro;
    }

    public void setAuthorIntro(String authorIntro) {
        this.authorIntro = authorIntro;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public boolean isWanted() {
        return isWanted;
    }

    public void setWanted(boolean wanted) {
        isWanted = wanted;
    }

    public void printBookInfo() {
        Log.d("BookInfo_isbn10", this.isbn10);
        Log.d("BookInfo_isbn13", this.isbn13);
        Log.d("BookInfo_title ", this.title);
        Log.d("BookInfo_originTitle", this.originTitle);
        Log.d("BookInfo_altTitle", this.altTitle);
        Log.d("BookInfo_subTitle", this.subTitle);
        Log.d("BookInfo_url", this.url);
        Log.d("BookInfo_alt", this.alt);
        Log.d("BookInfo_image", this.image);
        Log.d("BookInfo_images", this.images.toString());
        Log.d("BookInfo_author", this.author.toString());
        Log.d("BookInfo_translator ", this.translator.toString());
        Log.d("BookInfo_publisher", this.publisher);
        Log.d("BookInfo_pubdate", this.pubdate);
        Log.d("BookInfo_rating ", this.rating.toString());
        Log.d("BookInfo_binding", this.binding);
        Log.d("BookInfo_price", this.price);
        Log.d("BookInfo_pages", this.pages);
        Log.d("BookInfo_authorInt", this.authorIntro);
        Log.d("BookInfo_summary", this.summary);
        Log.d("BookInfo_catalog", this.catalog);
    }

    public JSONObject encodeJson() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("isbn10", this.isbn10);
            obj.put("isbn13", this.isbn13);
            obj.put("title ", this.title);
            obj.put("originTitle", this.originTitle);
            obj.put("altTitle", this.altTitle);
            obj.put("subTitle", this.subTitle);
            obj.put("url", this.url);
            obj.put("alt", this.alt);
            obj.put("image", this.image);
            JSONObject images = new JSONObject();
            images.put("small", this.images.get(0));
            images.put("large", this.images.get(1));
            images.put("medium", this.images.get(2));
            obj.put("images", images);
            obj.put("author", new JSONArray(this.author));
            obj.put("translator", new JSONArray(this.translator));
            obj.put("publisher", this.publisher);
            obj.put("pubdate", this.pubdate);
            JSONObject rating = new JSONObject();
            rating.put("max", this.rating.get(0));
            rating.put("numRaters", this.rating.get(1));
            rating.put("average", this.rating.get(2));
            rating.put("min", this.rating.get(3));
            obj.put("rating", rating);
            obj.put("binding", this.binding);
            obj.put("price", this.price);
            obj.put("pages", this.pages);
            obj.put("authorIntro", this.authorIntro);
            obj.put("summary", this.summary);
            obj.put("catalog", this.catalog);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("book json", obj.toString());
        return obj;
    }
}
