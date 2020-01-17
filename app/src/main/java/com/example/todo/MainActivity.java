package com.example.todo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Application;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //added:
    //global var:
    Activity act = this;


    private FloatingActionButton addTask;
    AppDataBase appDataBase;
    TaskAdapter taskAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Application app;


    //added:
    CheckBox priorityChk;
    CheckBox durationChk;

    //private RadioGroup priorityButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appDataBase = AppDataBase.getsInstance(getApplicationContext());
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        taskAdapter = new TaskAdapter(this);
        recyclerView.setAdapter(taskAdapter);
        addTask = findViewById(R.id.floatingActionButton3);


        //added:
        final CheckBox checkBox1 = (CheckBox) findViewById(R.id.checkBox); //final
        final CheckBox checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        checkBox1.setChecked(false); //true or false?
        checkBox2.setChecked(false); // true or false?
        //listener:
        this.initializeViews();


        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(MainActivity.this, addActivity.class);
                startActivity(intent);


            }
        });

        getTasks("onCreate");


        //SWIPE TO DELETE:!!!!
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Toast.makeText(MainActivity.this, "on Move", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                Toast.makeText(MainActivity.this, "on Swiped ", Toast.LENGTH_SHORT).show();
                //Remove swiped item from list and notify the RecyclerView
                List<Task> tasks = taskAdapter.getTasks();
                int position = viewHolder.getAdapterPosition();
                //taskAdapter.notifyDataSetChanged();
                //tasks.remove(position);
                //Added:
                int tbdeleted = tasks.get(position).getTask_id();
                //taskAdapter.notifyDataSetChanged();


                AppExec.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        appDataBase.taskDao().deleteTask(tasks.get(position));
                    }


                });
                taskAdapter.notifyItemRemoved(position);
                taskAdapter.notifyItemRangeChanged(position, tasks.size());

            }
        };//end_SWIPE

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }//end-onCreate


    private void getTasks(final String s) {
        app = this.getApplication();


        //MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        MainViewModel viewModel = new ViewModelProvider(this, new MainViewModelFactory(app)).get(MainViewModel.class);

        viewModel.getTaskList().observe(MainActivity.this, new Observer<List<Task>>() {

            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                taskAdapter.setTasks(tasks);

            }
        });

    }

    //added:
    private void initializeViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        priorityChk = (CheckBox) findViewById(R.id.checkBox);
        durationChk = (CheckBox) findViewById(R.id.checkBox2);
        app = this.getApplication();
        priorityChk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    durationChk.setEnabled(false);
                    //sort based on priority:
                    sortPrior("");
                } else {
                    durationChk.setEnabled(true);
                }
            }
        });
        durationChk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    priorityChk.setEnabled(false);
                    sortTime("");
                } else {
                    priorityChk.setEnabled(true);
                }
            }
        });


    }


    //sorting VMs:
    private void sortPrior(final String s) {
        app = this.getApplication();

        PSortViewModel viewModel = new ViewModelProvider(this, new PSortViewModelFactory(app)).get(PSortViewModel.class);
        viewModel.getTaskList().observe(MainActivity.this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                taskAdapter.setTasks(tasks);

            }
        });

    }

    private void sortTime(final String s) {
        app = this.getApplication();

        DSortViewModel viewModel = new ViewModelProvider(this, new DSortViewModelFactory(app)).get(DSortViewModel.class);
        viewModel.getTaskList().observe(MainActivity.this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                taskAdapter.setTasks(tasks);

            }
        });

    }


}


