package com.codeinfinity.oipp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.MyViewHolder> {

    private List<Project> arrayLists;


    public ProjectsAdapter(List<Project> arrayLists) {
        this.arrayLists = arrayLists;
    }

    @NonNull
    @Override
    public ProjectsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.home_display_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectsAdapter.MyViewHolder holder, int position) {

        Project project = arrayLists.get(position);


        holder.projectTitle.setText(project.getTitle());
        holder.uploadDate.setText(project.getUploadDate());
        holder.uploadTime.setText(project.getUploadTime());

        holder.domainUsed.setText(project.getDomainUsed());

        //        Glide.from()
        //           .into(projectImage);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProjectDetailsActivity.class);

                intent.putExtra("project_title", project.getTitle());
                intent.putExtra("project_domain", project.getDomainUsed());

                v.getContext().startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return arrayLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView projectTitle, uploadDate, uploadTime, domainUsed;
        ImageView projectImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            projectTitle = itemView.findViewById(R.id.projectTitleCard);
            uploadDate = itemView.findViewById(R.id.uploadDateCard);
            uploadTime = itemView.findViewById(R.id.uploadTimeCard);
            domainUsed = itemView.findViewById(R.id.domainProject);
            projectImage = itemView.findViewById(R.id.imageProject);


        }
    }
}
