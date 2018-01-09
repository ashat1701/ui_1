package com.example.user.ui_1;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by User on 07.01.2018.
 */

public class MyPageAdapter extends FragmentPagerAdapter {
    private Context context = null;
    ArrayList<MyPage> pages;
    public MyPageAdapter(Context context, ArrayList<MyPage>pages, FragmentManager mgr) {
        super(mgr);
        this.pages = pages;
        this.context = context;
    }

    @Override
    public int getCount() {
        return (2);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
         if (position < pages.size())
             return pages.get(position);
         MyPage tec = (MyPage.newInstance(position));
         pages.add(tec);
         return tec;
    }

    @Override
    public String getPageTitle(int position) {
        return (MyPage.getTitle(context, position));
    }
}
