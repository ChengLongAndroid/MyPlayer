package com.example.administrator.myplayer.fragment;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.administrator.myplayer.R;
import com.example.administrator.myplayer.bean.VideoConstant;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NetMusicFragment extends Fragment {

    public EditText edit;
    public MediaPlayer mediaPlayer;
    public Button netPause,pause, stop, replay;
    public SimpleAdapter sa;
    public ListView listview;
    public static List<Map<String, Object>> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_net_music, container, false);
        edit = (EditText) view.findViewById(R.id.edit);
        netPause = (Button) view.findViewById(R.id.button);
        pause = (Button) view.findViewById(R.id.button1);
        stop = (Button) view.findViewById(R.id.button2);
        replay = (Button) view.findViewById(R.id.button3);
        listview = (ListView) view.findViewById(R.id.listMusic);
        list = getlist();

        String[] from = {"musicUrlList"};
        int[] to = {R.id.textView};//从这个组件开始顺序填充list数据
        sa = new SimpleAdapter(getActivity() , list, R.layout.listview, from, to);
        listview.setAdapter(sa);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() { //建立监听ListView点击事件，i变量为点击item的位置
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String,Object> map =list.get(i);  //获取那一行的Map数据，开始处理
                String filepath= (String) map.get("musicUrlList");
//                for(Map.Entry<String,Object> e: map.entrySet()){
//                    filepath=((File)e.getValue()).getPath().toString();//强转为File类，用getPath获取路径
//                }


                System.out.println( "---------6------"+filepath);


                if(mediaPlayer!=null&&mediaPlayer.isPlaying()){  //判断是否在播放音乐
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer=null;
                }
                Musicplay(filepath);//路径放入音乐播放函数
                Toast.makeText(getActivity(),"当前显示为"+i,Toast.LENGTH_SHORT).show();
            }


        });


        netPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Musicplay(edit.getText().toString());
            }
        });


        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("继续".equals(pause.getText().toString())){//对比按钮Test属性，"继续"说明音乐是暂停的
                    mediaPlayer.start();
                    pause.setText("暂停");
                    return;
                }
                if(mediaPlayer!=null&&mediaPlayer.isPlaying()){  //音乐在播放，用paus函数暂停
                    mediaPlayer.pause();
                    pause.setText("继续");
                }
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer=null;
                }
                pause.setText("暂停");
                netPause.setEnabled(true);

            }
        });
        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
                    mediaPlayer.seekTo(0); //重音频最开始的地方播放
                }else {
                    Musicplay(edit.getText().toString());
//                     mediaPlayer.start();
                }
                pause.setText("暂停");
            }
        });



        return view;
    }


    public void Musicplay(String music){ //播放音乐函数
        try{
            mediaPlayer=new MediaPlayer();
            mediaPlayer.setDataSource(music);  //添加数据源
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);   //设置流媒体的类型，AudioManager可以修改系统的情景模式为音乐 可以后台播放
            mediaPlayer.prepareAsync();//异步准备
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {//准备完毕后调用
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();//开始播放音乐
                    netPause.setEnabled(false);
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {//放完一首歌后调用
                @Override
                public void onCompletion(MediaPlayer mp) {
                    netPause.setEnabled(true);//设置按钮为显示
                }
            });

        }catch (Exception e){}
    }


    public List<Map<String, Object>> getlist() {  //获取指定文件夹下所有音频文件路径的
        List<Map<String, Object>> list2 = new ArrayList<>();
        Map<String, Object> map;
        String[] musicUrlList = VideoConstant.musicUrlList;
        for (int i = 0; i < musicUrlList.length; i++) {
            map = new HashMap<>();
            map.put("musicUrlList", musicUrlList[i]);
            list2.add(map);
        }

        return list2;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
