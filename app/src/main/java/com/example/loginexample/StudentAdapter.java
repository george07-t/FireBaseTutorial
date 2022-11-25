package com.example.loginexample;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class StudentAdapter extends ArrayAdapter<StudentDataBase> {

    private Activity context;
    private List<StudentDataBase> studentDataBaseList;

    public StudentAdapter(Activity  context,List<StudentDataBase> studentDataBaseList) {
        super(context, R.layout.list_sample, studentDataBaseList);
        this.context = context;
        this.studentDataBaseList = studentDataBaseList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
       View view= inflater.inflate(R.layout.list_sample,null,true);
        StudentDataBase studentDataBase=studentDataBaseList.get(position);
        TextView t1=view.findViewById(R.id.loadt1);
        TextView t2=view.findViewById(R.id.loadt2);
        t1.setText("Name : "+studentDataBase.getName());
        t2.setText("Age : "+studentDataBase.getAge());
        return view;
    }
}
