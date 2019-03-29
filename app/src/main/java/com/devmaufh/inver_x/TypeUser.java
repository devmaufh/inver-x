package com.devmaufh.inver_x;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class TypeUser extends AppCompatActivity {
    SharedPreferences prefs;
    private Switch swType;
    private ImageView img_type;
    private Button btn_next;
    String type="";
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
                //Show main

            }
        });
    }
    private void bindUI(){
        prefs=getSharedPreferences("preferences", Context.MODE_PRIVATE);
        img_type=(ImageView)findViewById(R.id.img_type);
        swType=(Switch)findViewById(R.id.tipo_user);
        btn_next=(Button)findViewById(R.id.tippo_btn_next);
    }
    private void saveOnPreferences(String type){
        SharedPreferences.Editor editor=prefs.edit();
        editor.putString("type",type);
        editor.apply();
    }
}
