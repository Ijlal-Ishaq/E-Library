package com.example.ubitlibrary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.ubitlibrary.adapters.feedback_adp;
import com.example.ubitlibrary.objects.feedback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Feedback_section extends AppCompatActivity {

    private FloatingActionButton btn;
    private dialog_box_feedback dialog;
    private DatabaseReference fref;
    private List<feedback> feedbackList;
    private RecyclerView rv;
    private feedback_adp adp;
    private ProgressBar pbar;
    boolean flag=true;
    private ConstraintLayout cl_;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_section);


        btn=findViewById(R.id.fab);
        dialog=new dialog_box_feedback();
        pbar=findViewById(R.id.progressBar);
        rv=findViewById(R.id.rv);
        feedbackList=new ArrayList<>();
        adp=new feedback_adp(this,feedbackList);
        cl_=findViewById(R.id.cl_);


        LinearLayoutManager layoutManager=new LinearLayoutManager(this);

        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adp);



        fref= FirebaseDatabase.getInstance().getReference().child("feed_back");




        fref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                feedback obj=dataSnapshot.getValue(feedback.class);
                feedbackList.add(0,obj);

                if(flag){
                    pbar.setVisibility(View.INVISIBLE);
                    cl_.setVisibility(View.INVISIBLE);
                    flag=false;
                }

                adp.notifyDataSetChanged();


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dialog.showDialog(Feedback_section.this);

            }
        });






    }
}