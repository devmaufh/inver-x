package com.devmaufh.inver_x.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.devmaufh.inver_x.Adapters.SwipAdapter;
import com.devmaufh.inver_x.Adapters.SwipteAdapter2;
import com.devmaufh.inver_x.Models.CardItem;
import com.devmaufh.inver_x.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;

import link.fls.swipestack.SwipeStack;


/**
 * A simple {@link Fragment} subclass.
 */
public class InverCatalog extends Fragment implements SwipeStack.SwipeStackListener{
    private SwipeStack swipeStack;
    private SwipteAdapter2 adapter;
    private ArrayList<CardItem> cardItems;
    private Button invert,see;
    int favs=0;
    private FirebaseFirestore db;
    private QueryDocumentSnapshot snapshot;


    public InverCatalog() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_inver_catalog, container, false);
        binUI(view);
        invert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeStack.swipeTopViewToLeft();
            }
        });
        see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "A ver: "+favs, Toast.LENGTH_SHORT).show();


            }
        });
        return  view;
    }
    private void binUI(View view){
        db=FirebaseFirestore.getInstance();
        swipeStack=(SwipeStack) view.findViewById(R.id.catalog_swip);
        invert=(Button)view.findViewById(R.id.catalog_btn_fav);
        see=(Button)view.findViewById(R.id.catalog_btn_descrip);
        swipeStack.setListener(this);
        adapter= new SwipteAdapter2(fillArrayToTest(),getContext());
        swipeStack.setAdapter(adapter);
    }
    private ArrayList<CardItem> fillArrayToTest(){
        ArrayList<CardItem> list= new ArrayList<>();
        list.add(new CardItem("1","Test","Descripcion 1","Tecnol2ogía","Vision"));
        list.add(new CardItem("2","Test2","2Descripcion 1","Tecno3logía","Vision"));
        list.add(new CardItem("3","Test3","De3scripcion 1","Tecnol4ogía","Vision"));
        list.add(new CardItem("4","Tes4","Descr4ipcion 1","Tecnolog5ía","Vision"));
        return list;
    }


    @Override
    public void onViewSwipedToLeft(int position) {
        CardItem cardItem=(CardItem) adapter.getItem(position);
        Toast.makeText(getContext(), "ID: "+cardItem.getId(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onViewSwipedToRight(int position) {
    }

    @Override
    public void onStackEmpty() {
    }
    private QueryDocumentSnapshot getSnapshot(){
        db.collection("startup").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    }
                });
    }

}
