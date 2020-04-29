package com.ashokcouhan.blooduser;


import android.content.Context;
import android.text.Layout;
import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.ashokcouhan.blooduser.Inteface.ItemClickListener;


public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    public TextView txtOrderId, txtOrderStatus,txtGroup,txtUnit,txtOrderName;
    CardView cardView;


    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void hideAdapter(View view) {
        view.setVisibility(View.GONE);

    }
    public OrderViewHolder(final View itemView) {
        super(itemView);

        txtOrderId = itemView.findViewById(R.id.orderId);
        txtOrderStatus = itemView.findViewById(R.id.status);

        txtGroup = itemView.findViewById(R.id.uGroup);
        txtUnit = itemView.findViewById(R.id.orderUnit);
        txtOrderName = itemView.findViewById(R.id.orderName);
        cardView=itemView.findViewById(R.id.layoutOrder);





    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }




}

