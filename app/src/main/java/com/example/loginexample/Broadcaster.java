package com.example.loginexample;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class Broadcaster extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(!isConnected(context)){
            Toast.makeText(context.getApplicationContext(), "No Internet Connection ", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            View layout_dialog= LayoutInflater.from(context).inflate(R.layout.no_internet,null);
            builder.setView(layout_dialog);

            AppCompatButton refresh=layout_dialog.findViewById(R.id.inb1);
            AlertDialog dialog=builder.create();
            dialog.show();
            dialog.setCancelable(false);
            dialog.getWindow().setGravity(Gravity.CENTER);
            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    onReceive(context,intent);
                }
            });
        }
    }
    private boolean isConnected(Context context)
    {
        ConnectivityManager connectivityManager=(ConnectivityManager) context.getApplicationContext().getSystemService(context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo()!=null&& connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
