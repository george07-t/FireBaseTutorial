package com.example.loginexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText lged1,lged2;
    private Button lgb1;
    private TextView lgt1;
    private ProgressBar lgp1;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lged1=(EditText) findViewById(R.id.lged1);
        lged2=(EditText) findViewById(R.id.lged2);
        lgb1=(Button) findViewById(R.id.lgb1);
        lgt1=(TextView) findViewById(R.id.lgt1);
        lgp1=(ProgressBar) findViewById(R.id.lgp1);
        lgp1.setVisibility(View.INVISIBLE);
        lgb1.setOnClickListener(this);
        lgt1.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.lgb1)
        {

            UserLogin();
        }
        else if(view.getId()==R.id.lgt1)
        {
            Intent intent=new Intent(MainActivity.this,SignUpExam.class);
            startActivity(intent);
            finish();
        }
    }

    private void UserLogin() {
        String email =lged1.getText().toString().trim();
        String password =lged2.getText().toString().trim();
        lgp1.setVisibility(View.VISIBLE);
        if(email.isEmpty()){
            lged1.setError("Enter Email");
            lged1.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            lged1.setError("Enter a valid Email");
            lged1.requestFocus();
            return;
        }

        if(password.isEmpty()){
            lged2.setError("Enter Password");
            lged2.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            lged2.setError("Password will be more than 6 Characters");
            lged2.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
           lgp1.setVisibility(View.GONE);
                if(task.isSuccessful())
           {
               Intent intent=new Intent(MainActivity.this,SuccesssfullyLoggedIn.class);
               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               startActivity(intent);
               finish();
           }
           else{
               Toast.makeText(getApplicationContext(), "Log in unsuccessful", Toast.LENGTH_SHORT).show();
           }
            }
        });


    }

}