package com.example.printing_solution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity implements View.OnClickListener{
    private EditText user_id,user_password;
    private Button login;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth1;
    private String FID, type;
    TextView registration;
    DatabaseReference DR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setContentView(R.layout.activity_login);
        login = (Button)findViewById(R.id.login);
        registration =  (TextView)findViewById(R.id.reg);
        login.setOnClickListener(this);
        registration.setOnClickListener(this);
        firebaseAuth1= FirebaseAuth.getInstance();
        progressDialog =new ProgressDialog(this);

        FID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //DR = FirebaseDatabase.getInstance().getReference().child("Users").child(String.valueOf(FID));

    }
    public void LOGIN(String email,String password)
    {
        if(email.isEmpty())
        {
            Toast.makeText(this, "Provide Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.isEmpty())
        {
            Toast.makeText(this, "Provide Password", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Logging in");
        progressDialog.show();
        firebaseAuth1.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful())
            {
                FirebaseUser current_user =FirebaseAuth.getInstance().getCurrentUser();
                String uid= current_user.getUid();
                DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                dbref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        type = snapshot.child("type").getValue().toString();
                        if (type.equals("admin")){
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),type,Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(login.this,Admin.class);
                            startActivity(intent);
                            finish();

                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),type,Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(login.this,User.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
            else
            {
                Toast.makeText(this,"Unable to signin",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }
    public void register(){
        Toast.makeText(this,"Register Here",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent (login.this, registration.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login:
                user_id = (EditText)findViewById(R.id.user_id);
                user_password = (EditText)findViewById(R.id.user_password);
                String email = user_id.getText().toString().toLowerCase().trim();
                String password = user_password.getText().toString().trim();
                LOGIN(email,password);
                break;
            case R.id.reg:
                register();
                break;

        }
    }
}