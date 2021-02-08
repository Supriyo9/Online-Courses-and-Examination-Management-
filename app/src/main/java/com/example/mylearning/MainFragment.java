package com.example.mylearning;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {


    onFragmentBtnSelected listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main,container,false);

        Button Vidcall=view.findViewById(R.id.videocall);
        Vidcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onVideoCallSelected();
            }
        });


        Button assignmnt=view.findViewById(R.id.assignmnt);
        assignmnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAssignmentSelected();
            }
        });

        Button project=view.findViewById(R.id.prjct);
        project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onProjectSelected();
            }
        });

        Button exam=view.findViewById(R.id.exm);
        exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onExamSelected();
            }
        });

        Button evnt=view.findViewById(R.id.evntt);
        evnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEventSelected();
            }
        });

        Button stdymatrial=view.findViewById(R.id.studymatrial);
        stdymatrial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onStudyMaterialSelected();
            }
        });
         return  view;

    }

    public void onAttach(@NonNull Context context){

        super.onAttach(context);

        if(context instanceof onFragmentBtnSelected)
        {
            listener = (onFragmentBtnSelected) context;
        }
        else {
            throw new ClassCastException(context.toString()+"must implement listener");
        }
    }

    public interface onFragmentBtnSelected{

        public void onVideoCallSelected();
        public void onAssignmentSelected();
        public void onProjectSelected();
        public void onStudyMaterialSelected();
        public void onExamSelected();
        public void onEventSelected();


    }


}
