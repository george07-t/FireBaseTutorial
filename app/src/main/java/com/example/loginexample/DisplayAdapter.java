package com.example.loginexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class DisplayAdapter extends RecyclerView.Adapter<DisplayAdapter.MyViewHolder> {
    private Context context;
    private List<UploadImage> uploadImageslist;

    public DisplayAdapter(Context context, List<UploadImage> uploadImageslist) {
        this.context = context;
        this.uploadImageslist = uploadImageslist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);

       View view= layoutInflater.inflate(R.layout.item_layout,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UploadImage uploadImage =uploadImageslist.get(position);
        holder.t1.setText(uploadImage.getImagename());
        Picasso .get()
                .load(uploadImage.getImageuri())
                .placeholder(R.mipmap.ic_launcher_round)
                .fit()
                .centerCrop()
                .into(holder.im1);
    }

    @Override
    public int getItemCount() {
        return uploadImageslist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView t1;
        ImageView im1;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            t1=itemView.findViewById(R.id.dist1);
            im1=itemView.findViewById(R.id.disim);
        }
    }
}
