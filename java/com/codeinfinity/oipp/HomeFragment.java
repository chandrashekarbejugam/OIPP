package com.codeinfinity.oipp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private RecyclerView recyclerView;
    private ProjectsAdapter adapter;

    ImageView addBtn, search;

    ImageView notification;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("projects");
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.homeRecycler);
        addBtn = view.findViewById(R.id.addProjectBtn);
        search = view.findViewById(R.id.searchHomeProject);
        notification = view.findViewById(R.id.notificationIcon);
        adapter = new ProjectsAdapter(new ArrayList<>()); // Initialize with an empty list
        recyclerView.setAdapter(adapter);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddProjectActivity.class));
            }
        });


        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), NotificationActivity.class));
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SearchProjectsActivity.class));
            }
        });


        fetchInternshipApplications();
        return view;
    }

    private void fetchInternshipApplications() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Project> projects = new ArrayList<>();


                for (DataSnapshot applicationSnapshot : dataSnapshot.getChildren()) {


                    String projectId = applicationSnapshot.child("projectId").getValue(String.class);
                    String title = applicationSnapshot.child("title").getValue(String.class);
                    String description = applicationSnapshot.child("description").getValue(String.class);
                    List<String> tags = new ArrayList<>();//= applicationSnapshot.child("title").getValue(String.class);
                    List<String> teammembers = new ArrayList<>();//= applicationSnapshot.child("resumeURL").getValue(String.class);
                    String projectduration = applicationSnapshot.child("projectDuration").getValue(String.class);
                    String uploadDate = "Today date";//applicationSnapshot.child("uploadDate").getValue(String.class);
                    String domainUsed = applicationSnapshot.child("domainUsed").getValue(String.class);
                    String uploadTime = "Today";//applicationSnapshot.child("uploadTime").getValue(String.class);
                    String budget = applicationSnapshot.child("budget").getValue(String.class);
                    String ownerId = applicationSnapshot.child("ownerId").getValue(String.class);


                    // Create an Applicants object
                    Project project = new Project(projectId, title, description, tags, teammembers, projectduration, uploadDate, uploadTime,
                            domainUsed, budget, ownerId);

                    projects.add(project);
                }

                Collections.reverse(projects);

                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                ProjectsAdapter adapter = new ProjectsAdapter(projects);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors here
            }
        });
    }
}