package com.codeinfinity.oipp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class PostContentCommunityActivity extends AppCompatActivity {

    EditText content;

    Button post;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference community = database.getInstance().getReference().child("community");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_content_community);

        content = findViewById(R.id.postContentEditText);
        post = findViewById(R.id.postBtnComunity);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nextContent = content.getText().toString();
                if (nextContent.isEmpty()){
                    Toast.makeText(PostContentCommunityActivity.this, "Enter something to Post", Toast.LENGTH_SHORT).show();
                }else{
                    uploadPost();
                }
                // Call the method to upload the content to Firebase

            }
        });

    }


    private void uploadPost() {
        // Get current user UID from Firebase Auth
        String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Get current timestamp
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                .format(new Date());

        // Get content text
        String contentText = content.getText().toString().trim();

        String randomText = generateRandomText(6);

        String communityPostId = "OIPPPO" + randomText;

        // Create a Post object
        DiscussClass post = new DiscussClass(userUid, contentText, timestamp, communityPostId);

        // Upload the post to Firebase Realtime Database
        community.child(communityPostId).setValue(post);

        // Clear the input field
        content.setText("");

        finish();
        Toast.makeText(this, "Post uploaded successfully", Toast.LENGTH_SHORT).show();
    }

    public static String generateRandomText(int length) {
        // Define the characters you want to include in the random text
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        // Create a Random object
        Random random = new Random();

        // Initialize a StringBuilder to build the random text
        StringBuilder randomTextBuilder = new StringBuilder(length);

        // Generate random characters and append them to the StringBuilder
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            randomTextBuilder.append(randomChar);
        }

        // Convert the StringBuilder to a String and return
        return randomTextBuilder.toString();
    }
}