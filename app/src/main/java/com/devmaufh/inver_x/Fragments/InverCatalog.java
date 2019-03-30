package com.devmaufh.inver_x.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.devmaufh.inver_x.Activities.DetailStartup;
import com.devmaufh.inver_x.Adapters.SwipAdapter;
import com.devmaufh.inver_x.Adapters.SwipteAdapter2;
import com.devmaufh.inver_x.Models.CardItem;
import com.devmaufh.inver_x.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import link.fls.swipestack.SwipeStack;


/**
 * A simple {@link Fragment} subclass.
 */
public class InverCatalog extends Fragment implements SwipeStack.SwipeStackListener{
    private SwipeStack swipeStack;
    private SwipteAdapter2 adapter;
    private Button invert,see;
    int favs=0;
    private FirebaseFirestore db;
    private ArrayList<CardItem> listaItems;
    SharedPreferences prefs;


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
                //getStartups();
            }
        });
        return  view;
    }
    private void binUI(View view){
        prefs= getContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);

        db=FirebaseFirestore.getInstance();
        swipeStack=(SwipeStack) view.findViewById(R.id.catalog_swip);
        invert=(Button)view.findViewById(R.id.catalog_btn_fav);
        see=(Button)view.findViewById(R.id.catalog_btn_descrip);
        listaItems=fillArrayToTest();
        adapter= new SwipteAdapter2(listaItems,getContext());
        swipeStack.setAdapter(adapter);
        fillStartupsInfo();
        swipeStack.setListener(this);
    }
    private ArrayList<CardItem> fillArrayToTest(){
        ArrayList<CardItem> list= new ArrayList<>();
        list.add(new CardItem("1","Test","Descripcion 1","Tecnol2ogía","Vision","url"));
        return list;
    }


    @Override
    public void onViewSwipedToLeft(int position) {
        CardItem cardItem=(CardItem) adapter.getItem(position);
        Toast.makeText(getContext(), "ID: "+cardItem.getId(), Toast.LENGTH_SHORT).show();
        addStartupToFavorites(cardItem.getId());
        Intent intent= new Intent(getContext(),DetailStartup.class);
        intent.putExtra("startup",cardItem.getId().toString());
        startActivity(intent);
    }

    @Override
    public void onViewSwipedToRight(int position) {
    }

    @Override
    public void onStackEmpty() {
    }
    //Fill Tinder cards stacks with Firestore data
    private void fillStartupsInfo(){
        CollectionReference startupsCollection=db.collection("startups");
        startupsCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot queryDocumentSnapshot: task.getResult()){
                        getStartupInfo(queryDocumentSnapshot.getId());
                    }
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        adapter.notifyDataSetChanged();
    }
    private CardItem getStartupInfo(final String id_startup){
        final CardItem[] it = {null};

        DocumentReference startupsCollection=db.collection("startups").document(id_startup);
        startupsCollection.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document=task.getResult();
                            if(document.exists()){
                                String nombre=document.get("nombre").toString();
                                String description=document.get("descripcion").toString();
                                String giro=document.get("giro").toString();
                                String vision=document.get("vision").toString();
                                String url=document.get("url").toString();
                                //Toast.makeText(getContext(), url, Toast.LENGTH_SHORT).show();
                                it[0] =new CardItem(id_startup,nombre,description,giro,vision,url);

                                listaItems.add(it[0]);
                            }else{
                                Toast.makeText(getContext(), "El registro no existe", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getContext(), "Error "+  task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
        });
        return it[0];
    }

    private void addStartupToFavorites(String id){
        Map<String,Object> fav= new HashMap<>();
        db.collection("fav_inversionista").document(prefs.getString("id",""))
                .collection("fav").document(id).set(fav)
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(), "Agregado a favoritos", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "No es posible agregar a favoritos, intenta más tarde", Toast.LENGTH_SHORT).show();

            }
        });


    }


}
