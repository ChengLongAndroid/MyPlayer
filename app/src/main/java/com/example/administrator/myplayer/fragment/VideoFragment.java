package com.example.administrator.myplayer.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

import com.example.administrator.myplayer.R;
import com.example.administrator.myplayer.adapter.MyAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class VideoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public SimpleAdapter sa;
    public static List<Map<String, Object>> list;
    ListView listView;

    public VideoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment_video, container, false);
        list = getlist();

        listView = view.findViewById(R.id.videolist);
        MyAdapter adapter = new MyAdapter(list, getActivity());
        listView.setAdapter(adapter);
        return view;

    }


    public List<Map<String, Object>> getlist() {  //获取指定文件夹下所有音频文件路径的
        List<Map<String, Object>> list2 = new ArrayList<>();
        String sdString = Environment.getExternalStorageState();  //获取存储卡状态
        if (sdString.equals(Environment.MEDIA_MOUNTED)) {  //MEDIA_MOUNTED为正常可用
            File sdpath = new File((Environment.getExternalStorageDirectory() + "/DCIM/CAMERA"));//拿到根目录添加文件路径
            if (sdpath.listFiles() != null) {

                if (sdpath.listFiles().length > 0) {//判断是否有文件
                    for (File file : sdpath.listFiles()) {    //遍历添加到Map集合中

                        String name = file.getName();
                        int i = name.indexOf('.');
                        if (i != -1) {
                            name = name.substring(i);
                            if (name.equalsIgnoreCase(".mp4")
                                    || name.equalsIgnoreCase(".3gp")
                                    || name.equalsIgnoreCase(".wmv")
                                    || name.equalsIgnoreCase(".ts")
                                    || name.equalsIgnoreCase(".rmvb")
                                    || name.equalsIgnoreCase(".mov")
                                    || name.equalsIgnoreCase(".m4v")
                                    || name.equalsIgnoreCase(".avi")
                                    || name.equalsIgnoreCase(".m3u8")
                                    || name.equalsIgnoreCase(".3gpp")
                                    || name.equalsIgnoreCase(".3gpp2")
                                    || name.equalsIgnoreCase(".mkv")
                                    || name.equalsIgnoreCase(".flv")
                                    || name.equalsIgnoreCase(".divx")
                                    || name.equalsIgnoreCase(".f4v")
                                    || name.equalsIgnoreCase(".rm")
                                    || name.equalsIgnoreCase(".asf")
                                    || name.equalsIgnoreCase(".ram")
                                    || name.equalsIgnoreCase(".mpg")
                                    || name.equalsIgnoreCase(".v8")
                                    || name.equalsIgnoreCase(".swf")
                                    || name.equalsIgnoreCase(".m2v")
                                    || name.equalsIgnoreCase(".asx")
                                    || name.equalsIgnoreCase(".ra")
                                    || name.equalsIgnoreCase(".ndivx")
                                    || name.equalsIgnoreCase(".xvid")) {

                                Map<String, Object> map = new HashMap<>();
                                System.out.println("----------------3-----------" + file.toString());
                                map.put("videoUrlList", file);
                                list2.add(map);
                            }
                        }

                    }
                }

            }
        }
        return list2;
    }


}
