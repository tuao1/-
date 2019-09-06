package com.example.tuao.myapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.tuao.myapp.HttpCallbackListener;
import com.example.tuao.myapp.R;
import com.example.tuao.myapp.bean.UserInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 因为在子线程还没执行完，主线程就执行了
 * 可以用handler
 */

public class SpanishActivity extends Activity implements View.OnClickListener {
    private AutoCompleteTextView account;
    private TextView register;
    private EditText password;
    private Button login;
    private String maccount,mpassword;
    public static UserInfo userInfo=new UserInfo();
    private String line,result;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:Toast.makeText(SpanishActivity.this,"账号密码错误",Toast.LENGTH_SHORT).show();break;
                case 1:startActivity(new Intent(SpanishActivity.this,MainActivity.class));finish();break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spanish);
        intt();//实例化控件
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        password.setHintTextColor(Color.WHITE);

    }

    /**
     * 实例化控件
     */
    private void intt() {
        account=findViewById(R.id.account);
        password=findViewById(R.id.password);
        login=findViewById(R.id.sign_in_button);
        register=findViewById(R.id.register);
    }

    /**
     * 登录按钮进行监听
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_in_button:
                try {
                    checkDataCorrect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.register:startActivity(new Intent(SpanishActivity.this,RegisterActivity.class));break;
        }
    }

    /**
     * 判断账号密码是否正确
     * 正确跳转页面
     * 错误弹出提示
     */
    private void checkDataCorrect() throws IOException {
        maccount=account.getText().toString();
        mpassword=password.getText().toString();
        getDataCorrect(maccount,mpassword);
//        if(!result.equals("false")) {
//            Intent intent = new Intent(SpanishActivity.this, MainActivity.class);
//            startActivity(intent);
//            Log.d("gggggggg",result);
//            finish();
//        }else {
//            Log.d("result--------<",result);
//            Toast.makeText(this, "账号密码错误", Toast.LENGTH_SHORT).show();
//        }

    }

    /**
     * 发起网络请求
     * 验证正确性以及获取数据
     * @param maccount
     * @param mpassword
     * @return
     */
    private void getDataCorrect(String maccount, String mpassword) throws IOException {
      // URL url=new URL("http://192.168.43.8:8081/AppLoginServlet?account="+maccount+"&password="+mpassword);
        sendReQuest();






    }
    private void sendReQuest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                BufferedReader reader=null;
                try {
                    URL url=new URL("http://192.168.43.8:8081/AppLoginServlet?account="+maccount+"&password="+mpassword);
                    connection= (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream in=connection.getInputStream();
                    reader=new BufferedReader(new InputStreamReader(in));
                    StringBuilder response=new StringBuilder();
                    String line;
                    while ((line=reader.readLine())!=null){
                        response.append(line);
                    }
                    showResponse(response.toString());
                    in.close();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(reader!=null){
                        try {
                            reader.close();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    if(connection!=null){
                        connection.disconnect();
                    }
                }

                if(!result.equals("false")){
                    userInfo=JSON.parseObject(result,UserInfo.class);
                    Message message=new Message();
                    message.what=1;
                    handler.sendMessage(message);
                }else {
                    Message message=new Message();
                    message.what=0;
                    handler.sendMessage(message);
                }
            }
        }).start();
    }

    private void showResponse(String s) {
        result=s;
        Log.d("resultis----->",s);
    }


}
