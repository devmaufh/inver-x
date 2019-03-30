package com.devmaufh.inver_x.Activities;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.devmaufh.inver_x.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class DetailStartup extends AppCompatActivity {
    private TextView title, description,cantidad,vision;
    private ImageView img;
    FirebaseFirestore db;
    private String startup_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_startup);
        bindUI();


    }
    private void bindUI(){
        db=FirebaseFirestore.getInstance();
        title=(TextView) findViewById(R.id.details_giro);
        img=(ImageView)findViewById(R.id.details_img);
        cantidad=(TextView)findViewById(R.id.tvCantidad);
        description=(TextView)findViewById(R.id.tvDescr);
        vision=(TextView)findViewById(R.id.detail_vision);
        if(getIntent().getStringExtra("startup")!=null){
            startup_id=getIntent().getStringExtra("startup");
            setData();
        }
    }
    private void setData(){
        db.collection("startup").document(startup_id) .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        title.setText(task.getResult().get("nombre").toString());
                        description.setText(task.getResult().get("descripcion").toString());
                        cantidad.setText("$"+task.getResult().get("Cantidad"));
                        vision.setText(task.getResult().get("vision").toString());
                        Picasso.with(getBaseContext()).load(task.getResult().get("url").toString()).into(img);
                    }
                });
    }
}
