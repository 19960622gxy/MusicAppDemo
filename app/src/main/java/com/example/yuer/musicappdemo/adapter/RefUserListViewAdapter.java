package com.example.yuer.musicappdemo.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yuer.musicappdemo.R;
import com.example.yuer.musicappdemo.bean.User;

import java.util.List;

/**
 * Created by Yuer on 2017/4/24.
 */

public class RefUserListViewAdapter extends BaseAdapter {
    Context context;
    List<User> userInfo;
    public RefUserListViewAdapter(Context context, List<User> userInfo) {
        this.context=context;
        this.userInfo=userInfo;
    }

    @Override
    public int getCount() {
        return userInfo.size();
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
        View view= LayoutInflater.from(context).inflate(R.layout.update_user,parent,false);
        //将textview中的值设置为动态的
        TextView etRegeistName= (EditText) view.findViewById(R.id.etRegeistName);
        EditText etNickName=(EditText) view.findViewById(R.id.etNickName);
        EditText etRegeistPassword=(EditText) view.findViewById(R.id.etRegeistPassword);
        EditText etRegeistCheckPassword=(EditText) view.findViewById(R.id.etRegeistCheckPassword);
        EditText etPhone=(EditText) view.findViewById(R.id.etPhone);
        EditText etSex=(EditText) view.findViewById(R.id.etSex);

        User user=userInfo.get(position);

        etRegeistName.setText(user.getRegeistName());
        etNickName.setText(user.getNickName());
        etRegeistPassword.setText(user.getRegeistPassword());
        etRegeistCheckPassword.setText(user.getRegeistCheckPassword());
        etPhone.setText(user.getPhone());
        etSex.setText(user.getSex());

        return view;
    }
}
