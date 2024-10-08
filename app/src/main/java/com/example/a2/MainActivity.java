package com.example.a2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private ArrayList<Task> taskList;
    private EditText inputTask;
    private Button addTaskButton;
    private SharedPreferences sharedPreferences;

    private static final String PREFS_NAME = "todoAppPrefs";
    private static final String TASK_LIST_KEY = "taskList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        recyclerView = findViewById(R.id.recycler_view);
        addTaskButton = findViewById(R.id.add_task_button);
        inputTask = findViewById(R.id.input_task);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Load saved tasks from SharedPreferences
        taskList = loadTasks();

        // Initialize the task adapter and set up RecyclerView
        taskAdapter = new TaskAdapter(taskList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);

        // Add task button listener
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = inputTask.getText().toString();
                if (!taskName.isEmpty()) {
                    Task newTask = new Task(taskName);
                    taskAdapter.addTask(newTask);
                    inputTask.setText("");  // Clear input after adding task
                    saveTasks();  // Save the task list to SharedPreferences
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a task", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to load tasks from SharedPreferences
    private ArrayList<Task> loadTasks() {
        Set<String> taskSet = sharedPreferences.getStringSet(TASK_LIST_KEY, new HashSet<String>());
        ArrayList<Task> tasks = new ArrayList<>();
        for (String taskName : taskSet) {
            tasks.add(new Task(taskName));
        }
        return tasks;
    }

    // Method to save tasks to SharedPreferences
    private void saveTasks() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> taskSet = new HashSet<>();
        for (Task task : taskList) {
            taskSet.add(task.getTaskName());  // Store task names in a Set
        }
        editor.putStringSet(TASK_LIST_KEY, taskSet);  // Save task set in SharedPreferences
        editor.apply();  // Save changes asynchronously
    }
}
