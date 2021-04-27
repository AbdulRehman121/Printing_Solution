package com.example.printing_solution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import static android.widget.Toast.LENGTH_SHORT;
import static java.lang.System.exit;

public class Flex extends AppCompatActivity {
private Spinner spinner1;
private Button calculate,oder;
private TextView height,wirth,cost,Quantaty,address,da;
public String SI,heights,width,Q, values,ds;
public Double size,h,w,p,q;
public int val;
private DatabaseReference mDatabase;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flex);
        spinner1=(Spinner)findViewById(R.id.spinner1);
height=findViewById(R.id.height);
wirth=findViewById(R.id.width);
cost=(TextView)findViewById(R.id.cost);
Quantaty=findViewById(R.id.Quantaty);
p=1.00;
calculate=(Button) findViewById(R.id.calculate);
oder=(Button)findViewById(R.id.Oder);
address=findViewById(R.id.PostalAddress);
    da=findViewById(R.id.DA);

        ArrayAdapter<String> myAdapter= new ArrayAdapter<String>
     (Flex.this, android.R.layout.simple_list_item_1,
            getResources().getStringArray(R.array.FlexQuilty_arrays));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(myAdapter);

calculate.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        SI= spinner1.getSelectedItem().toString().trim();
        heights = height.getText().toString();
        width = wirth.getText().toString();
        Q=Quantaty.getText().toString().trim();
        if(SI.equals("Select Quality"))
        {
            Toast.makeText(getApplicationContext(), "QUALITY", LENGTH_SHORT).show();
            return;
        }
        if(heights.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "HEIGHT", LENGTH_SHORT).show();
            return;
        }if(width.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "WIDTH", LENGTH_SHORT).show();
            return;
        }if(Q.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Quantaty", LENGTH_SHORT).show();
            return;
        }

        h = new Double(heights);
        w = new Double(width);
        q = new Double(Q);
        size = h*w;

        if(SI.equals("China"))
        {
            p=size*25;
          //  Toast.makeText(getApplicationContext(),p.toString(), LENGTH_SHORT).show();

        }else if(SI.equals("Star"))
        {
            p=size*35;
        }else if(SI.equals("Backlit just flex"))
        {
            p=size*100;
        }else if(SI.equals("Backlit with all"))
        {
            p=size*650;
        }else if(SI.equals("Onevision with pasting"))
        {
            p=size*120;
        }else if(SI.equals("Vinyal with pasting"))
        {
            p=size*120;
        }else if(SI.equals("Onevision with pasting"))
        {
            p=size*35;
        }else if(SI.equals("Vinyal with pasting+lamentation"))
        {
            p=size*160;
        }else if(SI.equals("Reflector"))
        {
            p=size*150;
        }
        else {

            Toast.makeText(getApplicationContext(),"Select Qualtity",Toast.LENGTH_SHORT).show();
        }
        p=p*q;
        cost.setText("Rs."+p.toString());
        address.setVisibility(address.VISIBLE);
        da.setVisibility(da.VISIBLE);
        oder.setVisibility(oder.VISIBLE);
    }

});


oder.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        //get current order
        DatabaseReference cdbname = FirebaseDatabase.getInstance().getReference();

        ds=address.getText().toString().trim();
        if(ds.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Delivery Address Missing", LENGTH_SHORT).show();
            return;
        }
        //change current order value
        if (!height.getText().toString().equals(null) || !wirth.getText().toString().equals(null)) {
            cdbname.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    values = snapshot.child("prev_value").getValue().toString();
                    val = new Integer(values);
                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = current_user.getUid();
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Orders").child(values);
                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("User Id", uid);
                    //userMap.put("");
                    userMap.put("Height", heights);
                    userMap.put("Width", width);
                    userMap.put("Quantity", Q);
                    userMap.put("Quality", SI);
                    userMap.put("Price", p.toString());
                    userMap.put("Status", "Order Placed");
                    userMap.put("Product", "Flex");
                    userMap.put("DAddress", ds);
                    if (!height.getText().toString().equals(null)) {
                        mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Order Placed", Toast.LENGTH_SHORT).show();
                                    val = val + 1;
                                    values = String.valueOf(val);
                                    cdbname.child("prev_value").setValue(values);

                                    exit(0);

                                    height.setText(null);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Check Internet Connection", LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Please select credentails",LENGTH_SHORT).show();
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            //DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("orders").child(uid);


        }



    }
});

    }
}
