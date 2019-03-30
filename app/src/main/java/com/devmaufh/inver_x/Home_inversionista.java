package com.devmaufh.inver_x;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class Home_inversionista extends AppCompatActivity {
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_inversionista);
        bindUI();
        setFragment(new InverCatalog());

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment=null;
                switch (menuItem.getItemId()){
                    case R.id.menu_catalogo:
                        fragment= new InverCatalog();
                        break;
                    case R.id.menu_fav:
                        fragment=new InverFavoritos();
                        break;
                    case R.id.menu_perfil:
                        fragment=new InverPerfil();
                        break;
                }
                setFragment(fragment);
                return true;
            }
        });
    }
    private void bindUI(){
        bottomNavigation=(BottomNavigationView) findViewById(R.id.inver_home_bottom_nav);
    }
    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.inver_home_frame,fragment);
        fragmentTransaction.commit();
    }
}
