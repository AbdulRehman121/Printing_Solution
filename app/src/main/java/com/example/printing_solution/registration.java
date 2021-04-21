package com.example.printing_solution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

public class registration extends AppCompatActivity implements View.OnClickListener {
    private Button SIGNUP;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    private CheckBox token;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        firebaseAuth= FirebaseAuth.getInstance();
        SIGNUP = (Button)findViewById(R.id.Add);
        SIGNUP.setOnClickListener(this);
        token = (CheckBox)findViewById(R.id.condiion);
        progressDialog = new ProgressDialog(this);

    }
    public void signup(String email,String pass,String username,String city,String mobile)
    {
        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(getApplicationContext(),"Email Required",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(pass))
        {
            Toast.makeText(getApplicationContext(),"Password Required",Toast.LENGTH_SHORT).show();
            return;
        }
        if(token.isChecked()==true)
        {progressDialog.setMessage("Registering......");
            progressDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this, task -> {
                if (task.isSuccessful())
                {
                    FirebaseUser current_user =FirebaseAuth.getInstance().getCurrentUser();
                    String uid= current_user.getUid();
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("name", username);
                    userMap.put("City", city);
                    userMap.put("type", "user");
                    userMap.put("email", email);
                    userMap.put("mobile_no", mobile);
                    mDatabase.setValue(userMap).addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"User Registered, Please Login",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent (registration.this,MainActivity.class);
                                progressDialog.dismiss();
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Unable to store data",Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Unable to Register",Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            });}
        else
        {
            Toast.makeText(this,"Please agree to terms and conditions first",Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Add:
                EditText email = (EditText) findViewById(R.id.user_email);
                EditText password = (EditText) findViewById(R.id.password);
                EditText names = (EditText) findViewById(R.id.User_name);


                EditText com_name1 = (EditText) findViewById(R.id.city1);
                EditText ph = (EditText)findViewById(R.id.user_mobile_no);

                String em = email.getText().toString().toLowerCase().trim();
                String pass = password.getText().toString().trim();
                String name= names.getText().toString().trim();
                //String gender;
                String weight = com_name1.getText().toString().trim();
                String mobile= ph.getText().toString().trim();
                signup(em, pass,name,weight,mobile);
            default:
                return;
        }
    }
}
