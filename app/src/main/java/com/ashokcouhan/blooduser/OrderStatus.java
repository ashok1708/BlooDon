package com.ashokcouhan.blooduser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ashokcouhan.blooduser.Common.Common;
import com.ashokcouhan.blooduser.Model.Myorder;
import com.ashokcouhan.blooduser.Model.Requests;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrderStatus extends AppCompatActivity {

    RecyclerView recyclerView;
    //LyoutManager layoutManager;
    LinearLayoutManager layoutManager;
    TextView errorMsg;
    FirebaseRecyclerAdapter<Myorder, OrderViewHolder> adapter;
    DatabaseReference reference,myOrder;
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        errorMsg=findViewById(R.id.tvErrorMsg);
        toolbar=findViewById(R.id.toolbar_status);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);

        myOrder=FirebaseDatabase.getInstance().getReference("user").child(Common.currentUser.getMobile()).child("Myorder");
        loadRequests();
    }

    private void loadRequests()
    {
         adapter = new FirebaseRecyclerAdapter<Myorder, OrderViewHolder>(Myorder.class,R.layout.order_layout,OrderViewHolder.class,myOrder) {
            @Override
            protected void populateViewHolder(OrderViewHolder orderViewHolder, Myorder myorder, int i) {
                orderViewHolder.txtOrderName.setText(myorder.getBankname());
                orderViewHolder.txtOrderId.setText(adapter.getRef(i).getKey());
                orderViewHolder.txtUnit.setText(myorder.getUnits());
                orderViewHolder.txtOrderStatus.setText(Common.convertCodeToStatus(myorder.getStatus()));
                orderViewHolder.txtGroup.setText(Common.getGroupName(myorder.getGroup()));
            }

             @Override
             protected void onDataChanged() {
                 if(getItemCount()==0){
                     recyclerView.setVisibility(View.GONE);
                     errorMsg.setVisibility(View.VISIBLE);
                 }
             }
         };
         recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
