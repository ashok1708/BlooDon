package com.ashokcouhan.blooduser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ashokcouhan.blooduser.Model.BloodBank;
import com.ashokcouhan.blooduser.Model.Group;
import com.ashokcouhan.blooduser.Model.SingleGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class BankList extends AppCompatActivity {

    DatabaseReference reference;
    RecyclerView recyclerView;
    BankRecycle adapter;
    ArrayList<SingleGroup> listbank;
    Toolbar toolbar;
    ImageButton backBtn;
    SwipeRefreshLayout swipeRefreshLayout;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_list);

        toolbar=findViewById(R.id.toolbar);
       //  backBtn=findViewById(R.id.btnBack);
        swipeRefreshLayout=findViewById(R.id.swipplayout);


        setSupportActionBar(toolbar);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         getSupportActionBar().setDisplayShowHomeEnabled(true);
         toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
         toolbar.setNavigationOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 onBackPressed();
                 finish();
             }
         });



        listbank=new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));

        reference = FirebaseDatabase.getInstance().getReference().child("bloodbank");

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        swipeRefreshLayout.setRefreshing(false);
                        swipeRefreshLayout.setColorSchemeResources(R.color.primaryTextColor, R.color.primaryTextColor,R.color.primaryTextColor);


                    }
                },2000);
                listbank.clear();
                adapter.notifyDataSetChanged();
                loadList();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        loadList();
    }

   private void loadList()
    {
        final String bloodType=getIntent().getStringExtra("group");
        final String unit=getIntent().getStringExtra("unit");
        final int intunit=Integer.parseInt(unit);
        final String location=getIntent().getStringExtra("location");


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Log.d("data",dataSnapshot.getValue().toString());
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    BloodBank bloodBank= snapshot.getValue(BloodBank.class);
                    bloodBank.setId(snapshot.getKey());
                    // Log.d("Blood Bank",bloodBank.getName());
                    for(DataSnapshot snapshot1:snapshot.child("Group").getChildren())
                    {
                        // Log.d("check",snapshot1.getValue().toString());
                        SingleGroup singleGroup=new SingleGroup(bloodBank.getName(),snapshot1.getValue().toString(),snapshot1.getKey(),bloodBank.getLocation(),bloodBank.getId());

                        if(singleGroup.getGroup().equals(bloodType) && Integer.parseInt(singleGroup.getQunatity())>=intunit && singleGroup.getLocation().equals(location))
                        {
                            listbank.add(singleGroup);
                            Log.d("Group", singleGroup.getName());

                        }

                    }
                }
                adapter = new BankRecycle(BankList.this,listbank,unit);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }



}
