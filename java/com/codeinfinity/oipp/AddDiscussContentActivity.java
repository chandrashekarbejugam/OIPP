package com.codeinfinity.oipp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class AddDiscussContentActivity extends AppCompatActivity {

    ImageView backBtn;

    EditText discussContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_discuss_content);


        backBtn = findViewById(R.id.backBtnAddDiscuss);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddDiscussContentActivity.this, DiscussPageActivity.class));
            }
        });

    }
}