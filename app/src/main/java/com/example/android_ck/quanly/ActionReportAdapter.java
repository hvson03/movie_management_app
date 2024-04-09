package com.example.android_ck.quanly;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_ck.R;


import java.util.ArrayList;

public class ActionReportAdapter  extends RecyclerView.Adapter<ActionReportAdapter.MyViewHolder> {
    Context context;
    ArrayList<Bitmap> listanhphim;
    ArrayList<String> listtenphim;
    ArrayList<Integer> listgiaphim, listsoluongmua, listtongthu;
    ActionReportAdapter(Context context, ArrayList<Bitmap> listanhphim, ArrayList<String> listtenphim, ArrayList<Integer> listsoluongmua,
                        ArrayList<Integer> listgiaphim, ArrayList<Integer> listtongthu){
        this.context = context;
        this.listanhphim = listanhphim;
        this.listtenphim = listtenphim;
        this.listsoluongmua = listsoluongmua;
        this.listgiaphim = listgiaphim;
        this.listtongthu = listtongthu;
    }
    @NonNull
    @Override
    public ActionReportAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_quanly_baocao, parent, false);
        return new ActionReportAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActionReportAdapter.MyViewHolder holder, int position) {
        holder.txt_tenphim.setText(String.valueOf(listtenphim.get(position)));
        holder.txt_soluongmua.setText("Số lượng mua: " + String.valueOf(listsoluongmua.get(position)));
        holder.txt_gia.setText("Giá: " + String.valueOf(listgiaphim.get(position)) + " VNĐ ");
        holder.txt_dathuduoc.setText("Thu được: " + String.valueOf(listtongthu.get(position)) + " VNĐ");
        holder.img_anhphim.setImageBitmap(listanhphim.get(position));
    }

    @Override
    public int getItemCount() {
        return listtenphim.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_tenphim, txt_soluongmua, txt_gia, txt_dathuduoc;
        ImageView img_anhphim;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_anhphim = itemView.findViewById(R.id.img_quanly_baocao_anhphim);
            txt_tenphim = itemView.findViewById(R.id.txt_quanly_baocao_tenphim);
            txt_soluongmua = itemView.findViewById(R.id.txt_quanly_baocao_soluongmua);
            txt_gia = itemView.findViewById(R.id.txt_quanly_baocao_giaphim);
            txt_dathuduoc = itemView.findViewById(R.id.txt_quanly_baocao_thusp);
        }
    }
}
