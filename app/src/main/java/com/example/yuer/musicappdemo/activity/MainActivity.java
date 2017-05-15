package com.example.yuer.musicappdemo.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuer.musicappdemo.R;
import com.example.yuer.musicappdemo.adapter.MyListViewAdapter;
import com.example.yuer.musicappdemo.bean.Music;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView lvMusic;
      MyListViewAdapter adapter;
//    List<Music> musicList;//数据源
    MediaPlayer mp=new MediaPlayer();
    static TextView tvPlaying;
    static TextView tvArtist;
    static TextView tvNowDuration;
    static TextView tvDuration;
    static ImageButton ibPause;
    static ImageButton ibNext;
    private boolean isPause=false;
    private int nowIndex=-1;
    MusicService.MusicBinder mBinder ;
    public static SeekBar seek;
    int progress=0;
      ArrayList<Music> musicList;
    int lastPosition=-1;//没点事-1.点击了记录上一次点击的位置




    private ServiceConnection conn=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            //活动和服务绑定后执行的方法 拿IBinder对象
            mBinder= (MusicService.MusicBinder) service;
            //拿list集合
            mBinder.readList(musicList);

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };


    public static Handler handler=new Handler()   //设置进度条
    {
        //处理service发来的消息

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 1:
                    //修改进度条

                    int duration=msg.arg1;
                    int progress=msg.arg2;
                    //设置进度条
                    seek.setMax(duration);  //最大值是当前歌曲的时长
                    seek.setProgress(progress);//进度是当前进行的进度

//                    Date date=new Date(duration);
//                    date.getMinutes();
//                    date.getSeconds();progress+""
                    Date date1 = new Date(progress);

                    tvNowDuration.setText(date1.getMinutes()+":"+date1.getSeconds());
                    break;

//                case 2:
//                     int a= (int) msg.obj;
//                     String time=formatTime(a);
//                     tvDuration.setText("/"+time);

//                    String a = (String) msg.obj;
////                    musicList.get(msg.arg1).setIndex(-1);
////                    musicList.get(msg.arg2).setIndex(msg.arg2+1);
//                    tvPlaying.setText(a);
//                    adapter.notifyDataSetChanged();
//                    break;
                case 3:
                    ibPause.setImageResource(R.drawable.play);
                    break;
                case 4:
                    ibPause.setImageResource(R.drawable.pause);
                    break;

            }











        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent=new Intent(this,MusicService.class);
        bindService(intent,conn,BIND_AUTO_CREATE);
//        mBinder.readList(  musicList);

        lvMusic= (ListView) findViewById(R.id.lvMusicList);
        tvPlaying=(TextView) findViewById(R.id.tv_title_playing);
        tvArtist=(TextView) findViewById(R.id.tv_title_artist);
        tvNowDuration=(TextView) findViewById(R.id.tv_title_nowDuration);
        tvDuration=(TextView) findViewById(R.id.tv_title_duration);

        ibPause=(ImageButton) findViewById(R.id.ib_pause);
        ibNext=(ImageButton) findViewById(R.id.ib_next);

        seek=(SeekBar) findViewById(R.id.seek);   //进度条控件
        seek.setMax(100);//六分钟   取当前音乐的播放时长
        seek.setProgress(0);//播到10秒 当前音乐的播放进度   去取当前音乐的播放进度
        //设置SeekBar的拖动监听 实现拖动之后音乐跳转播放 滑动监听
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //进度改变后做的事情
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //开始滑动做的事情  正在拖动 这时停止计时器
//                mbinder将活动和服务绑定
                mBinder.seekWait();

            }


            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
//                  停止滑动后做的事情
                //停止拖动时，调整播放器的播放进度
                //1.得到用户拖动到的值、位置
                int progress=seek.getProgress();//seekbar知道拖动到哪里了
                //2.将播放器调整到这个位置  继续播放
                mBinder.seekTo(progress);//创建一个方法将当前进度设置到播放器中

                //拖动结束开启计时器
                mBinder.seekStart();


            }
        });

        musicList=new ArrayList<>();//暂时用死的数据源
//        musicList.add(new Music(1,"醉人间","平沙落雁音乐团队","醉人间","",0,0));
//        musicList.add(new Music(2,"放鹤亭","小坠",null,"",0,0));
//        musicList.add(new Music(3,"苏幕遮","张晓棠","盛世好风光","",0,0));
//        musicList.add(new Music(4,"琼花房","叶洛洛","琼花房","",0,0));

        adapter=new MyListViewAdapter(MainActivity.this,musicList);
        lvMusic.setAdapter(adapter);



        lvMusic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//                //修改图片  点击某图片修改图片
