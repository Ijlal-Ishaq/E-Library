package com.example.ubitlibrary.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ubitlibrary.Pdf_book_details;
import com.example.ubitlibrary.Professor_profile;
import com.example.ubitlibrary.R;
import com.example.ubitlibrary.Seller_profile;
import com.example.ubitlibrary.objects.book.book;
import com.example.ubitlibrary.objects.book.libraryBook;
import com.example.ubitlibrary.objects.book.pdfBook;
import com.example.ubitlibrary.objects.user.user;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class profile_adp extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private ArrayList<user> users;
    private Boolean seller=false;

    public profile_adp(Context context, ArrayList<user> users) {
        this.context = context;
        this.users = users;
    }

    public profile_adp(Context context, ArrayList<user> users,Boolean seller) {
        this.context = context;
        this.users = users;
        this.seller=seller;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.user_profile_layout, viewGroup, false);
        return new ViewHolder(view);

    }



    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int position) {

            ((ViewHolder) viewHolder).setDetails(users.get(position));

            ((ViewHolder)viewHolder).cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((ViewHolder) viewHolder).goto_details(users.get(position));
            }

            });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView photo;
        private ConstraintLayout cl;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView29);
            photo = itemView.findViewById(R.id.iv2);
            cl=itemView.findViewById(R.id.cl);
        }

        void goto_details(user user){

            if(!seller){
                Intent intent=new Intent(context, Professor_profile.class);
                intent.putExtra("user_details", user);
                context.startActivity(intent);
            }
            else {

                Intent intent = new Intent(context, Seller_profile.class);
                intent.putExtra("user_details", user);
                context.startActivity(intent);
            }

        }

        void setDetails(user user) {

            if(!seller) {

                if (user.getRole().equals("cr")) {

                    name.setText(user.getName() + " (CR)");
                    Picasso.get().load(user.getPhoto_url()).placeholder(R.color.colorPrimary).into(photo);


                } else if (user.getRole().equals("librarian")) {

                    name.setText(user.getName() + " (LIB)");
                    Picasso.get().load(user.getPhoto_url()).placeholder(R.color.colorPrimary).into(photo);


                } else if (user.getRole().equals("teacher")) {

                    name.setText(user.getName() + " (PROF)");
                    Picasso.get().load(user.getPhoto_url()).placeholder(R.color.colorPrimary).into(photo);


                }
            }else {

                name.setText(user.getName());
                Picasso.get().load(user.getPhoto_url()).placeholder(R.color.colorPrimary).into(photo);

            }
        }
    }



}

