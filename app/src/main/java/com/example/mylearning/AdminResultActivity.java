package com.example.mylearning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminResultActivity extends AppCompatActivity {


   private DatabaseReference reference;

   private RecyclerView recyclerView;

   private ArrayList<ScoreHelper> list;

   private  String testName;

   ViewResultAdapter viewResultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_result);


        Toolbar toolbar = findViewById(R.id.viewresult_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.black));
        getSupportActionBar().setTitle("Results");

        testName=getIntent().getStringExtra("test");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=findViewById(R.id.viewresult_recclye);

        reference= FirebaseDatabase.getInstance().getReference("Results").child(testName);

        list=new ArrayList<ScoreHelper>();


        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    ScoreHelper scoreHelper=dataSnapshot1.getValue(ScoreHelper.class);
                    list.add(scoreHelper);
                }

                viewResultAdapter=new ViewResultAdapter(AdminResultActivity.this,list);
                recyclerView.setAdapter(viewResultAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AdminResultActivity.this,"error",Toast.LENGTH_SHORT).show();

            }
        });
    }


    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }

        return super.onOptionsItemSelected(item);


    }

}
