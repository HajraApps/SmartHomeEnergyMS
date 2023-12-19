package com.example.suleiman_smarthomeenergymanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class DeleteAlarm extends AppCompatActivity implements ImageAdapterDeleteAlarm.buttonClickListener{
    private ProgressBar mProgressCircle;
    FloatingActionButton mCreateRem;
    RecyclerView mRecyclerview;
    private List<Model> mUploads;
    ImageAdapterDeleteAlarm mAdapter;
    DatabaseReference ref1, ref, ref2;
    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;

    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    String selectedKey;
    TextView txt;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_alarm);

        firebaseAuth = FirebaseAuth.getInstance();
        user= firebaseAuth.getCurrentUser();

        txt= findViewById(R.id.txt_heading2);
        txt.setPaintFlags(txt.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mProgressCircle = (ProgressBar) findViewById(R.id.progress_circle2);

        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerView2);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.setHasFixedSize(true);

        mUploads = new ArrayList<>();
        mAdapter = new ImageAdapterDeleteAlarm(DeleteAlarm.this, mUploads);
        mRecyclerview.setAdapter(mAdapter);
        mAdapter.setOnButtonClickListener(DeleteAlarm.this);

        firebaseUser = firebaseAuth.getCurrentUser();
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
                    }
                    else {

                    }
                }
                mAdapter.notifyDataSetChanged();
                mProgressCircle.setVisibility(View.INVISIBLE);
                //Binds the adapter with recyclerview
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DeleteAlarm.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });


    }

    @Override
    public void onbuttonClick(int position) {
        Model selectedItem = mUploads.get(position);
        selectedKey = selectedItem.getKey();
        deleteAlarmFunction();
    }

    private void deleteAlarmFunction() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                DeleteAlarm.this);
        alertDialog.setTitle("Delete Alarm");
        alertDialog.setMessage("Are you sure you want to Cancel the Alarm?");
        alertDialog.setIcon(R.drawable.logo);
        alertDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Alarms").child(user.getUid()).child("Tasks").child(selectedKey);
                        ref.getRef().removeValue();
                        startActivity(new Intent(DeleteAlarm.this, DeleteAlarm.class));
                        Toast.makeText(DeleteAlarm.this, "Alarm is Deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(DeleteAlarm.this, HomeScreen.class));
        finish();
    }

}