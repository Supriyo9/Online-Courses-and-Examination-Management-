package com.example.mylearning;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class AdminEventActivity extends AppCompatActivity {


    private EditText UploadstudymatT;
    private Button UploadstudymatB;


    private StorageReference storageReference;


    //

    private ListView myListViewSdmtAdmin;

    private DatabaseReference databaseReference;
    private List<uploadPDF> uploadPDFS;


    //

    private  String Ptestt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_event);




        Toolbar toolbar = findViewById(R.id.event_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.black));
        getSupportActionBar().setTitle("Evnet");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        Ptestt=getIntent().getStringExtra("test");


        UploadstudymatB=findViewById(R.id.uploadstudymatB);
        UploadstudymatT=findViewById(R.id.uploadstudymatT);

        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference("Event").child(Ptestt);

        UploadstudymatB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullna=UploadstudymatT.getText().toString().trim();

                if(TextUtils.isEmpty(fullna))
                {
                    UploadstudymatT.setError("Required");
                    return;
                }
                selectPDFfile();
            }
        });



        //show list also in this

        myListViewSdmtAdmin=findViewById(R.id.ListViewStdmtAdmin);
        uploadPDFS=new ArrayList<>();

        viewAllFiles();

        myListViewSdmtAdmin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    //

    private void selectPDFfile()
    {



        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select PDF file"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1&& resultCode==RESULT_OK
                && data!=null && data.getData()!=null)
        {
            uploadPDFfile(data.getData());
        }
    }

    private void uploadPDFfile(Uri data)
    {

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();


        StorageReference reference=storageReference.child("Event/"+System.currentTimeMillis()+".pdf");
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        Task<Uri> uri=taskSnapshot.getStorage().getDownloadUrl();
                        while (!uri.isComplete());
                        Uri url=uri.getResult();

                        uploadPDF uploadPDF=new uploadPDF(UploadstudymatT.getText().toString(),url.toString());
                        databaseReference.child(Ptestt).child(databaseReference.push().getKey()).setValue(uploadPDF);
                        Toast.makeText(AdminEventActivity.this,"File Uploaded",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                double progress=(100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();


                progressDialog.setMessage("Uploaded..."+(int)progress+"%");



            }
        });
    }


    //


    private void viewAllFiles() {




        databaseReference= FirebaseDatabase.getInstance().getReference("Event").child(Ptestt);

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
                myListViewSdmtAdmin.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    //
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
