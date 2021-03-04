package com.example.ubitlibrary.objects.book;

import android.os.Parcel;
import android.os.Parcelable;

public class book implements Parcelable {
    private String name;
    private String description;
    private String author;
    private String id;
    private String sender;

    public book(String name, String description, String author, String id, String sender) {
        this.name = name;
        this.description = description;
        this.author = author;
        this.id = id;
        this.sender = sender;
    }

    public book() {
    }


    protected book(Parcel in) {
        name = in.readString();
        description = in.readString();
        author = in.readString();
        id = in.readString();
        sender = in.readString();
    }

    public static final Creator<book> CREATOR = new Creator<book>() {
        @Override
        public book createFromParcel(Parcel in) {
            return new book(in);
        }

        @Override
        public book[] newArray(int size) {
            return new book[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(author);
        parcel.writeString(id);
        parcel.writeString(sender);
    }
}
