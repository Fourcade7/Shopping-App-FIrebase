package com.example.firebaseuploadimagejava;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyviewHolder> {


    Context context;
    ArrayList<Upload> arrayList=new ArrayList<>();

    public ImageAdapter(Context context, ArrayList<Upload> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item,parent,false);
        MyviewHolder myviewHolder=new MyviewHolder(view);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
       // Collections.reverse(arrayList);
        Upload upload=arrayList.get(position);

        holder.textView1.setText(upload.getmName());
        holder.textView2.setText(upload.getmOrder());

        Picasso.get().load(upload.getmImageUrl()).placeholder(R.drawable.badge).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity2 activity= (MainActivity2) v.getContext();
               // activity.readtodatabase2();
                if (upload.getmName().equals("Mevalar")){
                    activity.readtodatabase2("Mevalar");
                }
                if (upload.getmName().equals("Sabzavotlar")){
                    activity.readtodatabase2("Sabzavotlar");
                }
                if (upload.getmName().equals("Non")){
                    activity.readtodatabase2("Non");
                }





                Toast.makeText(context,upload.getmName(),Toast.LENGTH_SHORT).show();

            }
        });





    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {

        TextView textView1,textView2;
        ImageView imageView;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            textView1=itemView.findViewById(R.id.txtview1);
            textView2=itemView.findViewById(R.id.txtview2);
            imageView=itemView.findViewById(R.id.imgview);


        }



    }




}
