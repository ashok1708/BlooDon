package com.ashokcouhan.blooduser;


import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.ashokcouhan.blooduser.Inteface.ItemClickListener;


public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    public TextView txtOrderId, txtOrderStatus,txtGroup,txtUnit;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public OrderViewHolder(final View itemView) {
        super(itemView);

        txtOrderId = itemView.findViewById(R.id.orderId);
        txtOrderStatus = itemView.findViewById(R.id.status);

        txtGroup = itemView.findViewById(R.id.uGroup);
        txtUnit = itemView.findViewById(R.id.orderUnit);

        itemView.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }




}

