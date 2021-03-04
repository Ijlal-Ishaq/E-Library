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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ubitlibrary.adapters.books_adp;
import com.example.ubitlibrary.objects.book.book;
import com.example.ubitlibrary.objects.book.libraryBook;
import com.example.ubitlibrary.objects.book.pdfBook;
import com.example.ubitlibrary.objects.book.sellBook;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class Delete_book extends AppCompatActivity {

    private EditText et;
    private DatabaseReference fref;
    private DatabaseReference fref1;
    private ArrayList<book> books;
    private ArrayList<book> result_books;
    private book obj;
    private ProgressBar progressBar;
    private RecyclerView rv;
    private books_adp adp;
    private FirebaseAuth mAuth;
    private ConstraintLayout cl_;
    private ConstraintLayout cl_1;

    private boolean flag=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_book);






        mAuth=FirebaseAuth.getInstance();
        et=findViewById(R.id.et);
        fref= FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid());
        fref1=FirebaseDatabase.getInstance().getReference().child("books");
        books=new ArrayList<>();
        result_books=new ArrayList<>();
        cl_1=findViewById(R.id.cl_1);
        cl_=findViewById(R.id.cl_);
        progressBar=findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);
        rv=findViewById(R.id.rv);
        adp=new books_adp(this,result_books);

        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this);

        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adp);


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
            public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    fref1.child(Objects.requireNonNull(dataSnapshot.getValue(String.class))).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {


                            if(dataSnapshot1.exists()){

                            if(dataSnapshot1.hasChild("link")){
                                obj=dataSnapshot1.getValue(pdfBook.class);
                            } else if(dataSnapshot1.hasChild("location")){
                                obj = dataSnapshot1.getValue(libraryBook.class);
                            }else if(dataSnapshot1.hasChild("price")){
                                obj = dataSnapshot1.getValue(sellBook.class);
                            }

                            books.add(obj);
                            result_books.add(0,obj);
                            adp.notifyDataSetChanged();

                                if (flag) {
                                    cl_1.setVisibility(View.INVISIBLE);
                                    cl_.setVisibility(View.INVISIBLE);
                                    progressBar.setVisibility(View.INVISIBLE);
                                    flag = false;
                                }

                            }else {

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
        result_books.clear();
        adp.notifyDataSetChanged();

        if(et.getText().toString().trim().equals("")){
            Toast.makeText(Delete_book.this,"Enter Book's Name.",Toast.LENGTH_SHORT).show();
        }
        else {

            progressBar.setVisibility(View.VISIBLE);

            String search = et.getText().toString().trim().toLowerCase();

            for (book book : books) {
                if (book.getName().toLowerCase().startsWith(search)) {
                    result_books.add(book);
                    adp.notifyDataSetChanged();
                }
            }

            String[] search_words = search.split(" ");

            for (book book : books) {
                for (String word : search_words) {
                    if (book.getName().toLowerCase().contains(word)) {
                        if(!result_books.contains(book)){

                            result_books.add(book);
                            adp.notifyDataSetChanged();

                        }

                    }
                }
            }

            progressBar.setVisibility(View.INVISIBLE);

        }
    }



    @Override
    protected void onRestart() {
        super.onRestart();

        finish();
        startActivity(getIntent());

    }
}