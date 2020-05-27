package com.example.mylearning;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

public class AdminCourseProjctActivity extends AppCompatActivity {



    private Button AaddCat;



    private Dialog loadingDialog,addCatDialog;
    private AVLoadingIndicatorView avLoadingIndicatorView;

    /////3rd dialog
    private  TextView FistTime;
    private  Dialog FirstTimeAdd;
    private EditText FirstTimeAdddialogCatName;
    private Button FirstTimeAdddialogAddcatB;
    private StorageReference storageReference;
    private List<uploadPDF> uploadPDFS;
    //////

    private EditText dialogCatName;
    private Button dialogAddcatB;

    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private DatabaseReference myRef;

    private  String course;

    private ArrayList<String> result=new ArrayList<>();
    private int lastPos = -1;


    private ListView listView;
    private AdminProjectCourseActivityAdapter AssignAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_course_projct);





        Toolbar toolbar=findViewById(R.id.AprojcttoolbarCateg);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.black));
        getSupportActionBar().setTitle("Courses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        database=FirebaseDatabase.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();




        AaddCat=findViewById(R.id.Aaddcat);




        avLoadingIndicatorView = findViewById(R.id.loader1);
        avLoadingIndicatorView.setVisibility(View.VISIBLE);
        avLoadingIndicatorView.show();




       /* loadingDialog=new Dialog(AdminCourseProjctActivity.this);
        loadingDialog.setContentView(R.layout.loading_progressbar);
        loadingDialog.setCancelable(false);

        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        */



        addCatDialog=new Dialog(AdminCourseProjctActivity.this);
        addCatDialog.setContentView(R.layout.add_category_dialog);
        addCatDialog.setCancelable(true);
        addCatDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);


        dialogCatName=addCatDialog.findViewById(R.id.fisttime);
        dialogAddcatB=addCatDialog.findViewById(R.id.addcat_button);


        //////
        FirstTimeAdd=new Dialog(AdminCourseProjctActivity.this);
        FirstTimeAdd.setContentView(R.layout.add_first_time_dialog);
        FirstTimeAdd.setCancelable(true);
        FirstTimeAdd.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);


        FirstTimeAdddialogCatName=FirstTimeAdd.findViewById(R.id.fisttime);
        FirstTimeAdddialogAddcatB=FirstTimeAdd.findViewById(R.id.addFiestTime);
        FistTime=FirstTimeAdd.findViewById(R.id.firstEnter);
        FistTime.setText("Enter Project Name Below");




        AaddCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogCatName.getText().clear();
                addCatDialog.show();
            }
        });



        dialogAddcatB.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if(dialogCatName.getText().toString().isEmpty())
                {
                    dialogCatName.setError("Add Name");
                    return;
                }

                course=dialogCatName.getText().toString().trim();


                ///// myRef =database.getReference("Assignment").child(course).setValue();

                addCatDialog.dismiss();
                FirstTimeAdd.show();

            }
        });



        //////////
        FirstTimeAdddialogAddcatB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullnamee=FirstTimeAdddialogCatName.getText().toString().trim();

                if(TextUtils.isEmpty(fullnamee))
                {
                    FirstTimeAdddialogCatName.setError("Required");
                    return;
                }
                selectPDFfile();
            }
        });



        database= FirebaseDatabase.getInstance();
        myRef=database.getReference();
        listView=findViewById(R.id.test_listview);
        AssignAdapter=new AdminProjectCourseActivityAdapter(AdminCourseProjctActivity.this,result);
        listView.setAdapter(AssignAdapter);
        getResults();

    }



    /////here starts my 3rd dialog work

    private void selectPDFfile() {





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

    private void uploadPDFfile(Uri data) {




        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();


        StorageReference reference=storageReference.child("Project/"+System.currentTimeMillis()+".pdf");
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        myRef =database.getReference("Project");


                        Task<Uri> uri=taskSnapshot.getStorage().getDownloadUrl();
                        while (!uri.isComplete());
                        Uri url=uri.getResult();

                        uploadPDF uploadPDF=new uploadPDF(FirstTimeAdddialogCatName.getText().toString(),url.toString());
                        myRef.child(course).child(myRef.push().getKey()).setValue(uploadPDF);
                        Toast.makeText(AdminCourseProjctActivity.this,"File Uploaded",Toast.LENGTH_SHORT).show();
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





    /////here ends my 3rd dialog work










    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getResults() {

        //if(isAdmin) {

        myRef.child("Project").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                result.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    result.add(snapshot.getKey());
                }
                AssignAdapter.dataList = result;
                AssignAdapter.notifyDataSetChanged();
                    avLoadingIndicatorView.setVisibility(View.GONE);
                    avLoadingIndicatorView.hide();


                Log.e("The read success: ", "su" + result.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                    avLoadingIndicatorView.setVisibility(View.GONE);
                    avLoadingIndicatorView.hide();


                Log.e("The read failed: ", databaseError.getMessage());
            }
        });

        /////  }
        //// else
        // {

        myRef.child("Project").addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                result.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    ////// if(snapshot.hasChild(Objects
                    ///////.requireNonNull(auth.getUid())))
                    result.add(snapshot.getKey());
                }
                AssignAdapter.dataList=result;
                AssignAdapter.notifyDataSetChanged();
                 avLoadingIndicatorView.setVisibility(View.GONE);
                avLoadingIndicatorView.hide();
               /// Log.e("The read success: " ,"su"+result.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                avLoadingIndicatorView.setVisibility(View.GONE);
               avLoadingIndicatorView.hide();
                //Log.e("The read failed: " ,databaseError.getMessage());
            }
        });
        //// }
    }


    class AdminProjectCourseActivityAdapter extends ArrayAdapter<String> {

        private Context mContext;
        ArrayList<String> dataList;

        public AdminProjectCourseActivityAdapter( Context context,ArrayList<String> list) {
            super(context, 0 , list);
            mContext = context;
            dataList = list;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            View listItem = convertView;
            if(listItem == null)
                listItem = LayoutInflater.from(mContext).inflate(R.layout.sol_item,parent,false);
            // ((ImageView)listItem.findViewById(R.id.item_imageView))
            ///  .setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ranking));
            //((ImageView)listItem.findViewById(R.id.item_imageView)).setPadding(10,0,0,0);
            ((TextView)listItem.findViewById(R.id.item_textView)).setText(dataList.get(position));
            ((Button)listItem.findViewById(R.id.item_button)).setText("View");


            ((Button)listItem.findViewById(R.id.item_sol)).setText("Solution");


            Animation animation = AnimationUtils.loadAnimation(getContext(),
                    (position > lastPos) ? R.anim.up_from_bottom : R.anim.down_from_top);

            (listItem).startAnimation(animation);
            lastPos = position;
            ((Button)listItem.findViewById(R.id.item_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(AdminCourseProjctActivity.this, AdminProjectActivity.class);
                    intent.putExtra("test",dataList.get(position));
                    //////intent.putExtra("ISAdmin",isAdmin);
                    startActivity(intent);
                }
            });




            ((Button)listItem.findViewById(R.id.item_sol)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(AdminCourseProjctActivity.this, ViewProjcetSolActivity.class);
                    intent.putExtra("test",dataList.get(position));
                    //////intent.putExtra("ISAdmin",isAdmin);
                    startActivity(intent);
                }
            });


            return listItem;
        }
    }
}

