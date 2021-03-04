package com.example.ubitlibrary.objects.user;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.ubitlibrary.objects.book.pdfBook;

public class user implements Parcelable {

    private String uid;
    private String name;
    private String email;
    private String photo_url;
    private String role="student";
    public Boolean is_seller=false;


    public user(String uid, String name, String email, String photo_url, String role) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.photo_url = photo_url;
        this.role = role;
        this.is_seller=false;
    }

    public user(String uid, String name, String email, String photo_url) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.photo_url = photo_url;
        this.role = "student";
        this.is_seller=false;
    }



    public user(String uid, String name, String email, String photo_url, String role, Boolean is_seller) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.photo_url = photo_url;
        this.role = role;
        this.is_seller=is_seller;

    }


    public user(String uid, String name, String email, String photo_url, Boolean is_seller) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.photo_url = photo_url;
        this.role = "student";
        this.is_seller=is_seller;

    }




    public user() {
    }


    protected user(Parcel in) {
        uid = in.readString();
        name = in.readString();
        email = in.readString();
        photo_url = in.readString();
        role = in.readString();
        if(in.readInt()==1){
            is_seller=true;
        }else {
            is_seller=false;
        }
    }

    public static final Creator<user> CREATOR = new Creator<user>() {
        @Override
        public user createFromParcel(Parcel in) {
            return new user(in);
        }

        @Override
        public user[] newArray(int size) {
            return new user[size];
        }
    };


    public String getUid() {
        return uid;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }


    public String getPhoto_url() {
        return photo_url;
    }

    public String getRole() {
        return role;
    }

    public void download_book(pdfBook book , Context context){

        String url = book.getLink();

        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);



    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uid);
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeString(photo_url);
        parcel.writeString(role);
        if(is_seller){
            parcel.writeInt(1);
        }else {
            parcel.writeInt(0);
        }

    }
}
