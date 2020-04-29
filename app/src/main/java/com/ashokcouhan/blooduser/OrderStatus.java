package com.ashokcouhan.blooduser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import com.ashokcouhan.blooduser.Common.Common;
import com.ashokcouhan.blooduser.Model.Myorder;
import com.ashokcouhan.blooduser.Model.Requests;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrderStatus extends AppCompatActivity {

    RecyclerView recyclerView;
    //LyoutManager layoutManager;
    LinearLayoutManager layoutManager;

    FirebaseRecyclerAdapter<Myorder, OrderViewHolder> adapter;
    DatabaseReference reference,myOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

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
                orderViewHolder.txtGroup.setText(myorder.getGroupName());
            }
        };
         recyclerView.setAdapter(adapter);
    }


//    private void loadRequests(String  bankId, final String bankName,String key) {
//        final String localkey=key;
//        reference=FirebaseDatabase.getInstance().getReference("bloodbank").child(bankId).child("Requests");
//        adapter=new FirebaseRecyclerAdapter<Requests, OrderViewHolder>(
//                Requests.class,R.layout.order_layout,OrderViewHolder.class,reference
//        ) {
//            @Override
//            protected void populateViewHolder(OrderViewHolder orderViewHolder, Requests requests, int i) {
//
//                if(!adapter.getRef(i).getKey().equals(localkey))
//                {
//                    orderViewHolder.cardView.setVisibility(View.GONE);
//
//
//                    return;
//
//                }
//
//                orderViewHolder.txtOrderId.setText(adapter.getRef(i).getKey());
//                orderViewHolder.txtUnit.setText(requests.getUnit());
//                orderViewHolder.txtOrderStatus.setText(Common.convertCodeToStatus(requests.getStatus()));
//                orderViewHolder.txtOrderName.setText(bankName);
//
////                    orderViewHolder.txtOrderName.setVisibility(View.INVISIBLE);
////                    orderViewHolder.txtGroup.setVisibility(View.INVISIBLE);
////                    orderViewHolder.txtOrderId.setVisibility(View.INVISIBLE);
////                    orderViewHolder.txtUnit.setVisibility(View.INVISIBLE);
////                    orderViewHolder.txtOrderStatus.setVisibility(View.INVISIBLE);
//
//            }
//        };
//       // adapter.notifyDataSetChanged();
//        recyclerView.setAdapter(adapter);
//    }
}
