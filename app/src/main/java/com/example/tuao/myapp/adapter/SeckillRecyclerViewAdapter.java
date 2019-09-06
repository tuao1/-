package com.example.tuao.myapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tuao.myapp.R;
import com.example.tuao.myapp.bean.ResultBeanData;
import com.example.tuao.myapp.utils.Constants;

import java.util.List;

public class SeckillRecyclerViewAdapter extends RecyclerView.Adapter<SeckillRecyclerViewAdapter.ViewHolder> {


    private  List<ResultBeanData.ResultBean.SeckillInfoBean.ListBean> list;
    private  Context mContext;

    public SeckillRecyclerViewAdapter(Context mContext, List<ResultBeanData.ResultBean.SeckillInfoBean.ListBean> list) {
        this.mContext=mContext;
        this.list=list;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv_figure;
        private TextView tv_cover_price;
        private TextView tv_origin_price;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_figure=itemView.findViewById(R.id.iv_figure);
            tv_cover_price=itemView.findViewById(R.id.tv_cover_price);
            tv_origin_price=itemView.findViewById(R.id.tv_origin_price);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onSeckillRecycler!=null){
                        onSeckillRecycler.onItemOnclick(getLayoutPosition());
                    }
                }
            });
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=View.inflate(mContext,R.layout.item_seckill,null);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ResultBeanData.ResultBean.SeckillInfoBean.ListBean listBean=list.get(position);
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE+listBean.getFigure()).into(holder.iv_figure);
        holder.tv_cover_price.setText(listBean.getCover_price());
        holder.tv_origin_price.setText(listBean.getOrigin_price());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    //监听点击事件回调接口
    public interface  onSeckillRecycler{
        public void onItemOnclick(int position);
    }

    public void setOnSeckillRecycler(SeckillRecyclerViewAdapter.onSeckillRecycler onSeckillRecycler) {
        this.onSeckillRecycler = onSeckillRecycler;
    }

    private onSeckillRecycler onSeckillRecycler;
}
