package com.example.tuao.myapp.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.tuao.myapp.base.BaseFragment;

@SuppressLint("ValidFragment")
public class ShopCar extends BaseFragment {
    private TextView textView;

    public ShopCar(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        textView=new TextView(mcontext);
        textView.setText("购物车");
        return textView;
    }
}
