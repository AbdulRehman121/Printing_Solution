package com.example.printing_solution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import static android.widget.Toast.LENGTH_SHORT;

public class New_Olders extends AppCompatActivity {
private TextView cn,cmn,cem,cci,pn,pqul,pqan,ps,da,pp;
    DatabaseReference DR,DR1;
    private FirebaseUser FU;
    private String FID,UU,values,sv1;
    private Button next,previous;
    private int  val,v1=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FU = FirebaseAuth.getInstance().getCurrentUser();
        setContentView(R.layout.activity_new__olders);
        cn=findViewById(R.id.CName);
        cmn=findViewById(R.id.mobileno);
        cem=findViewById(R.id.mail);
        cci=findViewById(R.id.City);
        pn=findViewById(R.id.product);
        pqul=findViewById(R.id.quality);
        pqan=findViewById(R.id.Quantaty);
        ps=findViewById(R.id.size);
        da=findViewById(R.id.address);
        pp=findViewById(R.id.pp);
        next=findViewById(R.id.next);
        previous=findViewById(R.id.Previous);
        //FID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference cdbname = FirebaseDatabase.getInstance().getReference();
        cdbname.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                values = snapshot.child("prev_value").getValue().toString();
                val = new Integer(values);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       // for(int i=1;v1==i;i++) {
             sv1 = String.valueOf(v1);

             DR = FirebaseDatabase.getInstance().getReference().child("Orders").child(sv1);
             DR.addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot snapshot) {
                     pn.setText(snapshot.child("Product").getValue().toString());
                     pqul.setText(snapshot.child("Quality").getValue().toString());
                     ps.setText(snapshot.child("Height").getValue().toString() + " x " + snapshot.child("Width").getValue().toString());
                     //  pqan.setText(snapshot.child("Quantity").getValue().toString());
                     da.setText(snapshot.child("DAddress").getValue().toString());
                     pp.setText(snapshot.child("Price").getValue().toString());

                     UU = (snapshot.child("User Id").getValue().toString());
                     DR1 = FirebaseDatabase.getInstance().getReference().child("Users").child(UU);
                     DR1.addValueEventListener(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot snapshot) {
                             cn.setText(snapshot.child("name").getValue().toString().trim());
                             cmn.setText(snapshot.child("mobile_no").getValue().toString().trim());
                             cem.setText(snapshot.child("email").getValue().toString().trim());
                             cci.setText(snapshot.child("City").getValue().toString().trim());

                         }

                         @Override
                         public void onCancelled(@NonNull DatabaseError error) {

                         }
                     });


                 }


                 @Override
                 public void onCancelled(@NonNull DatabaseError error) {


                 }

             });
             next.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     v1++;
                     Toast.makeText(getApplicationContext(), sv1+","+val, Toast.LENGTH_SHORT).show();
//                     Intent intent1=new Intent(New_Olders.this,New_Olders.class);
//                     startActivity(intent1);

                 }
             });
//            if(val<=v1) {
//            continue;
//            }

        }
         }
    //}

