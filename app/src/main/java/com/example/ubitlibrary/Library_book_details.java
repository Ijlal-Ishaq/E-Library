package com.example.ubitlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.ubitlibrary.objects.book.book;
import com.example.ubitlibrary.objects.book.libraryBook;
import com.example.ubitlibrary.objects.user.Cr;
import com.example.ubitlibrary.objects.user.librarian;
import com.example.ubitlibrary.objects.user.teacher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import static com.example.ubitlibrary.MainActivity.current_user;


public class Library_book_details extends AppCompatActivity {

    private libraryBook book;
    private TextView name;
    private TextView author;
    private TextView description;
    private TextView sender;
    private TextView location;
    private TextView status;
    private Button delete;
    private Button issue;

    private dialog_box_delete_book dialog;

    private DatabaseReference fref;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_book_details);

        book = (libraryBook) getIntent().getParcelableExtra("book_details");

        mAuth=FirebaseAuth.getInstance();
        fref= FirebaseDatabase.getInstance().getReference().child("users");

        name=findViewById(R.id.textView32);
        author=findViewById(R.id.textView34);
        description=findViewById(R.id.textView36);
        sender=findViewById(R.id.textView38);
        location=findViewById(R.id.textView40);
        status=findViewById(R.id.textView42);
        delete=findViewById(R.id.button4);
        issue=findViewById(R.id.button5);
        dialog=new dialog_box_delete_book();

        name.setText(book.getName());
        author.setText(book.getAuthor());
        description.setText(book.getDescription());
        location.setText(book.getLocation());
        if(book.isIssued()){
            status.setText("Book is Not Available");
            issue.setText("Returned");
        }else {
            status.setText("Book is Available");
            issue.setText("Issue");
        }

        if(mAuth.getCurrentUser().getUid().equals(book.getSender()) && !current_user.getRole().equals("student")){
            delete.setVisibility(View.VISIBLE);
        }else {
            ((ViewManager)delete.getParent()).removeView(delete);
        }

        if(current_user.getRole().equals("librarian")){
            issue.setVisibility(View.VISIBLE);
        }else {
            ((ViewManager)issue.getParent()).removeView(issue);
        }

        fref.child(book.getSender()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sender.setText(dataSnapshot.child("name").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



//        fref.child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                    if(Objects.requireNonNull(dataSnapshot.child("role").getValue(String.class)).equals("librarian")){
//                        issue.setVisibility(View.VISIBLE);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });




        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.showDialog(Library_book_details.this);

                dialog.btn_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(mAuth.getCurrentUser().getUid().equals(book.getSender()) && !current_user.getRole().equals("student")) {
                            if(mAuth.getCurrentUser().getUid().equals(book.getSender()) && !current_user.getRole().equals("student")) {
                                if (current_user.getRole().equals("teacher")) {

                                    teacher teacher = (teacher) current_user;
                                    teacher.delete_book(book,Library_book_details.this);


                                } else if (current_user.getRole().equals("librarian")) {
                                    librarian librarian = (librarian) current_user;
                                    librarian.delete_book(book, Library_book_details.this);


                                } else if (current_user.getRole().equals("cr")) {
                                    Cr cr = (Cr) current_user;
                                    cr.delete_book(book, Library_book_details.this);


                                }
                            }
                        }

                    }
                });

            }
        });


        issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (current_user.getRole().equals("librarian")) {
                    librarian librarian = new librarian(current_user.getUid(), current_user.getName(), current_user.getEmail(), current_user.getPhoto_url(), current_user.getRole());


                    if(!book.isIssued()){


                        book.setIssued(true);
                        getIntent().putExtra("book_details",book);
                        librarian.issue_book(book,Library_book_details.this);



                    }else if(book.isIssued()){

                        book.setIssued(false);
                        getIntent().putExtra("book_details",book);
                        librarian.returned_book(book,Library_book_details.this);

                    }



                }

            }
        });


    }
}