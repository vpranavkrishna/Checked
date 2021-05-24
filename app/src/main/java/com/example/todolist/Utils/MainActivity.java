package com.example.todolist.Utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.todolist.Adapter.Toadapter;
import com.example.todolist.Model.ToDoModel;
import com.example.todolist.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements DialogCloseListener {

    private RecyclerView recyclerView;
    private Toadapter tasksAdapter;
    private FloatingActionButton fab;
    private List<ToDoModel> taskList;
    private DBhandler db;
    ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        db = new DBhandler(this);
        db.openDatabase();
        recyclerView = findViewById(R.id.tasksRecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new Toadapter(db,this);
        recyclerView.setAdapter(tasksAdapter);

        itemTouchHelper = new ItemTouchHelper(new Recycleritemtouchhelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        fab = findViewById(R.id.fab);


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