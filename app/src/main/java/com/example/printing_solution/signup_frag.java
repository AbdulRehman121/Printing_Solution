package com.example.printing_solution;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class signup_frag extends Fragment implements View.OnClickListener{
    private Button SIGNUP;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    private CheckBox token;
    private EditText email,password,names,com_name1,ph;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_signup_frag, container, false);
        firebaseAuth= FirebaseAuth.getInstance();
        email = (EditText) view.findViewById(R.id.user_email);
        password = (EditText) view.findViewById(R.id.password);
        names = (EditText) view.findViewById(R.id.User_name);
        com_name1 = (EditText) view.findViewById(R.id.city1);
        ph = (EditText)view.findViewById(R.id.user_mobile_no);
        SIGNUP = (Button)view.findViewById(R.id.Add);
        SIGNUP.setOnClickListener(this);
        token = (CheckBox)view.findViewById(R.id.condiion);
        return view;
    }
    public void signup(String email,String pass,String username,String city,String mobile)
    {
        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(getContext(),"Email Required",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(pass))
        {
            Toast.makeText(getContext(),"Password Required",Toast.LENGTH_SHORT).show();
            return;
        }
        if(token.isChecked()==true)
        {
            firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(getActivity(), task -> {
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
                    mDatabase.setValue(userMap).addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getContext(),"User Registered, Please Login",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent (getActivity(),reg.class);
                                startActivity(intent);

                            }
                            else
                            {
                                Toast.makeText(getContext(),"Unable to store data",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }
                else
                {
                    Toast.makeText(getContext(),"Unable to Register",Toast.LENGTH_SHORT).show();
                }

            });}
        else
        {
            Toast.makeText(getContext(),"Please agree to terms and conditions first",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Add:


                String em = email.getText().toString().toLowerCase().trim();
                String pass = password.getText().toString().trim();
                String name= names.getText().toString().trim();

                String weight = com_name1.getText().toString().trim();
                String mobile= ph.getText().toString().trim();
                signup(em, pass,name,weight,mobile);
            default:
                return;
        }
    }
}