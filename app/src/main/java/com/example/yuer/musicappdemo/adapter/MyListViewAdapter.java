package com.example.yuer.musicappdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yuer.musicappdemo.bean.Music;
import com.example.yuer.musicappdemo.R;

import java.util.List;

/**
 * Created by Yuer on 2017/4/12.
 */
//对应主界面数据源的接受操作
public class MyListViewAdapter extends BaseAdapter {
    Context context;
    List<Music> musicList;


//创建一个Music类构建数据源的类型
    public MyListViewAdapter(Context context, List<Music> musicList)
    {
        //拿到本地数据项以及上下文
        this.context=context;
        this.musicList=musicList;
    }

    @Override
    public int getCount() {//获取ListView要展示的条目的数量
        return musicList.size();
    }

    @Override
    public Object getItem(int i) {//找到某一项
        return null;
    }

    @Override
    public long getItemId(int i) {//找到某一项的id
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        //构建每个项显示的样式
        //构建item的内容 想要什么样式则去创建layout文件自己定义  在布局文件的item——music中可见
        View view1= LayoutInflater.from(context).inflate(R.layout.item_music,viewGroup,false);

        TextView tvIndex=(TextView) view1.findViewById(R.id.tv_item_id);
        TextView tvTitle=(TextView) view1.findViewById(R.id.tv_item_title);
        TextView tvArtist=(TextView) view1.findViewById(R.id.tv_item_artist);

        //你构建的是哪个位置的item，就去取哪个位置的数据源
        if (musicList.get(position).getIndex()==-1)
        {
          tvIndex.setBackgroundResource(R.drawable.pan);
        }
        else
        {
            tvIndex.setText(musicList.get(position).getIndex()+"");//int转String
        }

        tvTitle.setText(musicList.get(position).getTitle());
        tvArtist.setText(musicList.get(position).getArtist());

        return view1;

    }
}
