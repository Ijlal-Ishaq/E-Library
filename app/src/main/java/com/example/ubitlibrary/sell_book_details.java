package com.example.ubitlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.ubitlibrary.objects.book.pdfBook;
import com.example.ubitlibrary.objects.book.sellBook;
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

public class sell_book_details extends AppCompatActivity {



    private sellBook book;
    private TextView name;
    private TextView author;
    private TextView description;
    private TextView sender;
    private TextView price;
    private Button buy;
    private Button delete;

    private dialog_box_delete_book dialog;

    private DatabaseReference fref;
    private DatabaseReference fref1;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_book_details);



        book = (sellBook) getIntent().getParcelableExtra("book_details");

        mAuth=FirebaseAuth.getInstance();
        fref= FirebaseDatabase.getInstance().getReference().child("users");

        name=findViewById(R.id.textView32);
        author=findViewById(R.id.textView34);
        description=findViewById(R.id.textView36);
        sender=findViewById(R.id.textView38);
        price=findViewById(R.id.textView40);
        buy=findViewById(R.id.button4);
        delete=findViewById(R.id.button5);
        dialog=new dialog_box_delete_book();


        fref1=FirebaseDatabase.getInstance().getReference().child("books");

        name.setText(book.getName());
        author.setText(book.getAuthor());
        description.setText(book.getDescription());
        price.setText(String.valueOf(book.getPrice())+" Rs");


        if(mAuth.getCurrentUser().getUid().equals(book.getSender())){
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



        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "https://api.whatsapp.com/send?phone="+book.getContact().trim()+"&text="+"Hi, I wan't to buy this Book: "+"'"+book.getName()+"'";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);


            }
        });



        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dialog.showDialog(sell_book_details.this);


                dialog.btn_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(mAuth.getCurrentUser().getUid().equals(book.getSender()) && !current_user.getRole().equals("student")) {
                            if (current_user.getRole().equals("teacher")) {

                                teacher teacher = (teacher) current_user;
                                teacher.delete_book(book,sell_book_details.this);


                            } else if (current_user.getRole().equals("librarian")) {
                                librarian librarian = (librarian) current_user;
                                librarian.delete_book(book, sell_book_details.this);


                            } else if (current_user.getRole().equals("cr")) {
                                Cr cr = (Cr) current_user;
                                cr.delete_book(book, sell_book_details.this);


                            }
                        }


                    }
                });

            }
        });




    }
}