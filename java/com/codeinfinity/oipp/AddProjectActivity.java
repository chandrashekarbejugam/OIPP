package com.codeinfinity.oipp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class AddProjectActivity extends AppCompatActivity {

    private Set<String> selectedTags = new HashSet<>();
    private List<String> teamMembers = new ArrayList<>();
    private List<Uri> selectedImages = new ArrayList<>();

    private String projectId;

    private LinearLayout containerLayout;
    private Button addMemberButton;
    private Button btnSelectImage;
    private Button btnSubmit;
    private ImageView imageViewPreview;
    private EditText editTextTitle, editTextDescription, editTextProjectDuration, editTextBudget;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);


        projectId = UUID.randomUUID().toString();

        containerLayout = findViewById(R.id.containerLayout);
        addMemberButton = findViewById(R.id.addMemberButton);
        btnSelectImage = findViewById(R.id.btnUploadImage);
        btnSubmit = findViewById(R.id.btnSubmit);
        imageViewPreview = findViewById(R.id.imageViewPreview);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextProjectDuration = findViewById(R.id.editTextDuration);
        editTextBudget = findViewById(R.id.editTextBudget);

        addMemberButton.setOnClickListener(v -> addEditText());
        btnSelectImage.setOnClickListener(v -> openImagePicker());
        btnSubmit.setOnClickListener(v -> submitProjectDetails());

        MultiAutoCompleteTextView autoCompleteTags = findViewById(R.id.autoCompleteTags);

        String[] availableTags = getResources().getStringArray(R.array.available_tags);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, availableTags);

        autoCompleteTags.setAdapter(adapter);
        autoCompleteTags.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        // Inside the onItemClick method for autoCompleteTags
        autoCompleteTags.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedTag = autoCompleteTags.getText().toString().trim();
                if (!selectedTag.isEmpty()) {
                    // Split the entered tags by commas
                    String[] tagsArray = selectedTag.split(", ");

                    // Add each tag to the selectedTags set
                    for (String tag : tagsArray) {
                        if (!tag.isEmpty()) {
                            selectedTags.add(tag);
                        }
                    }

                    // Clear the entered tag to prevent duplicates
                    autoCompleteTags.setText("");
                }
            }
        });

    }

    private void addEditText() {
        EditText editText = new EditText(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        editText.setLayoutParams(layoutParams);
        editText.setHint("Enter Team Member Name");
        containerLayout.addView(editText);
        teamMembers.add(""); // Add an empty string as a placeholder for this team member
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true); // Allow selecting multiple images
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            if (data.getClipData() != null) {
                // Multiple images selected
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    selectedImages.add(imageUri);
                }
            } else if (data.getData() != null) {
                // Single image selected
                Uri imageUri = data.getData();
                selectedImages.add(imageUri);
            }

            // Display the preview of the first selected image
            if (!selectedImages.isEmpty()) {
                imageViewPreview.setImageURI(selectedImages.get(0));
            }
        }
    }

    // Inside your submitProjectDetails() method

    // Inside your submitProjectDetails() method
    private void submitProjectDetails() {
        // Check if the required fields are not empty
        if (selectedImages.isEmpty() || editTextTitle.getText().toString().isEmpty() ||
                editTextDescription.getText().toString().isEmpty() || teamMembers.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert selectedTags Set to List
        List<String> projectTags = new ArrayList<>(selectedTags);

        // Get the team members from the dynamic EditTexts
        List<String> teamMembersList = new ArrayList<>();
        for (int i = 0; i < containerLayout.getChildCount(); i++) {
            View view = containerLayout.getChildAt(i);
            if (view instanceof EditText) {
                String teamMember = ((EditText) view).getText().toString().trim();
                if (!teamMember.isEmpty()) {
                    teamMembersList.add(teamMember);
                }
            }
        }

        // Upload images to Firebase Storage
        uploadImagesToFirebase();

        // Save other project details to Firebase Database
        saveProjectDetailsToFirebase(projectTags, teamMembersList);
    }



    private void uploadImagesToFirebase() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        for (int i = 0; i < selectedImages.size(); i++) {
            String filename = "images/" + UUID.randomUUID().toString() + ".jpg";
            StorageReference imageRef = storageRef.child(filename);

            int finalI = i;
            imageRef.putFile(selectedImages.get(i))
                    .addOnSuccessListener(taskSnapshot -> {
                        // Image uploaded successfully
                        if (finalI == selectedImages.size() - 1) {
                            // This is the last image, perform any final actions here
                            Toast.makeText(AddProjectActivity.this, "All images uploaded successfully", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        // Image upload failed
                        Toast.makeText(AddProjectActivity.this, "Image upload failed", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    // Adjusted saveProjectDetailsToFirebase() method to include tags parameter
    // Adjusted saveProjectDetailsToFirebase() method to include tags and teamMembers parameters
    // Adjusted saveProjectDetailsToFirebase() method to include tags and teamMembers parameters
    private void saveProjectDetailsToFirebase(List<String> projectTags, List<String> teamMembersList) {

        DatabaseReference projectsRef = FirebaseDatabase.getInstance().getReference("projects");



        // Convert teamMembers List to String array
        String[] projectTeamMembers = teamMembersList.toArray(new String[0]);

        // Create a Project object with details
        Project project = new Project(
                projectId,
                editTextTitle.getText().toString(),
                editTextDescription.getText().toString(),
                projectTags,  // Use the List<String> for tags
                teamMembersList,  // Use the List<String> for teamMembers
                editTextProjectDuration.getText().toString(),
                editTextBudget.getText().toString(),
                "chandu",
                "date",
                "time",
                "Android"
        );

        // Save the Project object to Firebase Database
        projectsRef.child(projectId).setValue(project)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(AddProjectActivity.this, "Project details saved successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity after successful submission
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddProjectActivity.this, "Failed to save project details", Toast.LENGTH_SHORT).show();
                });
    }


}




