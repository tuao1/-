package com.example.tuao.myapp.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tuao.myapp.R;
import com.example.tuao.myapp.activity.SpanishActivity;
import com.example.tuao.myapp.base.BaseFragment;

@SuppressLint("ValidFragment")
public class WalletFragment extends BaseFragment implements View.OnClickListener {
    private String money;
    private ImageView wallet_return;
    private TextView mymoney;

    public WalletFragment(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view=View.inflate(mcontext, R.layout.fragment_my_wallet,null);
        money=SpanishActivity.userInfo.getUser_info().getMoney();
        wallet_return=view.findViewById(R.id.wallet_return);
        mymoney=view.findViewById(R.id.mymoney);
        wallet_return.setOnClickListener(this);
        mymoney.setText(money);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.wallet_return:getActivity().finish();
        }
    }
}
