package com.example.ubitlibrary.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class books_s_adp extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private ArrayList<sellBook> books;
    private int items_selected=0;
    public static ConstraintLayout cl_1;
    public static Button btn;
    private String contact="0";


    public books_s_adp(Context context, ArrayList<sellBook> books,ConstraintLayout cl_1,Button btn) {
        this.context = context;
        this.books = books;
        this.cl_1 = cl_1;
        this.btn = btn;




        this.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                contact=books.get(0).getContact();
                String url = "https://api.whatsapp.com/send?phone="+contact+"&text="+"Hi, I wan't to buy these Books: ";

                for(sellBook book : books){

                    if(book.get_selection()) {
                        url += "\n->" + book.getName();
                    }
                }

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);

            }
        });

    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;

            view = LayoutInflater.from(context).inflate(R.layout.sell_book_layout, viewGroup, false);
            return new ViewHolder3(view);


    }



    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int position) {

            ((ViewHolder3) viewHolder).setDetails(books.get(position));
            ((ViewHolder3) viewHolder).cl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(books.get(position).get_selection()){

                        ((ViewHolder3) viewHolder).set_selected((sellBook) books.get(position));

                    }else{
                    ((ViewHolder3) viewHolder).goto_details((sellBook) books.get(position));
                    }
                }
            });

        ((ViewHolder3)viewHolder).cl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                ((ViewHolder3) viewHolder).set_selected((sellBook) books.get(position));

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return books.size();
    }


    class ViewHolder3 extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView author;
        private ConstraintLayout cl;
        private ConstraintLayout cl_3;

        ViewHolder3(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView24);
            author = itemView.findViewById(R.id.textView28);
            cl=itemView.findViewById(R.id.cl);
            cl_3=itemView.findViewById(R.id.cl_3);
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

        void set_selected(sellBook book){



            if(book.get_selection()){

                book.set_selection(false);
                cl_3.setVisibility(View.INVISIBLE);
                items_selected--;

            }else {

                book.set_selection(true);
                cl_3.setVisibility(View.VISIBLE);
                items_selected++;

            }

            if(items_selected > 0){
                cl_1.setVisibility(View.VISIBLE);
                btn.setText("BUY ALL ("+items_selected+")");
            }else {
                cl_1.setVisibility(View.INVISIBLE);
            }
        }





    }

}
