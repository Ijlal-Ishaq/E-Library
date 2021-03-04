package com.example.ubitlibrary.objects.user;

import com.example.ubitlibrary.objects.interfaces.manage_book;

public class Cr extends student implements manage_book {

    public Cr(String uid, String name, String email, String photo_url, String role) {
        super(uid, name, email, photo_url, role);
    }

    public Cr(String uid, String name, String email, String photo_url, String role, Boolean is_seller) {
        super(uid, name, email, photo_url, role, is_seller);
    }

    public Cr() {
    }
}
