package com.example.suleiman_smarthomeenergymanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    private EditText u_gender, u_fname, u_phone, u_add;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private Button update;
    DatabaseReference ref;
    NetworkInfo nInfo;

    String us_gender, us_fname, us_phone, us_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        update=(Button) findViewById(R.id.save);
        u_gender= (EditText) findViewById(R.id.up_gender);
        u_fname= (EditText) findViewById(R.id.up_name);
        u_phone= (EditText) findViewById(R.id.up_phone);
        u_add= (EditText) findViewById(R.id.up_add);

        ConnectivityManager cManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        nInfo = cManager.getActiveNetworkInfo();

        firebaseAuth= FirebaseAuth.getInstance();
        //get firebase user
        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Profile");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    UserInformation userInformation= dataSnapshot.getValue(UserInformation.class);
                    u_gender.setText(userInformation.getUserGender());
                    u_fname.setText(userInformation.getUserName());
                    u_phone.setText(userInformation.getUserPhone());
                    u_add.setText(userInformation.getUserAddress());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Profile.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user!=null){
                    if (validate()) {
                        if (nInfo != null && nInfo.isConnected()) {
                            UserInformation userInformation = new UserInformation(us_fname, us_phone, us_add,us_gender);
                            ref.setValue(userInformation);
                            Toast.makeText(Profile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Profile.this, HomeScreen.class));
                            finish();

                        }
                    } else {
                        Toast.makeText(Profile.this, "Network is not available", Toast.LENGTH_LONG).show();
                    }
                }//user null
            }
        });
    }

    private Boolean validate(){
        boolean result= false;

        us_gender = u_gender.getText().toString();
        us_fname = u_fname.getText().toString();
        us_phone=u_phone.getText().toString();
        us_add= u_add.getText().toString();
        if(us_gender.isEmpty() ||  us_fname.isEmpty() || us_phone.isEmpty() || us_add.isEmpty()){
            Toast.makeText(this, "Fill every required information", Toast.LENGTH_SHORT).show();
        }else {
            result= true;
        }
        return result;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Profile.this, HomeScreen.class));
        finish();
    }
}