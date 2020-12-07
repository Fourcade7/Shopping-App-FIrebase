package com.example.firebaseuploadimagejava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private TextView mTextViewShowUploads;
    private EditText mEditTextFileName;
    private EditText mEditTextFileName2;
    private EditText mEditTextFileName3;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private ProgressBar mProgressBar2;
    private Uri mImageUri;
    private StorageTask mUploadTask;
    int a=0;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    Toast ts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ts=Toast.makeText(MainActivity.this, "Upload Sucsessfullly", Toast.LENGTH_SHORT);
        mButtonChooseImage = findViewById(R.id.button_choose_image);
        mButtonUpload = findViewById(R.id.button_upload);
        mTextViewShowUploads = findViewById(R.id.text_view_show_uploads);
        mEditTextFileName = findViewById(R.id.edit_text_file_name);
        mEditTextFileName2 = findViewById(R.id.edit_text_file_name2);
        mEditTextFileName3 = findViewById(R.id.edit_text_file_name3);
        mImageView = findViewById(R.id.image_view);
        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar2 = findViewById(R.id.progress_bar2);

        mProgressBar.setVisibility(View.INVISIBLE);
        mProgressBar2.setVisibility(View.INVISIBLE);



        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        mButtonUpload.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
               // mEditTextFileName.requestFocus()
                mProgressBar.setVisibility(View.VISIBLE);
                if (mUploadTask != null && mUploadTask.isInProgress() ) {
                    Toast.makeText(MainActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {

                    if (mEditTextFileName.getText().toString().equals("")){
                        Toast.makeText(MainActivity.this, "Zb uje", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        uploadFile(mEditTextFileName.getText().toString(),mEditTextFileName2.getText().toString(),mEditTextFileName3.getText().toString());

                    }

                }
            }
        });

        mTextViewShowUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MainActivity2.class));
            }
        });

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(mImageView);
        }
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadFile(String uploadpath,String productname,String order) {
        mStorageRef = FirebaseStorage.getInstance().getReference(uploadpath);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(uploadpath);
        if (mImageUri != null && !mEditTextFileName.getText().toString().equals("")) {

            StorageReference filereference=mStorageRef.child(System.currentTimeMillis()+"."+getFileExtension(mImageUri));

            filereference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               filereference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                   @Override
                   public void onSuccess(Uri uri) {
                        String uploadId = mDatabaseRef.push().getKey();
                        Upload upload=new Upload(productname,order,uri.toString(),uploadId);

                       mDatabaseRef.child(uploadId).setValue(upload);
                        // mDatabaseRef.child(Objects.requireNonNull(mDatabaseRef.push().getKey())).setValue(upload);
                       mProgressBar.setVisibility(View.INVISIBLE);
                       mProgressBar2.setVisibility(View.VISIBLE);
                       Timer timer=new Timer();

                       TimerTask tt=new TimerTask() {
                           @Override
                           public void run() {

                            mProgressBar2.setProgress(a);
                            a=a+1;
                           if (a==100){
                               timer.cancel();
                               a=0;
                               mProgressBar2.setProgress(a);
                               ts.show();
                               mProgressBar2.setVisibility(View.INVISIBLE);
                           }
                           }
                       };
                       timer.schedule(tt,0,50);

                   }
               });
                }
            });


        } else {
            Toast.makeText(this, "Rasm tanlab nom yoz", Toast.LENGTH_SHORT).show();
        }
    }
}