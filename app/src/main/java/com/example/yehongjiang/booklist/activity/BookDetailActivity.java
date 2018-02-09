package com.example.yehongjiang.booklist.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.commit451.nativestackblur.NativeStackBlur;
import com.example.yehongjiang.booklist.R;
import com.example.yehongjiang.booklist.model.Book;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


/**
 * <copyright>Copyright (c) 2018 All Right Reserved</copyright>
 * <author>Van Ye</author>
 * <date>2018/1/23</date>
 * <summary>booklist</summary>
 */
public class BookDetailActivity extends AppCompatActivity {
    private Book book;
    private Toolbar toolbar;
    private ImageView bookImageBackgroud;
    private ImageView bookImage;
    private TextView bookTitle;
    private TextView bookPubInfo;
    private Button wanted;
    private ExpandableTextView expandableTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdetail);

        Bundle bundle = getIntent().getExtras();
        book = (Book) bundle.getSerializable("content");

        toolbar = findViewById(R.id.toolbar_detail);
        toolbar.setTitleTextColor(getColor(android.R.color.white));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_arrow_back_white_24dp);

        bookImageBackgroud = findViewById(R.id.book_image_backgroud);
        bookImage = findViewById(R.id.book_image);
        bookTitle = findViewById(R.id.book_title);
        bookPubInfo = findViewById(R.id.publish_info);
        wanted = findViewById(R.id.want);
        expandableTextView = findViewById(R.id.expand_text_area);

        wanted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bookImage.setScaleType(ImageView.ScaleType.FIT_XY);
        Picasso.with(this)
                .load(book.getImages().get(2))
                .into(bookImage, new Callback.EmptyCallback() {
                    @Override
                    public void onSuccess() {
                        super.onSuccess();
                        Drawable drawable = bookImage.getDrawable();

                        // check if height too large there
                        DisplayMetrics dm = new DisplayMetrics();
                        getWindowManager().getDefaultDisplay().getMetrics(dm);
                        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) bookImage.getLayoutParams();
                        ViewGroup.LayoutParams vlp = bookImageBackgroud.getLayoutParams();
                        int targetWidthBook = (int) (dm.widthPixels * 0.45);
                        int targetWidthBlur = dm.widthPixels;
                        int targetHeightBook = (int) ((float) targetWidthBook / drawable.getMinimumWidth() * drawable.getMinimumHeight());

                        int blurMarginTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (24 + 56), getResources().getDisplayMetrics());
                        int targetHeightBlur = targetHeightBook + blurMarginTop;

                        rlp.width = targetWidthBook;
                        rlp.height = targetHeightBook;

                        vlp.width = targetWidthBlur;
                        vlp.height = targetHeightBlur;

                        bookImage.setLayoutParams(rlp);
                        bookImageBackgroud.setLayoutParams(vlp);
                        Bitmap bitmap = ((BitmapDrawable) (bookImage.getDrawable())).getBitmap();
                        Bitmap blur = NativeStackBlur.process(bitmap, 35);
                        bookImageBackgroud.setImageBitmap(blur);
                    }
                });

        String title = book.getTitle();
        if (!book.getSubTitle().equals("")) {
            title = title + ": " + book.getSubTitle();
        }
        bookTitle.setText(title);

        String publisher = book.getPublisher();
        String author = book.getAuthor().get(0);
        for (int i = 1; i < book.getAuthor().size(); i++) {
            author = author + ", " + book.getAuthor().get(i);
        }
        String pubDate = book.getPubdate();

        String pubInfo = author + " / " + publisher + " / " + pubDate;
        bookPubInfo.setText(pubInfo);

        String summary = book.getSummary();
        if (summary.equals("")) {
            summary = "no introduction";
        }
        expandableTextView.setText(summary);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
