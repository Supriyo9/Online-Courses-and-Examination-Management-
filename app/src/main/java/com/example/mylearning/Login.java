package com.example.mylearning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {


    EditText mPassword,mEmail;
    String uid;
    TextView mCreateBtn,forgetTextLink;
    Button  mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    RadioButton rdiostudentL,rdioteacherL;
    String user="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        mPassword=findViewById(R.id.LPasswordBTN);
        forgetTextLink=findViewById(R.id.forgotpassword);
        mEmail=findViewById(R.id.LEmailBTN);
        mCreateBtn=findViewById(R.id.NewUserTXT);
        mLoginBtn=findViewById(R.id.LogInBTN);

        fAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.LprogressBar);



        rdiostudentL=findViewById(R.id.studentl);
        rdioteacherL=findViewById(R.id.teacherl);





       /*if(fAuth.getCurrentUser()!=null)
        {
           // startActivity(new Intent(getApplicationContext(),AdminPanel.class));
           // finish();




                            fAuth = FirebaseAuth.getInstance();

                            firebaseDatabase=FirebaseDatabase.getInstance();



                            firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
                            uid=firebaseUser.getUid();

                            databaseReference=firebaseDatabase.getReference("Alluser");





                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange( DataSnapshot dataSnapshot) {


                                    String name=dataSnapshot.child(uid).child("User").getValue(String.class);



                                    if(user.equals("Student"))
                                    {


                                        if (name.equals(user)) {

                                            Toast.makeText(Login.this, "Logged In succesfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                            Login.this.finish();
                                        }



                                    }




                                    else if (user.equals("Teacher"))
                                    {

                                        if (name.equals(user)) {

                                            Toast.makeText(Login.this, "Logged In succesfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), AdminPanel.class));
                                            Login.this.finish();
                                        }


                                    }




                                }

                                @Override
                                public void onCancelled( DatabaseError databaseError) {

                                    Toast.makeText(Login.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();

                                }
                            });







        }

        */








        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                String email=mEmail.getText().toString().trim();
                String password=mPassword.getText().toString().trim();



                if(rdiostudentL.isChecked())
                {
                    user="Student";
                }

                if(rdioteacherL.isChecked())
                {
                    user="Teacher";
                }



                if(TextUtils.isEmpty(user))
                {
                    Toast.makeText(Login.this,"Please select user type. ",Toast.LENGTH_SHORT).show();
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



                //authonticate

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {




                            fAuth = FirebaseAuth.getInstance();

                            firebaseDatabase=FirebaseDatabase.getInstance();



                            firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
                            uid=firebaseUser.getUid();

                            databaseReference=firebaseDatabase.getReference("Alluser");





                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange( DataSnapshot dataSnapshot) {


                                    String name=dataSnapshot.child(uid).child("User").getValue(String.class);



                                    if(user.equals("Student"))
                                    {


                                        if (name.equals(user)) {

                                            Toast.makeText(Login.this, "Logged In succesfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                            Login.this.finish();
                                        }

                                        else {
                                            Toast.makeText(Login.this, "You are not a Student.", Toast.LENGTH_SHORT).show();

                                            progressBar.setVisibility(View.GONE);
                                        }

                                    }




                                    else if (user.equals("Teacher"))
                                    {

                                        if (name.equals(user)) {

                                            Toast.makeText(Login.this, "Logged In succesfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), AdminPanel.class));
                                            Login.this.finish();
                                        }

                                        else {
                                            Toast.makeText(Login.this, "You are not a Teacher.", Toast.LENGTH_SHORT).show();

                                            progressBar.setVisibility(View.GONE);
                                        }

                                    }




                                }

                                @Override
                                public void onCancelled( DatabaseError databaseError) {

                                    Toast.makeText(Login.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();

                                }
                            });






                        }
                        else
                        {
                            Toast.makeText(Login.this,"Error !"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });
            }
        });


        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
                finish();

            }
        });


        forgetTextLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetMail=new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog=new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter Your Email To Receive Reset link ");
                passwordResetDialog.setView(resetMail);


                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String mail=resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Login.this,"Reset Link Sent To Your Mail",Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(Login.this,"Error! link not sent"+e.getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                passwordResetDialog.show();
            }
        });

    }
}
