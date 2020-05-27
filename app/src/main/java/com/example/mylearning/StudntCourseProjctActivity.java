package com.example.mylearning;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

public class StudntCourseProjctActivity extends AppCompatActivity {




    private Dialog loadingDialog;

    private AVLoadingIndicatorView avLoadingIndicatorView;
    private StorageReference storageReference;


    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private DatabaseReference myRef;



    private ArrayList<String> result=new ArrayList<>();
    private int lastPos = -1;


    private ListView listView;
    private StudentProjectCourseActivityAdapter AssignAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studnt_course_projct);




        Toolbar toolbar=findViewById(R.id.StudentAAAAsssitoolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.black));
        getSupportActionBar().setTitle("Courses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        database= FirebaseDatabase.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();



        avLoadingIndicatorView = findViewById(R.id.loader1);
        avLoadingIndicatorView.setVisibility(View.VISIBLE);
        avLoadingIndicatorView.show();


/*
        loadingDialog=new Dialog(StudntCourseProjctActivity.this);
        loadingDialog.setContentView(R.layout.loading_progressbar);
        loadingDialog.setCancelable(false);

        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);




 */




        database= FirebaseDatabase.getInstance();
        myRef=database.getReference();
        listView=findViewById(R.id.test_listview);
        AssignAdapter=new StudentProjectCourseActivityAdapter(StudntCourseProjctActivity.this,result);
        listView.setAdapter(AssignAdapter);
        getResults();

    }









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


               /// Log.e("The read success: ", "su" + result.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                  avLoadingIndicatorView.setVisibility(View.GONE);
                    avLoadingIndicatorView.hide();


               // Log.e("The read failed: ", databaseError.getMessage());
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
               // Log.e("The read success: " ,"su"+result.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
               avLoadingIndicatorView.setVisibility(View.GONE);
               avLoadingIndicatorView.hide();
              //  Log.e("The read failed: " ,databaseError.getMessage());
            }
        });
        //// }
    }


    class StudentProjectCourseActivityAdapter extends ArrayAdapter<String> {

        private Context mContext;
        ArrayList<String> dataList;

        public StudentProjectCourseActivityAdapter( Context context,ArrayList<String> list) {
            super(context, 0 , list);
            mContext = context;
            dataList = list;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            View listItem = convertView;
            if(listItem == null)
                listItem = LayoutInflater.from(mContext).inflate(R.layout.test_item,parent,false);
            // ((ImageView)listItem.findViewById(R.id.item_imageView))
            ///  .setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ranking));
            //((ImageView)listItem.findViewById(R.id.item_imageView)).setPadding(10,0,0,0);
            ((TextView)listItem.findViewById(R.id.item_textView)).setText(dataList.get(position));
            ((Button)listItem.findViewById(R.id.item_button)).setText("View");

            Animation animation = AnimationUtils.loadAnimation(getContext(),
                    (position > lastPos) ? R.anim.up_from_bottom : R.anim.down_from_top);

            (listItem).startAnimation(animation);
            lastPos = position;
            ((Button)listItem.findViewById(R.id.item_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(StudntCourseProjctActivity.this, Projects.class);
                    intent.putExtra("test",dataList.get(position));
                    //////intent.putExtra("ISAdmin",isAdmin);
                    startActivity(intent);
                }
            });
            return listItem;
        }
    }
}

