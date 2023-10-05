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

public class MintaAdapter extends RecyclerView.Adapter<MintaAdapter.MyViewHolder>{


    private Context context;
    private List<Minta> mintaList;

    public MintaAdapter(Context context) {
        this.context = context;
        mintaList = new ArrayList<>();
    }

    public void addMinta(Minta minta){
        mintaList.add(minta);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.data_permintaan,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Minta minta = mintaList.get(position);
        holder.id.setText(minta.getIdpermintaan());
        holder.nama.setText(minta.getNama());
        holder.namabrg.setText(minta.getNamaBrg());
        holder.tgl.setText(minta.getTgl());
        holder.ket.setText(minta.getKeterangan());
        holder.keputusan.setText(minta.getKeputusan());
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
        return mintaList.size();
    }

    public Minta getItem(int position) {
        return mintaList.get(position);
    }

    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView id,nama, namabrg, tgl, ket, keputusan;

        private CardView myCardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.Idpermintaan);
            nama = itemView.findViewById(R.id.nama);
            namabrg = itemView.findViewById(R.id.namabrg);
            tgl = itemView.findViewById(R.id.tgl);
            ket = itemView.findViewById(R.id.ket);
            keputusan = itemView.findViewById(R.id.keputusan);
            myCardView = itemView.findViewById(R.id.myCardView);

        }
    }
}
