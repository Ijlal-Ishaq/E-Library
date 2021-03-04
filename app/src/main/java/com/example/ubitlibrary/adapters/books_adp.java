package com.example.ubitlibrary.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ubitlibrary.Library_book_details;
import com.example.ubitlibrary.Pdf_book_details;
import com.example.ubitlibrary.R;
import com.example.ubitlibrary.objects.book.book;
import com.example.ubitlibrary.objects.book.libraryBook;
import com.example.ubitlibrary.objects.book.pdfBook;
import com.example.ubitlibrary.objects.book.sellBook;
import com.example.ubitlibrary.sell_book_details;

import java.util.ArrayList;

public class books_adp extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private ArrayList<book> books;

    public books_adp(Context context, ArrayList<book> books) {
        this.context = context;
        this.books = books;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        if (viewType == 0) {
            view = LayoutInflater.from(context).inflate(R.layout.lib_book_layout, viewGroup, false);
            return new ViewHolder(view);

        } else if(viewType ==1){
            view = LayoutInflater.from(context).inflate(R.layout.pdf_book_layout, viewGroup, false);
            return new ViewHolder1(view);
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.sell_book_layout, viewGroup, false);
            return new ViewHolder3(view);

        }
    }

    @Override
    public int getItemViewType(int position) {

        if (books.get(position) instanceof libraryBook) {
            return 0;
        } else if(books.get(position) instanceof pdfBook) {
            return 1;
        } else {
            return 3;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int position) {
        if (getItemViewType(position) == 0) {


            ((ViewHolder) viewHolder).setDetails(books.get(position));
            ((ViewHolder)viewHolder).cl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ViewHolder) viewHolder).goto_details((libraryBook)books.get(position));
                }
            });


        } else if(getItemViewType(position) == 1){

            ((ViewHolder1) viewHolder).setDetails(books.get(position));
            ((ViewHolder1)viewHolder).cl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ViewHolder1) viewHolder).goto_details((pdfBook)books.get(position));
                }
            });


        }else if(getItemViewType(position) == 3) {

            ((ViewHolder3) viewHolder).setDetails(books.get(position));
            ((ViewHolder3) viewHolder).cl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ViewHolder3) viewHolder).goto_details((sellBook) books.get(position));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView author;
        private ConstraintLayout cl;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView24);
            author = itemView.findViewById(R.id.textView28);
            cl=itemView.findViewById(R.id.cl);
        }

        void goto_details(libraryBook book){
            Intent intent=new Intent(context, Library_book_details.class);
            intent.putExtra("book_details", book);
            context.startActivity(intent);
        }

        void setDetails(book book) {
            name.setText(book.getName());
            author.setText(book.getAuthor());
        }
    }

    class ViewHolder1 extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView author;
        private ConstraintLayout cl;

        ViewHolder1(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView24);
            author = itemView.findViewById(R.id.textView28);
            cl=itemView.findViewById(R.id.cl);

        }


        void goto_details(pdfBook book){
            Intent intent=new Intent(context, Pdf_book_details.class);
            intent.putExtra("book_details", book);
            context.startActivity(intent);
        }

        void setDetails(book book) {
            name.setText(book.getName());
            author.setText(book.getAuthor());
        }
    }


    class ViewHolder3 extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView author;
        private ConstraintLayout cl;

        ViewHolder3(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView24);
            author = itemView.findViewById(R.id.textView28);
            cl=itemView.findViewById(R.id.cl);

        }


        void goto_details(sellBook book){
            Intent intent=new Intent(context, sell_book_details.class);
            intent.putExtra("book_details", book);
            context.startActivity(intent);
        }

        void setDetails(book book) {
            name.setText(book.getName());
            author.setText(book.getAuthor());
        }
    }

}
