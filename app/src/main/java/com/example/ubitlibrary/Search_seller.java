package com.example.ubitlibrary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ubitlibrary.adapters.profile_adp;
import com.example.ubitlibrary.objects.user.user;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Search_seller extends AppCompatActivity {



    private EditText et;
    private DatabaseReference fref;
    private ArrayList<user> sellers;
    private ArrayList<user> result_sellers;
    private user obj;
    private ProgressBar progressBar;
    private RecyclerView rv;
    private profile_adp adp;
    private boolean flag=true;
    private ConstraintLayout cl_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_seller);



        et=findViewById(R.id.et);
        fref= FirebaseDatabase.getInstance().getReference().child("users");
        sellers=new ArrayList<>();
        result_sellers=new ArrayList<>();
        progressBar=findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);
        cl_=findViewById(R.id.cl_);

        rv=findViewById(R.id.rv);
        adp=new profile_adp(this,result_sellers,true);

        RecyclerView.LayoutManager layoutManager= new GridLayoutManager(this,3);

        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adp);


        fref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if(dataSnapshot.hasChild("is_seller")) {

                    if (dataSnapshot.child("is_seller").getValue(Boolean.class)) {

                        sellers.add(dataSnapshot.getValue(user.class));
                        result_sellers.add(dataSnapshot.getValue(user.class));
                        adp.notifyDataSetChanged();

                        if (flag) {
                            cl_.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            flag = false;
                        }


                    }
                }
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

        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

               searching();

                return false;
            }
        });


        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                searching();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }


    void searching(){
        result_sellers.clear();
        adp.notifyDataSetChanged();

        if(et.getText().toString().trim().equals("")){
            Toast.makeText(Search_seller.this,"Enter Professor's Name.",Toast.LENGTH_SHORT).show();
        }
        else {

            progressBar.setVisibility(View.VISIBLE);

            String search = et.getText().toString().trim().toLowerCase();

            for (user user : sellers) {
                if (user.getName().toLowerCase().startsWith(search)) {
                    result_sellers.add(user);
                    adp.notifyDataSetChanged();
                }
            }

            String[] search_words = search.split(" ");

            for (user user : sellers) {
                for (String word : search_words) {
                    if (user.getName().toLowerCase().contains(word)) {
                        if(!result_sellers.contains(user)){

                            result_sellers.add(user);
                            adp.notifyDataSetChanged();

                        }

                    }
                }
            }

            progressBar.setVisibility(View.INVISIBLE);

        }
    }

}