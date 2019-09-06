package com.example.tuao.myapp.fragment;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tuao.myapp.R;
import com.example.tuao.myapp.base.BaseFragment;

@SuppressLint("ValidFragment")
public class PhoneAlterFragment extends BaseFragment implements View.OnClickListener{
    private ImageView back,donghua;
    private TextView safe;
    private Button genghuan;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:safe.setText("正在进行智能安全检测");handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        safe.setText("正在检测您的账户环境");
                    }
                },1000);break;
                case 2:safe.setText("您的账户当前处于安全环境，可直接输入要更换的手机号");
               // Glide.with(mcontext).load(R.drawable.ic_check_circle_black_24dp).into(donghua);
                    donghua.setImageResource(R.drawable.ic_check_circle_black_24dp);
                    genghuan.setClickable(true);
                    genghuan.setBackgroundResource(R.drawable.queding);
                break;
            }

        }
    };

    public PhoneAlterFragment(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view=View.inflate(mcontext, R.layout.fragment_alter_phone,null);
        initWidget(view);
        Animation animation=AnimationUtils.loadAnimation(mcontext,R.anim.rotate);
        donghua.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Message message=new Message();
                message.what=1;
                handler.sendMessage(message);




            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Message message=new Message();
                message.what=2;
                handler.sendMessage(message);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        return view;
    }
//实例化控件
    private void initWidget(View view) {
        back=view.findViewById(R.id.back_phonenumber);
        back.setOnClickListener(this);
        donghua=view.findViewById(R.id.donghua);
        safe=view.findViewById(R.id.safe);
        genghuan=view.findViewById(R.id.genghuan);
        genghuan.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_phonenumber:getActivity().finish();break;
            case R.id.genghuan:replaceragment();break;
        }
    }

    private void replaceragment() {
        android.support.v4.app.FragmentManager manager=getActivity().getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.change,new ChangePhoneFragment(mcontext));
        transaction.commit();


    }
}
