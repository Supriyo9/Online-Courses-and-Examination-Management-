package com.example.mylearning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ChangePasswordDialog extends AppCompatActivity {


    Button chngY,chngN;
    EditText resetMail;
    FirebaseAuth fAuth;
    String mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_dialog);

        chngY= findViewById(R.id.ChangepassY);
        chngN= findViewById(R.id.ChangpassN);
        resetMail= findViewById(R.id.urmail);





            fAuth = FirebaseAuth.getInstance();
            chngY.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mail= resetMail.getText().toString();


                    if(!TextUtils.isEmpty(mail)) {


                    fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ChangePasswordDialog.this, "Reset Link Sent To Your Mail", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(ChangePasswordDialog.this, "Error! link not sent" + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });


                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Enter Email",Toast.LENGTH_SHORT).show();
                    }

                }
            });


            chngN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(ChangePasswordDialog.this, MainActivity.class);
                    startActivity(intent);

                }
            });




    }
}
