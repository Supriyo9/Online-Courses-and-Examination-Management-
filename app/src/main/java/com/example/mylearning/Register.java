package com.example.mylearning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {



    EditText mFullname,mPassword,mPhone,mEmail;
    Button mRegisterBtn;
    TextView mLoginBtn;
    RadioButton rdiostudent,rdioteacher;

    String user="";
    String email,mobile,fullname,password;

    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth fAuth;


    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullname=findViewById(R.id.FullNameTXT);
        mPassword=findViewById(R.id.CreatePassword);
        mPhone=findViewById(R.id.MobileTXT);
        mEmail=findViewById(R.id.REmailTXT);
        mRegisterBtn=findViewById(R.id.RegisterBTN);
        mLoginBtn=findViewById(R.id.AlreadyRegisteredTXT);
        rdiostudent=findViewById(R.id.studentr);
        rdioteacher=findViewById(R.id.teacherr);



        fAuth=FirebaseAuth.getInstance();

        databaseReference=FirebaseDatabase.getInstance().getReference("Alluser");
        progressBar=findViewById(R.id.progressBar);



      /*  if(fAuth.getCurrentUser()!=null)
        {
           // startActivity(new Intent(getApplicationContext(),AdminPanel.class));
           // finish();
        }

       */

        
        mRegisterBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {


                email=mEmail.getText().toString().trim();
                password=mPassword.getText().toString().trim();
                 mobile=mPhone.getText().toString().trim();
                 fullname=mFullname.getText().toString().trim();

                if(rdiostudent.isChecked())
                {
                    user="Student";
                }

                if(rdioteacher.isChecked())
                {
                    user="Teacher";
                }


                if(TextUtils.isEmpty(user))
                {
                    Toast.makeText(Register.this,"Please select user type. ",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(fullname))
                {
                    mFullname.setError("Full name is required");
                    return;
                }

                if(TextUtils.isEmpty(mobile))
                {
                    mPhone.setError("Mobile No. is required");
                    return;
                }

                if(mobile.length()<8)
                {
                    mPhone.setError("No. should be 10 digits long");
                    return;
                }


                if(TextUtils.isEmpty(email))
                {
                    mEmail.setError("Email is required");
                    return;
                }


                if(TextUtils.isEmpty(password))
                {
                    mPassword.setError("Password is required");
                    return;
                }

                if(password.length()<8)
                {
                    mPassword.setError("Password should be 8 characters long");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //register

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {

                            //verification link
                           /* FirebaseUser user=fAuth.getCurrentUser();
                            user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Register.this,"Verification Email Link Sent ",Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("tag","onFailure: Email Not Sent"+e.getMessage());

                                }
                            });

                            */

                           RegisterHelper information= new RegisterHelper(fullname, mobile, email, user);


                           FirebaseDatabase.getInstance().getReference("Alluser")
                                   .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                   .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {


                                   Toast.makeText(Register.this,"Registered succesfully",Toast.LENGTH_SHORT).show();
                                   startActivity(new Intent(getApplicationContext(),Login.class));

                                   Register.this.finish();

                               }
                           });



                        }
                        else
                        {
                            Toast.makeText(Register.this,"Error"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });
            }
        });


        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();

            }
        });


    }
}
