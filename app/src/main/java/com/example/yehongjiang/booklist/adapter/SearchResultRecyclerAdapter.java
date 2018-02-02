package com.example.yehongjiang.booklist.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yehongjiang.booklist.R;
import com.example.yehongjiang.booklist.activity.BookDetailActivity;
import com.example.yehongjiang.booklist.model.Book;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * <copyright>Copyright (c) 2018 All Right Reserved</copyright>
 * <author>Van Ye</author>
 * <date>2018/1/28</date>
 * <summary>booklist</summary>
 */
public class SearchResultRecyclerAdapter extends RecyclerView.Adapter<SearchResultRecyclerAdapter.SearchResultItemViewHolder>{
    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<Book> books;


    public SearchResultRecyclerAdapter(Context context, ArrayList<Book> books) {
        this.context = context;
        this.books = new ArrayList<>();
        this.books.addAll(books);
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setBooks(ArrayList<Book> book){
        this.books.clear();
        this.books.addAll(book);
    }

    public void clear(){
        this.books.clear();
    }

    @Override
    public SearchResultItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchResultItemViewHolder(layoutInflater.inflate(R.layout.book_card_in_search_result, parent, false));
    }

    @Override
    public void onBindViewHolder(final SearchResultItemViewHolder holder, final int position) {
        Book book = books.get(position);
        book.printBookInfo();
        String title = book.getTitle();
        if(!book.getSubTitle().equals("")){
            title = title + ":" + book.getSubTitle();
        }
        holder.title.setText(title);
        String publisher = book.getPublisher();
        String author = book.getAuthor().get(0);
        for(int i = 1; i < book.getAuthor().size(); i++){
            author = author + ", " + book.getAuthor().get(i);
        }
        String pubDate = book.getPubdate();
        String pubInfo = author + "/" + publisher + "/" + pubDate;
        holder.subText.setText(pubInfo);

        Picasso.with(context)
                .load(book.getImages().get(2))
                .into(holder.imageView, new Callback.EmptyCallback(){
                    @Override
                    public void onSuccess() {
                        super.onSuccess();
                        Drawable drawable = holder.imageView.getDrawable();
                        int targetWidth = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, context.getResources().getDisplayMetrics());
                        int targetHeight = (int)((float)targetWidth/drawable.getMinimumWidth()*drawable.getMinimumHeight());
                        RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams)holder.imageView.getLayoutParams();
                        rl.height = targetHeight;
                        rl.width = targetWidth;
                        holder.imageView.setLayoutParams(rl);
                        final Bitmap source = ((BitmapDrawable)drawable).getBitmap();
                        Palette.from(source)
                                .maximumColorCount(32)
                                .generate(new Palette.PaletteAsyncListener() {
                                    @Override
                                    public void onGenerated(Palette palette) {
                                        Palette.Swatch swatch = palette.getVibrantSwatch();
                                        if(swatch != null){
                                            holder.cardView.setCardBackgroundColor(swatch.getRgb());
                                        }
                                    }
                                });
                    }
                });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("content", books.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("size", String.valueOf(books.size()));
        return books.size();
    }

    class SearchResultItemViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView imageView;
        TextView title;
        TextView subText;

        public SearchResultItemViewHolder(final View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_in_search);
            imageView = itemView.findViewById(R.id.card_book_image_in_search_list);
            title = itemView.findViewById(R.id.title_in_list);
            subText = itemView.findViewById(R.id.subtext_in_list);
        }
}


}

