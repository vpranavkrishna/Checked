package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.todolist.Adapter.Toadapter;
import com.example.todolist.Model.ToDoModel;
import com.example.todolist.Utils.AddNewTask;
import com.example.todolist.Utils.DBhandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener {

    private RecyclerView recyclerView;
    private Toadapter tasksAdapter;
    private FloatingActionButton fab;
    private List<ToDoModel> taskList;
    private DBhandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        db = new DBhandler(this);
        taskList = new ArrayList<>();
        recyclerView = findViewById(R.id.tasksRecyclerview);
        fab = findViewById(R.id.fab);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new Toadapter(db,this);
        recyclerView.setAdapter(tasksAdapter);
        taskList = db.getAll();
        Collections.reverse(taskList);
        tasksAdapter.setTask(taskList);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newTask().show( getSupportFragmentManager(),AddNewTask.TAG);
            }
        });

    }
    @Override
    public void handleDialogClose(DialogInterface dialogInterface)
    {
        taskList = db.getAll();
        Collections.reverse(taskList);
        tasksAdapter.setTask(taskList);
        tasksAdapter.notifyDataSetChanged();
    }

}