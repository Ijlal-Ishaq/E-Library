package com.example.ubitlibrary;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;


public class dialog_box_select_mul {



    public static Dialog dialog;

    public void showDialog(final Context context){





        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);


            dialog.setContentView(R.layout.dialogbox_select_mul);



            Button btn =dialog.findViewById(R.id.button2);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });













        dialog.setCanceledOnTouchOutside(true);



        dialog.show();



    }






}
