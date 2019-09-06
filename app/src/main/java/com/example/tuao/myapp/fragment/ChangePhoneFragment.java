package com.example.tuao.myapp.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tuao.myapp.R;
import com.example.tuao.myapp.activity.SpanishActivity;
import com.example.tuao.myapp.base.BaseFragment;
import com.example.tuao.myapp.utils.NetRequest;
import com.example.tuao.myapp.utils.SuijiString;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

@SuppressLint("ValidFragment")
public class ChangePhoneFragment extends BaseFragment implements View.OnClickListener{
    private EditText shuru_phone,shuru_phone_verdfation;
    private Button getVCode,change_phone;
    private String vcCode;
    private String phonenumber;
    private String result="false";//网络请求的结果
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
           Toast.makeText(mcontext,result,Toast.LENGTH_SHORT).show();
            switch (msg.what){
                case 1:if(result.equals("false")){
                    Toast.makeText(mcontext,"修改失败",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent=new Intent();
                    intent.putExtra("result_phone",phonenumber);
                    getActivity().setResult(Activity.RESULT_OK,intent);
                    getActivity().finish();
                }
            }
        }
    };

    public ChangePhoneFragment(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view=View.inflate(mcontext,R.layout.changephone_fragment,null);

        init(view);

        return view;
    }

    private void init(View view) {
        shuru_phone=view.findViewById(R.id.shuru_phone);
        shuru_phone_verdfation=view.findViewById(R.id.shuru_phone_verdfation);
        getVCode=view.findViewById(R.id.getVCode);
        getVCode.setOnClickListener(this);
        change_phone=view.findViewById(R.id.change_phone);
        change_phone.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.getVCode:sendVerifation();break;
            case R.id.change_phone:

               checkVcCode();
                break;


        }
    }

    private void checkVcCode() {
        if((!shuru_phone_verdfation.getText().toString().equals(vcCode))||(!phonenumber.equals(shuru_phone.getText().toString()))){
            Toast.makeText(mcontext,"请重新输入",Toast.LENGTH_SHORT).show();

        }else {

            sendRequest();
        }
    }

    private void sendRequest() {


        new Thread(new Runnable() {
            @Override
            public void run() {
                NetRequest netRequest=new NetRequest();
                try {
                    String account=SpanishActivity.userInfo.getUser_info().getAccount();//账号
                    String a="http://192.168.43.8:8081/BaseServlet?recode=3&phonenumber="+URLEncoder.encode(phonenumber,"utf-8")+"&account="+account;
                    netRequest.sendRequest(new URL(a));
                    result=netRequest.getResult();
                    Message message=new Message();
                    message.what=1;
                    handler.sendMessage(message);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }


            }
        }).start();


    }

    /**
     * 发送验证码
     */
    private void sendVerifation() {
         phonenumber=shuru_phone.getText().toString();
        if(phonenumber.length()!=11||checkNumber(phonenumber)==false){
            Toast.makeText(mcontext,"请输入正确的手机号",Toast.LENGTH_SHORT).show();
        }else {
            SmsManager smsManager = SmsManager.getDefault();
            vcCode = new SuijiString().createRandom(true, 4);//生成一个4位数的随机字符串
            smsManager.sendTextMessage(phonenumber, null, "【涂奥最帅】  您本次操作验证码为：" + vcCode + ",  请妥善保管，切勿告知他人！ ", null, null);//发送短信
        }
    }
    private boolean checkNumber(String p){
        for(int i=0;i<p.length();i++){
            if(p.charAt(i)<'0'||p.charAt(i)>'9'){
                return false;

            }
        }
        return true;
    }

    private void alterPosition() {
    }

    //按钮的监听事件
    private void startAnimation() {
        Animation animation=AnimationUtils.loadAnimation(mcontext,R.anim.translate);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //这里写操作 如send（）； send函数中New SendMsg （号码，内容）；
                } else {
                    Toast.makeText(mcontext, "你没启动权限", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
        }
    }
}
