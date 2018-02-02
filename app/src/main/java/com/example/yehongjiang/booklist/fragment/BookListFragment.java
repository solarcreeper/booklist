package com.example.yehongjiang.booklist.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yehongjiang.booklist.R;

import io.karim.MaterialTabs;

/**
 ## <copyright file="Sample.cs" company="My Company Name">
 ## Copyright (c) 19/07/2010 23:01:05 All Right Reserved
 ## </copyright>
 ## <author>Andy Parkhill</author>
 ## <date>19/07/2010 23:01:05 </date>
 ## <summary>Class representing a Sample entity</summary>
 */

public class BookListFragment extends Fragment {
    public static final String ARG_SECTION_CATEGORY = "section_category";
    public final static int BOOKLIST_SECTION_CATEGORY_INTERNET = 0;
    public final static int BOOKLIST_SECTION_CATEGORY_MINE = 1;

    private ViewPager booklistPager;
    private MaterialTabs tabs;


    public static Fragment newInstance() {
        return new BookListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_booklist, container, false);
        tabs = rootView.findViewById(R.id.material_tabs);
        booklistPager = rootView.findViewById(R.id.booklist_container);
        //set adapter
        booklistPager.setAdapter(new AppSectionsPagerAdapter(getFragmentManager(), getActivity()));
        // Bind the tabs to the ViewPager
        tabs.setViewPager(booklistPager);
        return rootView;
    }

    public class AppSectionsPagerAdapter extends FragmentPagerAdapter {
        Context mContext;

        public AppSectionsPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            mContext = context;
        }

        @Override
        public Fragment getItem(int position) {
            return BookGridFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position){
            switch (position){
                case BOOKLIST_SECTION_CATEGORY_INTERNET:
                    return mContext.getString(R.string.booklist_recommend_books);
                case BOOKLIST_SECTION_CATEGORY_MINE:
                    return mContext.getString(R.string.booklist_my_books);
                default:
                    return mContext.getString(R.string.booklist_my_error);
            }
        }
    }
}
