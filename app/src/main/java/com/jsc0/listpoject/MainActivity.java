package com.jsc0.listpoject;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity implements listAdapter.addView{

    private ListView listView;
    public int allViewHeigth;
    private Map<Integer,View> listViews = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.lv);
        ViewTreeObserver vto = listView.getViewTreeObserver();
        listView.post(new Runnable() {

            @Override
            public void run() {
                allViewHeigth = listView.getHeight() / 3 * 2;
                listAdapter listAdapter = new listAdapter(MainActivity.this,MainActivity.this, allViewHeigth);
                listView.setAdapter(listAdapter);
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState==SCROLL_STATE_IDLE) {
                    int one = listView.getFirstVisiblePosition();//获得当前显示的最上方item的position
                    int two = listView.getLastVisiblePosition(); //获得当前显示的最下方item的position
                    judgment(one, two);
                    Log.i("状态","执行了");
                }
            }

            /**
             *
             * @param view
             * @param firstVisibleItem  第一个可见项是ListView的第几项
             * @param visibleItemCount 可见项的总数
             * @param totalItemCount  总项数
             */
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int two = listView.getLastVisiblePosition(); //获得当前显示的最下方item的position
//                Log.i("状态","第一个"+firstVisibleItem+"最后一个"+two);
                judgment(firstVisibleItem,two);
            }
        });

    }

    public void judgment(int top,int bottom){
        Log.i("map数",listViews.size()+"");
        if(top!=-1) {
            View convertView = listViews.get(top);
            if(null!=convertView) {
                ImageView imageView = (ImageView) convertView.findViewById(R.id.item_ImageView);
                View views = (View) convertView.findViewById(R.id.item_View);
                Rect rect = new Rect();
                boolean visibility = convertView.getLocalVisibleRect (rect);
                int baifenbi = rect.height() * 100 / allViewHeigth;
                Log.i("rect","第"+top+"个,显示"+baifenbi + "%");
                if(baifenbi<70){
                    views.setVisibility(View.GONE);
                }else{
                    views.setVisibility(View.VISIBLE);
                }
            }else{
                Log.i("状态","没获取到"+top);
            }
        }
        if(bottom!=-1) {
            View convertView = listViews.get(bottom);
            if(null!=convertView) {
                ImageView imageView = (ImageView) convertView.findViewById(R.id.item_ImageView);
                View views = (View) convertView.findViewById(R.id.item_View);
                Rect rect = new Rect();
                boolean visibility = convertView.getLocalVisibleRect (rect);
                int baifenbi = rect.height() * 100 / allViewHeigth;
                Log.i("rect","第"+bottom+"个,显示"+baifenbi + "%");
                if(baifenbi>90){
                    views.setVisibility(View.VISIBLE);
                }
            }else{
                Log.i("状态","没获取到"+bottom);
            }
        }
    }

    @Override
    public void addView(int position, View view) {
        listViews.put(position,view);//每次滑动加载新的contentView都会put进来如果Item多了就吃内存了需要处理
    }
}
