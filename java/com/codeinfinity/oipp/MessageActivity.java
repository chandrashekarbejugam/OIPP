package com.codeinfinity.oipp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {

    TextView nameUser;

    private FirebaseManager firebaseManager;
    private EditText messageEditText;

    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    String userUid = firebaseUser.getUid();
    private Button sendButton;
    private RecyclerView recyclerView;
    private MessageAdapter adapter = new MessageAdapter(new ArrayList<>());



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);


        nameUser = findViewById(R.id.userNameChat);

        String name = getIntent().getStringExtra("userNameProfile");
        nameUser.setText(name);




        firebaseManager = new FirebaseManager();
        messageEditText = findViewById(R.id.editTextChat);
        sendButton = findViewById(R.id.sendBtnMessage);
        recyclerView = findViewById(R.id.recyclerView);


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageEditText.getText().toString().trim();
                if (!messageText.isEmpty()) {
                    Message message = new Message(messageText, userUid, System.currentTimeMillis());
                    firebaseManager.sendMessage(message);
                    messageEditText.setText("");
                }
            }
        });



        firebaseManager.getDatabaseReference().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Message message = snapshot.getValue(Message.class);
                if (message != null) {
                    adapter.addMessage(message);
                    recyclerView.scrollToPosition(adapter.getItemCount() - 1);
                }
            }

            @Override
            public void onChildChanged(@androidx.annotation.NonNull DataSnapshot snapshot, @androidx.annotation.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@androidx.annotation.NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@androidx.annotation.NonNull DataSnapshot snapshot, @androidx.annotation.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {

            }

            // Implement other ChildEventListener methods as needed
        });

    }

}