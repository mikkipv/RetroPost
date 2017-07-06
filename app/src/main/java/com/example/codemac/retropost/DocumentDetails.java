package com.example.codemac.retropost;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by codemac on 3/7/17.
 */

public class DocumentDetails extends RecyclerView.Adapter<DocumentDetails.ViewHolder> {

    private RecyclerView recyclerView;
    private View parentView;
    private LinearLayoutManager layoutManager;
    public List<login> detailsList;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view_detail,parent,false);

        ViewHolder viewHolder=new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.viewname.setText(detailsList.get(position).getRname());
        holder.viewlat.setText(detailsList.get(position).getRlat());
        holder.viewlong.setText(detailsList.get(position).getRlong());
        holder.viewaddr.setText(detailsList.get(position).getRadd());
        holder.viewloc.setText(detailsList.get(position).getRloc());

        //Picasso.with(context).load(detailsList.get(position).getImage()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public  TextView viewname;
        public  TextView viewlat;
        public  TextView viewlong;
        public  TextView viewaddr;
        public  TextView viewloc;
        public  ImageView imageView;



        public ViewHolder(View itemView) {
            super(itemView);
            viewname = (TextView) itemView.findViewById(R.id.viewname);
            viewlat = (TextView) itemView.findViewById(R.id.viewlat);
            viewlong= (TextView) itemView.findViewById(R.id.viewlong);
            viewaddr = (TextView) itemView.findViewById(R.id.viewadd);
            viewloc = (TextView) itemView.findViewById(R.id.viewloc);
            imageView = (ImageView) itemView.findViewById(R.id.viewimg);


        }
    }
}
