package com.example.tuao.myapp.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tuao.myapp.R;
import com.example.tuao.myapp.utils.NetRequest;
import com.example.tuao.myapp.utils.SuijiString;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class RegisterActivity extends Activity implements View.OnClickListener {
    private EditText name,account,password,repassword,phone,verification_code;
    private TextView ver_code;
    private ImageView register_return;
    private Button register;
    private String mname,maccount,mpassword,rempassword,mphone,mverfication;
    private String result;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:if(result!=null&&result.equals("true")){
                    Toast.makeText(RegisterActivity.this,"注册成功，即将返回登录页面",Toast.LENGTH_SHORT).show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    },1500);
                }else {
                    Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
                }

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiser);
        init();
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }
        ver_code.setOnClickListener(this);
        register.setOnClickListener(this);
        register_return.setOnClickListener(this);

    }

    /**
     * 实例化各个控件
     */
    private void init() {
        name=findViewById(R.id.register_name);
        account=findViewById(R.id.register_account);
        password=findViewById(R.id.register_password);
        repassword=findViewById(R.id.re_register_password);
        phone=findViewById(R.id.register_phone);
        verification_code=findViewById(R.id.register_verfication);
        ver_code=findViewById(R.id.getVerfication);
        register=findViewById(R.id.register_button);
        register_return=findViewById(R.id.register_return);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.getVerfication:sendVerification();break;
            case R.id.register_button:register_sendNet();break;
            case R.id.register_return:finish();break;
        }
    }

    private void register_sendNet() {
        //判断有没有信息为空的情况
        String ename=name.getText().toString();//姓名
        String eaccount=account.getText().toString();//账号
        String epassword=password.getText().toString();//密码
        String erepassword=repassword.getText().toString();//确认密码
        String ephone=phone.getText().toString();//手机号
        String evarcation=verification_code.getText().toString();//验证码
        if(!epassword.equals(erepassword)){
            Toast.makeText(this,"密码不一致，请重新输入",Toast.LENGTH_SHORT).show();
        }else {
            if(ename.equals("")||eaccount.equals("")||epassword.equals("")||erepassword.equals("")||ephone.equals("")||evarcation.equals("")){
                Toast.makeText(this,"请把信息填写完整",Toast.LENGTH_SHORT).show();
            }else {
                if(evarcation.equals(mverfication)) {
                    //发起网络请求
                    sendRequestRegister(ename, eaccount, epassword, ephone );
                }else {
                    Toast.makeText(this,"验证码错误",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * 发起注册的网络请求
     * @param ename
     * @param eaccount
     * @param epassword
     * @param ephone
     */
    private void sendRequestRegister(final String ename, final String eaccount, final String epassword, final String ephone) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String a=null;
                try {
                     a="http://192.168.43.8:8081/BaseServlet?recode=1&account="+eaccount+"&password="+epassword+"&name="+URLEncoder.encode(ename,"utf-8")+"&phone="+ephone;
                    URL url=new URL(a);
                    NetRequest netRequest=new NetRequest();
                    netRequest.sendRequest(url);
                    result=netRequest.getResult();

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                Message message=new Message();
                message.what=1;
                handler.sendMessage(message);
            }
        }).start();

    }

    private void sendVerification() {
        String phoneNumber=phone.getText().toString().trim();
        if(phoneNumber.equals("")){
            Toast.makeText(this,"电话号码不能为空",Toast.LENGTH_SHORT).show();
        }else {
            SmsManager smsManager = SmsManager.getDefault();
            mverfication = new SuijiString().createRandom(true, 4);//生成一个4位数的随机字符串
            smsManager.sendTextMessage(phoneNumber, null, "【涂奥最帅】  您本次操作验证码为：" + mverfication + ",  请妥善保管，切勿告知他人！ ", null, null);//发送短信
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
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
