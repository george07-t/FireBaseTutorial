package com.example.loginexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SuccesssfullyLoggedIn extends AppCompatActivity {
    FirebaseAuth mauth;
    private Button saveid, loadid,imageid;
    private EditText nameid, ageid;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successsfully_logged_in);
        mauth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Students");
        saveid = (Button) findViewById(R.id.saveid);
        loadid = (Button) findViewById(R.id.loadid);
        imageid = (Button) findViewById(R.id.imageid);
        nameid = (EditText) findViewById(R.id.nameid);
        ageid = (EditText) findViewById(R.id.ageid);
        imageid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),ImageUpload.class);
                startActivity(intent);
            }
        });
        saveid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    savadata();
                } catch (Exception e) {
                    nameid.setError("Enter Name");
                    nameid.requestFocus();
                    return;
                }


            }
        });
        loadid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent=new Intent(SuccesssfullyLoggedIn.this,LoadData.class);
                    startActivity(intent);
                } catch (Exception e) {
                    nameid.setError("No Data");
                    nameid.requestFocus();
                    return;
                }
            }
        });

    }

    public void savadata() {
        String name = nameid.getText().toString();
        String age = ageid.getText().toString();
        Double age1 = Double.parseDouble(age);
        String key = databaseReference.push().getKey();
        StudentDataBase studentDataBase = new StudentDataBase(name, age);
        databaseReference.child(key).setValue(studentDataBase);
        Toast.makeText(getApplicationContext(), "Stored", Toast.LENGTH_SHORT).show();
        nameid.getText().clear();
        ageid.getText().clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.signout_example, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.so1) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(SuccesssfullyLoggedIn.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = mauth.getCurrentUser();
        if (firebaseUser != null) {
            //user log in
        } else {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}