package com.example.mylearning;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VideoCall extends AppCompatActivity {


    TextView textView;
    Button v;
    String uid;
    FirebaseAuth fAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call);

       /* textView=findViewById(R.id.hero);
        v=findViewById(R.id.buttonv);


               fAuth = FirebaseAuth.getInstance();

               firebaseDatabase=FirebaseDatabase.getInstance();



               firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
               uid=firebaseUser.getUid();

               databaseReference=firebaseDatabase.getReference("Alluser");

               databaseReference.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange( DataSnapshot dataSnapshot) {


                       String name=dataSnapshot.child(uid).child("User").getValue(String.class);


                       textView.setText(name);



                   }

                   @Override
                   public void onCancelled( DatabaseError databaseError) {

                       Toast.makeText(VideoCall.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();

                   }
               });

        */




    }
}
