package com.example.administrator.myplayer.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.administrator.myplayer.R;
import com.example.administrator.myplayer.adapter.MyAdapter;
import com.example.administrator.myplayer.bean.VideoConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NetworkVideoFragment extends Fragment {
    public SimpleAdapter adapter;
    public static List<Map<String, Object>> list;
    ListView listView;

    public NetworkVideoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_network_video, container, false);
        listView = view.findViewById(R.id.netVideo);
        list = getList();
        MyAdapter adapter = new MyAdapter(list, getContext());
        listView.setAdapter(adapter);

        return view;
    }

    private List<Map<String, Object>> getList() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map;
        String[] videoUrlList = VideoConstant.videoUrlList;
        for (int i = 0; i < videoUrlList.length; i++) {
            map = new HashMap<>();
            map.put("videoUrlList", videoUrlList[i]);
            list.add(map);
        }

        return list;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
