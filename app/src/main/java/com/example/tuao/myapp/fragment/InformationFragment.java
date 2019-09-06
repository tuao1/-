package com.example.tuao.myapp.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import com.example.tuao.myapp.R;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.tuao.myapp.activity.AlterActivity;
import com.example.tuao.myapp.activity.MainActivity;
import com.example.tuao.myapp.activity.SpanishActivity;
import com.example.tuao.myapp.base.BaseFragment;

@SuppressLint("ValidFragment")
public class InformationFragment extends BaseFragment implements View.OnClickListener {
    private ImageView alter_back;
    private LinearLayout headportrait,username,account_password,phone_number,logout,adress;
    private TextView back_this_account,username_get,phonenumber_get,adress_get;
    private static int HEADPORTRAIT=1,USERNAME=2,ACCOUNT_PASSWORD=3,PHONE_NUMBER=4,ADRESS=5,LOGOT=6;
    private String username_set="",phonenumber_set="",adress_set="";
    @SuppressLint("ValidFragment")
    public InformationFragment(Context context) {
        super(context);
    }

    @Override
    public View initView() {
       View view=View.inflate(mcontext, R.layout.fragment_information,null);
      inithandles(view);
      username_set=SpanishActivity.userInfo.getUser_info().getName();
      phonenumber_set=SpanishActivity.userInfo.getUser_info().getPhone();
      adress_set=SpanishActivity.userInfo.getUser_info().getAdress();
      username_get.setText(username_set);
      phonenumber_get.setText(phonenumber_set);
      adress_get.setText(adress_set);

        return view;
    }

    private void inithandles( View view) {
        alter_back=view.findViewById(R.id.alter_back);
        alter_back.setOnClickListener(this);
        headportrait=view.findViewById(R.id.headportrait);
        headportrait.setOnClickListener(this);
        username=view.findViewById(R.id.username);
        username.setOnClickListener(this);
        account_password=view.findViewById(R.id.account_password);
        account_password.setOnClickListener(this);
        phone_number=view.findViewById(R.id.phone_number);
        phone_number.setOnClickListener(this);
        logout=view.findViewById(R.id.logout);
        logout.setOnClickListener(this);
        back_this_account=view.findViewById(R.id.back_this_account);
        back_this_account.setOnClickListener(this);
        username_get=view.findViewById(R.id.username_get);
        phonenumber_get=view.findViewById(R.id.phone_number_get);
        adress=view.findViewById(R.id.adress);
        adress.setOnClickListener(this);
        adress_get=view.findViewById(R.id.adress_get);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //返回
            case R.id.alter_back:getActivity().finish();break;
            //头像
            case R.id.headportrait:break;
            //用户名
            case R.id.username:Intent intent=new Intent(mcontext,AlterActivity.class);intent.putExtra("alter_number",USERNAME);startActivityForResult(intent,100);break;
           // 账号密码
            case R.id.account_password:break;
            //手机号
            case R.id.phone_number:Intent intent_p=new Intent(mcontext,AlterActivity.class);intent_p.putExtra("alter_number",PHONE_NUMBER);startActivityForResult(intent_p,200);break;
            //地址
            case R.id.adress:break;
            //注销
            case R.id.logout:break;
            //退出账号
            case R.id.back_this_account:getActivity().finish();MainActivity.intance_MainActivity.finish();
            Intent intent1=new Intent(mcontext,SpanishActivity.class);
            startActivity(intent1);
            break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 100:
                if(resultCode==getActivity().RESULT_OK){
                    String result=data.getStringExtra("修改结果");//修改后的用户名
                    username_get.setText(result);
                    SpanishActivity.userInfo.getUser_info().setName(result);

                } break;
            case 200:
                if(resultCode==getActivity().RESULT_OK){
                    String phone=data.getStringExtra("result_phone");
                    phonenumber_get.setText(phone);
                    SpanishActivity.userInfo.getUser_info().setPhone(phone);
                }break;

        }
    }
}
