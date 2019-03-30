package com.devmaufh.inver_x.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devmaufh.inver_x.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class InverPerfil extends Fragment {


    public InverPerfil() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inver_perfil, container, false);
    }

}
