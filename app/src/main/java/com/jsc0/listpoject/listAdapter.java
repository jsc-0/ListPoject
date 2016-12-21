package com.jsc0.listpoject;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/12/21.
 */

public class listAdapter  extends BaseAdapter {
    private Activity activity;
    private int height;
    private addView addView;

    interface addView{
        public void addView(int position,View view);
    }

    public listAdapter(Activity activity,addView addView, int height){
        this.activity = activity;
        this.height = height;
        this.addView = addView;
    }

    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (null == convertView) {
            LayoutInflater inflater = LayoutInflater.from(activity);
            convertView = inflater.inflate(R.layout.item_list,null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.item_ImageView);
            viewHolder.view = (View)convertView.findViewById(R.id.item_View);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,height);
        viewHolder.imageView.setLayoutParams(layoutParams);
        double zhanshi = height * 0.7;
        LayoutParams layoutParamsView = new LayoutParams(LayoutParams.MATCH_PARENT,(int)zhanshi);
        layoutParamsView.gravity = Gravity.BOTTOM;
        viewHolder.view.setLayoutParams(layoutParamsView);
        addView.addView(position,convertView);
        return convertView;
    }

    class ViewHolder{
        private View view;
        private ImageView imageView;
    }
}
