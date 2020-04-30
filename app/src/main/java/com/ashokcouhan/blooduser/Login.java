package com.ashokcouhan.blooduser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.ashokcouhan.blooduser.Common.Common;
import com.ashokcouhan.blooduser.Model.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class Login extends AppCompatActivity {
    EditText edtMobile,edtPass;
    Button btnLogin,btnSignUp;
    DatabaseReference reference;
    CheckBox remeberBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtMobile=findViewById(R.id.edtMobile);
        edtPass=findViewById(R.id.edtPassword);
        btnLogin=findViewById(R.id.btnLogin);
        btnSignUp=findViewById(R.id.btnSignup);
        remeberBox=findViewById(R.id.rememberMe);

        edtPass.setTransformationMethod(new PasswordTransformationMethod());

        Paper.init(this);  // save current user

        FirebaseApp.initializeApp(this);
        reference= FirebaseDatabase.getInstance().getReference().child("user");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Common.isConnectedToInternet(getBaseContext())) {
                    final ProgressDialog mDialog = new ProgressDialog(Login.this);
                    mDialog.setMessage("Please Wait...");
                    mDialog.show();

                    final String lMobile = edtMobile.getText().toString();
                    final String lPass = edtPass.getText().toString();

                    if (TextUtils.isEmpty(lMobile) && TextUtils.isEmpty(lPass)) {
                        edtMobile.setError("Please enter the Mobile number...");

                        edtPass.setError("Please enter the password...");
                        mDialog.dismiss();
                    } else {
                        if (TextUtils.isEmpty(lMobile)) {
                            edtMobile.setError("Please enter the Mobile number...");
                            mDialog.dismiss();
                        }
                        if (TextUtils.isEmpty(lMobile)) {
                            edtPass.setError("Please enter the password...");
                            mDialog.dismiss();
                        }
                    }

                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(lMobile).exists()) {
                                mDialog.dismiss();
                                User user = dataSnapshot.child(lMobile).getValue(User.class);
                                user.setMobile(lMobile);
                                if (user.getPassword().equals(lPass)) {
                                    if (remeberBox.isChecked()) {
                                        Paper.book().write(Common.USER_KEY, edtMobile.getText().toString());
                                        Paper.book().write(Common.PWD_KEY, edtPass.getText().toString());
                                    }

                                    String msg = "Welcome " + user.getName() ;
                                    Toast.makeText(Login.this, msg, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    Common.currentUser = user;
                                    startActivity(intent);
                                    finish();

                                } else {
                                    Toast.makeText(Login.this, "Wrong Password...", Toast.LENGTH_SHORT).show();

                                }
                            } else {
                                Toast.makeText(Login.this, "User Not Found...", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
//                    reference.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.child(lMobile).exists()) {
//                                mDialog.dismiss();
//                                User user = dataSnapshot.child(lMobile).getValue(User.class);
//                                user.setMobile(lMobile);
//                                if (user.getPassword().equals(lPass)) {
//                                    if (remeberBox.isChecked()) {
//                                        Paper.book().write(Common.USER_KEY, edtMobile.getText().toString());
//                                        Paper.book().write(Common.PWD_KEY, edtPass.getText().toString());
//                                    }
//
//                                    String msg = "Welcome " + user.getName() + " ...";
//                                    Toast.makeText(Login.this, msg, Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(Login.this, MainActivity.class);
//                                    Common.currentUser = user;
//                                    startActivity(intent);
//                                    finish();
//
//                                } else {
//                                    Toast.makeText(Login.this, "Wrong Password...", Toast.LENGTH_SHORT).show();
//
//                                }
//                            } else {
//                                Toast.makeText(Login.this, "User Not Found...", Toast.LENGTH_SHORT).show();
//                            }
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
                }
                else{
                    Toast.makeText(Login.this, "Check your Internet Connection !!", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,SignUp.class);
                startActivity(intent);
            }
        });



    }
}
