package com.example.mylearning;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Application;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.io.File;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        MainFragment.onFragmentBtnSelected,FragmentSetting.onFragmentSettingSelected {

    TextView fullname,email;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    FirebaseAuth fAuth;

    String uid;

    public static String FullName;

    private Dialog loadingDialog;


    //
    public TextView USer_email,userID;

    FirebaseDatabase firebaseDatabase;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        fullname=findViewById(R.id.headerName);
        email=findViewById(R.id.headerEmail);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();


        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.container_fragment, new MainFragment());
        fragmentTransaction.commit();

        navigationView.setNavigationItemSelectedListener(this);



        //


        View header = navigationView.getHeaderView(0);

        USer_email = header.findViewById(R.id.headerEmail);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTextOnUser();
        }


        userID = header.findViewById(R.id.headerName);
        setUserName();


        //


        fAuth = FirebaseAuth.getInstance();



    }




    //
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setTextOnUser() {


        FirebaseUser usero = FirebaseAuth.getInstance().getCurrentUser();
        USer_email.setText(Objects.requireNonNull(usero).getEmail());
    }


    private void setUserName() {




        FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference;
        FirebaseUser firebaseUser;
        //


        fAuth = FirebaseAuth.getInstance();

        firebaseDatabase=FirebaseDatabase.getInstance();



        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        uid=firebaseUser.getUid();

        databaseReference=firebaseDatabase.getReference("Alluser");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {


                FullName=dataSnapshot.child(uid).child("fullname").getValue(String.class);


                userID.setText(FullName);



            }

            @Override
            public void onCancelled( DatabaseError databaseError) {

                Toast.makeText(MainActivity.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();

            }
        });

    }




    //



    /*public boolean onCreateOptionMenuSelected( Menu menu) {

        getMenuInflater().inflate(R.menu.menu_notification,menu);
        return true;
    }

     */


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        drawerLayout.closeDrawer(GravityCompat.START);//to close drawer automatically
        if (menuItem.getItemId() == R.id.home1) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.container_fragment, new MainFragment());
            fragmentTransaction.commit();


        }


        if (menuItem.getItemId() == R.id.settings) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.container_fragment, new FragmentSetting());
            fragmentTransaction.commit();

        }
        if (menuItem.getItemId() == R.id.faq) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.container_fragment, new FragmentFAQ());
            fragmentTransaction.commit();

        }
        if (menuItem.getItemId() == R.id.logout) {

            loadingDialog=new Dialog(MainActivity.this);
            loadingDialog.setContentView(R.layout.loading_progressbar);
            loadingDialog.setCancelable(false);
            loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            loadingDialog.show();


            FirebaseAuth.getInstance().signOut();

            loadingDialog.dismiss();
            Toast.makeText(MainActivity.this,"Logged Out succesfully",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        }
        if (menuItem.getItemId() == R.id.share) {

           /* ApplicationInfo api = getApplicationContext().getApplicationInfo();
            String apkpath = api.sourceDir;
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("application/vnd.android.package-archive");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(apkpath)));
            startActivity(Intent.createChooser(intent, "Share Using"));

            */

           Toast.makeText(this,"Nothing to share",Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public void onVideoCallSelected() {
        startActivity(new Intent(getApplicationContext(), Chat_Handler.class));


    }

    @Override
    public void onAssignmentSelected() {
        startActivity(new Intent(getApplicationContext(), StudntAssignCourseActivity.class));

    }

    @Override
    public void onProjectSelected() {
        startActivity(new Intent(getApplicationContext(), StudntCourseProjctActivity.class));

    }

    @Override
    public void onStudyMaterialSelected() {
        startActivity(new Intent(getApplicationContext(), StudntCoursStudyActivity.class));

    }

    @Override
    public void onExamSelected() {
        startActivity(new Intent(getApplicationContext(), Tests.class));

    }

    @Override
    public void onEventSelected() {

        startActivity(new Intent(getApplicationContext(), StudntEvntCoursActivity.class));

    }

    @Override
    public void onChangePassword() {

        startActivity(new Intent(getApplicationContext(), ChangePasswordDialog.class));

    }
}