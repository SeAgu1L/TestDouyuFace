package com.example.wangpengyu.testdouyuface.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wangpengyu.testdouyuface.R;
import com.example.wangpengyu.testdouyuface.baen.RoomBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;


/**
 * Created by wangpengyu on 16/10/19.
 */
public class RvAdapter extends RecyclerView.Adapter<RvAdapter.IDyViewHolder> {

    private Context context;

    private List<RoomBean> roomBeanList;

    public RvAdapter(Context context, List<RoomBean> roomBeanList) {
        this.context = context;
        this.roomBeanList = roomBeanList;
    }

    @Override
    public IDyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(context).inflate(R.layout.item_rcy,parent,false);
        return new IDyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(IDyViewHolder holder, int position) {
        SimpleDraweeView mTestSDV = holder.mSDV;
        mTestSDV.setImageURI(Uri.parse(roomBeanList.get(position).getAvatarMid()));

    }

    @Override
    public int getItemCount() {
        return roomBeanList.size();
    }

    class IDyViewHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView mSDV;
        public IDyViewHolder(View itemView) {
            super(itemView);
            mSDV = (SimpleDraweeView) itemView.findViewById(R.id.sdv);
        }
    }
}
