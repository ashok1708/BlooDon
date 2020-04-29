package com.ashokcouhan.blooduser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
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

public class SplashScreen extends AppCompatActivity {

    DatabaseReference reference;
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        FirebaseApp.initializeApp(this);
        reference= FirebaseDatabase.getInstance().getReference().child("user");

        Paper.init(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getSupportActionBar().hide();
        logo=findViewById(R.id.logoview);
        final Animation animation1 =
                AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.fade);
        logo.startAnimation(animation1);
        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    sleep(2000);



                }

                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    String user=Paper.book().read(Common.USER_KEY);
                    String pass=Paper.book().read(Common.PWD_KEY);
                    if(user!= null && pass!=null)
                    {
                        if(!user.isEmpty() && !pass.isEmpty())
                            login(user,pass);
                    }
                    else {
                        Intent mainIntent = new Intent(SplashScreen.this, Login.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                        startActivity(mainIntent);
                        finish();
                    }
                }
            }
        };
        thread.start();

    }
    protected void onPause()
    {
        super.onPause();

        finish();
    }
    private void login(final String mobile, final String pass)
    {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(mobile).exists())
                {

                    User user=dataSnapshot.child(mobile).getValue(User.class);
                    user.setMobile(mobile);
                    if(user.getPassword().equals(pass))
                    {
                        String msg="Welcome "+user.getName()+" ...";
                        //Toast.makeText(Login.this, msg, Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(SplashScreen.this,MainActivity.class);
                        Common.currentUser=user;
                        startActivity(intent);

                    }
                    else{
                        Toast.makeText(SplashScreen.this, "Wrong Password...", Toast.LENGTH_SHORT).show();

                    }
                }
                else {
                    Toast.makeText(SplashScreen.this, "User Not Found...", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}