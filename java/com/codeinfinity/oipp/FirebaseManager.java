package com.codeinfinity.oipp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

// FirebaseManager.java
public class FirebaseManager {
    private DatabaseReference databaseReference;

    public FirebaseManager() {
        this.databaseReference = FirebaseDatabase.getInstance().getReference("messages");
    }

    public void sendMessage(Message message) {
        databaseReference.push().setValue(message);
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }
}

