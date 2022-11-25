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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUpExam extends AppCompatActivity implements View.OnClickListener {
    private EditText lged3,lged5;
    private Button lgb2;
    private TextView lgt2;
    private ProgressBar lgp2;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_exam);
        lged3=(EditText) findViewById(R.id.lged2);

        lged5=(EditText) findViewById(R.id.lged4);
        lgb2=(Button) findViewById(R.id.lgb2);
        lgt2=(TextView) findViewById(R.id.lgt2);
        lgp2=(ProgressBar) findViewById(R.id.lgp2);
        lgb2.setOnClickListener(this);
        lgt2.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.lgb2)
        {
            UserRegister();
        }
        else if(view.getId()==R.id.lgt2)
        {
            Intent intent=new Intent(SignUpExam.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    private void UserRegister() {
        String email=lged3.getText().toString().trim();
        String password =lged5.getText().toString().trim();
        lgp2.setVisibility(View.VISIBLE);
        if(email.isEmpty()){
            lged3.setError("Enter Email");
            lged3.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            lged3.setError("Enter a valid Email");
            lged3.requestFocus();
            return;
        }

        if(password.isEmpty()){
            lged5.setError("Enter Password");
            lged5.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            lged5.setError("Password will be more than 6 Characters");
            lged5.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        lgp2.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Intent intent=new Intent(SignUpExam.this,MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                            Toast.makeText(getApplicationContext(), "Register Successfull", Toast.LENGTH_SHORT).show();
                        } else {
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(getApplicationContext(), "User already Registered", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                });
    }
}