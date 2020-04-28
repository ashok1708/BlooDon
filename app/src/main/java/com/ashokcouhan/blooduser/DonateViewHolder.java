package com.ashokcouhan.blooduser;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashokcouhan.blooduser.Inteface.ItemClickListener;

public class DonateViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tvDate,tvCamp,tvLocation;
    private ItemClickListener itemClickListener;

    public DonateViewHolder(@NonNull View itemView) {
        super(itemView);

        tvDate=itemView.findViewById(R.id.donateDate);
        tvCamp=itemView.findViewById(R.id.donateCamp);
        tvLocation=itemView.findViewById(R.id.donateLocation);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
