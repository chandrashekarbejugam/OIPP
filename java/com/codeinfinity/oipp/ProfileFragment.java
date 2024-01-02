package com.codeinfinity.oipp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    TextView userName;

    Button message;

    private int normalTextColor;  // Set your default text color here
    private int selectedTextColor;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    LinearLayout details, projects;

    RelativeLayout detailsSection, projectsSection;

    View detailsLine, projectsLine;

    TextView detailsTxt, projectsTxt;

    String userNameProfile = "";

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    FirebaseUser firebaseUser = mAuth.getCurrentUser();

    String uid = firebaseUser.getUid();
    DatabaseReference proflie = database.getInstance().getReference().child("usersDetails");
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        userName = view.findViewById(R.id.userNameProfile);

        details = view.findViewById(R.id.detailsLayoutProfile);
        projects = view.findViewById(R.id.projectsLayoutProfile);

        message = view.findViewById(R.id.messageBtn);

        detailsSection = view.findViewById(R.id.detailsViewProfile);
        projectsSection = view.findViewById(R.id.projectsViewProfile);

        detailsLine = view.findViewById(R.id.detailsLineProfile);
        projectsLine = view.findViewById(R.id.projectsLineProfile);

        detailsTxt = view.findViewById(R.id.detailsTextProfile);
        projectsTxt = view.findViewById(R.id.projectTextProfile);






        detailsSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                details.setVisibility(View.VISIBLE);
                detailsLine.setVisibility(View.VISIBLE);
                projects.setVisibility(View.GONE);

                projectsLine.setVisibility(View.INVISIBLE);

//                detailsTxt.setText(getStyledTextForBold("Details", android.graphics.Typeface.NORMAL));
//                projectsTxt.setText(getStyledTextForBold("Projects", android.graphics.Typeface.BOLD));



                detailsTxt.setText(getStyledText("Details", R.color.dark_blue, R.color.ash));
                projectsTxt.setText(getStyledText("Projects", R.color.ash, R.color.dark_blue));


            }
        });

        projectsSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                details.setVisibility(View.GONE);
                detailsLine.setVisibility(View.INVISIBLE);
                projects.setVisibility(View.VISIBLE);
                projectsLine.setVisibility(View.VISIBLE);

//                detailsTxt.setText(getStyledTextForBold("Details", android.graphics.Typeface.BOLD));
//                projectsTxt.setText(getStyledTextForBold("Projects", android.graphics.Typeface.NORMAL));

                detailsTxt.setText(getStyledText("Details", R.color.ash, R.color.dark_blue));
                projectsTxt.setText(getStyledText("Projects", R.color.dark_blue, R.color.ash));


            }
        });


        proflie.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child(uid).child("name").getValue(String.class);
                userName.setText(name);
                userNameProfile = name;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MessageActivity.class);
                intent.putExtra("userNameProfile", userNameProfile);
                startActivity(intent);
            }
        });

        return view;
    }


    // Helper method to create bold text
//    private SpannableString getStyledTextForBold(String text, int typefaceStyle) {
//        SpannableString spannableString = new SpannableString(text);
//
//        // Set style as bold or normal
//        spannableString.setSpan(new StyleSpan(typefaceStyle), 0, text.length(), 0);
//
//        return spannableString;
//    }


    private SpannableStringBuilder getStyledText(String text, int textColor, int backgroundColor) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);

        // Set style as bold
        spannableStringBuilder.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, text.length(), SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Set text color
        int color = ContextCompat.getColor(getContext(), textColor);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(color), 0, text.length(), SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Set background color (if needed)
        // int bgColor = ContextCompat.getColor(this, backgroundColor);
        // spannableStringBuilder.setSpan(new BackgroundColorSpan(bgColor), 0, text.length(), SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableStringBuilder;
    }
}