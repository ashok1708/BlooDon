package com.ashokcouhan.blooduser;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashokcouhan.blooduser.Common.Common;
import com.ashokcouhan.blooduser.Model.Requests;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BookBlood extends AppCompatActivity {
    TextView tvName,tvLocation,tvUnit,tvGroup,tvMsg;
    Button btnSubmit,btnCancle,btnDone;
    ImageView ivCheck;

    String group,id,requireBlood,unit,Name;
    long currentSystemTime;

    FirebaseDatabase databaseReq,databaseOrder;
    DatabaseReference requests,userOrder;
    MyOrder myOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_blood);

         Name=getIntent().getStringExtra("name");
        group=getIntent().getStringExtra("group");
        unit=getIntent().getStringExtra("unit");
        String location=getIntent().getStringExtra("location");
        id=getIntent().getStringExtra("id");
        requireBlood=getIntent().getStringExtra("requireBlood");

        databaseReq = FirebaseDatabase.getInstance();
        databaseOrder = FirebaseDatabase.getInstance();

        requests = databaseReq.getReference("bloodbank").child(id).child("Requests");
        userOrder=databaseOrder.getReference("user").child(Common.currentUser.getMobile()).child("My Order");
         myOrder=new MyOrder(id,Name,group,requireBlood);

        tvName=findViewById(R.id.bankName);
        tvLocation=findViewById(R.id.bankLocation);
        tvUnit=findViewById(R.id.groupUnit);
        tvGroup=findViewById(R.id.chooseGroup);
        btnSubmit=findViewById(R.id.btnConfirm);
        btnCancle=findViewById(R.id.btnCancel);
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
                 btnSubmit.setVisibility(View.INVISIBLE);
                 btnCancle.setVisibility(View.INVISIBLE);
                 ivCheck.setVisibility(View.VISIBLE);
                 tvMsg.setVisibility(View.VISIBLE);
                 btnDone.setVisibility(View.VISIBLE);

                  currentSystemTime=System.currentTimeMillis();  //in milli seconds.. for transaction id




                 Requests request = new Requests(Common.currentUser.getName(),
                            Common.currentUser.getMobile(),
                            Common.currentUser.getAddress(),
                            group,
                         requireBlood);
                 requests.child(String.valueOf(currentSystemTime)).setValue(request);
                 setToUser();

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

    private void setToUser()
    {
        Thread thread=new Thread()
        {
            public void run(){
                try {
                    sleep(2000);

                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    userOrder.child(String.valueOf(currentSystemTime)).setValue(myOrder);
                }
            }
        };

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}