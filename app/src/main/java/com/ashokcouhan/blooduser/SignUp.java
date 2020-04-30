package com.ashokcouhan.blooduser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.helper.DateTimePickerEditText;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ashokcouhan.blooduser.Common.Common;
import com.ashokcouhan.blooduser.Model.User;
import com.google.android.material.textfield.TextInputLayout;
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
    TextInputLayout inputLayoutAddress;
    String stt="",cty="",stte="",pcd="";


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
        inputLayoutAddress=findViewById(R.id.textInputAdd);
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

      edtAddress.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
               final AlertDialog dialog= new AlertDialog.Builder(SignUp.this).create();
              dialog.setTitle("Address");
              dialog.setIcon(R.drawable.ic_home_black_24dp);
              LayoutInflater inflater= getLayoutInflater();
              final View  dialogView = inflater.inflate(R.layout.layout_address,null);
              final EditText street =   dialogView.findViewById(R.id.edtStreet);
              final EditText city =   dialogView.findViewById(R.id.edtCity);
              final EditText pincode =   dialogView.findViewById(R.id.edtPinCode);
              Button btnCancel =dialogView.findViewById(R.id.btnCancel);
              Button btnSubmit =dialogView.findViewById(R.id.btnSbmt);
              final MaterialSpinner spnState= dialogView.findViewById(R.id.spnState);

              ArrayAdapter<String> adapterState = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item,
                      getResources().getStringArray(R.array.india_states));

              adapterState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
              spnState.setAdapter(adapterState);
              dialog.setView(dialogView);
              dialog.setCancelable(false);

              if(!stt.isEmpty() && !cty.isEmpty() && !stte.isEmpty() && !pcd.isEmpty() )
              {
                  street.setText(stt);
                  city.setText(cty);
                  pincode.setText(pcd);
                  //spnState.setPrompt(stte);
                  spnState.setSelection(adapterState.getPosition(stte));
              }

              btnSubmit.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      if(street.getText().toString().isEmpty())
                      {
                          street.setError("Please enter address");
                          return;
                      }
                      if(city.getText().toString().isEmpty())
                      {
                          street.setError("Please enter city");
                          return;
                      }
                      if(pincode.getText().toString().isEmpty())
                      {
                          street.setError("Please enter Pin Code");
                          return;
                      }

                      stt=street.getText().toString();
                      cty=city.getText().toString();
                      stte=spnState.getSelectedItem().toString();
                      pcd=pincode.getText().toString();

                      String fullAdd=stt+" , "+cty+" , "+stte+" , "+pcd;
                      edtAddress.setText(fullAdd);
                      dialog.dismiss();

                  }
              });
              btnCancel.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      if(stt.isEmpty() && cty.isEmpty() && stte.isEmpty() && pcd.isEmpty() )
                      {
                          Toast.makeText(SignUp.this, "Cannot fill the address", Toast.LENGTH_SHORT).show();
                          dialog.dismiss();
                      }

                      dialog.dismiss();
                  }
              });

              dialog.show();
          }
      });

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
