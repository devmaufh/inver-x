package com.devmaufh.inver_x.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.devmaufh.inver_x.Models.CardItem;
import com.devmaufh.inver_x.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerStartupAdapter extends RecyclerView.Adapter<RecyclerStartupAdapter.ViewHolder> {
    Context context;
    private List<CardItem>cardItemList;
    ClickListener listener;
    private int layout;

    public RecyclerStartupAdapter(List<CardItem> cardItemList, int layout,ClickListener listener) {
        this.cardItemList = cardItemList;
        this.listener = listener;
        this.layout = layout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(layout,viewGroup,false);
        ViewHolder vh= new ViewHolder(v);
        context=viewGroup.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.binUI(cardItemList.get(i),listener);
    }

    @Override
    public int getItemCount() {
        return cardItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvTitle,tvDescription;
        public ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle=(TextView)itemView.findViewById(R.id.fav_title);
            tvDescription=(TextView)itemView.findViewById(R.id.fav_descrip);
            img=(ImageView)itemView.findViewById(R.id.fav_img);
        }
        public void binUI(final CardItem item,final ClickListener listener){
            tvTitle.setText(item.getNombre());
            tvDescription.setText(item.getDescripcion());
            Picasso.with(context).load(item.getUrl()).into(img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.clickListener(item,getAdapterPosition());
                }
            });
        }
    }

    public interface ClickListener{
        void clickListener(CardItem item,int position);
    }
}
