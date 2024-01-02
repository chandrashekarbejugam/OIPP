package com.codeinfinity.oipp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DiscussPageActivity extends AppCompatActivity {

    RelativeLayout addThought;


    TextView projectname;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discuss_page);

        addThought = findViewById(R.id.addThoughtDiscuss);
        backBtn = findViewById(R.id.backBtnDiscuss);

        projectname = findViewById(R.id.projectTitleDiscuss);

        String title = getIntent().getStringExtra("projectTitle");

        projectname.setText(title);

        addThought.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DiscussPageActivity.this, AddDiscussContentActivity.class));
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DiscussPageActivity.this, ProjectDetailsActivity.class));
            }
        });
    }
}