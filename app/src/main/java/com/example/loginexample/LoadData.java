package com.example.loginexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoadData extends AppCompatActivity {
    private ListView loaddataid;
    DatabaseReference databaseReference;
    private List<StudentDataBase> studentDataBaseList;
    private StudentAdapter studentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_data);
        loaddataid = (ListView) findViewById(R.id.loaddataid);
        databaseReference = FirebaseDatabase.getInstance().getReference("Students");
        studentDataBaseList = new ArrayList<>();
        studentAdapter = new StudentAdapter(LoadData.this, studentDataBaseList);

    }

    @Override
    protected void onStart() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                studentDataBaseList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    StudentDataBase studentDataBase = snapshot1.getValue(StudentDataBase.class);
                    studentDataBaseList.add(studentDataBase);
                }
                loaddataid.setAdapter(studentAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        super.onStart();
    }
}