package com.wanis.assessmentdesenvolvimentoandroid.Entities;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.wanis.assessmentdesenvolvimentoandroid.Database.FirebaseDB;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by munirwanis on 17/12/17.
 */

public class Users {

    private String id;
    private String name;
    private String email;
    private String password;
    private String cpf;

    public Users() {
    }

    public void save(){
        DatabaseReference dbRef = FirebaseDB.getFirebase();
        dbRef.child("usuario").child(String.valueOf(getId())).setValue(this);

    }


    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String , Object > hashMapUsuario = new HashMap<>();

        hashMapUsuario.put("id", getId());
        hashMapUsuario.put("name", getName());
        hashMapUsuario.put("email", getEmail());
        hashMapUsuario.put("password", getPassword());
        hashMapUsuario.put("cpf", getCpf());

        return hashMapUsuario;

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}