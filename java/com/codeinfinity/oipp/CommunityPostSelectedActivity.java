package com.codeinfinity.oipp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CommunityPostSelectedActivity extends AppCompatActivity {

    TextView content, name;
    ImageView pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_post_selected);





        content = findViewById(R.id.mainContentSelected);
        pic = findViewById(R.id.userImageCommunityPost);

        name = findViewById(R.id.userNameCommunityPost);

        String contentText = getIntent().getStringExtra("content");
        String userName = getIntent().getStringExtra("userName");
        String userPic = getIntent().getStringExtra("userProfile");


        content.setText(contentText);
        name.setText(userName);



        Picasso.get().load(userPic).into(pic);

    }

}