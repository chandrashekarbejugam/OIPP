package com.codeinfinity.oipp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProjectDetailsActivity extends AppCompatActivity {

    String title, domain;

    TextView titleTxt, domainTxt, showHideText;

    LinearLayout hideLayoutProject, discussPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);

        title = getIntent().getStringExtra("project_title");
        domain = getIntent().getStringExtra("project_domain");

        titleTxt = findViewById(R.id.projectTitle);
        domainTxt = findViewById(R.id.projectDescription);
        hideLayoutProject = findViewById(R.id.hideLayout);
        discussPage = findViewById(R.id.discussLayout);
        showHideText = findViewById(R.id.textClickToShow);

        titleTxt.setText(title);
        domainTxt.setText(domain);

        showHideText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHideText.setVisibility(View.GONE);
                hideLayoutProject.setVisibility(View.VISIBLE);
            }
        });

        discussPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProjectDetailsActivity.this, DiscussPageActivity.class);
                intent.putExtra("projectTitle", title);
                startActivity(intent);
            }
        });

    }
}