package com.codeinfinity.oipp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommunityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommunityFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ImageView addPost;

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;

    ProgressBar progressBar;
    private List<DiscussClass> postList;
    private Map<String, CommunityUser> userMap;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CommunityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CommunityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CommunityFragment newInstance(String param1, String param2) {
        CommunityFragment fragment = new CommunityFragment();
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
        View view = inflater.inflate(R.layout.fragment_community, container, false);

        addPost = view.findViewById(R.id.addPostCommunity);

        

        recyclerView = view.findViewById(R.id.recyclerCommunity);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        postList = new ArrayList<>();
        userMap = new HashMap<>();
        postAdapter = new PostAdapter(postList, userMap);
        recyclerView.setAdapter(postAdapter);



        // Fetch user details first
        fetchUserDetails();


        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PostContentCommunityActivity.class));
            }
        });

        return view;
    }

    private void fetchUserDetails() {

        

        DatabaseReference usersReference = FirebaseDatabase.getInstance().getReference("userProfile");
        usersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userMap.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CommunityUser communityUser = snapshot.getValue(CommunityUser.class);
                    userMap.put(snapshot.getKey(), communityUser);
                }
                // After fetching user details, fetch posts
                fetchPosts();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                
                Toast.makeText(getContext(), "Error fetching user details: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchPosts() {
        DatabaseReference postsReference = FirebaseDatabase.getInstance().getReference("community");
        postsReference.orderByChild("timestamp").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DiscussClass discussClass = snapshot.getValue(DiscussClass.class);
                    if (discussClass != null){
                        postList.add(discussClass);
                    }else{
                        Toast.makeText(getContext(), "please wait!", Toast.LENGTH_SHORT).show();
                    }
                    
                }

                // Reverse the order of postList to show recent posts at the top
                Collections.reverse(postList);

                postAdapter.notifyDataSetChanged();
                
            }


        @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                
                Toast.makeText(getContext(), "Error Fetching Posts", Toast.LENGTH_SHORT).show();
                Log.e("Firebase", "Error fetching posts: " + databaseError.getMessage());
            }
        });
    }

}