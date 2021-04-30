package com.example.printing_solution;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executor;

public class login_frag extends Fragment implements View.OnClickListener{
    private EditText email,password;
    private Button login;
    private String emails,passwords;
    private FirebaseAuth firebaseAuth1;
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login_frag, container, false);
        firebaseAuth1= FirebaseAuth.getInstance();
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        login = (Button)view.findViewById(R.id.login);
        login.setOnClickListener(this);
        return view;
    }
    public void login(String email, String password)
    {
        if(email.isEmpty())
        {
            Toast.makeText(getContext(), "Provide Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.isEmpty())
        {
            Toast.makeText(getContext(), "Provide Password", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth1.signInWithEmailAndPassword(email,password).addOnCompleteListener(getActivity(), task -> {
            if (task.isSuccessful())
            {
                FirebaseUser current_user =FirebaseAuth.getInstance().getCurrentUser();
                String uid= current_user.getUid();
                DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                dbref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String type = snapshot.child("type").getValue().toString();
                        if (type.equals("admin")){
                            Toast.makeText(getContext(),type,Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(),Admin.class);
                            startActivity(intent);


                        }
                        else{

                            Toast.makeText(getView().getContext(),type,Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(),User.class);
                            startActivity(intent);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
            else
            {
                Toast.makeText(getContext(),"Unable to signin",Toast.LENGTH_SHORT).show();

            }
        });

    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login:
                emails = email.getText().toString().toLowerCase().trim();
                passwords = password.getText().toString().trim();
                login(emails,passwords);
                break;
            default:
                Toast.makeText(getContext(),"Nothing Clicked",Toast.LENGTH_SHORT).show();
        }
    }
}