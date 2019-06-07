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

import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.administrator.myplayer.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MusicFragment extends Fragment {

    public MediaPlayer mediaPlayer;
    public Button pause,stop,replay;
    public SimpleAdapter sa;
    public ListView listview;
    public static List<Map<String,Object>> list;


    public MusicFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_fragment_music, container, false);

        pause = (Button) view.findViewById(R.id.button2);
        stop = (Button) view.findViewById(R.id.button3);
        replay = (Button) view.findViewById(R.id.button4);

        listview= (ListView) view.findViewById(R.id.listMusic);
        list=getlist();

        String[] from = {"abc"};//Map对象的键数组
        int[] to = {R.id.textView};//从这个组件开始顺序填充list数据
        sa = new SimpleAdapter(getActivity() , list, R.layout.listview, from, to);
        listview.setAdapter(sa);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() { //建立监听ListView点击事件，i变量为点击item的位置
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String,Object> map =list.get(i);  //获取那一行的Map数据，开始处理
                String filepath=null;
//  想在没有键的情况下获取值，Map类提供了一个称为entrySet()的方法，这个方法返回一个Map.Entry实例化后的对象集。接着，Map.Entry类提供了一个getKey()方法和一个getValue()方法
                for(Map.Entry<String,Object> e: map.entrySet()){
                    filepath=((File)e.getValue()).getPath().toString();//强转为File类，用getPath获取路径
                }

                if(mediaPlayer!=null&&mediaPlayer.isPlaying()){  //判断是否在播放音乐
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer=null;
                }


                Musicplay(filepath);//路径放入音乐播放函数
                Toast.makeText(getActivity(),"当前显示为"+i,Toast.LENGTH_SHORT).show();
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

            }
        });
        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
                    mediaPlayer.seekTo(0); //重音频最开始的地方播放
                }else {
                  // mediaPlayer.start();
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

                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {//放完一首歌后调用
                @Override
                public void onCompletion(MediaPlayer mp) {

                }
            });

        }catch (Exception e){}
    }



    public List<Map<String,Object>> getlist() {  //获取指定文件夹下所有音频文件路径的
        List<Map<String,Object>> list2 =new ArrayList<>();
        String sdString= Environment.getExternalStorageState();  //获取存储卡状态
        if(sdString.equals(Environment.MEDIA_MOUNTED)){  //MEDIA_MOUNTED为正常可用
            File sdpath=new File((Environment.getExternalStorageDirectory()+"/netease/cloudmusic/Music"));//拿到根目录添加文件路径
            if(sdpath.listFiles().length>0){//判断是否有文件
                for (File file : sdpath.listFiles()){    //遍历添加到Map集合中
                    Map<String,Object> map=new HashMap<>();
                    map.put("abc",file);
                    list2.add(map);
                }
            }
        }
        return list2;
    }

}
