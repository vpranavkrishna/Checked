package com.example.todolist.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.Utils.MainActivity;
import com.example.todolist.Model.ToDoModel;
import com.example.todolist.R;
import com.example.todolist.Utils.AddNewTask;
import com.example.todolist.Utils.DBhandler;

import java.util.List;

public class Toadapter extends RecyclerView.Adapter<Toadapter.ViewHolder> {

    private List<ToDoModel> toDoModelList;
    private MainActivity activity;
    private DBhandler db;
    public Toadapter(DBhandler db, MainActivity activity)
    {      this.db = db;
        this.activity = activity;
    }
    @Override
    @NonNull
 public ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType)
 {
      View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasklayout,parent,false);
      return (new ViewHolder(itemView));}
      public void onBindViewHolder(ViewHolder holder ,int position)
      {
          db.openDatabase();
          final ToDoModel item = toDoModelList.get(position);
          holder.task.setText(item.getTask());
          holder.task.setChecked(tobool(item.getStatus()));
          holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                  if(isChecked)
                  {
                      db.updateStatus(item.getId(),1);
                  }
                  else
                  {
                      db.updateStatus(item.getId(),0);
                  }
              }
          });
      }





    private boolean tobool(int n)
      {
          return n !=0;
      }
    @Override
    public int getItemCount() {
        return toDoModelList.size();
    }
    public Context getContext() {
        return activity;
    }

      public void setTask(List<ToDoModel> toDoModelList)
      {
          this.toDoModelList=toDoModelList;
          notifyDataSetChanged();
      }

    public void deleteitem(int position)
    {
        ToDoModel item = toDoModelList.get(position);
        db.deleteTask(item.getId());
        toDoModelList.remove(position);
        notifyItemRemoved(position);
    }

      public void edititem(int position)
      {
          ToDoModel item = toDoModelList.get(position);
      Bundle bundle = new Bundle();
      bundle.putInt("id",item.getId());
      bundle.putString("task",item.getTask());
      AddNewTask fragment  = new AddNewTask();
      fragment.setArguments(bundle);
      fragment.show(activity.getSupportFragmentManager(),AddNewTask.TAG);
      }

      public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;

        ViewHolder (View view)
        {
            super(view);
            task =  view.findViewById(R.id.checkbox);
        }

      }


}
