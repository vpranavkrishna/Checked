package com.example.todolist.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.DefaultDatabaseErrorHandler;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.example.todolist.DialogCloseListener;
import com.example.todolist.Model.ToDoModel;
import com.example.todolist.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Collection;

import static android.view.WindowManager.*;

public class AddNewTask extends BottomSheetDialogFragment {
    public static final String TAG = "ActionBottomDialog";
    private EditText addTask;
    private Button save;
    private DBhandler db;
    public static AddNewTask newTask()
    { return(new AddNewTask());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.newtask,container,false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInsatanceState) {

        super.onViewCreated(view, savedInsatanceState);
        //Since its a fragment u have to get view first
        addTask = getView().findViewById(R.id.newtask);
        save = getView().findViewById(R.id.save);
        db = new DBhandler(getActivity());
        db.openDatabase();
        boolean isUpdate = false;
        final Bundle bundle = getArguments();
        if (bundle != null) {
            isUpdate = true;
            String task = bundle.getString("task");
            addTask.setText(task);
            if (task.length() > 0) {
                save.setTextColor(ContextCompat.getColor(getContext(),android.R.color.holo_orange_dark));
            }
            addTask.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (addTask.toString() == null) {
                        save.setEnabled(false);
                        save.setTextColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
                    } else {
                        save.setEnabled(true);
                        save.setTextColor(ContextCompat.getColor(getContext(), android.R.color.holo_orange_dark));
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }

            });
            boolean finalIsUpdate = isUpdate;
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text = addTask.getText().toString();
                    if (finalIsUpdate) {
                        db.updateTask(bundle.getInt("id"), text);
                    } else {
                        ToDoModel task = new ToDoModel();
                        task.setTask(text);
                        task.setStatus(0);
                    }
                    dismiss();
                }
            });
        }
    }
@Override
    public void onDismiss(DialogInterface dialogInterface)
{
    Activity activity = getActivity();
    if(activity instanceof DialogCloseListener) {
        ((DialogCloseListener)activity).handleDialogClose(dialogInterface);
    }
}


}
