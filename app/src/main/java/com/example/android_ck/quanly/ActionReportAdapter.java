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
    ArrayList<String> listtenphim, listtaikhoan;
    ArrayList<Integer> listmaphim, listgiaphim, listsoluong, listthanhtien;
    ActionReportAdapter(Context context,ArrayList<String> listtaikhoan, ArrayList<Bitmap> listanhphim, ArrayList<Integer> listmaphim, ArrayList<String> listtenphim, ArrayList<Integer> listgiaphim, ArrayList<Integer> listsoluong, ArrayList<Integer> listthanhtien){
        this.context = context;
        this.listtaikhoan = listtaikhoan;
        this.listanhphim = listanhphim;
        this.listmaphim = listmaphim;
        this.listtenphim = listtenphim;
        this.listgiaphim = listgiaphim;
        this.listsoluong = listsoluong;
        this.listthanhtien = listthanhtien;
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
        holder.txt_tenphim.setText("Phim: " + String.valueOf(listtenphim.get(position)));
        holder.txt_tenkhachhang.setText("Khách hàng: " + String.valueOf(listtaikhoan.get(position)));
        holder.txt_gia.setText("Tổng: " + String.valueOf(listgiaphim.get(position)) + "đ ");
        holder.txt_soluong.setText(" " + String.valueOf(listsoluong.get(position)) + " ");
        holder.txt_thanhtien.setText(" " + String.valueOf(listthanhtien.get(position)) + "đ");
        holder.img_anhphim.setImageBitmap(listanhphim.get(position));
    }

    @Override
    public int getItemCount() {
        return listtenphim.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_tenphim, txt_tenkhachhang, txt_gia, txt_soluong, txt_thanhtien;
        ImageView img_anhphim;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_tenphim = itemView.findViewById(R.id.txt_quanly_baocao_tenphim);
            txt_tenkhachhang = itemView.findViewById(R.id.txt_quanly_baocao_tenkhachhang);
            txt_gia = itemView.findViewById(R.id.txt_quanly_baocao_giaphim);
            txt_soluong = itemView.findViewById(R.id.txt_quanly_baocao_soluong);
            txt_thanhtien = itemView.findViewById(R.id.txt_quanly_baocao_thanhtien);
            img_anhphim = itemView.findViewById(R.id.img_quanly_baocao_anhphim);
        }
    }
}
