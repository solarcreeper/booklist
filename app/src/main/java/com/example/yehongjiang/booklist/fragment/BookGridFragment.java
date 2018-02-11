package com.example.yehongjiang.booklist.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yehongjiang.booklist.R;
import com.example.yehongjiang.booklist.adapter.BookGridRecyclerViewAdapter;
import com.example.yehongjiang.booklist.model.Book;
import com.example.yehongjiang.booklist.model.BookList;
import com.example.yehongjiang.booklist.model.DataManage;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

/**
 * <copyright>Copyright (c) 2018 All Right Reserved</copyright>
 * <author>Van Ye</author>
 * <date>2018/1/23</date>
 * <summary>booklist</summary>
 */

public class BookGridFragment extends BookListFragment {
    private RecyclerView mRecyclerView;
    private BookGridRecyclerViewAdapter adapter;
    private int mSectiontCategory;

    public BookGridFragment() {

    }

    public static Fragment newInstance(int sectionCategory) {
        BookGridFragment fragment = new BookGridFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_CATEGORY, sectionCategory);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mSectiontCategory = getArguments().getInt(ARG_SECTION_CATEGORY);
        View rootView = inflater.inflate(R.layout.fragment_book_grid, container, false);
        mRecyclerView = rootView.findViewById(R.id.recycleview_book_grid);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        adapter = new BookGridRecyclerViewAdapter(getActivity(), new ArrayList<Book>());
        mRecyclerView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new BooksGenerateTask(mSectiontCategory).execute();
    }

    public class BooksGenerateTask extends AsyncTask<String, Integer, BookList> {
        int mCategory;

        BooksGenerateTask(int category) {
            mCategory = category;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("MAT", "PreExceute");

        }

        @Override
        protected BookList doInBackground(String... params) {
            BookList bookList = new BookList();
//            SharedPreferences preferences = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
//            String uid = preferences.getString("userId", "-1");
            switch (mCategory){
                case BOOKLIST_SECTION_CATEGORY_INTERNET:
                    bookList = DataManage.getRequiredBooks(2, 0);
                    break;
                case BOOKLIST_SECTION_CATEGORY_MINE:
                    bookList = DataManage.getBookList("1",2, 5);
                    break;
                default:
                    break;
            }
            Log.d("booklist", String.valueOf(bookList.getBookCount()));
            return bookList;
        }

        @Override
        protected void onPostExecute(BookList result) {
            super.onPostExecute(result);
            ArrayList<Book> mBooks = result.getBooks();
            adapter.setmBooks(mBooks);
            adapter.notifyDataSetChanged();
            FloatingActionButton fab = getActivity().findViewById(R.id.fab);
            fab.show();
            fab.attachToRecyclerView(mRecyclerView);
        }
    }

}
