package com.example.ubitlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ubitlibrary.objects.user.Cr;
import com.example.ubitlibrary.objects.user.librarian;
import com.example.ubitlibrary.objects.user.student;
import com.example.ubitlibrary.objects.user.teacher;
import com.example.ubitlibrary.objects.user.user;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private ImageView profile_pic;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference fref;
    private custom_dialog_box dialog_box;
    public static user current_user;
    private dialog_box_no_access no_access;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        mAuth=FirebaseAuth.getInstance();


        profile_pic=findViewById(R.id.iv);

        no_access=new dialog_box_no_access();


        try {
            mUser=mAuth.getCurrentUser();
            Picasso.get().load(mUser.getPhotoUrl()).placeholder(R.color.colorPrimaryDark).into(profile_pic);
        }catch (Exception e){
            //
        }


        fref= FirebaseDatabase.getInstance().getReference().child("users").child(mUser.getUid());

        dialog_box = new custom_dialog_box();

        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dialog_box.showDialog(MainActivity.this);


            }
        });





        fref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild("role")){

                    if(dataSnapshot.child("role").getValue(String.class).equals("teacher")){

                        current_user = dataSnapshot.getValue(teacher.class);

                    }else if(dataSnapshot.child("role").getValue(String.class).equals("librarian")){

                        current_user = dataSnapshot.getValue(librarian.class);

                    }else if(dataSnapshot.child("role").getValue(String.class).equals("cr"))
                    {
                        current_user = dataSnapshot.getValue(Cr.class);

                    }else if(dataSnapshot.child("role").getValue(String.class).equals("student"))
                    {
                        current_user = dataSnapshot.getValue(student.class);

                    }

                }else {

                    current_user = dataSnapshot.getValue(student.class);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });







    }

    private boolean exit=false;
    Handler handler=new Handler();


    @Override
    public void onBackPressed() {


        if(exit){
            MainActivity.super.onBackPressed();
        }
        else {

            exit=true;
            Toast.makeText(this,"Press Again to Exit App",Toast.LENGTH_SHORT).show();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit=false;
                }
            },1300);


        }




    }

    public void goto_feedback(View view) {

        startActivity(new Intent(MainActivity.this,Feedback_section.class));

    }

    public void goto_contactus(View view) {

        startActivity(new Intent(MainActivity.this,Contact_us.class));


    }

    public void goto_addbook(View view) {

        if(current_user!=null){

            if(current_user.getRole().equals("student")&&!current_user.is_seller){

                no_access.showDialog(this);

            }else if(current_user.getRole().equals("teacher")||current_user.getRole().equals("librarian")||current_user.getRole().equals("cr")||current_user.is_seller) {
                startActivity(new Intent(MainActivity.this, Addbook.class));
            }

        }else {
            Toast.makeText(MainActivity.this,"Network Problem, Try Again.",Toast.LENGTH_SHORT).show();
        }


    }

    public void goto_search_book(View view) {

        startActivity(new Intent(MainActivity.this,Search_book_section.class));

    }


    public void goto_delete_book(View view) {

        if(current_user!=null){

            if(current_user.getRole().equals("student")&&!current_user.is_seller){

                no_access.showDialog(this);


            }else if(current_user.getRole().equals("teacher")||current_user.getRole().equals("librarian")||current_user.getRole().equals("cr")||current_user.is_seller) {
                startActivity(new Intent(MainActivity.this, Delete_book.class));
            }

        }else {
            Toast.makeText(MainActivity.this,"Network Problem, Try Again.",Toast.LENGTH_SHORT).show();
        }


    }


    public void goto_professor(View view) {

        startActivity(new Intent(MainActivity.this, Search_professor.class));


    }

    public void goto_recentbooks(View view) {

        startActivity(new Intent(MainActivity.this, Recent_books.class));

    }

    public void goto_sellers(View view) {
        startActivity(new Intent(MainActivity.this, Search_seller.class));

    }

    public void goto_buy_book(View view) {

        startActivity(new Intent(MainActivity.this, Buy_book.class));


    }
}