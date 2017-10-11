package com.iot.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import iot.accenture.com.warehouseapplication.R;


/**
 * Created by amit.gaike on 12/29/2016.
 */

public class LoginScreenFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_screen,
                container, false);
        ImageView loginButton=(ImageView)view.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),PostLoginActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    public LoginScreenFragment(){

    }
    public static Fragment getFragment(){
        LoginScreenFragment loginScreenFragment=new LoginScreenFragment();
        return loginScreenFragment;
    }


}