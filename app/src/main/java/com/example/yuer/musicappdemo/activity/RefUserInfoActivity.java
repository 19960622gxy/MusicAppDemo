package com.example.yuer.musicappdemo.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuer.musicappdemo.R;
import com.example.yuer.musicappdemo.adapter.RefUserListViewAdapter;
import com.example.yuer.musicappdemo.adapter.UserListViewAdapter;
import com.example.yuer.musicappdemo.bean.User;
import com.example.yuer.musicappdemo.utils.MyDBHelper;

import java.util.ArrayList;
import java.util.List;

public class RefUserInfoActivity extends AppCompatActivity {

    private static final String TAG = "RefUserInfoActivity";
    TextView etRegeistName;
    EditText etNickName,etRegeistPassword,etRegeistCheckPassword,etPhone,etSex,etContent;
    Button btnUpdate;

    ListView lvRefUserInfo;
    RefUserListViewAdapter adapter;
    List<User> userInfo;
    MyDBHelper dbHelper;
    String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ref_user_info);

        //实例化
        etRegeistName=(TextView) findViewById(R.id.etRegeistName);
        etNickName=(EditText) findViewById(R.id.etNickName);
        etRegeistPassword=(EditText) findViewById(R.id.etRegeistPassword);
        etRegeistCheckPassword=(EditText) findViewById(R.id.etRegeistCheckPassword);
        etPhone=(EditText) findViewById(R.id.etPhone);
        etSex=(EditText) findViewById(R.id.etSex);
        etContent=(EditText) findViewById(R.id.etContent);
        btnUpdate=(Button) findViewById(R.id.btnUpdate);


//        lvRefUserInfo=(ListView) findViewById(R.id.lvRefUserInfo);
        dbHelper=new MyDBHelper(this,"MyDB.db",null,1);

//        userInfo=new ArrayList<>();  //创建死数据源
//        adapter=new RefUserListViewAdapter(this,userInfo);
//        lvRefUserInfo.setAdapter(adapter);
        Intent intent=getIntent();
        userName=intent.getStringExtra("regeistName");
//        Log.d("regeistName",userName);

        //取活的数据（数据库中的数据）
        initData();


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //注册用户 去数据库 用户表中更新一条用户数据

                //先取到用户输入的值
                String nickName=etNickName.getText().toString();
                String regeistPassword=etRegeistPassword.getText().toString();
                String regeistCheckPassword=etRegeistCheckPassword.getText().toString();
                String phone=etPhone.getText().toString();
                String sex=etSex.getText().toString();
                String content=etContent.getText().toString();


//                Intent intent=getIntent();
//                String regeistName=intent.getStringExtra("regeistName");
//                Log.d(TAG, "onClick: "+regeistName);

                //判断两次密码是否相同
                    if (!regeistCheckPassword.equals(regeistPassword))
                    {
                        Toast.makeText(RefUserInfoActivity.this,"两次输入的密码不相同！",Toast.LENGTH_SHORT).show();
                        etRegeistPassword.setText("");
                        etRegeistCheckPassword.setText("");  //清空，让用户重新输入

                    }
                    else
                    {
                        //插入数据
                        SQLiteDatabase db=dbHelper.getWritableDatabase();//数据库有则拿到 无则创建并拿到
                        ContentValues values=new ContentValues();
                        values.put("nickName",nickName);
                        values.put("regeistPassword",regeistPassword);
                        values.put("regeistCheckPassword",regeistCheckPassword);
                        values.put("phone",phone);
                        values.put("sex",sex);
                        values.put("content",content);
                        //中间还是有其他的数据项，可后面再添加，需要注意，要在布局先修改，再到这边

//                        Intent intent=getIntent();
//                        String regeistName=intent.getStringExtra("regeistName");

                        long id= db.update("UserInfo",values,"regeistName = ?",new String[]{userName});
                        if (id!=-1)
                        {
                            Toast.makeText(RefUserInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else
                        {
                            Toast.makeText(RefUserInfoActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                        }
                    }


            }
        });






    }

    private void initData() {
        //取数据库中的用户信息  添加到集合中
        SQLiteDatabase db=dbHelper.getWritableDatabase();
//        Cursor cursor= db.query("UserInfo",null,null,null,null,null,null);
        Cursor cursor=db.rawQuery("select * from UserInfo where regeistName=?",new String[]{userName});
        if (cursor.moveToFirst()){
            do {
                etRegeistName.setText(cursor.getString(cursor.getColumnIndex("regeistName")));
                etNickName.setText(cursor.getString(cursor.getColumnIndex("nickName")));
                etRegeistPassword.setText(cursor.getString(cursor.getColumnIndex("regeistPassword")));
                etRegeistCheckPassword.setText(cursor.getString(cursor.getColumnIndex("regeistCheckPassword")));
                etPhone.setText(cursor.getString(cursor.getColumnIndex("phone")));
                etSex.setText(cursor.getString(cursor.getColumnIndex("sex")));
                etContent.setText(cursor.getString(cursor.getColumnIndex("content")));
            }while (cursor.moveToNext());
        }
//        while(cursor.moveToNext())
//        {
//            //通过列号取值
//            int id=cursor.getInt(0);
//            String regeistName=cursor.getString(1);
//            String regeistPassword=cursor.getString(2);
//            String regeistCheckPassword=cursor.getString(3);
//            String phone=cursor.getString(4);
//            String sex=cursor.getString(5);
//            String nickName=cursor.getString(6);
//            String content=cursor.getString(7);
//
//            //拿到一个对象
//
//            User user=new User(id,regeistName,regeistPassword,regeistCheckPassword,phone,sex,nickName,content);
//            //将对象添加到集合中
//            userInfo.add(user);
//        }
//        adapter.notifyDataSetChanged();//刷新listview的adapter

    }
}
