package com.example.tuao.myapp.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tuao.myapp.R;
import com.example.tuao.myapp.activity.SpanishActivity;
import com.example.tuao.myapp.base.BaseFragment;
import com.example.tuao.myapp.utils.NetRequest;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

@SuppressLint("ValidFragment")
public class UserAlterFragment extends BaseFragment implements View.OnClickListener{
    private ImageView username_alter_back;
    private EditText shuru_username;
    private Button queding;
    private String result="false";
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:if(result.equals("true")){
                    Intent intent=new Intent();
                    intent.putExtra("修改结果",shuru_username.getText().toString());
                    getActivity().setResult(Activity.RESULT_OK,intent);

                    getActivity().finish();
                }else {
                    Toast.makeText(mcontext,"修改失败",Toast.LENGTH_SHORT).show();
                }break;
            }
        }
    };
    public UserAlterFragment(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view=View.inflate(mcontext, R.layout.fragment_alter_username,null);
        inithandles(view);
        return view;
    }

    private void inithandles(View view) {
        username_alter_back=view.findViewById(R.id.username_alter_back);
        shuru_username=view.findViewById(R.id.shuru_username);
        queding=view.findViewById(R.id.queding);
        username_alter_back.setOnClickListener(this);
        queding.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.username_alter_back:getActivity().finish();break;
            case R.id.queding://修改用户名并检验合格性
if(checkUserNumber()) {sendQuestNet();}
else {
    Toast.makeText(mcontext,"请输入正确的用户名",Toast.LENGTH_SHORT).show();
}

        }
    }

    /**
     * 发送网络请求
     */
    //a="http://192.168.43.8:8081/BaseServlet?recode=1&account="+eaccount+"&password="+epassword+"&name="+URLEncoder.encode(ename,"utf-8")+"&phone="+ephone;
    private void sendQuestNet() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String username=shuru_username.getText().toString();
                String account=SpanishActivity.userInfo.getUser_info().getAccount();
                try {
                    String a="http://192.168.43.8:8081/BaseServlet?recode=2&username="+URLEncoder.encode(username,"utf-8")+"&account="+account;
                    URL url=new URL(a);
                    NetRequest netRequest=new NetRequest();
                    netRequest.sendRequest(url);
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

    private boolean checkUserNumber() {
        String username=shuru_username.getText().toString();
        if (((username.charAt(0)>=0x4E00&&username.charAt(0)<=0x9FA5)||(username.charAt(0)>=65&&username.charAt(0)<=122))&&username.length()>=2&&username.length()<=16){
            return true;
        }else {
            return false;
        }
    }
}
