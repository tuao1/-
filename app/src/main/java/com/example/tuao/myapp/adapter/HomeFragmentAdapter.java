package com.example.tuao.myapp.adapter;

import android.content.Context;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tuao.myapp.R;
import com.example.tuao.myapp.bean.ResultBeanData;
import com.example.tuao.myapp.utils.Constants;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;
import com.zhy.magicviewpager.transformer.RotateDownPageTransformer;
import com.zhy.magicviewpager.transformer.RotateYTransformer;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.SimpleFormatter;

public class HomeFragmentAdapter extends RecyclerView.Adapter {
    //广告条幅
    public static final int BANNER=0;
    //频道
    public static final int CHANNEL=1;
    //活动
    public static final int ACT=2;
    //秒杀
    public static final int SECKILL=3;
    //推荐
    public static final int RECOMMEND=4;
    //热卖
    public static final int HOT=5;
    private final Context mcontext;
    private final ResultBeanData.ResultBean resultBean;


    //当前类型
    private int currentType=BANNER;
    private LayoutInflater mlayoutInflater;

    public HomeFragmentAdapter(Context mcontext, ResultBeanData.ResultBean resultBean) {
        this.mcontext=mcontext;
        this.resultBean=resultBean;
        mlayoutInflater=LayoutInflater.from(mcontext);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==BANNER){
            return new BannerViewHolder(mcontext,mlayoutInflater.inflate(R.layout.banner_viewpager,null));
        }else if(viewType==CHANNEL){
            return new ChannelViewHolder(mcontext,mlayoutInflater.inflate(R.layout.channel_item,null));
        }else if(viewType==ACT){
            return new ActViewHolder(mcontext,mlayoutInflater.inflate(R.layout.act_item,null));
        }else if(viewType==SECKILL){
            return new SeckillViewholder(mcontext,mlayoutInflater.inflate(R.layout.seckill_item,null));
        }else if(viewType==RECOMMEND){
            return new RecommendViewholder(mcontext,mlayoutInflater.inflate(R.layout.recommend_item,null));
        }else if(viewType==HOT){
            return new HotViewholder(mcontext,mlayoutInflater.inflate(R.layout.hot_item,null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==BANNER){
            BannerViewHolder bannerViewHolder= (BannerViewHolder) holder;
            bannerViewHolder.setData(resultBean.getBanner_info());
        }else if(getItemViewType(position)==CHANNEL){
            ChannelViewHolder channelViewHolder= (ChannelViewHolder) holder;
            channelViewHolder.setData(resultBean.getChannel_info());

        }else if (getItemViewType(position)==ACT){
            ActViewHolder actViewHolder= (ActViewHolder) holder;
            actViewHolder.setData(resultBean.getAct_info());
        }else if(getItemViewType(position)==SECKILL){
            SeckillViewholder seckillViewholder= (SeckillViewholder) holder;
            seckillViewholder.setData(resultBean.getSeckill_info());
        }else if(getItemViewType(position)==RECOMMEND){
            RecommendViewholder recommendViewholder= (RecommendViewholder) holder;
            recommendViewholder.setData(resultBean.getRecommend_info());
        }else if(getItemViewType(position)==HOT){
            HotViewholder hotViewholder= (HotViewholder) holder;
            hotViewholder.setData(resultBean.getHot_info());
        }

    }


    class HotViewholder extends RecyclerView.ViewHolder{

        private Context mContext;
        private TextView tv_more_hot;
        private GridView gv_hot;
        private HotGridViewAdapter adapter;
        public HotViewholder(Context mcontext, View itemView) {
            super(itemView);
            this.mContext=mcontext;
            tv_more_hot=itemView.findViewById(R.id.tv_more_hot);
            gv_hot=itemView.findViewById(R.id.gv_hot);
            gv_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(mContext,"position="+position,Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void setData(List<ResultBeanData.ResultBean.HotInfoBean> hot_info) {
            adapter=new HotGridViewAdapter(mContext,hot_info);
            gv_hot.setAdapter(adapter);
        }
    }
    class RecommendViewholder extends RecyclerView.ViewHolder{
        private Context mContext;
        private TextView tv_more_recommend;
        private GridView gv_recommend;
        private RecommendGridViewAdapter adapter;

        public RecommendViewholder(Context mcontext, View itemView) {
            super(itemView);
            mContext=mcontext;
            tv_more_recommend=itemView.findViewById(R.id.tv_more_recommend);
            gv_recommend=itemView.findViewById(R.id.gv_recommend);
            gv_recommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(mContext,"position="+position,Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void setData(List<ResultBeanData.ResultBean.RecommendInfoBean> recommend_info) {
            adapter=new RecommendGridViewAdapter(mContext,recommend_info);
            gv_recommend.setAdapter(adapter);
        }
    }



    class SeckillViewholder extends RecyclerView.ViewHolder{
        private Context mContext;
        private TextView tv_time_seckill;
        private TextView tv_more_seckill;
        private RecyclerView rv_seckill;
        private SeckillRecyclerViewAdapter adapter;
        private long dt=0;//相差多少时间
        private android.os.Handler handler=new android.os.Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                dt=dt-1000;
                SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
                String time=format.format(new Date(dt));
                tv_time_seckill.setText(time);
                handler.removeMessages(0);
                handler.sendEmptyMessageDelayed(0,1000);
                if(dt<=0){
                    handler.removeCallbacksAndMessages(null);
                }
            }
        };

        public SeckillViewholder(Context mcontext, View itemView) {
            super(itemView);
            mContext=mcontext;
            tv_time_seckill=itemView.findViewById(R.id.tv_time_seckill);
            tv_more_seckill=itemView.findViewById(R.id.tv_more_seckill);
            rv_seckill=itemView.findViewById(R.id.rv_seckill);
        }

        public void setData(ResultBeanData.ResultBean.SeckillInfoBean seckill_info) {
            adapter=new SeckillRecyclerViewAdapter(mContext,seckill_info.getList());
            rv_seckill.setAdapter(adapter);
            rv_seckill.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
            adapter.setOnSeckillRecycler(new SeckillRecyclerViewAdapter.onSeckillRecycler() {
                @Override
                public void onItemOnclick(int position) {
                    Toast.makeText(mContext,"position="+position,Toast.LENGTH_SHORT).show();
                }
            });
            dt=Integer.valueOf(seckill_info.getEnd_time())-Integer.valueOf(seckill_info.getStart_time());
            handler.sendEmptyMessageDelayed(0,1000);
        }
    }
    class ActViewHolder extends RecyclerView.ViewHolder{
        private Context mContext;
        private ViewPager act_viewpager;


        public ActViewHolder(Context mcontext, View itemView) {
            super(itemView);
            mContext=mcontext;
            act_viewpager=itemView.findViewById(R.id.act_viewpager);
        }

        public void setData(final List<ResultBeanData.ResultBean.ActInfoBean> act_info) {
            act_viewpager.setPageMargin(20);
            act_viewpager.setOffscreenPageLimit(3);
            act_viewpager.setPageTransformer(true,new RotateYTransformer());
            act_viewpager.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {
                    return act_info.size();
                }

                @Override
                public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                    return view==object;
                }

                @NonNull
                @Override
                public Object instantiateItem(@NonNull ViewGroup container, int position) {
                    ImageView imageView=new ImageView(mContext);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    Glide.with(mContext).load(Constants.BASE_URL_IMAGE+act_info.get(position).getIcon_url()).into(imageView);
                    container.addView(imageView);
                    return imageView;
                }

                @Override
                public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                   container.removeView((View) object);
                }
            });
        }
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder{

        private Context mContext;
        private GridView gv_channel;
        private ChannelAdapter adapter;
        public ChannelViewHolder(Context mcontext, View itemView) {
            super(itemView);
            this.mContext=mcontext;
            gv_channel=itemView.findViewById(R.id.gv_channel);
        }

        public void setData(List<ResultBeanData.ResultBean.ChannelInfoBean> channel_info) {
            adapter=new ChannelAdapter(mContext,channel_info);
            gv_channel.setAdapter(adapter);
        }
    }
    class BannerViewHolder extends RecyclerView.ViewHolder{

      private Context mContext;
      private View itemView;
      private Banner banner;

        public BannerViewHolder(Context mcontext, View itemView) {
            super(itemView);
            this.mContext=mcontext;
            this.banner=itemView.findViewById(R.id.banner);
        }

        public void setData(List<ResultBeanData.ResultBean.BannerInfoBean> banner_info) {
            //设置Banner的数据
            final List<String> imagesUrl=new ArrayList<>();
            for(int i=0;i<banner_info.size();i++){
                String imageUrl=banner_info.get(i).getImage();
                imagesUrl.add(Constants.BASE_URL_IMAGE+imageUrl);
            }
            banner.setImages(imagesUrl);
            banner.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    Glide.with(context).load(path).into(imageView);
                }
            });
            banner.start();

        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (position){
            case BANNER:currentType=BANNER;break;
            case CHANNEL:currentType=CHANNEL;break;
            case ACT:currentType=ACT;break;
            case SECKILL:currentType=SECKILL;break;
            case RECOMMEND:currentType=RECOMMEND;break;
            case HOT:currentType=HOT;break;
        }
        return currentType;
    }

    @Override
    public int getItemCount() {
        //开发一个页面就加一
        return 6;
    }
}
