package com.example.a2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private ArrayList<Task> taskList;

    // Constructor for TaskAdapter
    public TaskAdapter(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    // ViewHolder class for RecyclerView
    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public TextView taskNameTextView;

        public TaskViewHolder(View itemView) {
            super(itemView);
            taskNameTextView = itemView.findViewById(R.id.task_name_text);
        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the task item layout
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        // Bind the task name to the TextView
        Task currentTask = taskList.get(position);
        holder.taskNameTextView.setText(currentTask.getTaskName());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    // Method to update the list of tasks
    public void addTask(Task task) {
        taskList.add(task);
        notifyDataSetChanged();  // Notify the adapter that the data set has changed
    }
}
