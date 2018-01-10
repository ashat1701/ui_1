package com.example.user.ui_1;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    ArrayList<myMem> arrayMemes = new ArrayList<>();
    private int pageNumber;
    private ActionBar actionBar;
    public RecyclerView listView;
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
    public void addMem(myMem mem){
        int x = arrayMemes.size();
        arrayMemes.add(mem);
        adapter.notifyItemInserted(x);
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments() != null ? getArguments().getInt("num") : 1;
    }
    static String getTitle(Context context, int position) {
        return "Страница № " + String.valueOf(position+1);
    }
    MainActivity mainActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         mainActivity = (MainActivity) getActivity();
        View page = inflater.inflate(R.layout.page_typ1, container, false);
        listView = page.findViewById(R.id.list);
        arrayMemes = new ArrayList<>();
/*
        arrayMemes.add(new myMem("MemeTest",1, new ImageView(container.getContext())));
        arrayMemes.get(0).picture.setImageResource(R.drawable.p1);

        arrayMemes.add(new myMem("MemeTest",2, new ImageView(container.getContext())));
        arrayMemes.get(1).picture.setImageResource(R.drawable.p1);

        arrayMemes.add(new myMem("MemeTe1st",3, new ImageView(container.getContext())));
        arrayMemes.get(2).picture.setImageResource(R.drawable.p1);

        arrayMemes.add(new myMem("MemeTest",4, new ImageView(container.getContext())));
        arrayMemes.get(3).picture.setImageResource(R.drawable.p1);*/
        adapter = new MemesAdapter(container.getContext(),arrayMemes);
        listView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(container.getContext());
        listView.setLayoutManager(layoutManager);

        listView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mainActivity.reDownload();
            }
        });
        return page;
    }

}
