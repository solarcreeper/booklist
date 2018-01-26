package com.example.yehongjiang.booklist.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
 * <date>2018/1/23</date>
 * <summary>booklist</summary>
 */
public class BookGridRecycleViewAdapter extends RecyclerView.Adapter<BookGridRecycleViewAdapter.BookGridViewHolder> {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private ArrayList<Book> mBooks;

    class BookGridViewHolder extends RecyclerView.ViewHolder {
        private final double CARD_HEIGHT_SCALE_RATIO = 0.5;
        private CardView cardView;
        private TextView mTextView;
        private ImageView mImageView;

        BookGridViewHolder(final View itemView) {
            super(itemView);
            DisplayMetrics dm = itemView.getResources().getDisplayMetrics();
            ViewGroup.LayoutParams lp = itemView.getLayoutParams();
            lp.height = (int) (CARD_HEIGHT_SCALE_RATIO * dm.widthPixels);
            itemView.setLayoutParams(lp);

            cardView = itemView.findViewById(R.id.card_in_grid);
            mTextView = itemView.findViewById(R.id.title_in_grid);
            mImageView = itemView.findViewById(R.id.card_book_image_in_grid);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("GridViewHolder", "onClick --> position = " + getAdapterPosition());
                    Intent intent = new Intent(itemView.getContext(), BookDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("content", mBooks.get(getAdapterPosition()));
                    intent.putExtras(bundle);
                    itemView.getContext().startActivity(intent);
                }
            });

        }
    }

    public BookGridRecycleViewAdapter(Context mContext, ArrayList<Book> mBooks) {
        this.mContext = mContext;
        this.mBooks = mBooks;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public BookGridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BookGridViewHolder(mLayoutInflater.inflate(R.layout.book_card_in_grid, parent, false));
    }

    @Override
    public void onBindViewHolder(final BookGridViewHolder holder, int position) {
        Log.d("bindViewHolder", "start");
        holder.mTextView.setText(mBooks.get(position).getTitle());
        Picasso.with(mContext)
                .load(mBooks.get(position).getImages().get(2))
                .into(holder.mImageView, new Callback.EmptyCallback(){
                    @Override
                    public void onSuccess(){
                        Bitmap source = ((BitmapDrawable)holder.mImageView.getDrawable()).getBitmap();
                        Palette.from(source)
                                .maximumColorCount(32)
                                .generate(new Palette.PaletteAsyncListener() {
                                    @Override
                                    public void onGenerated(Palette palette) {
                                        Palette.Swatch swatch = palette.getVibrantSwatch();
                                        if (swatch != null){
                                            holder.cardView.setCardBackgroundColor(swatch.getRgb());
                                        }
                                    }
                                });
                    }
                });
    }

    @Override
    public int getItemCount() {
        Log.d("item count", String.valueOf(mBooks.size()));
        return mBooks.size();
    }
}
