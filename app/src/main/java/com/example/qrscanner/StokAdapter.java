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
        holder.no.setText(stok.getNo());
        holder.ket.setText(stok.getKet());
        holder.tgl.setText(stok.getTgl());
        holder.namabrg.setText(stok.getBarang());
        holder.kate.setText(stok.getKategori());
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


        private TextView no;
        private TextView ket;
        private TextView tgl;
        private TextView namabrg;
        private TextView kate;

        private CardView myCardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            no = itemView.findViewById(R.id.no);
            ket = itemView.findViewById(R.id.ket);
            tgl = itemView.findViewById(R.id.tgl);
            namabrg = itemView.findViewById(R.id.namabrg);
            kate = itemView.findViewById(R.id.kateg);
            myCardView = itemView.findViewById(R.id.myCardView);

        }
    }
}
