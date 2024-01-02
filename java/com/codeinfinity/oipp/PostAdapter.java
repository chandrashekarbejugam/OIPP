package com.codeinfinity.oipp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<DiscussClass> postList;
    private Map<String, CommunityUser> userMap; // Map to store user details
    private Context context;

    public PostAdapter(List<DiscussClass> postList, Map<String, CommunityUser> userMap) {
        this.postList = postList;
        this.userMap = userMap;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.community_post_layout, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        DiscussClass discussClass = postList.get(position);

        if (discussClass != null) {
            CommunityUser user = userMap.get(discussClass.getUserUid());

            // Check if views are not null before trying to access and set text
            if (holder.usernameTextView != null) {
                holder.usernameTextView.setText(user.getName());
            }
            if (holder.contentTextView != null) {
                holder.contentTextView.setText(discussClass.getContentText());
            }
            if (holder.timeCommunity != null) {
                holder.timeCommunity.setText(discussClass.getTimeStamp());
            }

            holder.usernameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ProfileViewActivity.class);
                    intent.putExtra("userUid", discussClass.getUserUid());
                    v.getContext().startActivity(intent);
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), CommunityPostSelectedActivity.class);
                    intent.putExtra("content", discussClass.getContentText());
                    intent.putExtra("userProfile", userMap.get(discussClass.getUserUid()).getProfilePic());
                    intent.putExtra("userName", userMap.get(discussClass.getUserUid()).getName());
                    v.getContext().startActivity(intent);
                }
            });

            // Load profile image using Picasso or any other image loading library
            if (holder.profileImageView != null) {
                Picasso.get().load(userMap.get(discussClass.getUserUid()).getProfilePic()).into(holder.profileImageView);
            }
        }
    }


    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {

        ImageView profileImageView;
        TextView usernameTextView;
        TextView contentTextView, timeCommunity;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImageView = itemView.findViewById(R.id.userImageCommunity);
            usernameTextView = itemView.findViewById(R.id.userNameCommunity);
            contentTextView = itemView.findViewById(R.id.contentCommunity);
            timeCommunity = itemView.findViewById(R.id.dateTextCommunity);
        }
    }
}

