package com.example.ubitlibrary;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;


public class dialog_box_delete_book {



    public static Dialog dialog;
    public Button btn_yes;
    public Button btn_no;

    public void showDialog(final Context context){





        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);


            dialog.setContentView(R.layout.dialogbox_delete_book);



            btn_yes =dialog.findViewById(R.id.button2);
            btn_no =dialog.findViewById(R.id.button6);

            btn_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });













            dialog.setCanceledOnTouchOutside(true);



             dialog.show();



    }






}
