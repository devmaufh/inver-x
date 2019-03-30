package com.devmaufh.inver_x.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.devmaufh.inver_x.Models.CardItem;
import com.devmaufh.inver_x.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class SwipAdapter extends ArrayAdapter<CardItem> {
    private ArrayList<CardItem> cardsItems;
    private Context context;


    public SwipAdapter(Context context, ArrayList<CardItem> cardItems) {
        super(context,0,cardItems);
        this.context=context;
        this.cardsItems=cardItems;
    }

    @Override
    public int getCount() {
        return cardsItems.size();
    }

    @Nullable
    @Override
    public CardItem getItem(int position) {
        return cardsItems.get(position);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CardItem item=cardsItems.get(position);
        convertView= LayoutInflater.from(context).inflate(R.layout.card_catalog,parent,false);
        ImageView img=convertView.findViewById(R.id.cardcatalog_img);
        TextView name=convertView.findViewById(R.id.cardcatalog_title);
        TextView ubic=convertView.findViewById(R.id.cardcatalog_ubic);

        Picasso.with(convertView.getContext()).load(R.drawable.emprendedor).into(img);
        name.setText(item.getNombre());
        ubic.setText(item.getGiro());

        return convertView;
    }

}

