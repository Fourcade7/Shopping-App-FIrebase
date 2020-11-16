package com.example.firebaseuploadimagejava;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter  extends RecyclerView.Adapter<ProductAdapter.Myviewholder2> {

    Context context;
    ArrayList<Upload> arrayList2=new ArrayList<>();

    public ProductAdapter(Context context, ArrayList<Upload> arrayList2) {
        this.context = context;
        this.arrayList2 = arrayList2;
    }

    @NonNull
    @Override
    public Myviewholder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item,parent,false);
        Myviewholder2 myviewHolder2=new Myviewholder2(view);
        return myviewHolder2;
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder2 holder, int position) {
        Upload upload=arrayList2.get(position);

        holder.textView1.setText(upload.getmName());
        holder.textView2.setText(upload.getmOrder());
        Picasso.get().load(upload.getmImageUrl()).placeholder(R.mipmap.ic_launcher).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity2 activity= (MainActivity2) v.getContext();
                activity.Delteitem2(upload.getmKey(),upload.getmImageUrl());

                Toast.makeText(context,upload.getmKey()+upload.getmImageUrl(),Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList2.size();
    }

    public class Myviewholder2 extends RecyclerView.ViewHolder {

        TextView textView1,textView2;
        ImageView imageView;
        public Myviewholder2(@NonNull View itemView) {
            super(itemView);

            textView1=itemView.findViewById(R.id.txtvproduct);
            textView2=itemView.findViewById(R.id.txtvproduct2);
            imageView=itemView.findViewById(R.id.imgviewproduct);
        }
    }
}
