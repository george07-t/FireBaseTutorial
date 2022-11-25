package com.example.loginexample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ImageUpload extends AppCompatActivity implements View.OnClickListener {
    private Button cb, sb, db;
    private EditText ed1;
    private ImageView im1;
    private ProgressBar pim;
    private Uri imuri;
    private static final int IMAGE_REQUEST = 1;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    StorageTask uploadtask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);
        databaseReference = FirebaseDatabase.getInstance().getReference("Upload");
        storageReference = FirebaseStorage.getInstance().getReference("Upload");

        cb = findViewById(R.id.cim1);
        sb = findViewById(R.id.sim1);
        db = findViewById(R.id.dim1);
        ed1 = findViewById(R.id.eim1);
        im1 = findViewById(R.id.im1);
        pim = findViewById(R.id.pim1);
        cb.setOnClickListener(this);
        sb.setOnClickListener(this);
        db.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i=0;
        if (view.getId() == R.id.sim1) {
            if (uploadtask != null && uploadtask.isInProgress()) {
                Toast.makeText(getApplicationContext(), "Wait for Uploading", Toast.LENGTH_SHORT).show();
            } else {
               int x= saveimage(i);
               if(x==1)
               {
                   ed1.getText().clear();
                   im1.setImageResource(0);
               }

            }

        }
        if (view.getId() == R.id.dim1) {
            Intent intent=new Intent(ImageUpload.this,DisplayImage.class);
            startActivity(intent);
        }
        if (view.getId() == R.id.cim1) {
            openfilechooser();
        }
    }

    public void openfilechooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    public String getFileextension(Uri imr1) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imr1));
    }

    public int  saveimage(int j) {
        String imname = ed1.getText().toString().trim();
        if (imname.isEmpty()) {
            ed1.setError("Enter a Name");
            ed1.requestFocus();
            Toast.makeText(getApplicationContext(), "Enter a name", Toast.LENGTH_SHORT).show();
           // return;
        } else {
            StorageReference ref = storageReference.child(System.currentTimeMillis() + "." + getFileextension(imuri));
            ref.putFile(imuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(), "Successfully Stored", Toast.LENGTH_SHORT).show();

                    Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful());
                    Uri downuri=uriTask.getResult();
                    UploadImage uploadImage = new UploadImage(imname, downuri.toString());
                    String uploadid = databaseReference.push().getKey();
                    databaseReference.child(uploadid).setValue(uploadImage);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Unsuccessfully Stored" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
return j=1;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imuri = data.getData();
            Picasso.get().load(imuri).into(im1);
        }
    }
}