package com.example.ubitlibrary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ubitlibrary.adapters.books_adp;
import com.example.ubitlibrary.adapters.books_s_adp;
import com.example.ubitlibrary.objects.book.book;
import com.example.ubitlibrary.objects.book.libraryBook;
import com.example.ubitlibrary.objects.book.pdfBook;
import com.example.ubitlibrary.objects.book.sellBook;
import com.example.ubitlibrary.objects.user.user;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class Seller_profile extends AppCompatActivity {


    private ImageView iv;
    private TextView tv;
    private DatabaseReference fref;
    private DatabaseReference fref1;
    private ArrayList<sellBook> books;
    private ArrayList<sellBook> result_books;
    private sellBook obj;
    private ConstraintLayout cl_;
    private ConstraintLayout cl_1;
    private ConstraintLayout cl_3;
    private Button btn;
    private ProgressBar progressBar;
    private RecyclerView rv;
    private books_s_adp adp;
    private FirebaseAuth mAuth;
    private boolean flag=true;
    private dialog_box_select_mul dialog;
    static int cnt = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_profile);
        user professor=getIntent().getParcelableExtra("user_details");

        iv=findViewById(R.id.iv6);
        tv=findViewById(R.id.textView16);
        mAuth=FirebaseAuth.getInstance();
        fref= FirebaseDatabase.getInstance().getReference().child("users").child(professor.getUid());
        fref1=FirebaseDatabase.getInstance().getReference().child("books");
        books=new ArrayList<>();
        result_books=new ArrayList<>();
        cl_=findViewById(R.id.cl_);
        cl_1=findViewById(R.id.cl_1);
        cl_3=findViewById(R.id.cl_3);
        btn=findViewById(R.id.btn);

        if(cnt==0) {
            dialog = new dialog_box_select_mul();
            dialog.showDialog(this);
            cnt++;
        }
        progressBar=findViewById(R.id.pb);
        progressBar.setVisibility(View.VISIBLE);
        rv=findViewById(R.id.rv);
        adp=new books_s_adp(this,result_books,cl_3,btn);

        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this);

        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adp);

        if (professor != null) {
            Picasso.get().load(professor.getPhoto_url()).placeholder(R.color.colorPrimary).into(iv);

            tv.setText(professor.getName());

//            if(professor.getRole().equals("teacher")){
//
//                tv.setText("(PROF) "+professor.getName());
//
//            }
//            else if(professor.getRole().equals("librarian")){
//
//                tv.setText("(LIB) "+professor.getName());
//
//            }
//            else if(professor.getRole().equals("cr")){
//
//                tv.setText("(CR) "+professor.getName());
//
//            }else if(professor.getRole().equals("student")){
//
//                tv.setText("(STU) "+professor.getName());
//
//            }

        }


        fref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.hasChild("added_book"))
                {
                    cl_1.setVisibility(View.INVISIBLE);
                    cl_.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        fref.child("added_book").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    fref1.child(Objects.requireNonNull(dataSnapshot.getValue(String.class))).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {

                            if (dataSnapshot1.exists()) {

                                if (dataSnapshot1.hasChild("price")) {
                                    obj = dataSnapshot1.getValue(sellBook.class);


                                    books.add(obj);
                                    result_books.add(obj);
                                    adp.notifyDataSetChanged();


                                    if (flag) {
                                        cl_1.setVisibility(View.INVISIBLE);
                                        cl_.setVisibility(View.INVISIBLE);
                                        progressBar.setVisibility(View.INVISIBLE);
                                        flag = false;
                                    }



                                }

                            }
                             else {

                                if (flag) {
                                    cl_1.setVisibility(View.INVISIBLE);
                                    cl_.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.INVISIBLE);

                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                String id=dataSnapshot.getValue(String.class);

                for (book book:books){
                    if(book.getId().equals(id)){
                        books.remove(book);
                    }
                }


            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }

    @Override
    protected void onRestart() {
        super.onRestart();

        finish();
        startActivity(getIntent());

    }

}