package com.example.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Date;

public class addActivity extends AppCompatActivity {
    //priority consts
    public static final int HIGH = 1;
    public static final int MED = 2;
    public static final int LOW = 3;
    //UIs:
    Button addTask;
    RadioButton high_prior;
    RadioButton med_prior;
    RadioButton low_prior;
    EditText desc;
    EditText timer;

    AppDataBase adb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);
        addTask = findViewById(R.id.addButton);
        desc = findViewById(R.id.editTextTaskDescription);
        high_prior = findViewById(R.id.radButton1);
        med_prior = findViewById(R.id.radButton2);
        low_prior = findViewById(R.id.radButton1);
        timer = findViewById(R.id.editText);
        adb = AppDataBase.getsInstance(getApplicationContext());
        //Intent:
        final Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            addTask.setText("Update");
            //data base:
            final LiveData<Task> task = adb.taskDao().getTaskById(intent.getIntExtra("id", 0));
            AddTaskViewModel vm = new AddTaskViewModel(adb, intent.getIntExtra("id", 0));
            vm.getTask().observe(addActivity.this, new Observer<Task>() {
                @Override
                public void onChanged(@Nullable Task task) {
                    final int priority = task.getPriority();

                    desc.setText(task.getDescription());
                    Toast.makeText(addActivity.this, task.getDescription().toString(), Toast.LENGTH_SHORT).show();
                    //might err:
                    setPriority(priority);

                }
            });
        }
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task_desc = desc.getText().toString().trim();
                int priority = getPriorityFromViews();
                Date cDate = new Date();
                String task_duration = timer.getText().toString().trim();
                //create task:
                final Task task = new Task(task_desc, priority, ((float) Double.parseDouble(task_duration)), cDate);

                AppExec.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (intent != null && intent.hasExtra("id")) {


                            task.setTask_id(intent.getIntExtra("id", 0));
                            adb.taskDao().updateTask(task);
                        } else {
                            adb.taskDao().insertTask(task);

                        }
                        finish();
                    }
                });
            }


        });
    }

    public int getPriorityFromViews() {
        int priority = 1;
        int checkedId = ((RadioGroup) findViewById(R.id.radioGroup)).getCheckedRadioButtonId();
        switch (checkedId) {
            case R.id.radButton1:
                priority = HIGH;
                break;
            case R.id.radButton2:
                priority = MED;
                break;
            case R.id.radButton3:
                priority = LOW;
        }
        return priority;
    }

    /*public void setPriority(int priority) {

        switch (priority) {

            case HIGH:
                high_prior.setChecked(true);
                break;
            case MED:
                med_prior.setChecked(true);
                break;
            case LOW:
                low_prior.setChecked(true);
                break;

        }

    }*/
    public void setPriority(int priority) {
        ImageView marker = (ImageView) findViewById(R.id.marker);
        switch (priority) {
            case HIGH:
                high_prior.setChecked(true);
                marker.setImageDrawable(getResources().getDrawable(R.drawable.layout_bg_high));
                break;
            case MED:
                med_prior.setChecked(true);
                marker.setImageDrawable(getResources().getDrawable(R.drawable.layout_bg_medium));
                break;
            case LOW:
                low_prior.setChecked(true);
                marker.setImageDrawable(getResources().getDrawable(R.drawable.layout_bg_low));
                break;

        }

    }
}