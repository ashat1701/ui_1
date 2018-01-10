package com.example.user.ui_1;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MemesAdapter extends RecyclerView.Adapter<MemesAdapter.MemesViewHolder>{
    public static class MemesViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView picture;
        TextView name;
        MemesViewHolder(View view){
            super(view);
            cardView = (CardView) view.findViewById(R.id.list);
            picture = (ImageView) view.findViewById(R.id.picture);
            name = (TextView) view.findViewById(R.id.name);
        }
    }
    public ArrayList<myMem>memes;
    public ArrayList<MemesViewHolder>memesViewHolders;
    public Context thisContext;
    public MemesAdapter(Context context,ArrayList<myMem>memes){
        this.memes = memes;
        this.thisContext = context;
    }
    private Context getContext(){
        return thisContext;
    }
    @Override
    public int getItemCount(){
        return memes.size();
    }
    @Override public MemesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
       Context context = viewGroup.getContext();
       LayoutInflater inflater = LayoutInflater.from(context);
       View tecView = inflater.inflate(R.layout.one_mem,viewGroup, false);
       MemesViewHolder viewHolder = new MemesViewHolder(tecView);
       return viewHolder;
    }
    @Override
    public void onBindViewHolder(MemesViewHolder memesViewHolder, int i){
        memesViewHolder.picture.setImageDrawable(memes.get(i).picture.getDrawable());
        memesViewHolder.name.setText(memes.get(i).name);
    }
    @Override
    public  void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }
}
