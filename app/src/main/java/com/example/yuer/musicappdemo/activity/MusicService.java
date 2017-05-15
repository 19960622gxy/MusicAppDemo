package com.example.yuer.musicappdemo.activity;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuer.musicappdemo.R;
import com.example.yuer.musicappdemo.adapter.MyListViewAdapter;
import com.example.yuer.musicappdemo.bean.Music;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;

public class MusicService extends Service {
    MediaPlayer mp;
    public   MusicBinder mBinder=new MusicBinder();
    Timer timer;
//    TextView tvPlaying;
    List<Music> musicList;

    MyListViewAdapter adapter;
    int lastPosition=-1;//没点事-1.点击了记录上一次点击的位置


    private boolean isPause=false;  //是否正在暂停
//    private boolean isNext=false;  //是否进行下一首
    private int position=0;
    private int musicIndex=0;
    public MusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mp=new MediaPlayer();
        //初始化一下计时器
        timer=new Timer();
        //播放结束监听
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //播放结束会执行的方法  回调方法  只知道播完了 但具体逻辑不知道
                //重播或播放下一曲

                //重播
                mp.reset();
                try {
                    mp.setDataSource(musicList.get(position).getUrl());
                    mp.prepare();
                    mp.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


    }



    @Override
    public IBinder onBind(Intent intent) {
      return mBinder;
    }

    class MusicBinder extends Binder
    {


        //用于描述该服务对外 提供了哪些方法
         public  void start(int po)
         {
             position = po;
                 //播放方法
                 Log.d(TAG, "start: 开始播放！");
                 //1.设置资源
                 //2.准备prepare
                 //3.start
                 try {
                     mp.reset();
                     mp.setDataSource(musicList.get(po).getUrl());//真机的话可使用这个路径sdcard/0.mp3
                     mp.prepare();
                     mp.start();

                     moveSeek();//播放并显示进度
                 }
                 catch (Exception e) {
                     e.printStackTrace();
                 }
//             Message msg = new Message();
//             msg.what=2;
//             msg.obj = musicList.get(po).getTitle();
//                MainActivity.handler.sendMessage(msg);
//             tvPlaying.setText(musicList.get(position).getTitle());


    }
        public  void setResource(String url)
        {

        }

        public void seekTo(int progress)    //进度条跳到
        {
            //调整Mediaplayer的进度
            mp.seekTo(progress);
        }



        public void pause()
        {
            Log.d(TAG, "pause: 暂停！");

            if (mp.isPlaying())
            {

                //如果正在播放，暂停

                Message msg = new Message();
                msg.what=3;
                msg.obj = musicList.get(position).getId();
                MainActivity.handler.sendMessage(msg);
                mp.pause();
//                moveSeek();//播放并显示进度
                seekWait();
                isPause=true;
            }
            else
            {
                if (isPause=true) {
                    Message msg = new Message();
                    msg.what=4;
                    msg.obj = musicList.get(position).getId();
                    MainActivity.handler.sendMessage(msg);
                    mp.start();
                    seekStart();
                }
                isPause=false;
            }

        }







        public  void stop()
        {
            if (mp.isPlaying())
            {
                //如果正在播放，暂停
                mp.stop();
//                mp.reset();
            }

        }
        
        public void readList(ArrayList<Music> musicList1)
        {
            musicList=musicList1;
        }

        //下一首
        public void Next() {
//            Message msg = new Message();
//            msg.arg2 =position;
            if (position+1>musicList.size()-1)
            {
                position=0;
            }
            else
            {
                position+=1;
            }
            try {
                mp.reset();
                mp.setDataSource(musicList.get(position).getUrl());
                mp.prepare();
                mp.start();
                moveSeek();//播放并显示进度

//                msg.what = 2;
//                msg.arg1 = position;
//                msg.obj = musicList.get(position).getDuration();

//                MainActivity.handler.sendMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



        public void seekWait()
        {
            //停止计时器
            //拿计时器对象  也可将他设置为成员变量
            timer.cancel();//取消计时器就是停止
        }

        public void seekStart()
        {
            //开启计时器
            //再调一遍moveseekfangfa
            timer=new Timer();
            moveSeek();

        }

        public  void addSeek()
        {
            int duration=mp.getDuration();//时长
            int progress=mp.getCurrentPosition();//进度
            //通过计时器循环传递进度值  每隔一秒传一下进度
        }
    }


    public void moveSeek()
    {

        int duration=mp.getDuration();//时长
        if (duration>0)
        {
            //如果时长不等于0
//            Log.d(TAG, "moveSeek: ");
            //通过计时器
             TimerTask task=new TimerTask() {
                @Override
                public void run() {
                    //间隔执行任务  让进度条动起来
                    //Service中的mp读当前进度 传给MainActivity的SeekBar
                    int duration=mp.getDuration();//时长
                    int progress=mp.getCurrentPosition();//进度
                    //Hander 异步消息处理机制
                    Message msg=new Message();
                    msg.what=1;
                    msg.arg1=duration;
                    msg.arg2=progress;
                    MainActivity.handler.sendMessage(msg);
                }
            };
            timer.schedule(task,5,1000);//开启一个计时任务
            //              延迟五毫秒  间隔
        }
//        else
//        {
//            Toast.makeText(this,"当前没有音乐正在播放",Toast.LENGTH_SHORT).show();
//        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
