package com.example.ubitlibrary.objects.book;

        import android.os.Parcel;
        import android.os.Parcelable;

public class libraryBook extends book implements Parcelable {

    private String location;
    private boolean issued;


    public libraryBook(String name, String description, String author, String id, String sender, String location, boolean issued) {
        super(name, description, author, id, sender);
        this.location = location;
        this.issued = issued;
    }


    public libraryBook() {
    }

    protected libraryBook(Parcel in) {
        super.setName( in.readString());
        super.setDescription(in.readString());
        super.setAuthor(in.readString());
        super.setId(in.readString());
        super.setSender(in.readString());
        location = in.readString();
        issued = in.readByte() != 0;
    }

    public static final Creator<libraryBook> CREATOR = new Creator<libraryBook>() {
        @Override
        public libraryBook createFromParcel(Parcel in) {
            return new libraryBook(in);
        }

        @Override
        public libraryBook[] newArray(int size) {
            return new libraryBook[size];
        }
    };

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isIssued() {
        return issued;
    }

    public void setIssued(boolean issued) {
        this.issued = issued;
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
        parcel.writeString(location);
        parcel.writeByte((byte) (issued ? 1 : 0));
    }
}
