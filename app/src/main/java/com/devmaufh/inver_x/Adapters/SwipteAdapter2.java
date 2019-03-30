package com.devmaufh.inver_x.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.devmaufh.inver_x.Models.CardItem;
import com.devmaufh.inver_x.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SwipteAdapter2 extends BaseAdapter {
    private ArrayList<CardItem> cardsItems;
    private Context context;

    public SwipteAdapter2(ArrayList<CardItem> cardsItems, Context context) {
        this.cardsItems = cardsItems;
        this.context = context;
    }

    @Override
    public int getCount() {
        return cardsItems.size();
    }

    @Override
    public Object getItem(int position) {
        return cardsItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(R.layout.card_catalog,parent,false);
        CardItem item=cardsItems.get(position);
        ImageView img=convertView.findViewById(R.id.cardcatalog_img);
        TextView name=convertView.findViewById(R.id.cardcatalog_title);
        TextView ubic=convertView.findViewById(R.id.cardcatalog_ubic);
        Picasso.with(convertView.getContext()).load(item.getUrl()).into(img);
        name.setText(item.getNombre());
        ubic.setText(item.getGiro());
        return  convertView;
    }
}
