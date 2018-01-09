package com.example.user.ui_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 07.01.2018.
 */

public class MemesAdapter extends ArrayAdapter<myMem>{
    public MemesAdapter(Context context, ArrayList<myMem>memes){
        super(context,R.layout.one_mem, memes);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
       myMem mem =  getItem(position);
       if (convertView == null){
           convertView = LayoutInflater.from(getContext()).inflate(R.layout.one_mem, parent, false);
       }
        TextView name = convertView.findViewById(R.id.name);
        ImageView pic = convertView.findViewById(R.id.picture);
        name.setText(mem.name);
        pic.setImageDrawable(mem.picture.getDrawable());
        return convertView;
    }
}
