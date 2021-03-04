package com.example.ubitlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.ubitlibrary.objects.user.user;

public class Sign_in extends AppCompatActivity {


    private static final String TAG = "fail";
    private static final int RC_SIGN_IN = 333;
    private FirebaseAuth mAuth;
    private GoogleSignInClient googleSignInClient;
    private ConstraintLayout signInButton;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        pb=findViewById(R.id.pb);

        mAuth=FirebaseAuth.getInstance();

        signInButton = findViewById(R.id.cl);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pb.setVisibility(View.VISIBLE);
                signIn();
            }
        });



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.

        googleSignInClient= GoogleSignIn.getClient(this, gso);







    }




    private void signIn() {

        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                pb.setVisibility(View.INVISIBLE);
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }




    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            boolean new_user=task.getResult().getAdditionalUserInfo().isNewUser();
                            if(new_user){
                                creat_new_user(user);
                            }else {
                                update_user_name(user);
                            }


                        } else {

                            Toast.makeText(Sign_in.this,"Sign in failed.",Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    private void update_user_name(FirebaseUser user) {
        DatabaseReference fref= FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());


            fref.child("name").setValue(user.getDisplayName());

            if(user.getPhotoUrl()!=null) {
                fref.child("photo_url").setValue(user.getPhotoUrl().toString());
            }

            updateUI(user);

    }

    private void creat_new_user(final FirebaseUser user) {

        DatabaseReference fref= FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
        user obj;

        if(user.getPhotoUrl()!=null) {
            obj = new user(user.getUid(), user.getDisplayName(), user.getEmail(), user.getPhotoUrl().toString(), "student");
        }else {
            obj = new user(user.getUid(), user.getDisplayName(), user.getEmail(), "null", "student");
        }

        fref.setValue(obj).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                updateUI(user);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(Sign_in.this,"Sign in failed.",Toast.LENGTH_SHORT).show();
                mAuth.signOut();

            }
        });

    }

    private void updateUI(Object o) {


        pb.setVisibility(View.INVISIBLE);
        startActivity(new Intent(Sign_in.this,MainActivity.class));
        finish();


    }


    public void onBackPressed(View view) {
        super.onBackPressed();
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();

        if(user!=null){
            updateUI(user);
        }


    }
}