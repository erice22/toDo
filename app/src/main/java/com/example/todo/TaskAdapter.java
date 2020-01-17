package com.example.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TViewHolder> {
    //member variables:
    private List<Task> ctaskItems;
    private Context cContext;
    //constructor:
    public TaskAdapter(Context context){
        cContext=context;
    }
    //getter:
    public List<Task> getTasks(){return ctaskItems;}
    //super_methods:
    @Override
    public TViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(cContext).inflate(R.layout.row_display,parent,false);
        return new TViewHolder((v));
    }
    @Override
    public void onBindViewHolder(TViewHolder holder, int position){
        Task tEntry = ctaskItems.get(position);
        String desc = tEntry.getDescription();
        float duration = tEntry.getDuration();
        int priority = tEntry.getPriority();
        //update:
        //might need to change increment:
        if (priority == 3){
            holder.priorityMarker.setImageDrawable(cContext.getResources().getDrawable(R.drawable.layout_bg_low));
        } else if (priority == 2) {
            holder.priorityMarker.setImageDrawable(cContext.getResources().getDrawable(R.drawable.layout_bg_medium));
        }
        else{
            holder.priorityMarker.setImageDrawable(cContext.getResources().getDrawable(R.drawable.layout_bg_high));
        }
        holder.taskDuration.setText(Double.toString(duration)+" min");
        holder.taskDescView.setText(desc);
    }

    @Override
    public int getItemCount(){
        return (ctaskItems==null)?0:ctaskItems.size();
    }
    public void setTasks(List<Task> newList){
        ctaskItems = newList;
        notifyDataSetChanged();
    }
    //nested class:
    class TViewHolder extends RecyclerView.ViewHolder{
        //row_display elements:
        TextView taskDescView;
        TextView taskDuration;
        ImageView priorityMarker;
        //constructor:
        public TViewHolder(View tView){
            super(tView);
            taskDescView = tView.findViewById(R.id.taskDescription);
            taskDuration = tView.findViewById(R.id.taskDuration);
            priorityMarker= tView.findViewById(R.id.marker);
        }
    }//end-nested
//added:


}
