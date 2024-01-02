package com.codeinfinity.oipp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class UserDetailsActivity extends AppCompatActivity {

    ImageView profileUser;

    EditText username;

    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        profileUser = findViewById(R.id.profilePicUserDetails);

        username = findViewById(R.id.userNameUserDetails);

    }
}