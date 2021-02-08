package com.example.mylearning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class AdminPanel extends AppCompatActivity {

   private Button adminxam,adminprjct,adminvido,adminassig,adminevnt,adminstdmt;

    FirebaseAuth fAuth;

    private Button DView,DCondct;

    private Dialog ViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);




        ViewResult=new Dialog(AdminPanel.this);
        ViewResult.setContentView(R.layout.view_result_dialog);
        ViewResult.setCancelable(true);
        ViewResult.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);


        DView=ViewResult.findViewById(R.id.fisttime);
        DCondct=ViewResult.findViewById(R.id.conductexam);


        Toolbar toolbar=findViewById(R.id.Admintoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Teacher Panel");



        adminassig=findViewById(R.id.adminassig);
        adminevnt=findViewById(R.id.adminevnt);
        adminprjct=findViewById(R.id.Adminprojct);
        adminstdmt=findViewById(R.id.AdminstdmtImg);
        adminxam=findViewById(R.id.AdminexamImg);
        adminvido=findViewById(R.id.adminvido);



        fAuth = FirebaseAuth.getInstance();


        adminxam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                ViewResult.show();
            }
        });


        DCondct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewResult.dismiss();
                startActivity(new Intent(getApplicationContext(),create_quiz_main.class));


            }
        });



        DView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewResult.dismiss();
                startActivity(new Intent(getApplicationContext(),ResultsAdmin.class));///AdminResultActivity

            }
        });

        adminvido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Chat_Handler.class));


            }
        });

        adminevnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AdminEvntCourseActivity.class));
            }
        });

        adminassig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AdminAssignCourseActivity.class));
            }
        });
        adminprjct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AdminCourseProjctActivity.class));
            }
        });

        adminstdmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AdminCourStudyActivity.class));
            }
        });



    }


    public  boolean onCreateOptionsMenu(Menu menu)
    {

        getMenuInflater().inflate(R.menu.menu_notification,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();
        if(id==R.id.Adminlogout)
        {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
