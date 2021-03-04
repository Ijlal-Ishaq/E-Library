package com.example.ubitlibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.app.Fragment;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.example.ubitlibrary.MainActivity.current_user;

public class Addbook extends AppCompatActivity {


    private Spinner spinner;
    private List<String> list;
    private Fragment f1;
    private Fragment f2;
    private Fragment f3;
    public static ProgressBar pbar;
    public dialog_box_no_access_book dialog_box;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbook);




        pbar=findViewById(R.id.progressBar4);

        spinner=findViewById(R.id.spinner);

        dialog_box=new dialog_box_no_access_book();

        list=new ArrayList<>();





        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,R.layout.spinner_text,R.id.tv, list);





        spinner.setAdapter(adp);

        f1= new add_lib_book();
        f2= new add_pdf_book();
        f3=new add_sell_book();


        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(current_user.getRole().equals("teacher")||current_user.getRole().equals("cr")){


            list.add("PDF Book");
            list.add("Library Book");
            list.add("SELL Book");

            fragmentTransaction.replace(R.id.framelayout, f2 );

        }else if(current_user.getRole().equals("librarian")){



            list.add("Library Book");
            list.add("PDF Book");
            list.add("SELL Book");

            fragmentTransaction.replace(R.id.framelayout, f1 );


        }else if(current_user.is_seller){



            list.add("SELL Book");
            list.add("Library Book");
            list.add("PDF Book");

            fragmentTransaction.replace(R.id.framelayout, f3 );

        }

        spinner.setAdapter(adp);


        fragmentTransaction.commit();





        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(current_user.getRole().equals("teacher")||current_user.getRole().equals("cr")){

                    if(i==0){

                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.framelayout, f2);
//                          fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();


                    }
                    else if(i==2 && current_user.is_seller) {

                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.framelayout, f3);
//                          fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();


                    }else {

                        spinner.setSelection(0);
                        dialog_box.showDialog(Addbook.this);

                    }



                }

                else if(current_user.getRole().equals("librarian")){

                    if(i==0){

                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.framelayout, f1);
//                          fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();

                    }else if(i==1) {

                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.framelayout, f2);
//                      fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                    else if(i==2 && current_user.is_seller) {

                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.framelayout, f3);
//                      fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                    }
                    else {

                        spinner.setSelection(0);
                        dialog_box.showDialog(Addbook.this);

                    }



                }else if(current_user.is_seller){

                    if(i==0){


                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.framelayout, f3);
//                      fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();


                    }else {

                        spinner.setSelection(0);
                        dialog_box.showDialog(Addbook.this);

                    }


                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




    }


}