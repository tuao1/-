package com.example.tuao.myapp.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tuao.myapp.base.BaseFragment;

import java.net.MalformedURLException;

@SuppressLint("ValidFragment")
public class AuctionFragment extends BaseFragment {
    private TextView textView;

    @SuppressLint("ValidFragment")
    public AuctionFragment(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        textView=new TextView(mcontext);
        textView.setText("拍卖");
        return textView;

    }


}
