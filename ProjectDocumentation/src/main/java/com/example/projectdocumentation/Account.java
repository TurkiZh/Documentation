package com.example.projectdocumentation;

import java.io.Serializable;
import java.util.List;

public class Account implements Serializable {

    private String username;
    private String password;
    private boolean admin;

    public Account(String username,String password, boolean admin){
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAdmin(boolean admin) { this.admin = admin; }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean getAdmin(){ return admin; }

}
