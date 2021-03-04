package com.example.ubitlibrary;

import android.os.Bundle;

import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ubitlibrary.objects.book.pdfBook;
import com.example.ubitlibrary.objects.book.sellBook;
import com.example.ubitlibrary.objects.user.Cr;
import com.example.ubitlibrary.objects.user.librarian;
import com.example.ubitlibrary.objects.user.student;
import com.example.ubitlibrary.objects.user.teacher;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.ubitlibrary.MainActivity.current_user;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link add_sell_book#newInstance} factory method to
 * create an instance of this fragment.
 */
public class add_sell_book extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public add_sell_book() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment add_sell_book.
     */
    // TODO: Rename and change types and number of parameters
    public static add_sell_book newInstance(String param1, String param2) {
        add_sell_book fragment = new add_sell_book();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private Button add;
    public static EditText name;
    public static EditText description;
    public static EditText author;
    public static EditText contact;
    public static EditText price;
    public static DatabaseReference fref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_sell_book, container, false);


        fref= FirebaseDatabase.getInstance().getReference().child("books");

        add=v.findViewById(R.id.button3);
        name=v.findViewById(R.id.editTextTextMultiLine);
        description=v.findViewById(R.id.editTextTextMultiLine3);
        author=v.findViewById(R.id.editTextTextMultiLine4);
        contact=v.findViewById(R.id.editTextTextMultiLine5);
        price=v.findViewById(R.id.editTextTextMultiLine2);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(name.getText().toString().trim().equals("")||description.getText().toString().trim().equals("")||author.getText().toString().trim().equals("")||
                        contact.getText().toString().trim().equals("")||contact.getText().toString().trim().equals("")){

                    Toast.makeText(getActivity(), "All Fields are necessary.", Toast.LENGTH_SHORT).show();

                }else {

                    String key = fref.push().getKey();

                    sellBook obj = new sellBook(name.getText().toString().trim(), description.getText().toString().trim(), author.getText().toString().trim(),
                            key, current_user.getUid(), contact.getText().toString().trim(),price.getText().toString().trim());

                    if (current_user.getRole().equals("teacher")&&current_user.is_seller) {

                        teacher teacher=(teacher) current_user;
                        teacher.add_book(obj,getActivity());


                    } else if (current_user.getRole().equals("librarian")&&current_user.is_seller) {
                        librarian librarian=(librarian) current_user;
                        librarian.add_book(obj,getActivity());


                    } else if( current_user.getRole().equals("cr")&&current_user.is_seller){
                        Cr cr=(Cr) current_user;
                        cr.add_book(obj,getActivity());

                    } else if( current_user.getRole().equals("student")&&current_user.is_seller){
                        student student=(student) current_user;
                        student.add_book(obj,getActivity());

                    }
                }

            }
        });


        return v;

    }
}