package com.example.user.ui_1;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 08.01.2018.
 */

public class MyPage extends android.support.v4.app.Fragment  {
    private int pageNumber;
    private ActionBar actionBar;
    public ObservableListView listView;
    public MemesAdapter adapter;

    public MyPage(){

    }
    public static MyPage newInstance(int page){
        MyPage tec = new MyPage();
        Bundle args = new Bundle();
        args.putInt("pageNumber", page);
        tec.setArguments(args);
        return tec;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments() != null ? getArguments().getInt("num") : 1;
    }
    static String getTitle(Context context, int position) {
        return "Страница № " + String.valueOf(position+1);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainActivity mainActivity = (MainActivity) getActivity();

        View page = inflater.inflate(R.layout.page_typ1, container, false);
        listView = page.findViewById(R.id.list);
        listView.setScrollViewCallbacks(mainActivity);
        ArrayList<myMem> arrayMemes = new ArrayList<myMem>();
        adapter = new MemesAdapter(page.getContext(), arrayMemes);
        listView.setAdapter(adapter);

        return page;
    }

}
