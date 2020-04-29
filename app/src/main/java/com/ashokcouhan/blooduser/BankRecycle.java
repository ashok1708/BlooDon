package com.ashokcouhan.blooduser;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.ashokcouhan.blooduser.Common.Common;
import com.ashokcouhan.blooduser.Model.BloodBank;
import com.ashokcouhan.blooduser.Model.SingleGroup;

import java.util.ArrayList;


public class BankRecycle extends RecyclerView.Adapter<BankRecycle.ViewHolder>{

    Context context;
    ArrayList<SingleGroup> bloodBanks;
    String group,requireBlood;


    public BankRecycle(Context c , ArrayList<SingleGroup> p,String requireBlood){
        context = c;
        bloodBanks = p;
        this.requireBlood=requireBlood;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvName,tvGroup,tvLocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName=itemView.findViewById(R.id.tvName);

            tvGroup=itemView.findViewById(R.id.group);
            tvLocation=itemView.findViewById(R.id.location);



        }
    }


    @NonNull
    @Override
    public BankRecycle.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.bank_recycle, parent, false);
        BankRecycle.ViewHolder viewHolder = new BankRecycle.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.tvName.setText(bloodBanks.get(position).getName());
        group= Common.getGroupName(bloodBanks.get(position).getGroup());

        holder.tvGroup.setText(group);

        holder.tvLocation.setText(bloodBanks.get(position).getLocation());



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, BookBlood.class);
                intent.putExtra("name",bloodBanks.get(position).getName());
                intent.putExtra("group",group);
                intent.putExtra("location",bloodBanks.get(position).getLocation());
               // intent.putExtra("unit",bloodBanks.get(position).getQunatity());
                intent.putExtra("id",bloodBanks.get(position).getId());
                intent.putExtra("requireBlood",requireBlood);
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return bloodBanks.size();
    }
}