//                ImageView imageView= (ImageView) view.findViewById(R.id.tv_item_id);
//                imageView.setImageResource(R.drawable.pan);  这个行不通

                ibPause.setImageResource(R.drawable.pause);

                tvPlaying.setText(musicList.get(i).getTitle());
                tvArtist.setText("-"+musicList.get(i).getArtist());


//                String position=nowTime(musicList.get(i).getDuration());
//                tvNowDuration.setText(position);

                String time=formatTime(musicList.get(i).getDuration());
                tvDuration.setText("/"+time);

                mBinder.start(i);

            //改数据源，让listview进行刷新
                try {
                    musicList.get(i).setIndex(-1);//-1表示正在播放
                    if (lastPosition!=-1)
                    {
                        if (lastPosition!=i) {
                            musicList.get(lastPosition).setIndex(lastPosition + 1);
                            musicList.get(nowIndex).setIndex(nowIndex+1);
                        }
                    }
                    adapter.notifyDataSetChanged();            //刷新列表

                    lastPosition=i;
                    nowIndex = i;
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }



            }
        });


        //实现暂停或播放按钮
        ibPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                       mBinder.pause();
            }
        });


        //实现播放下一首
        ibNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if (nowIndex!=-1){//不允许直接播放下一首

                            tvPlaying.setText(musicList.get(nowIndex+1).getTitle());
                            tvArtist.setText("-"+musicList.get(nowIndex+1).getArtist());

                            String time=formatTime(musicList.get(nowIndex+1).getDuration());
                            tvDuration.setText("/"+time);
                            mBinder.Next();

                            ibPause.setImageResource(R.drawable.pause);
//                    if (nowIndex!=-1){    //移到这里可以播放，但是不能显示图片

                            musicList.get(nowIndex).setIndex(nowIndex+1);
                            musicList.get(nowIndex+1).setIndex(-1);
                            adapter.notifyDataSetChanged();
                            nowIndex+=1;


                    }



            }
        });

        //因为下一步需要权限
//        所以提前动态申请
        if (ContextCompat.checkSelfPermission(MainActivity.this,android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            //没权限，申请
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
        else
        {
            //通过内容提供器可以查询手机中所有音乐文件，返回cursor对象，存到musicList中，再取出数据即可
            //下面是活的数据源
            initMusicList();//需要权限
        }


















        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }



    private static String formatTime(int time) {
        if (time/1000%60<10)
        {
            return time/1000/60+":0"+time/1000%60;
        }
        else
        {
            return time/1000/60+":"+time/1000%60;
        }
    }


    //销毁时释放资源
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp.stop();
    }

    //动态权限申请时的结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case 1:
                if (grantResults.length>0&& grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    initMusicList();//初始化
                }
                else
                {
                    Toast.makeText(this,"You denied the permission ",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    private void initMusicList() {

        //初始化 音乐列表（读本地音乐文件）
        Cursor cursor=getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null,null,null,MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

        //遍历cursor中的内容
        while(cursor.moveToNext())
        {
            int id=cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
            String title=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
            String artist=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
            String album=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
            String url=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
            int duration=cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
            int size=cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
            int index=cursor.getPosition();//能自动的到这个位置
            int currentPosition=cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
            //读取一条音乐数据 存入数据源list中
            musicList.add(new Music(id,title,artist,album,url,duration,size,index+1,currentPosition));
        }

        //读完数据后，刷新列表
        adapter.notifyDataSetChanged();//通知数据项改变了。
    }




   //返回按钮
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    //创建菜单栏的
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //应用顶部菜单  三个点点 Setting
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    //侧滑栏上也包含一些菜单  菜单的点击监听
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_user_manager) {
            // Handle the camera action
            //跳转到用户管理的活动中。
            Intent intent=new Intent(MainActivity.this, UserManagerActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_userinfo) {
            //跳转到修改用户信息界面
            Intent intent=new Intent(MainActivity.this,RefUserInfoActivity.class);
            //跳转显示的应该是当前用户的信息，所以跳转前应携带这条用户信息
            Intent intent1=getIntent();
            String regeistName=intent1.getStringExtra("regeistName");
            intent.putExtra("regeistName",regeistName);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
