package com.example.donezo.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.donezo.AddNewTask;
import com.example.donezo.MainActivity;
import com.example.donezo.Model.ToDoModel;
import com.example.donezo.R;
import com.example.donezo.Utils.DataBaseHelper;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {
    private List<ToDoModel> mList;
    private MainActivity activity;
    private DataBaseHelper myDb;

    public ToDoAdapter(DataBaseHelper myDB,MainActivity activity){
        this.activity=activity;
        this.myDb=myDB;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
                 final ToDoModel item=mList.get(position);
                 holder.checkBox.setText(item.getTask());
                 holder.checkBox.setChecked((toBoolean(item.getStatus())));
                 holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                     @Override
                     public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                         if(buttonView.isChecked()){
                             myDb.updateStatus(item.getId(),0);

                         }else{
                             myDb.updateStatus(item.getId(),0);

                         }
                     }
                 });
    }

    private boolean toBoolean(int status) {
        return status!=0;
    }
    public Context getContext(){
        return  activity;
    }


    @Override
    public int getItemCount() {

        return mList.size();
    }
    @SuppressLint("NotifyDataSetChanged")
    public  void setTasks(List<ToDoModel> mList){
        this.mList=mList;
        notifyDataSetChanged();
    }
    public void deleteTask(int position){
              ToDoModel item =mList.get(position);
              myDb.deleteTask(item.getId());

              mList.remove(position);
              notifyItemRemoved(position);
    }
    public void editItems(int postion){
        ToDoModel item = mList.get(postion);
        Bundle bundle =new Bundle();
        bundle.putInt("Id",item.getId());
        bundle.putString("task",item.getTask());

        AddNewTask task =new AddNewTask();
        task.setArguments(bundle);
        task.show(activity.getSupportFragmentManager(),task.getTag());

    }
    public  static  class  MyViewHolder extends  RecyclerView.ViewHolder{
         CheckBox checkBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox=itemView.findViewById(R.id.checkbox);
        }
    }
}
