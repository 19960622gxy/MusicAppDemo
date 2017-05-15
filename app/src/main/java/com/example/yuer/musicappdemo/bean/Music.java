package com.example.yuer.musicappdemo.bean;

/**
 * Created by Yuer on 2017/4/12.
 */
//创建一个Music类构建数据源的类型
public class Music {
    //Music对象的所有属性都封装到这个类中
    private int id;
    private String title;
    private String artist;
    private String album;//专辑名
    private String url;//歌曲路径
    private int duration;//时长
    private int size;//文件大小
    //添加一个序号属性方便我们修改
    private int index;//歌曲的序号

    private int currentPosition;//歌曲正在播放的时候的位置

    public Music(int id, String title, String artist, String album, String url, int duration, int size,int index,int currentPosition) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.url = url;
        this.duration = duration;
        this.size = size;
        this.index=index;
        this.currentPosition=currentPosition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }
}
