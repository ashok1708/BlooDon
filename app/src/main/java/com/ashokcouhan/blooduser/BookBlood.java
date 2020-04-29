package com.ashokcouhan.blooduser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokcouhan.blooduser.Common.Common;
import com.ashokcouhan.blooduser.Model.Myorder;
import com.ashokcouhan.blooduser.Model.Requests;

import com.ashokcouhan.blooduser.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class BookBlood extends AppCompatActivity  {
    TextView tvName,tvLocation,tvUnit,tvGroup,tvMsg;
    Button btnSubmit,btnDone;
    ImageView ivCheck;

    String group,id,requireBlood,unit,Name;
    long currentSystemTime;

    FirebaseDatabase databaseReq,databaseOrder;
    DatabaseReference requests,userOrder;
   Myorder myOrder;
    Requests request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_blood);

        FirebaseApp.initializeApp(this);
         Name=getIntent().getStringExtra("name");
        group=getIntent().getStringExtra("group");
        unit=getIntent().getStringExtra("unit");
        String location=getIntent().getStringExtra("location");
        id=getIntent().getStringExtra("id");
        requireBlood=getIntent().getStringExtra("requireBlood");


        databaseReq = FirebaseDatabase.getInstance();
        databaseOrder = FirebaseDatabase.getInstance();

        Paper.init(this);

        requests = databaseReq.getReference("bloodbank").child(id).child("Requests");
        userOrder=databaseOrder.getReference("user").child(Common.currentUser.getMobile()).child("Myorder");

         myOrder=new Myorder(id,Name,group,requireBlood);
        request = new Requests(Common.currentUser.getName(),
                Common.currentUser.getMobile(),
                Common.currentUser.getAddress(),
                group,
                requireBlood);

        tvName=findViewById(R.id.bankName);
        tvLocation=findViewById(R.id.bankLocation);
        tvUnit=findViewById(R.id.groupUnit);
        tvGroup=findViewById(R.id.chooseGroup);
        btnSubmit=findViewById(R.id.btnConfirm);

        btnDone=findViewById(R.id.btnDone);
        tvMsg=findViewById(R.id.tvMsg);
        ivCheck=findViewById(R.id.ivCheck);




        tvName.setText(Name);
        tvUnit.setText(requireBlood);
        tvLocation.setText(location);

        String type="";

        if(group.equals("Aposi")){
            type="A+";
        }

        if(group.equals("ABposi")){
            type="AB+";
        }
        if(group.equals("Aneg")){
            type="A-";
        }if(group.equals("Bposi")){
            type="B+";
        }if(group.equals("Bneg")){
            type="B-";
        }if(group.equals("Oposi")){
            type="O+";
        }
        if(group.equals("Oneg")){
            type="O-";
        }

        tvGroup.setText(type);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSystemTime=System.currentTimeMillis();  //in milli seconds.. for transaction id
                 showAlertDialog();
            }
        });



    }
    private void showAlertDialog() {
        final AlertDialog.Builder builder= new AlertDialog.Builder(BookBlood.this);
        builder.setTitle("Confirm Booking... ");
         builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {

                 try {

                     Log.d("Error", "get error");
                 }
                 catch (Exception e) {
                     e.printStackTrace();
                 }
                 finally {
                     userOrder.child(String.valueOf(currentSystemTime)).setValue(myOrder);
                 }




                 Toast.makeText(BookBlood.this, "Request is send to bank", Toast.LENGTH_SHORT).show();


                 requests.child(String.valueOf(currentSystemTime)).setValue(request);
                 // Log.d("error","this sis time to check");

                 btnSubmit.setVisibility(View.INVISIBLE);

                 ivCheck.setVisibility(View.VISIBLE);
                 tvMsg.setVisibility(View.VISIBLE);
                 btnDone.setVisibility(View.VISIBLE);



             }
         });

         builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 dialog.dismiss();
             }
         });
         builder.show();
    }




}
