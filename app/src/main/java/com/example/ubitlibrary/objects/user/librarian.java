package com.example.ubitlibrary.objects.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.ubitlibrary.Addbook;
import com.example.ubitlibrary.MainActivity;
import com.example.ubitlibrary.add_lib_book;
import com.example.ubitlibrary.add_pdf_book;
import com.example.ubitlibrary.objects.book.book;
import com.example.ubitlibrary.objects.book.libraryBook;
import com.example.ubitlibrary.objects.book.pdfBook;
import com.example.ubitlibrary.objects.interfaces.manage_book;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class librarian extends user implements manage_book {


    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private DatabaseReference fref= FirebaseDatabase.getInstance().getReference().child("books");
    private DatabaseReference fref1= FirebaseDatabase.getInstance().getReference().child("users");





    public librarian(String uid, String name, String email, String photo_url, String role) {
        super(uid, name, email, photo_url, role);
    }



    public librarian(String uid, String name, String email, String photo_url, String role, Boolean is_seller) {
        super(uid, name, email, photo_url, role, is_seller);
    }

    public librarian() {
    }

    public void issue_book(libraryBook book, final Context context){
        fref.child(book.getId()).child("issued").setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText(context, "Book Issued Successfully.", Toast.LENGTH_SHORT).show();
                ((Activity)context).finish();
                context.startActivity(((Activity) context).getIntent());


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(context, "Task Unsuccessful", Toast.LENGTH_SHORT).show();


            }
        });
    }

    public void returned_book(libraryBook book, final Context context){
        fref.child(book.getId()).child("issued").setValue(false).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText(context, "Book returned Successfully.", Toast.LENGTH_SHORT).show();
                ((Activity)context).finish();
                context.startActivity(((Activity) context).getIntent());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(context, "Task Unsuccessful", Toast.LENGTH_SHORT).show();


            }
        });
    }
//
//    @Override
//    public void add_book(final book book, final Context context) {
//
//        Addbook.pbar.setVisibility(View.VISIBLE);
//
//
//        fref.child(book.getId()).setValue(book).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//
//                fref1.child(mAuth.getCurrentUser().getUid()).child("added_book").child(book.getId()).setValue(book.getId()).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(context, "Book added Successfully.", Toast.LENGTH_SHORT).show();
//
//
//                        if (book instanceof libraryBook) {
//
//                            add_lib_book.name.setText("");
//                            add_lib_book.description.setText("");
//                            add_lib_book.author.setText("");
//                            add_lib_book.location.setText("");
//
//                        }else if(book instanceof pdfBook){
//
//                            add_pdf_book.name.setText("");
//                            add_pdf_book.description.setText("");
//                            add_pdf_book.author.setText("");
//                            add_pdf_book.link.setText("");
//
//                        }
//
//
//
//                        Addbook.pbar.setVisibility(View.INVISIBLE);
//
//
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(context, "Task Unsuccessful, Try Again.", Toast.LENGTH_SHORT).show();
//
//
//                        Addbook.pbar.setVisibility(View.INVISIBLE);
//
//                    }
//                });
//
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//                Toast.makeText(context, "Task Unsuccessful, Try Again.", Toast.LENGTH_SHORT).show();
//
//                Addbook.pbar.setVisibility(View.INVISIBLE);
//
//            }
//        });
//
//
//
//    }
//
//    @Override
//    public void delete_book(book book, final Context context) {
//
//
//
//        fref.child(book.getId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//
//                Toast.makeText(context, "Book deleted Successfully.", Toast.LENGTH_SHORT).show();
//                ((Activity)context).onBackPressed();
//
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//                Toast.makeText(context, "Task Unsuccessful, Try Again.", Toast.LENGTH_SHORT).show();
//
//
//            }
//        });
//
//    }
}
