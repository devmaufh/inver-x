package com.devmaufh.inver_x.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.devmaufh.inver_x.Activities.DetailStartup;
import com.devmaufh.inver_x.Adapters.RecyclerStartupAdapter;
import com.devmaufh.inver_x.Models.CardItem;
import com.devmaufh.inver_x.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class InverFavoritos extends Fragment {
    private RecyclerView rv_favs;
    private List<CardItem>  itemList;
    private RecyclerView.Adapter rvAdapter;
    private RecyclerView.LayoutManager layoutManager;
    SharedPreferences prefs;
    private FirebaseFirestore db;




    public InverFavoritos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_inver_favoritos, container, false);
        bindUI(view);
        intializeRecycler(view);
        return view;
    }
    private void bindUI(View view){
        prefs= getContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        db=FirebaseFirestore.getInstance();
        rv_favs=(RecyclerView)view.findViewById(R.id.frfav_recycler);
        layoutManager= new LinearLayoutManager(view.getContext());
        itemList= new ArrayList<>();

        // Testing functions
        intializeRecycler(view  );
        //fillFavorites();
    }
    private void intializeRecycler(final View view){
        itemList=testArray();
        rvAdapter= new RecyclerStartupAdapter( itemList, R.layout.card_favoritos, new RecyclerStartupAdapter.ClickListener() {
            @Override
            public void clickListener(CardItem item, int position) {
                Intent intent= new Intent(getContext(), DetailStartup.class);
                intent.putExtra("startup",item.getId());
                startActivity(intent);
            }
        });
        rv_favs.setLayoutManager(layoutManager);
        rv_favs.setAdapter(rvAdapter);
    }
    private List<CardItem> testArray()   {
        ArrayList<CardItem> list= new ArrayList<>();
        list.add(new CardItem("2hcMNHrNeKWVv8QtuCnw\n", "Test", "","Tecnol2ogía","Vision","https://firebasestorage.googleapis.com/v0/b/inevrtics.appspot.com/o/startups%2Fpanelessolares.jpg?alt=media&token=1ce51965-9b3d-4992-82ac-8423245b2848"));
        list.add(new CardItem("2hcMNHrNeKWVv8QtuCnw\n", "Test", "","Tecnol2ogía","Vision","https://firebasestorage.googleapis.com/v0/b/inevrtics.appspot.com/o/startups%2Fpanelessolares.jpg?alt=media&token=1ce51965-9b3d-4992-82ac-8423245b2848"));
        list.add(new CardItem("2hcMNHrNeKWVv8QtuCnw\n", "Test", "","Tecnol2ogía","Vision","https://firebasestorage.googleapis.com/v0/b/inevrtics.appspot.com/o/startups%2Fpanelessolares.jpg?alt=media&token=1ce51965-9b3d-4992-82ac-8423245b2848"));


        return list;
    }
    private void fillFavorites(){
        db.collection("fav_inversionista").document(prefs.getString("id","null")).collection("fav")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot document:task.getResult().getDocuments()){
                            itemList.add(getStartupInfo(document.getId()));
                        }
                        rvAdapter.notifyDataSetChanged();
                    }
                });
    }

    private CardItem getStartupInfo(final String id_startup){
        Log.w("Before existing","ID:        "+id_startup);
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
                                itemList.add(it[0]);
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

}
