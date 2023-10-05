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

public class StokAdapter extends RecyclerView.Adapter<StokAdapter.MyViewHolder>{


    private Context context;
    private List<Stok> stokList;

    public StokAdapter(Context context) {
        this.context = context;
        stokList = new ArrayList<>();
    }

    public void addStok(Stok stok){
        stokList.add(stok);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.data_stok,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Stok stok = stokList.get(position);
        holder.namabrg.setText(stok.getnamaBrg());
        holder.Jmlh.setText(stok.getJmlh());
        holder.Vendor.setText(stok.getVendor());
        holder.tgl.setText(stok.getTgl());
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
        return stokList.size();
    }

    public Stok getItem(int position) {
        return stokList.get(position);
    }

    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        private TextView namabrg, Jmlh, Vendor, tgl;

        private CardView myCardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            namabrg = itemView.findViewById(R.id.namabrg);
            Jmlh = itemView.findViewById(R.id.Jmlh);
            Vendor = itemView.findViewById(R.id.Vendor);
            tgl = itemView.findViewById(R.id.tgl);
            myCardView = itemView.findViewById(R.id.myCardView);

        }
    }
}
