package com.example.tuao.myapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tuao.myapp.R;
import com.example.tuao.myapp.bean.ResultBeanData;
import com.example.tuao.myapp.utils.Constants;

import java.util.List;

public class RecommendGridViewAdapter extends BaseAdapter {
    private  List<ResultBeanData.ResultBean.RecommendInfoBean> datas;
    private Context mContext;
    public RecommendGridViewAdapter(Context mContext, List<ResultBeanData.ResultBean.RecommendInfoBean> recommend_info) {
        this.mContext=mContext;
        this.datas=recommend_info;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView=View.inflate(mContext, R.layout.item_recommend_grid_view,null);
            viewHolder=new ViewHolder();
            viewHolder.iv_recommend=convertView.findViewById(R.id.iv_recommend);
            viewHolder.tv_name=convertView.findViewById(R.id.tv_name);
            viewHolder.tv_price=convertView.findViewById(R.id.tv_price);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        ResultBeanData.ResultBean.RecommendInfoBean recommendInfoBean=datas.get(position);
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE+recommendInfoBean.getFigure()).into(viewHolder.iv_recommend);
        viewHolder.tv_name.setText(recommendInfoBean.getName());
        viewHolder.tv_price.setText("¥"+recommendInfoBean.getCover_price());
        return convertView;
    }
    static class ViewHolder{
        ImageView iv_recommend;
        TextView tv_name;
        TextView tv_price;
    }
}
