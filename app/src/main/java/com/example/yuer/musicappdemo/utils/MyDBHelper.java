package com.example.yuer.musicappdemo.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Yuer on 2017/4/13.
 */

public class MyDBHelper extends SQLiteOpenHelper {

    //常量定义数据库语句
    public static final String CREATE_USERINFO="CREATE TABLE UserInfo(id integer" +
                          " primary key autoincrement,regeistName text,regeistPassword text,regeistCheckPassword text" +
                          ",phone text,sex text,nickName text,content text)";



    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建用户表
        db.execSQL(CREATE_USERINFO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
     //数据库升级
//        db.equals("drop table if exist UserInfo");
        db.execSQL("drop table if exists UserInfo");//删除之前的表再重新创建一下
        onCreate(db);//需要升级时要修改版本号1变2 一次叠加

    }
}
