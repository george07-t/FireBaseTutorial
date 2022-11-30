package com.example.loginexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Lab_Work extends AppCompatActivity {
    private Button b1, b2;
    private EditText e1, e2, e3, e4;
    private TextView t1;
    DatabaseReference databaseReference;
    int maxid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_work);
        b1 = (Button) findViewById(R.id.enterid);
        e1 = (EditText) findViewById(R.id.nameid);
        e2 = (EditText) findViewById(R.id.cgpaid);
        b2 = (Button) findViewById(R.id.displayid);
        e3 = (EditText) findViewById(R.id.namedisid);
        e4 = (EditText) findViewById(R.id.cgpadisid);
        t1 = (TextView) findViewById(R.id.disdata);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("cgpalist");
        cgpaList cgpal = new cgpaList();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
           maxid= (int) snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = e1.getText().toString().trim();
                float cgpa = Float.parseFloat(e2.getText().toString().trim());
                cgpal.setName(name);
                cgpal.setCgpa(cgpa);
                databaseReference.child(String.valueOf(maxid+1)).setValue(cgpal);
                Toast.makeText(getApplicationContext(), "Added...", Toast.LENGTH_SHORT).show();
                e1.getText().clear();
                e2.getText().clear();

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference().child("cgpalist").child("1");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String gpa=snapshot.child("cgpa").getValue().toString();
                        String name=snapshot.child("name").getValue().toString();
                        e3.setText(name);
                        e4.setText(gpa);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {


                    }
                });
            }
        });
    }
}