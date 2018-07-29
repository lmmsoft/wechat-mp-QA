package com.github.binarywang.demo.wx.mp;

import java.io.Serializable;

class User implements Serializable {
    int id;
    String name;
    String email;

    User() {
    }

    User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
