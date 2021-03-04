package com.example.ubitlibrary.objects;

import com.google.firebase.auth.FirebaseUser;

public class feedback {

    private String msg;
    private String key;
    private String uid;


    public feedback() {
    }


    public feedback(String msg, String key, String uid) {
        this.msg = msg;
        this.key = key;
        this.uid = uid;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
