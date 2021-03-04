package com.example.ubitlibrary.objects.book;

import android.os.Parcel;
import android.os.Parcelable;

public class pdfBook extends book implements Parcelable {

    private String link;
    private int downloads;

    public pdfBook(String name, String description, String author, String id, String sender, String link, int downloads) {
        super(name, description, author, id, sender);
        this.link = link;
        this.downloads = downloads;
    }

    public pdfBook() {
    }

    protected pdfBook(Parcel in) {
        super.setName( in.readString());
        super.setDescription(in.readString());
        super.setAuthor(in.readString());
        super.setId(in.readString());
        super.setSender(in.readString());
        link = in.readString();
        downloads = in.readInt();
    }

    public static final Creator<pdfBook> CREATOR = new Creator<pdfBook>() {
        @Override
        public pdfBook createFromParcel(Parcel in) {
            return new pdfBook(in);
        }

        @Override
        public pdfBook[] newArray(int size) {
            return new pdfBook[size];
        }
    };

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

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
        parcel.writeString(link);
        parcel.writeInt(downloads);
    }
}
