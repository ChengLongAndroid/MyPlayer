package com.example.administrator.myplayer.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.myplayer.R;


import java.util.List;
import java.util.Map;

import cn.jzvd.JZVideoPlayerStandard;

public class MyAdapter extends BaseAdapter {


    public List<Map<String, Object>> list;
    private Context context = null;

    public MyAdapter(List<Map<String, Object>> list, Context context) {
        this.list = list;
        this.context = context;


    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyAdapter.ViewHolder holder = null;
        if (null == convertView) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.videolist, null);
            holder = new MyAdapter.ViewHolder();
            holder.jzVideoPlayerStandard = convertView.findViewById(R.id.tv);
            holder.text2 = convertView.findViewById(R.id.textv2);
            convertView.setTag(holder);
        } else {

            holder = (MyAdapter.ViewHolder) convertView.getTag();
        }

        System.out.println("-----------555-------"+list.get(position).get("videoUrlList").toString());


        Uri uri = Uri.parse("http://vfx.mtime.cn/Video/2019/03/18/mp4/190318214226685784.mp4");

        holder.jzVideoPlayerStandard.setUp(list.get(position).get("videoUrlList").toString(), JZVideoPlayerStandard.SCREEN_WINDOW_LIST);  //设置路径,模式，
     //   holder.jzVideoPlayerStandard.thumbImageView.setImageBitmap(getVideoBitmap(list.get(position).get("abc").toString()));  //设置视频缩略图
        holder.text2.setText(list.get(position).get("videoUrlList").toString());
        return convertView;
    }

    public final class ViewHolder {
        public TextView text2;
        public JZVideoPlayerStandard jzVideoPlayerStandard;
    }


    public static Bitmap getVideoBitmap(String path) {
        Log.e("Icon", "path:" + path);
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(path);
            Bitmap frameAtTime = retriever.getFrameAtTime();
            return frameAtTime;
        } catch (Exception e) {
            return null;
        } finally {
            retriever.release();
        }

    }


}