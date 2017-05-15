package com.example.yuer.musicappdemo.activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yuer.musicappdemo.R;
import com.example.yuer.musicappdemo.utils.MyDBHelper;

public class RegeistActivity extends AppCompatActivity {

    EditText etRegeistName,etNickName,etRegeistPassword,etRegeistCheckPassword,etPhone,etSex;
    Button btnRegeist;
    MyDBHelper dbHelper;//要使用这个类 先创建这个对象 再去onCreate中实例化

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regeist);
        //实例化
        etRegeistName=(EditText) findViewById(R.id.etRegeistName);
        etNickName=(EditText) findViewById(R.id.etNickName);
        etRegeistPassword=(EditText) findViewById(R.id.etRegeistPassword);
        etRegeistCheckPassword=(EditText) findViewById(R.id.etRegeistCheckPassword);
        etPhone=(EditText) findViewById(R.id.etPhone);
        etSex=(EditText) findViewById(R.id.etSex);

        btnRegeist=(Button) findViewById(R.id.btnRegeist);
        dbHelper=new MyDBHelper(this,"MyDB.db",null,1);
        //设置按钮的点击监听。
        btnRegeist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //注册用户 去数据库 用户表中插入一条用户数据

                //先取到用户输入的值
                String regeistName=etRegeistName.getText().toString();
                String nickName=etNickName.getText().toString();
                String regeistPassword=etRegeistPassword.getText().toString();
                String regeistCheckPassword=etRegeistCheckPassword.getText().toString();
                String phone=etPhone.getText().toString();
                String sex=etSex.getText().toString();
                //判断一下用户名是否为空
                if (regeistName.isEmpty()||nickName.isEmpty()||regeistPassword.isEmpty()||regeistCheckPassword.isEmpty())
                {
                    //提示用户输入
                    Toast.makeText(RegeistActivity.this,"请完善账号信息",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    //判断两次密码是否相同
                    if (!regeistCheckPassword.equals(regeistPassword))
                    {
                        Toast.makeText(RegeistActivity.this,"两次输入的密码不相同！",Toast.LENGTH_SHORT).show();
                        etRegeistPassword.setText("");
                        etRegeistCheckPassword.setText("");  //清空，让用户重新输入

                    }
                    else
                    {
                        //插入数据
                        SQLiteDatabase db=dbHelper.getWritableDatabase();//数据库有则拿到 无则创建并拿到
                        ContentValues values=new ContentValues();
                        values.put("regeistName",regeistName);
                        values.put("nickName",nickName);
                        values.put("regeistPassword",regeistPassword);
                        values.put("regeistCheckPassword",regeistCheckPassword);
                        values.put("phone",phone);
                        values.put("sex",sex);
                        //中间还是有其他的数据项，可后面再添加，需要注意，要在布局先修改，再到这边


                       long id= db.insert("UserInfo",null,values);
                       if (id!=-1)
                       {
                           Toast.makeText(RegeistActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                           finish();
                       }
                        else
                       {
                           Toast.makeText(RegeistActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                       }
                    }
                }

            }
        });

    }
}
