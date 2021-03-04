package com.example.ubitlibrary.objects.interfaces;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.ubitlibrary.Addbook;
import com.example.ubitlibrary.add_lib_book;
import com.example.ubitlibrary.add_pdf_book;
import com.example.ubitlibrary.add_sell_book;
import com.example.ubitlibrary.objects.book.book;
import com.example.ubitlibrary.objects.book.libraryBook;
import com.example.ubitlibrary.objects.book.pdfBook;
import com.example.ubitlibrary.objects.book.sellBook;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public interface manage_book {

    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    DatabaseReference fref= FirebaseDatabase.getInstance().getReference().child("books");
    DatabaseReference fref1= FirebaseDatabase.getInstance().getReference().child("users");



    default void add_book(final book book, final Context context) {

        Addbook.pbar.setVisibility(View.VISIBLE);


        fref.child(book.getId()).setValue(book).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                fref1.child(mAuth.getCurrentUser().getUid()).child("added_book").child(book.getId()).setValue(book.getId()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Book added Successfully.", Toast.LENGTH_SHORT).show();


                        if (book instanceof libraryBook) {


                            add_lib_book.name.setText("");
                            add_lib_book.description.setText("");
                            add_lib_book.author.setText("");
                            add_lib_book.location.setText("");


                        }else if(book instanceof pdfBook){


                            add_pdf_book.name.setText("");
                            add_pdf_book.description.setText("");
                            add_pdf_book.author.setText("");
                            add_pdf_book.link.setText("");


                        }else if(book instanceof sellBook){


                            add_sell_book.name.setText("");
                            add_sell_book.description.setText("");
                            add_sell_book.author.setText("");
                            add_sell_book.contact.setText("");
                            add_sell_book.price.setText("");


                        }



                        Addbook.pbar.setVisibility(View.INVISIBLE);



                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Task Unsuccessful, Try Again.", Toast.LENGTH_SHORT).show();


                        Addbook.pbar.setVisibility(View.INVISIBLE);

                    }
                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(context, "Task Unsuccessful, Try Again.", Toast.LENGTH_SHORT).show();

                Addbook.pbar.setVisibility(View.INVISIBLE);

            }
        });



    }


    default void delete_book(book book, final Context context) {



        fref.child(book.getId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText(context, "Book deleted Successfully.", Toast.LENGTH_SHORT).show();
                ((Activity)context).onBackPressed();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(context, "Task Unsuccessful, Try Again.", Toast.LENGTH_SHORT).show();


            }
        });

    }


}
