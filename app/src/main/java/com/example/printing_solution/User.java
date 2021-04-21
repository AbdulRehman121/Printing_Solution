package com.example.printing_solution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class User extends AppCompatActivity {
    TextView UN,W,H,MN;
    DatabaseReference DR;
    private FirebaseUser FU;
    private String FID;
private Button Flex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FU = FirebaseAuth.getInstance().getCurrentUser();
        setContentView(R.layout.activity_user);
        Flex=(Button)findViewById(R.id.Flex);
        UN = (TextView) findViewById(R.id.username);
        W = (TextView) findViewById(R.id.city);
        H = (TextView) findViewById(R.id.email);
        MN = (TextView) findViewById(R.id.MN);
        FID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DR = FirebaseDatabase.getInstance().getReference().child("Users").child(String.valueOf(FID));
        DR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = (snapshot.child("name").getValue().toString());
                String city = snapshot.child("City").getValue().toString();
                String email = snapshot.child("email").getValue().toString();
                String MN1 = snapshot.child("mobile_no").getValue().toString();
                UN.setText(name);
                W.setText(city);
                H.setText(email);
                MN.setText(MN1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }

        });

        Flex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(User.this,Flex.class);
                startActivity(intent2);
            }
        });


    }
}