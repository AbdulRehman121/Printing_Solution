package com.example.printing_solution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.widget.Toast.LENGTH_SHORT;

public class New_Olders extends AppCompatActivity implements View.OnClickListener{
private TextView cn,cmn,cem,cci,pn,pqul,pqan,ps,da,pp,ono,os;
    DatabaseReference DR,DR1;
    private FirebaseUser FU;
    private String FID,UU,values,sv1;
    private Button previous,load;
    private int  val,v1=0;
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
        ono=findViewById(R.id.OrderNo);
        os=findViewById(R.id.Ostatus);
        previous=findViewById(R.id.prev);
        previous.setVisibility(previous.GONE);
        previous.setOnClickListener(this);
        load = (Button)findViewById(R.id.load);
        load.setOnClickListener(this);
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

        }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.load){
            previous.setVisibility(previous.VISIBLE);
            load.setText("Next");
            ++v1;
        }
        else if(v.getId() == R.id.prev && v1!=1)
        {
           // Toast.makeText(getApplicationContext(),"First Item Shown",LENGTH_SHORT).show();
            v1=v1-1;
        }
        if (v1<val && v1!=0)
        {
        sv1 = String.valueOf(v1);
        ono.setText("Order NO: "+sv1);
        DR = FirebaseDatabase.getInstance().getReference().child("Orders").child(sv1);
        DR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pn.setText("Product: "+snapshot.child("Product").getValue().toString());
                pqul.setText("Quality: "+snapshot.child("Quality").getValue().toString());
                ps.setText("Size: "+snapshot.child("Height").getValue().toString() + " x " + snapshot.child("Width").getValue().toString());
                //  pqan.setText(snapshot.child("Quantity").getValue().toString());
                da.setText("Delivrey Address: "+snapshot.child("DAddress").getValue().toString());
                pp.setText("Price: "+snapshot.child("Price").getValue().toString());
                os.setText("Status: "+snapshot.child("Status").getValue().toString());
                UU = (snapshot.child("User Id").getValue().toString());
                DR1 = FirebaseDatabase.getInstance().getReference().child("Users").child(UU);
                DR1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        cn.setText("Nmae: "+snapshot.child("name").getValue().toString().trim());
                        cmn.setText("Mobile No: "+snapshot.child("mobile_no").getValue().toString().trim());
                        cem.setText("Email: "+snapshot.child("email").getValue().toString().trim());
                        cci.setText("City: "+snapshot.child("City").getValue().toString().trim());

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
    }
    else
        {
            Toast.makeText(getApplicationContext(),"End of list",LENGTH_SHORT).show();
        }
    }

}