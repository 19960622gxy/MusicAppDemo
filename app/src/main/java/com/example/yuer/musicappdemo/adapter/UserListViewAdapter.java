package com.example.yuer.musicappdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yuer.musicappdemo.R;
import com.example.yuer.musicappdemo.bean.User;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Yuer on 2017/4/14.
 */
//对应用户管理的数据源接受  在侧滑栏
public class UserListViewAdapter extends BaseAdapter {
    Context context;
    List<User> userManager; //全局变量，使他能被使用
    //需要一个用户信息 泛型  用User表示 用于用户管理
    public UserListViewAdapter(Context context, List<User> userManager) {
        this.context=context;//上下文
        this.userManager=userManager;//数据源
    }

    @Override
    public int getCount() {
        return userManager.size();//这个一定要实现一下,还有getView，其他的可以不怎么用,
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //使用item布局文件 生成对应的view        inflate()将布局文件填充到view中
        View view= LayoutInflater.from(context).inflate(R.layout.item_user,parent,false);
        //将textview中的值设置为动态的
        TextView tvNickName= (TextView) view.findViewById(R.id.tv_item_nickName);
        TextView tvContent=(TextView) view.findViewById(R.id.tv_item_content);
        TextView tvSex=(TextView) view.findViewById(R.id.tv_item_sex);
        TextView tvPhone=(TextView) view.findViewById(R.id.tv_item_phone);
         //给值，先要有一个user对象
        User user=userManager.get(position);
//        把控件附上数据源的值
        tvNickName.setText(user.getNickName());
        tvContent.setText(user.getContent());
        tvSex.setText(user.getSex());
        tvPhone.setText(user.getPhone());
        return view;
    }
}
