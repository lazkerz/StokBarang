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

public class BrgAdapter extends RecyclerView.Adapter<BrgAdapter.MyViewHolder>{


    private Context context;
    private List<Barang> barangList;

    public BrgAdapter(Context context) {
        this.context = context;
        barangList = new ArrayList<>();
    }

    public void addBarang(Barang barang){
        barangList.add(barang);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.data_brg,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Barang barang = barangList.get(position);
        holder.kate.setText(barang.getKategori());
        holder.no.setText(barang.getNo());
        holder.namabrg.setText(barang.getBarang());
        holder.nrk.setText(barang.getNrk());
        holder.nama.setText(barang.getNama());
        holder.jbtn.setText(barang.getJabatan());
        holder.tgl.setText(barang.getTgl());
        holder.id.setText(barang.getIdBrg());
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
        return barangList.size();
    }

    public Barang getItem(int position) {
        return barangList.get(position);
    }

    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView kate;
        private TextView no;
        private TextView namabrg;
        private TextView nrk;
        private TextView nama;
        private TextView jbtn;
        private TextView tgl;
        private TextView id;

        private CardView myCardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            kate = itemView.findViewById(R.id.jeniskateg);
            no = itemView.findViewById(R.id.no);
            namabrg = itemView.findViewById(R.id.namabrg);
            nrk = itemView.findViewById(R.id.nrk);
            nama = itemView.findViewById(R.id.nama);
            jbtn = itemView.findViewById(R.id.jbtn);
            tgl = itemView.findViewById(R.id.tgl);
            id = itemView.findViewById(R.id.id);
            myCardView = itemView.findViewById(R.id.myCardView);

        }
    }
}
