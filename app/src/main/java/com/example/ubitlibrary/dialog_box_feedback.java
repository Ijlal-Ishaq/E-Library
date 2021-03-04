package com.example.ubitlibrary;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.ubitlibrary.objects.feedback;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class dialog_box_feedback {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference fref;

    public static Dialog dialog;

    public void showDialog(final Context context){


        mAuth= FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        fref= FirebaseDatabase.getInstance().getReference().child("feed_back");



        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);


            dialog.setContentView(R.layout.dialogbox_feedback);
            final EditText et = (EditText) dialog.findViewById(R.id.editTextTextPersonName);



            Button btn =dialog.findViewById(R.id.button2);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(!et.getText().toString().trim().equals("")){

                        String id=fref.push().getKey();
                        feedback obj=new feedback(et.getText().toString().trim(),id,user.getUid());

                        fref.child(id).setValue(obj).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(context, "Sent Successfully.", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(context, " Unsuccessful, Try Again. ", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                            }
                        });



                    }else {
                        Toast.makeText(context, "Write Something...", Toast.LENGTH_SHORT).show();
                    }





                }
            });













        dialog.setCanceledOnTouchOutside(true);



        dialog.show();



    }






}
