package com.devmaufh.inver_x.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.devmaufh.inver_x.R;

public class DetailStartup extends AppCompatActivity {
    private TextView title, description,cantidad,vision;
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_startup);
    }
    private void bindUI(){
        title=(TextView) findViewById(R.id.details_giro);

    }
}
