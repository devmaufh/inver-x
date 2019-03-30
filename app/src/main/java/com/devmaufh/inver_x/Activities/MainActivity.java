package com.devmaufh.inver_x.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.devmaufh.inver_x.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    SignInButton btn_login_g;
    GoogleSignInClient client;
    private int RC_SIGN_CODE=777;
    private FirebaseFirestore db;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binUI();
        intializeClient();
        check_if_login();
        btn_login_g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=client.getSignInIntent();
                startActivityForResult(intent,RC_SIGN_CODE);
            }
        });
    }
    private void binUI(){
        prefs=getSharedPreferences("preferences", Context.MODE_PRIVATE);

        db=FirebaseFirestore.getInstance();
        btn_login_g=(SignInButton)findViewById(R.id.sign_in_button);

    }
    private void intializeClient(){
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        client= GoogleSignIn.getClient(this,gso);
    }
    private void check_if_login(){
        GoogleSignInAccount account= GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null) {
            Toast.makeText(this, "Ya estás logead", Toast.LENGTH_SHORT).show();
            saveOnPreferences(account.getId(),account.getDisplayName(),account.getEmail());
            launch_main();

        }else
            Toast.makeText(this, "Aun no estás logeado", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_CODE && resultCode== Activity.RESULT_OK){
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            //saveOnSharedPreferences(account.getEmail(),account.getDisplayName(),account.getId());
            String id=account.getId();
            String name=account.getDisplayName();
            String email=account.getEmail();
            saveOnPreferences(id,name,email);
            Map<String,Object> user=new HashMap<>();
            user.put("email",email);
            user.put("name",name);
            launch_main();
            Log.w("AVEEEEEEEER", ""+account.getDisplayName()+"\n"+account.getEmail());

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("AVEEEEEEEER", "signInResult:failed code=" + e.getStatusCode());
        }
    }
    private void launch_main(){
        Toast.makeText(this, "Al main", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, TypeUser.class));
    }
    private void saveOnPreferences(String id,String name,String email){
        SharedPreferences.Editor editor=prefs.edit();
        editor.putString("id",id);
        editor.putString("name",name);
        editor.putString("email",email);
        editor.apply();
    }
}
