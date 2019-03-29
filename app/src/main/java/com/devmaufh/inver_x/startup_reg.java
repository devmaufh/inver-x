package com.devmaufh.inver_x;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class startup_reg extends AppCompatActivity {
    private EditText name,giro,descr,vis,cant;
    private Button btn;
    SharedPreferences prefs;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_reg);
        bindUI();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar() ;
            }
        });

    }

    private void registrar() {
        boolean flag=false;
        String nom=name.getText().toString();
        String gi=giro.getText().toString();
        String de=descr.getText().toString();
        String vi=vis.getText().toString();
        String c=cant.getText().toString();
        if(!TextUtils.isEmpty(nom))
            if(!TextUtils.isEmpty(gi))
                if(!TextUtils.isEmpty(de))
                    if(!TextUtils.isEmpty(vi))
                        if(!TextUtils.isEmpty(c))
                            flag=true;
                        else
                            Toast.makeText(this, "Ingresa una cantidad válida", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(this, "Ingresa una visión válida", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "Ingresa una descripción", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Ingresa un giro válido", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Ingresa un nombre válido", Toast.LENGTH_SHORT).show();

        if(flag){
            db.collection("startups").document(prefs.getString("id","null")).set(get_hash(nom,gi,de,vi,c))
                    .addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            Toast.makeText(startup_reg.this, "Startup correcta", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(startup_reg.this, "Registro incorrecto intente más tarde", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }

    private void bindUI(){
        db=FirebaseFirestore.getInstance();
        prefs=getSharedPreferences("preferences", Context.MODE_PRIVATE);
        name=(EditText) findViewById(R.id.regst_name);
        giro=(EditText)findViewById(R.id.regst_giro);
        descr=(EditText)findViewById(R.id.regst_descri);
        vis=(EditText)findViewById(R.id.regst_vision);
        cant=(EditText)findViewById(R.id.regst_cantidad);
        btn=(Button)findViewById(R.id.regst_btn);
    }
    private Map get_hash(String name,String giro,String descr,String vis,String cant){
        Map<String,Object>  hash=new HashMap<>();
        hash.put("nombre",name);
        hash.put("giro",giro);
        hash.put("descripcion",descr);
        hash.put("vision",vis);
        hash.put("Cantidad",cant);
        return hash;
    }
}