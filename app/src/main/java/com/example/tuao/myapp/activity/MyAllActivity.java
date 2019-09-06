package com.example.tuao.myapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tuao.myapp.R;
import com.example.tuao.myapp.fragment.InformationFragment;
import com.example.tuao.myapp.fragment.WalletFragment;

public class MyAllActivity extends FragmentActivity implements View.OnClickListener {
    public static Activity intance_MyAllActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_all);
       intance_MyAllActivity=this;
        Intent intent=getIntent();
        int fragment_number=intent.getIntExtra("fragment_number",1);
       replaceFragment(fragment_number);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //case R.id.wallet_return:finish();break;
        }
    }
    private void replaceFragment(int fragment_number){
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=manager.beginTransaction();
        switch (fragment_number){
            case 1:break;
            case 2:fragmentTransaction.replace(R.id.left_fragment,new InformationFragment(this));break;
            case 3:fragmentTransaction.replace(R.id.left_fragment,new WalletFragment(this));break;
        }
        fragmentTransaction.commit();
    }
}
