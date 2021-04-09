package com.cleanup.todoc.viewmodel;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cleanup.todoc.R;
import com.cleanup.todoc.databinding.ItemTaskBinding;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

public class TaskViewHolder extends RecyclerView.ViewHolder {
    ItemTaskBinding itemb;

    Task task;
    private ImageView mImageView;
    private TextView mTask;
    private TextView mProject;
    private ImageView mdelete;

    private TaskViewHolder(View itemView) {
        super(itemView);
        bind();
    }

    static TaskViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent,false);
        return new TaskViewHolder(view);
    }



    public void bind() {
        mImageView = itemb.imgProject;
        mTask = itemb.lblTaskName;
        mProject = itemb.lblProjectName;
        mdelete = itemb.imgDelete;

        mImageView.setImageResource((int)task.projectId);
        mTask.setText(task.getName());
        mProject.setText(task.getProject().toString());
    }
}
