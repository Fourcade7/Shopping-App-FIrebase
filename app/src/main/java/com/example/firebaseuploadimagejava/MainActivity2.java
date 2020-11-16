package com.example.firebaseuploadimagejava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity2 extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerView2;
    private ImageAdapter mAdapter;
    private ProductAdapter productAdapter;
    private ProgressBar mProgressCircle;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference mDatabaseRef2;
    private ArrayList<Upload> mUploads;
    private ArrayList<Upload> mUploads2;

    private FirebaseStorage mStorage;
    private FirebaseStorage mStorage2;

    private ValueEventListener mDBListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mProgressCircle = findViewById(R.id.progressbar);
        mUploads = new ArrayList<>();
        mUploads2 = new ArrayList<>();

        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mRecyclerView2 = findViewById(R.id.recyclerview2);
        mRecyclerView2.setHasFixedSize(true);
       mRecyclerView2.setLayoutManager(new GridLayoutManager(this,2));
        readtodatabase("uploads");
        readtodatabase2("products");


    }
    public void readtodatabase2(String readpath2){
        mStorage2 = FirebaseStorage.getInstance();
        mDatabaseRef2 = FirebaseDatabase.getInstance().getReference(readpath2);

        mDatabaseRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUploads2.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Upload upload2 = postSnapshot.getValue(Upload.class);
                    mUploads2.add(upload2);
                }

//                mAdapter = new ImageAdapter(MainActivity2.this,mUploads);
                productAdapter=new ProductAdapter(MainActivity2.this,mUploads2);
//                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView2.setAdapter(productAdapter);


                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity2.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void readtodatabase(String readpath){
        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(readpath);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUploads.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    mUploads.add(upload);
                }

                mAdapter = new ImageAdapter(MainActivity2.this,mUploads);
                mRecyclerView.setAdapter(mAdapter);
                //  mRecyclerView2.setAdapter(mAdapter);


                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity2.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }
    public void Delteitem(String key,String imgurl) {
//        Upload selectedItem = mUploads.get(position);
          String selectedKey = key;
          String selectedUrl = imgurl;
          StorageReference imageRef = mStorage.getReferenceFromUrl(selectedUrl);
          imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabaseRef.child(selectedKey).removeValue();
                Toast.makeText(MainActivity2.this, "Item deleted", Toast.LENGTH_SHORT).show();
                mAdapter.notifyDataSetChanged();
            }
        });
    }
    public void Delteitem2(String key,String imgurl) {
//        Upload selectedItem = mUploads.get(position);
        String selectedKey2 = key;
        String selectedUrl2 = imgurl;
        StorageReference imageRef2 = mStorage2.getReferenceFromUrl(selectedUrl2);
        imageRef2.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabaseRef2.child(selectedKey2).removeValue();
                Toast.makeText(MainActivity2.this, "Item deleted", Toast.LENGTH_SHORT).show();
                productAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}