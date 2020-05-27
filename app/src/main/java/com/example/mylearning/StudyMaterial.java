package com.example.mylearning;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudyMaterial extends AppCompatActivity {

    private ListView myListViewSdmt;

   private DatabaseReference databaseReference;
   private List<uploadPDF> uploadPDFS;

    private Dialog loadingDialog;

    private String Sname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_material);



        Sname=getIntent().getStringExtra("test");



        Toolbar toolbar=findViewById(R.id.studymatrial_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.black));
        getSupportActionBar().setTitle("Study Material");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        myListViewSdmt=findViewById(R.id.ListViewStdmt);
        uploadPDFS=new ArrayList<>();


        loadingDialog=new Dialog(StudyMaterial.this);
        loadingDialog.setContentView(R.layout.loading_progressbar);
        loadingDialog.setCancelable(false);
        //loadingDialog.getWindow().setBackgroundDrawableResource(R.);

        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();

        viewAllFiles();

        myListViewSdmt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                uploadPDF uploadPDF= uploadPDFS.get(position);


                Intent intent=new Intent();
                intent.setType(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(uploadPDF.getUrl()),"application/pdf");
                startActivity(Intent.createChooser(intent,"Open using"));
            }
        });
    }

    private void viewAllFiles() {

        databaseReference= FirebaseDatabase.getInstance().getReference("StudyMaterial").child(Sname);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for(DataSnapshot postSnapshot :dataSnapshot.getChildren())
                {
                    uploadPDF uploadPDF=postSnapshot.getValue(com.example.mylearning.uploadPDF.class);

                    uploadPDFS.add(uploadPDF);
                }

                String[] uploads =new String[uploadPDFS.size()];

                for(int i=0;i<uploads.length;i++)
                {
                    uploads[i]=uploadPDFS.get(i).getName();
                }

                loadingDialog.dismiss();

                ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,uploads)
                {

                    @Override
                    public View getView(int position,  View convertView, ViewGroup parent)
                    {

                        View view=super.getView(position, convertView, parent);

                        TextView myText= view.findViewById(android.R.id.text1);
                        myText.setTextColor(Color.BLACK);
                        return view;
                    }
                };
                myListViewSdmt.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
