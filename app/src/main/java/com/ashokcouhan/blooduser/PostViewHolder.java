package com.ashokcouhan.blooduser;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashokcouhan.blooduser.Inteface.ItemClickListener;

public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

     public  ImageView ivPost;
     public TextView tvName,tvDescrp;
     public ItemClickListener itemClickListener;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);

        ivPost=itemView.findViewById(R.id.ivPost);
        tvName=itemView.findViewById(R.id.tvPostName);
        tvDescrp=itemView.findViewById(R.id.tvPostdescr);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
