package com.example.tuao.myapp.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.tuao.myapp.R;
import com.example.tuao.myapp.base.BaseFragment;
import com.example.tuao.myapp.fragment.PhoneAlterFragment;
import com.example.tuao.myapp.fragment.UserAlterFragment;

public class AlterActivity extends FragmentActivity {
    public static Activity intance_AlterActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter);
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 2);
        }
        intance_AlterActivity=this;
        Intent intent=getIntent();
        int number=intent.getIntExtra("alter_number",0);
        replace(number);
    }

    private void replace(int number) {
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        switch (number){
            case 2:transaction.replace(R.id.alter_fragment,new UserAlterFragment(this));break;
            case 4:transaction.replace(R.id.alter_fragment,new PhoneAlterFragment(this));break;
        }
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //这里写操作 如send（）； send函数中New SendMsg （号码，内容）；
                } else {
                    Toast.makeText(this, "你没启动权限", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
        }
    }
}
