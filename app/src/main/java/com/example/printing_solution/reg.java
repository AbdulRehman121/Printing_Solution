package com.example.printing_solution;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class reg extends AppCompatActivity implements View.OnClickListener{
    private Button login,signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        login = (Button)findViewById(R.id.login);

        signup = (Button)findViewById(R.id.signup);
        login();
        login.setOnClickListener(this);
        signup.setOnClickListener(this);
    }

    public void login()
    {
        login_frag lf = new login_frag();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container,lf);
        ft.commit();

    }
    public void signup()
    {
        signup_frag sf = new signup_frag();
        FragmentTransaction st = getSupportFragmentManager().beginTransaction();
        st.replace(R.id.container,sf);
        st.commit();
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login:
                login();
                break;
            case R.id.signup:
                signup();
                break;
        }
    }
}