package com.example.yehongjiang.booklist.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yehongjiang.booklist.R;

/**
 * <copyright>Copyright (c) 2018 All Right Reserved</copyright>
 * <author>Van Ye</author>
 * <date>2018/1/26</date>
 * <summary>booklist</summary>
 */
public class TestFragment extends Fragment{
    private RecyclerView mRecyclerView;

    public TestFragment(){

    }

    public static Fragment newInstance(int position){
        TestFragment fragment = new TestFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("AG", position);
        fragment.setArguments(bundle);
        return fragment ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_book_grid, container, false);
        mRecyclerView = rootView.findViewById(R.id.recycleview_book_grid);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        return rootView;
    }
}
