package com.example.ubitlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.ubitlibrary.objects.book.libraryBook;
import com.example.ubitlibrary.objects.book.pdfBook;
import com.example.ubitlibrary.objects.user.Cr;
import com.example.ubitlibrary.objects.user.librarian;
import com.example.ubitlibrary.objects.user.teacher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.ubitlibrary.MainActivity.current_user;

public class Pdf_book_details extends AppCompatActivity {


    private pdfBook book;
    private TextView name;
    private TextView author;
    private TextView description;
    private TextView sender;
    private TextView downloads;
    private Button download;
    private Button delete;

    private DatabaseReference fref;
    private DatabaseReference fref1;
    private FirebaseAuth mAuth;
    private dialog_box_delete_book dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_book_details);

        dialog=new dialog_box_delete_book();

        book = (pdfBook) getIntent().getParcelableExtra("book_details");

        mAuth=FirebaseAuth.getInstance();
        fref= FirebaseDatabase.getInstance().getReference().child("users");

        name=findViewById(R.id.textView32);
        author=findViewById(R.id.textView34);
        description=findViewById(R.id.textView36);
        sender=findViewById(R.id.textView38);
        downloads=findViewById(R.id.textView40);
        download=findViewById(R.id.button4);
        delete=findViewById(R.id.button5);


        fref1=FirebaseDatabase.getInstance().getReference().child("books");

        name.setText(book.getName());
        author.setText(book.getAuthor());
        description.setText(book.getDescription());
        downloads.setText(String.valueOf(book.getDownloads())+" downloads");


        if(mAuth.getCurrentUser().getUid().equals(book.getSender()) && !current_user.getRole().equals("student")){
            delete.setVisibility(View.VISIBLE);

        }else{
            ((ViewManager)delete.getParent()).removeView(delete);
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



        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fref.child(mAuth.getCurrentUser().getUid()).child("recent_books").push().setValue(book.getId());
                fref1.child(book.getId()).child("downloads").setValue(book.getDownloads()+1);
                MainActivity.current_user.download_book(book,Pdf_book_details.this);

            }
        });



        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.showDialog(Pdf_book_details.this);

                dialog.btn_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(mAuth.getCurrentUser().getUid().equals(book.getSender()) && !current_user.getRole().equals("student")) {
                            if (current_user.getRole().equals("teacher")) {

                                teacher teacher = (teacher) current_user;
                                teacher.delete_book(book,Pdf_book_details.this);


                            } else if (current_user.getRole().equals("librarian")) {
                                librarian librarian = (librarian) current_user;
                                librarian.delete_book(book, Pdf_book_details.this);


                            } else if (current_user.getRole().equals("cr")) {
                                Cr cr = (Cr) current_user;
                                cr.delete_book(book, Pdf_book_details.this);


                            }
                        }


                    }
                });

            }
        });




    }
}