package com.ashokcouhan.blooduser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.helper.DateTimePickerEditText;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ashokcouhan.blooduser.Common.Common;
import com.ashokcouhan.blooduser.Model.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiper.MaterialSpinner;

import java.util.Date;

public class SignUp extends AppCompatActivity {
    EditText edtName,edtMobile,edtPass,edtAddress,edtFather;
    MaterialSpinner spnGender,spnGroup;
    Button btmSigUp;
    DatabaseReference reference;
    DateTimePickerEditText edtDOB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtName=findViewById(R.id.edtName);
        edtMobile=findViewById(R.id.edtMobile);
        edtPass=findViewById(R.id.edtPassword);
        //edtAge=findViewById(R.id.edtAge);
        edtAddress=findViewById(R.id.edtAddress);
        edtFather=findViewById(R.id.edtFather);
        spnGender=findViewById(R.id.spnGender);
        spnGroup=findViewById(R.id.spnGroup);
        btmSigUp=findViewById(R.id.btnSignUp);
        edtDOB=findViewById(R.id.edtDOB);
        edtDOB.setFormat("dd-MMM-yyyy");


        ArrayAdapter<String> adapterGender = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.gender));

        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGender.setAdapter(adapterGender);

        ArrayAdapter<String> adapterGroup = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.group_array));

        adapterGroup.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGroup.setAdapter(adapterGroup);

        FirebaseApp.initializeApp(this);
        reference= FirebaseDatabase.getInstance().getReference().child("user");

        edtPass.setTransformationMethod(new PasswordTransformationMethod());

        btmSigUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Common.isConnectedToInternet(getBaseContext())) {
                    final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                    mDialog.setMessage("Hold On...");
                    mDialog.show();

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String lname = "", lfather = "",  lsex = "", lmobile = "", laddress = "", bloodGroup = "", lpass = "",ldob;
                            lname = edtName.getText().toString();
                            lpass = edtPass.getText().toString();
                            Date dob=edtDOB.getDate();
                            ldob=Common.dateToString(dob);
                            laddress = edtAddress.getText().toString();
                            lfather = edtFather.getText().toString();
                            lmobile = edtMobile.getText().toString();
                            bloodGroup = spnGroup.getSelectedItem().toString();
                            lsex = spnGender.getSelectedItem().toString();

                          String groupName=Common.getGroupType(bloodGroup);

                            if (lname.isEmpty() && lpass.isEmpty() && lmobile.isEmpty() && ldob.isEmpty() && laddress.isEmpty() && lfather.isEmpty()) {
                                Toast.makeText(SignUp.this, "Please fill the information...", Toast.LENGTH_SHORT).show();
                                mDialog.dismiss();
                                return;
                            }
                            if (lmobile.isEmpty()) {
                                edtMobile.setError("Please fill the number...");
                                mDialog.dismiss();
                                return;
                            }
                            if (lname.isEmpty()) {
                                edtMobile.setError("Please fill the name...");
                                mDialog.dismiss();
                                return;
                            }
                            if (lpass.isEmpty()) {
                                edtMobile.setError("Please set the password...");
                                mDialog.dismiss();
                                return;
                            }


                            Log.d("check",bloodGroup+ " "+groupName);
                                if (dataSnapshot.child(lmobile).exists()) {
                                    mDialog.dismiss();
                                    Toast.makeText(SignUp.this, "Number is already exists...", Toast.LENGTH_SHORT).show();

                                } else {
                                    mDialog.dismiss();
                                    User user = new User(lname, lpass, lmobile, lfather, ldob, lsex, groupName, laddress);
                                    Log.d("check2",user.getBloodGroup());
                                    reference.child(lmobile).setValue(user);
                                    Toast.makeText(SignUp.this, "Successfully SignUp...", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignUp.this, MainActivity.class);
                                    Common.currentUser = user;
                                    startActivity(intent);
                                }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else{
                    Toast.makeText(SignUp.this, "Check your internet connection !!", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}
