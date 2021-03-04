package com.example.ubitlibrary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
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

import com.example.ubitlibrary.adapters.books_adp;
import com.example.ubitlibrary.objects.book.book;
import com.example.ubitlibrary.objects.book.libraryBook;
import com.example.ubitlibrary.objects.book.pdfBook;
import com.example.ubitlibrary.objects.book.sellBook;
import com.example.ubitlibrary.objects.user.user;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Buy_book extends AppCompatActivity {

    private EditText et;
    private DatabaseReference fref;
    private ArrayList<book> books;
    private ArrayList<book> result_books;
    private book obj;
    private ProgressBar progressBar;
    private RecyclerView rv;
    private books_adp adp;
    private boolean flag=true;
    private ConstraintLayout cl_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_book);



        et=findViewById(R.id.et);
        fref= FirebaseDatabase.getInstance().getReference().child("books");
        books=new ArrayList<>();
        result_books=new ArrayList<>();
        progressBar=findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);
        rv=findViewById(R.id.rv);
        adp=new books_adp(this,result_books);
        cl_=findViewById(R.id.cl_);

        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this);

        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adp);



        fref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if(dataSnapshot.hasChild("price")) {

                    progressBar.setVisibility(View.VISIBLE);

                    if (dataSnapshot.hasChild("link")) {

                        obj = dataSnapshot.getValue(pdfBook.class);

                    } else if (dataSnapshot.hasChild("location")) {

                        obj = dataSnapshot.getValue(libraryBook.class);

                    } else if (dataSnapshot.hasChild("price")) {

                        obj = dataSnapshot.getValue(sellBook.class);


                    }


                    books.add(obj);
                    result_books.add(0, obj);
                    adp.notifyDataSetChanged();

                    if (flag) {
                        cl_.setVisibility(View.INVISIBLE);
                        flag = false;
                    }

                    progressBar.setVisibility(View.INVISIBLE);
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
        result_books.clear();
        adp.notifyDataSetChanged();

        if(et.getText().toString().trim().equals("")){
            Toast.makeText(Buy_book.this,"Enter Book's Name.",Toast.LENGTH_SHORT).show();
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