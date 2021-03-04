package com.example.ubitlibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Contact_us extends AppCompatActivity {

    private ImageView iv1;
    private ImageView iv3;
    private ImageView iv4;
    private ImageView iv5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);




        

        iv1=findViewById(R.id.iv1);
        iv3=findViewById(R.id.iv3);
        iv4=findViewById(R.id.iv4);
        iv5=findViewById(R.id.iv5);


        Picasso.get().load("https://lh3.googleusercontent.com/a-/AOh14GijWM3iE20KpBAmoQrb7xT5w7DCcY5rK1_V2Z9j=s96-c").placeholder(R.color.colorlight).into(iv1);
        Picasso.get().load("https://lh3.googleusercontent.com/a-/AOh14GjRLo5N0hcPLT5p56yPPix9BpMML-cA7cnDB_SsXQ=s96-c").placeholder(R.color.colorlight).into(iv3);
        Picasso.get().load("https://lh5.googleusercontent.com/-AGY7EHLqhOg/AAAAAAAAAAI/AAAAAAAAAAA/AMZuucl6uwO_2KwX-V_xfIz_GQH8qYf9-g/s96-c/photo.jpg").placeholder(R.color.colorlight).into(iv4);
        Picasso.get().load("https://lh3.googleusercontent.com/a-/AOh14GhBG3pD_R6WrIIoSJjPcinVsGmiklEDnnzDJPYlFw=s96-c").placeholder(R.color.colorlight).into(iv5);




    }

    public void contact_ijlal(View view) {

        String url = "https://api.whatsapp.com/send?phone="+"+92 3352770966";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);

    }

    public void contact_hamza(View view) {

        String url = "https://api.whatsapp.com/send?phone="+"+92 3132901290";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);

    }

    public void contact_saad(View view) {

        String url = "https://api.whatsapp.com/send?phone="+"+92 3333883122";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);

    }

    public void contact_shehryay(View view) {

        String url = "https://api.whatsapp.com/send?phone="+"+92 3350203840";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);

    }
}