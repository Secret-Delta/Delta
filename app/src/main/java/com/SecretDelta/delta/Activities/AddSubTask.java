package com.SecretDelta.delta.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.SecretDelta.delta.Models.SubTaskModel;
import com.SecretDelta.delta.Models.TaskModel;
import com.SecretDelta.delta.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;

public class AddSubTask extends Activity {
    BottomSheetDialog bottomSheetDialog;
    Button save;
    EditText subtask;

    DatabaseReference dbRef;
    SubTaskModel subTaskModel;

    private void clearControls() {
        subtask.setText("");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int width = WindowManager.LayoutParams.WRAP_CONTENT;
        int height = WindowManager.LayoutParams.WRAP_CONTENT;

        getWindow().setLayout(width, height);
        bottomSheetDialog = new BottomSheetDialog(this);
        createDialog();
        bottomSheetDialog.show();
    }

    @SuppressLint("InflateParams")
    private void createDialog() {
        View view = getLayoutInflater().inflate(R.layout.sub_task_dialog, null, false);

        save = view.findViewById(R.id.subTaskButton);
        subtask = view.findViewById(R.id.subTaskText);
        TaskModel t = new TaskModel();
        subTaskModel = new SubTaskModel();
//        UUID uuid = UUID.randomUUID();
//        long taskID = uuid.getLeastSignificantBits();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();

                subTaskModel.setTask(subtask.getText().toString().trim());
                subTaskModel.setCheck(0);
                t.getArrayList().add(subTaskModel);
            }
        });

        bottomSheetDialog.setContentView(view);

    }
}
