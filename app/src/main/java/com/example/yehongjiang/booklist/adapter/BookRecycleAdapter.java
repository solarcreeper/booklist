package com.example.yehongjiang.booklist.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yehongjiang.booklist.R;
import com.example.yehongjiang.booklist.activity.BookDetailActivity;
import com.example.yehongjiang.booklist.model.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * <copyright>Copyright (c) 2018 All Right Reserved</copyright>
 * <author>Van Ye</author>
 * <date>2018/1/24</date>
 * <summary>booklist</summary>
 */
public class BookRecycleAdapter extends RecyclerView.Adapter<BookRecycleViewHolder>{
    private LayoutInflater inflater;
    private Context mContext;
    private ArrayList<Book> mBooks;

    public BookRecycleAdapter(Context context, ArrayList<Book> books) {
        this.mContext = context;
        this.mBooks = books;
        this.inflater = LayoutInflater.from(context);
    }

    public void add(ArrayList<Book> books) {
        mBooks.addAll(books);
        notifyItemInserted(mBooks.size() - 1);
    }

    @Override
    public BookRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BookRecycleViewHolder(inflater.inflate(R.layout.book_card_in_grid, parent, false));
    }

    @Override
    public void onBindViewHolder(final BookRecycleViewHolder holder, final int position) {
        holder.mTextView.setText(mBooks.get(position).getTitle());
        // 设置图片
        Picasso.with(mContext)
                .load(mBooks.get(position).getImages().get(2))
                .into(holder.mImageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("GridViewHolder", "onClick --> position = " + String.valueOf(position));
                Intent intent = new Intent(holder.itemView.getContext(), BookDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("content", mBooks.get(position));
                intent.putExtras(bundle);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }
}

class BookRecycleViewHolder extends RecyclerView.ViewHolder{
    final double CARD_HEIGHT_SCALE_RATIO = 0.5;
    CardView cardView;
    TextView mTextView;
    ImageView mImageView;

    public BookRecycleViewHolder(View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.card_view);
        mTextView = itemView.findViewById(R.id.title_in_grid);
        mImageView = itemView.findViewById(R.id.card_book_image_in_grid);
    }
}