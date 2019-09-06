package com.example.tuao.myapp.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.example.tuao.myapp.R;
import com.example.tuao.myapp.adapter.HomeFragmentAdapter;
import com.example.tuao.myapp.base.BaseFragment;
import com.example.tuao.myapp.bean.ResultBeanData;
import com.example.tuao.myapp.utils.Constants;
import com.example.tuao.myapp.utils.NetRequest;

import java.net.MalformedURLException;
import java.net.URL;

@SuppressLint("ValidFragment")
public class HomeFragment extends BaseFragment {
    private RecyclerView rvhome;
    private HomeFragmentAdapter adapter;
    //msg=1;加载成功
    private android.os.Handler handler=new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
           switch (msg.what){
               case 1:adapter=new HomeFragmentAdapter(mcontext,resultBean);
           rvhome.setAdapter(adapter);
          rvhome.setLayoutManager(new GridLayoutManager(mcontext,1));break;
           }
        }
    };

    private String result="false";
    private ResultBeanData.ResultBean resultBean;


    @SuppressLint("ValidFragment")
    public HomeFragment(Context context) {
        super(context);
    }

    @Override
    public View initView()  {
       View view=View.inflate(mcontext, R.layout.fragment_home,null);
       rvhome=view.findViewById(R.id.rv_home);
        initData();
       //控件的实例化及监听先略过
        return view;
    }

    @Override
    public void initData()  {
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetRequest netRequest=new NetRequest();
                try {
                    netRequest.sendRequest(new URL(Constants.HOME_URL));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                result=netRequest.getResult();
                processData(netRequest.getResult());
                Message message=new Message();
                message.what=1;
                handler.sendMessage(message);
            }
        }).start();

    }

    private void processData(String result) {
        ResultBeanData resultBeanData=JSON.parseObject(result,ResultBeanData.class);
        resultBean=resultBeanData.getResult();
        if(resultBean!=null){
           // 有数据,设置适配器
//            adapter=new HomeFragmentAdapter(mcontext,resultBean);
//            rvhome.setAdapter(adapter);
//            rvhome.setLayoutManager(new GridLayoutManager(mcontext,1));
            Message message=new Message();
            message.what=1;
            handler.sendMessage(message);
        }else {
            //没有数据
        }
    }
}
