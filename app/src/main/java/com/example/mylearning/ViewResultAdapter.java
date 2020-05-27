package com.example.mylearning;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import javax.xml.transform.Result;

public class ViewResultAdapter extends RecyclerView.Adapter<ViewResultAdapter.ViewHolder> {

    Context context;
    ArrayList<ScoreHelper> scoreHelpers;

    public ViewResultAdapter(Context context, ArrayList<ScoreHelper> scoreHelpers) {
        this.context = context;
        this.scoreHelpers = scoreHelpers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.view_result,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.Scorename.setText(scoreHelpers.get(position).getName());
        holder.Sscore.setText("Score : "+scoreHelpers.get(position).getScore());
      ////  holder.Scourse.setText("Course : "+scoreHelpers.get(position).getCourse());
       //// holder.Sset.setText("Set No : "+String.valueOf(scoreHelpers.get(position).getSet()));

    }

    @Override
    public int getItemCount() {
        return scoreHelpers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Scorename,Sscore;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Scorename=(TextView)itemView.findViewById(R.id.ScoreName);
            Sscore=(TextView)itemView.findViewById(R.id.ScoreMarks);

        }
    }
}
