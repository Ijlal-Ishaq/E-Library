package com.example.ubitlibrary.adapters;

import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ubitlibrary.R;
import com.example.ubitlibrary.objects.feedback;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class feedback_adp extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<feedback> feedbackList;
    LayoutInflater mInflater;
    static Context context;
    private DatabaseReference fref;

    public feedback_adp(Context context, List<feedback> feedbackList) {

        this.feedbackList=feedbackList;
        this.mInflater= LayoutInflater.from(context);
        this.context=context;
        fref= FirebaseDatabase.getInstance().getReference().child("users");



    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        View mItemView = mInflater.inflate(R.layout.feedback_layout,
                viewGroup, false);
        return new feedback_adp.viewHolder(mItemView);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {

        TextView message;
        final TextView name;
        ImageView iv;

        message=viewHolder.itemView.findViewById(R.id.textView13);
        name=viewHolder.itemView.findViewById(R.id.textView21);
        iv=viewHolder.itemView.findViewById(R.id.iv7);

        message.setText(feedbackList.get(position).getMsg());


        fref.child(feedbackList.get(position).getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name.setText(dataSnapshot.child("name").getValue(String.class));
                Picasso.get().load(dataSnapshot.child("photo_url").getValue(String.class)).placeholder(R.color.colorPrimaryDark).into(iv);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }




    public  class viewHolder extends RecyclerView.ViewHolder {



        public viewHolder(@NonNull View itemView) {
            super(itemView);


        }



    }



}
