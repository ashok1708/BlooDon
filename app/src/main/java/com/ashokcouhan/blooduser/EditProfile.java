package com.ashokcouhan.blooduser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Toast;

import com.ashokcouhan.blooduser.Common.Common;
import com.ashokcouhan.blooduser.Model.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tiper.MaterialSpinner;

public class EditProfile extends AppCompatActivity {
    EditText edtName,edtPass,edtAddress,edtFather;
    Button btnDone;
    DatabaseReference reference;
    String stt="",cty="",stte="",pcd="";
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        FirebaseApp.initializeApp(this);
        reference= FirebaseDatabase.getInstance().getReference().child("user");

        edtName=findViewById(R.id.edtName);
        edtPass=findViewById(R.id.updtPass);
        edtAddress=findViewById(R.id.edtAddress);
        edtFather=findViewById(R.id.edtFather);
        btnDone=findViewById(R.id.btnSignUp);

        toolbar=findViewById(R.id.toolbar_edit);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        edtName.setText(Common.currentUser.getName());
        edtAddress.setText(Common.currentUser.getAddress());
        edtFather.setText(Common.currentUser.getFatherName());

        edtPass.setTransformationMethod(new PasswordTransformationMethod());

        edtPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialog = new AlertDialog.Builder(EditProfile.this).create();

                LayoutInflater inflater= getLayoutInflater();
                final View  dialogView = inflater.inflate(R.layout.layout_password,null);
                final EditText oldPass=dialogView.findViewById(R.id.edtOldPass);
                final EditText newPass=dialogView.findViewById(R.id.edtNewPass);
                final EditText conPass=dialogView.findViewById(R.id.edtConPass);
                Button btnCancel =dialogView.findViewById(R.id.btnCancel);
                Button btnSubmit =dialogView.findViewById(R.id.btnSbmt);

                oldPass.setTransformationMethod(new PasswordTransformationMethod());
                newPass.setTransformationMethod(new PasswordTransformationMethod());
                conPass.setTransformationMethod(new PasswordTransformationMethod());
                dialog.setView(dialogView);
                dialog.setCancelable(false);

                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(oldPass.getText().toString().isEmpty())
                        {
                            oldPass.setError("Enter the old password");
                            return;
                        }
                        if(newPass.getText().toString().isEmpty())
                        {
                            newPass.setError("Enter the new password");
                            return;
                        }
                        if(conPass.getText().toString().isEmpty())
                        {
                            newPass.setError("Enter the Confirm password");
                            return;
                        }
                        String oldPassword=oldPass.getText().toString();
                        String newPassword=conPass.getText().toString();
                        String conPassword=conPass.getText().toString();

                        if(!Common.currentUser.getPassword().equals(oldPassword))
                        {
                            Toast.makeText(EditProfile.this, "Old Password is not match !", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(!newPassword.equals(conPassword))
                        {
                            Toast.makeText(EditProfile.this, "Password not match..!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        edtPass.setText(conPass.getText().toString());
                        dialog.dismiss();
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        edtAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialog= new AlertDialog.Builder(EditProfile.this).create();
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
                            Toast.makeText(EditProfile.this, "Cannot fill the address", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }

                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });


        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String lname = "", lfather = "", laddress = "", lpass = "";

                lname = edtName.getText().toString();
                laddress = edtAddress.getText().toString();
                lfather = edtFather.getText().toString();
                lpass=edtPass.getText().toString();

                if(Common.currentUser.getPassword().equals(lpass))
                {
                    lpass=Common.currentUser.getPassword();
                }
                reference.child(Common.currentUser.getMobile()).child("name").setValue(lname);
                reference.child(Common.currentUser.getMobile()).child("address").setValue(laddress);
                reference.child(Common.currentUser.getMobile()).child("fatherName").setValue(lfather);

                User editUser = new User(lname,lpass, Common.currentUser.getMobile(), lfather, Common.currentUser.getDob(),Common.currentUser.getSex(),Common.currentUser.getBloodGroup(), laddress);
                Common.currentUser=editUser;
                Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }


        });
    }
}
