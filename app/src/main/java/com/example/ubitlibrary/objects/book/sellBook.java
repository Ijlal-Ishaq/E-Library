package com.example.ubitlibrary.objects.book;

import android.os.Parcel;
import android.os.Parcelable;

public class sellBook extends book implements Parcelable {

    private String contact;
    private String price;
    private Boolean is_selected=false;

    public sellBook(String name, String description, String author, String id, String sender, String contact, String price) {
        super(name, description, author, id, sender);
        this.contact = contact;
        this.price = price;
    }


    public sellBook() {
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public static Creator<sellBook> getCREATOR() {
        return CREATOR;
    }


    public void set_selection (Boolean i){

        is_selected=i;

    }

    public boolean get_selection (){

        return(is_selected);

    }


    protected sellBook(Parcel in) {
        super.setName( in.readString());
        super.setDescription(in.readString());
        super.setAuthor(in.readString());
        super.setId(in.readString());
        super.setSender(in.readString());
        contact = in.readString();
        price = in.readString();
    }

    public static final Creator<sellBook> CREATOR = new Creator<sellBook>() {
        @Override
        public sellBook createFromParcel(Parcel in) {
            return new sellBook(in);
        }

        @Override
        public sellBook[] newArray(int size) {
            return new sellBook[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(super.getName());
        parcel.writeString(super.getDescription());
        parcel.writeString(super.getAuthor());
        parcel.writeString(super.getId());
        parcel.writeString(super.getSender());
        parcel.writeString(contact);
        parcel.writeString(price);
    }
}
