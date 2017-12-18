package com.wanis.assessmentdesenvolvimentoandroid.Database;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by munirwanis on 17/12/17.
 */

public class FirebaseDB {

    private static DatabaseReference dbRef;
    private static FirebaseAuth auth;

    public static DatabaseReference getFirebase() {
        if (dbRef == null) {
            dbRef = FirebaseDatabase.getInstance().getReference();
        }
        return dbRef;
    }

    public static FirebaseAuth getFirebaseAuth() {
        if (auth == null) {
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }

}
