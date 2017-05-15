package com.example.yuer.musicappdemo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.UserManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuer.musicappdemo.R;
import com.example.yuer.musicappdemo.adapter.UserListViewAdapter;
import com.example.yuer.musicappdemo.bean.User;
import com.example.yuer.musicappdemo.utils.MyDBHelper;

import java.util.ArrayList;
import java.util.List;

public class UserManagerActivity extends AppCompatActivity {
    ListView lvUserManager;
    UserListViewAdapter adapter;
    List<User> userManager;    //先创建一个死的数据源
    //取活的数据源要使用MyDBHelper
    MyDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manager);
        lvUserManager=(ListView) findViewById(R.id.lvUserManager);

        dbHelper=new MyDBHelper(this,"MyDB.db",null,1);

        userManager=new ArrayList<>();  //创建死数据源
//        userManager.add(new User(1,"gxy","112523","112523","18360944833","女","成住坏空","在所难免..."));
//        userManager.add(new User(2,"lx","111111","111111","18360944832","男","最珍贵","覆水难收..."));
//        userManager.add(new User(3,"jt","111111","111111","18360944823","男","天梯","万劫不复..."));


        adapter=new UserListViewAdapter(this,userManager);
        lvUserManager.setAdapter(adapter);

        //点击item行时的监听，弹出一个对话框
        lvUserManager.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(UserManagerActivity.this);
                //拿到该项的数据    是一个User对象
                final User user=userManager.get(position);

                builder.setTitle("是否删除"+user.getNickName()+"的信息？");
                builder .setMessage("用户名：" +user.getRegeistName()
                        +"\n个性签名："+user.getContent()
                        +"\n电话号码："+user.getPhone());
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //删除该用户
                        SQLiteDatabase db=dbHelper.getWritableDatabase();
                        db.delete("UserInfo","id = ?",new String[]{user.getId()+""});
                        //删除了数据库中的项 也要删除文件中显示的哪一个
                        userManager.remove(position);
                        adapter.notifyDataSetChanged();//刷新一下
                        Toast.makeText(UserManagerActivity.this,"已删除",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });


        //取活的数据（数据库中的数据）
        initData();
    }

    private void initData() {


        //取所有数据

        //取数据库中的用户信息  添加到集合中
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor= db.query("UserInfo",null,null,null,null,null,null);
        while(cursor.moveToNext())
        {
            //通过列号取值
            int id=cursor.getInt(0);
            String regeistName=cursor.getString(1);
            String regeistPassword=cursor.getString(2);
            String regeistCheckPassword=cursor.getString(3);
            String phone=cursor.getString(4);
            String sex=cursor.getString(5);
            String nickName=cursor.getString(6);
            String content=cursor.getString(7);

            //拿到一个对象
//   死数据源进行测试        User user=new User(1,"hulin","112523","112523","18363456833","女","狐狸","竭尽全力...");
            User user=new User(id,regeistName,regeistPassword,regeistCheckPassword,phone,sex,nickName,content);
            //将对象添加到集合中
            userManager.add(user);
        }
        adapter.notifyDataSetChanged();//刷新listview的adapter

    }
}
