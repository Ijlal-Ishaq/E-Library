package com.example.ubitlibrary;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;


public class custom_dialog_box {

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    public static Dialog dialog;

    public void showDialog(final Context context){


        mAuth= FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();



        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);


            dialog.setContentView(R.layout.dialog1);
            TextView text = (TextView) dialog.findViewById(R.id.textView4);
            text.setText(user.getDisplayName());
            TextView text1 = (TextView) dialog.findViewById(R.id.textView7);
            text1.setText(user.getEmail());
            final ImageView imageView = dialog.findViewById(R.id.circleImageView);
            Picasso.get().load(user.getPhotoUrl()).into(imageView);

            Button btn =dialog.findViewById(R.id.button);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GoogleSignInOptions gso = new GoogleSignInOptions.
                            Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                            build();

                    GoogleSignInClient googleSignInClient= GoogleSignIn.getClient(context,gso);
                    googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                mAuth.signOut();
                                context.startActivity(new Intent(context,Sign_in.class));
                                ((Activity)context).finish();
                            }
                        }
                    });
                    dialog.dismiss();

                }
            });













        dialog.setCanceledOnTouchOutside(true);



        dialog.show();



    }






}
