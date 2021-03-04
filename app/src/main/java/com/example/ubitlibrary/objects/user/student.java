package com.example.ubitlibrary.objects.user;

import com.example.ubitlibrary.objects.interfaces.manage_book;

public class student extends user implements manage_book {

    public student(String uid, String name, String email, String photo_url, String role) {
        super(uid, name, email, photo_url, role);
    }

    public student(String uid, String name, String email, String photo_url, String role, Boolean is_seller) {
        super(uid, name, email, photo_url, role, is_seller);
    }

    public student(String uid, String name, String email, String photo_url) {
        super(uid, name, email, photo_url);
    }

    public student(String uid, String name, String email, String photo_url, Boolean is_seller) {
        super(uid, name, email, photo_url, is_seller);
    }

    public student() {
    }



}
