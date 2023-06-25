package com.example.qrscanner;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder>{


    private Context context;
    private List<Kategori> kategoriList;

    public UserAdapter(Context context) {
        this.context = context;
        kategoriList = new ArrayList<>();
    }

    public void addKategori(Kategori kategori){
        kategoriList.add(kategori);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.data_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Kategori kategori = kategoriList.get(position);
        holder.kate.setText(kategori.getKategori());
        holder.jmlh.setText(kategori.getJmlh());
        holder.myCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return kategoriList.size();
    }

    public Kategori getItem(int position) {
        return kategoriList.get(position);
    }

    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView kate, jmlh;

        private CardView myCardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            kate = itemView.findViewById(R.id.kategori);
            jmlh = itemView.findViewById(R.id.jmlh);
            myCardView = itemView.findViewById(R.id.myCardView);

        }
    }
}
