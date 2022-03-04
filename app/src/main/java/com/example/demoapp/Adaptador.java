package com.example.demoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.demoapp.Models.Film;

import java.util.ArrayList;
import java.util.List;

public class Adaptador extends BaseAdapter {

    private Context context;
    private List<Film> arrayList;
    public Adaptador(Context context, List<Film> arrayList){
        this.context = context;
        this.arrayList = arrayList;

    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        Film film = (Film) getItem(position);
        view = LayoutInflater.from(context).inflate(R.layout.listarpeliculas,null);
        TextView title= (TextView)  view.findViewById(R.id.txtNombrePelicula);
        TextView director = (TextView)  view.findViewById(R.id.txtDirector);
        TextView opening_crawl = (TextView) view.findViewById(R.id.txtOpeningCrawl) ;
        TextView url = (TextView)  view.findViewById(R.id.txtUrl);

        url.setText(film.getUrl());
        title.setText(film.getTitle());
        director.setText(film.getDirector());
        opening_crawl.setText(film.getOpening_crawl());

        return view;
    }

}
