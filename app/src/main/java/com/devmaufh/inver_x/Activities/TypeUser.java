package com.devmaufh.inver_x.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.devmaufh.inver_x.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class TypeUser extends AppCompatActivity {
    SharedPreferences prefs;
    private Switch swType;
    private ImageView img_type;
    private Button btn_next;
    private FirebaseFirestore db;

    private  String type="inversionistas";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_user);
        bindUI();
        swType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Picasso.with(getApplicationContext()).load(R.drawable.emp).into(img_type);
                    type="emprendedores";
                }else{
                    Picasso.with(getApplicationContext()).load(R.drawable.inv).into(img_type);
                    type="inversionistas";
                }
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOnPreferences(type);
            }
        });
    }
    private void bindUI(){
        prefs=getSharedPreferences("preferences", Context.MODE_PRIVATE);
        img_type=(ImageView)findViewById(R.id.img_type);
        swType=(Switch)findViewById(R.id.tipo_user);
        btn_next=(Button)findViewById(R.id.tippo_btn_next);
        db=FirebaseFirestore.getInstance();
    }
    private void saveOnPreferences(String type){
        SharedPreferences.Editor editor=prefs.edit();
        editor.putString("type",type);
        editor.apply();
        if(prefs.getString("type","No type    ").equals("inversionistas")) {
            registraInversionista();
        }
        else {
            registraUserStartup();
        }
    }
    private void launchNext(Intent intent){
        startActivity(intent);
    }
    private void registraInversionista(){
        String id=prefs.getString("id","null");
        String name=prefs.getString("name","null");
        String email=prefs.getString("email","null");
        Map<String, Object> usr=new HashMap<>();
        usr.put("nombre",name);
        usr.put("email",email);
        db.collection("inversionista").document(id).set(usr)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        launchNext(new Intent(getApplicationContext(), Home_inversionista.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(TypeUser.this, "Error al registrar, intenta más tarde", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void registraUserStartup(){
        String id=prefs.getString("id","null");
        String name=prefs.getString("name","null");
        String email=prefs.getString("email","null");
        Map<String,Object> usr= new HashMap<>();
        usr.put("nombre",name);
        usr.put("email",email);
        db.collection("users_startup").document(id).set(usr)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(TypeUser.this, "Cool", Toast.LENGTH_SHORT).show();
                        launchNext(new Intent(getApplicationContext(), startup_reg.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(TypeUser.this, "Error al registrar usuario, intenta más tarde", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
