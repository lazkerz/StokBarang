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

public class SetujuAdapter extends RecyclerView.Adapter<SetujuAdapter.MyViewHolder>{


    private Context context;
    private List<Setuju> setujuList;

    public SetujuAdapter(Context context) {
        this.context = context;
        setujuList = new ArrayList<>();
    }

    public void addSetuju(Setuju setuju){
        setujuList.add(setuju);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.data_persetujuan,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SetujuAdapter.MyViewHolder holder, int position) {

        Setuju setuju = setujuList.get(position);
        holder.persetujuan.setText(setuju.getPersetujuan());
        holder.jmlh.setText(setuju.getJmlh());
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
        return setujuList.size();
    }

    public Setuju getItem(int position) {
        return setujuList.get(position);
    }

    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView persetujuan, jmlh;

        private CardView myCardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            persetujuan = itemView.findViewById(R.id.persetujuan);
            jmlh = itemView.findViewById(R.id.jmlh);
            myCardView = itemView.findViewById(R.id.myCardView);

        }
    }
}
