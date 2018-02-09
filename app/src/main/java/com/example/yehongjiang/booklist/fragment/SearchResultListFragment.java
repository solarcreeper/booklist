package com.example.yehongjiang.booklist.fragment;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yehongjiang.booklist.R;
import com.example.yehongjiang.booklist.adapter.SearchResultRecyclerAdapter;
import com.example.yehongjiang.booklist.model.Book;
import com.example.yehongjiang.booklist.model.BookList;
import com.example.yehongjiang.booklist.model.DataManage;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

/**
 * <copyright>Copyright (c) 2018 All Right Reserved</copyright>
 * <author>Van Ye</author>
 * <date>2018/1/26</date>
 * <summary>booklist</summary>
 */
public class SearchResultListFragment extends Fragment {
    private String query;
    private RecyclerView mRecyclerView;
    private SearchResultRecyclerAdapter adapter;

    public SearchResultListFragment() {

    }

    public static Fragment newInstance(String query) {
        SearchResultListFragment fragment = new SearchResultListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("query", query);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        query = args.getString("query");
        View rootView = inflater.inflate(R.layout.fragment_search_result, container, false);
        mRecyclerView = rootView.findViewById(R.id.rv_search_result_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new SearchResultRecyclerAdapter(getActivity(), new ArrayList<Book>());
        mRecyclerView.setAdapter(adapter);

        new SearchAsyTask(getActivity(), mRecyclerView).execute(query);

        return rootView;
    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//    }

    class SearchAsyTask extends AsyncTask<String, Integer, BookList> {
        Activity mContext;
        RecyclerView mRecyclerView;

        SearchAsyTask(Activity context, RecyclerView recyclerView) {
            mContext = context;
            mRecyclerView = recyclerView;
        }

        @Override
        protected BookList doInBackground(String... params) {
            String query = params[0];
            int start = 0;
            int num = 2;
            return DataManage.getRequiredBooks(num, start, query);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(BookList books) {
            super.onPostExecute(books);
            ArrayList<Book> book = books.getBooks();
            adapter.setBooks(book);
            adapter.notifyDataSetChanged();
            FloatingActionButton fab = getActivity().findViewById(R.id.fab);
            fab.show();
            fab.attachToRecyclerView(mRecyclerView);
        }
    }
}
