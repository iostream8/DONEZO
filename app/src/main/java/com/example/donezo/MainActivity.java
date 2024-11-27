package com.example.donezo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.donezo.Adapter.ToDoAdapter;
import com.example.donezo.Model.ToDoModel;
import com.example.donezo.Utils.DataBaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDialogCloseListner {

     RecyclerView Recyclerview;
     FloatingActionButton addbutton;
     DataBaseHelper myDB;
     List<ToDoModel> mList;
     ToDoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Recyclerview = findViewById(R.id.recylerView);
        addbutton = findViewById(R.id.addbutton);
        myDB = new DataBaseHelper(MainActivity.this);
        mList = new ArrayList<>();
        adapter = new ToDoAdapter(myDB , MainActivity.this);

        Recyclerview.setHasFixedSize(true);
        Recyclerview.setLayoutManager(new LinearLayoutManager(this));
        Recyclerview.setAdapter(adapter);

        mList = myDB.getAllTask();
        Collections.reverse(mList);
        adapter.setTasks(mList);

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager() , AddNewTask.TAG);
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(Recyclerview);
    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        mList = myDB.getAllTask();
        Collections.reverse(mList);
        adapter.setTasks(mList);
        adapter.notifyDataSetChanged();
    }
}