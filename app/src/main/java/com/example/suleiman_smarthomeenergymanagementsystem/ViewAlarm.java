package com.example.suleiman_smarthomeenergymanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ViewAlarm extends AppCompatActivity {
    private ProgressBar mProgressCircle;
    RecyclerView mRecyclerview;
    private List<Model> mUploads;
    ImageAdapterViewAlarms mAdapter;
    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_alarm);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        txt= findViewById(R.id.txt_heading);
        txt.setPaintFlags(txt.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mProgressCircle = (ProgressBar) findViewById(R.id.progress_circle1);

        mRecyclerview = (RecyclerView) findViewById(R.id.recycler_view1);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.setHasFixedSize(true);


        mUploads = new ArrayList<>();
        mAdapter = new ImageAdapterViewAlarms(ViewAlarm.this, mUploads);
        mRecyclerview.setAdapter(mAdapter);

        user = firebaseAuth.getCurrentUser();


        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Alarms").child(user.getUid()).child("Tasks");
        mDBListener =mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUploads.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    if (postSnapshot.exists()){
                                Model model = postSnapshot.getValue(Model.class);
                                model.setKey(postSnapshot.getKey());
                                mUploads.add(model);
                        }//postsnapshot exit end

                    }
                mAdapter.notifyDataSetChanged();
                mProgressCircle.setVisibility(View.INVISIBLE);
                //Binds the adapter with recyclerview
                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewAlarm.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ViewAlarm.this, HomeScreen.class));
        finish();
    }
}