package com.ashokcouhan.blooduser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import com.ashokcouhan.blooduser.Common.Common;
import com.ashokcouhan.blooduser.Model.Requests;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrderStatus extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Requests, OrderViewHolder> adapter;


    DatabaseReference reference,myOrder;
    MyOrder order;
    static String bankId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        bankId=getBankId();

        Log.d("idcheck",bankId+" or");
        reference=FirebaseDatabase.getInstance().getReference("bloodbank").child("0003").child("Requests");




        loadRequests();


    }
     String getBankId()
    {
        final String[] id = new String[1];
        myOrder=FirebaseDatabase.getInstance().getReference("user").child(Common.currentUser.getMobile()).child("Myorder");
        myOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Log.d("Order",dataSnapshot.getValue().toString());
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    order=snapshot.getValue(MyOrder.class);
                    id[0] =order.getBankid();
                    Log.d("id",order.getBankid());
                    break;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return id[0];
    }

    private void loadRequests() {
        adapter=new FirebaseRecyclerAdapter<Requests, OrderViewHolder>(
                Requests.class,R.layout.order_layout,OrderViewHolder.class,reference
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder orderViewHolder, Requests requests, int i) {

                orderViewHolder.txtOrderId.setText(adapter.getRef(i).getKey());
                orderViewHolder.txtUnit.setText(requests.getUnit());
                orderViewHolder.txtOrderStatus.setText(Common.convertCodeToStatus(requests.getStatus()));
            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
}
