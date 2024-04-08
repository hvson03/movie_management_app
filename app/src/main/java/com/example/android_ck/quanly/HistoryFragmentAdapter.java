package com.example.android_ck.quanly;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_ck.R;

import java.util.ArrayList;

public class HistoryFragmentAdapter extends RecyclerView.Adapter<HistoryFragmentAdapter.MyViewHolder> {
    Context context;
    ArrayList<Bitmap> listanhphim;
    ArrayList<String> listtenphim, listtaikhoan, listtheloai;
    ArrayList<Integer> listgiaphim, listsoluong;
    HistoryFragmentAdapter(Context context,ArrayList<String> listtaikhoan, ArrayList<Bitmap> listanhphim, ArrayList<String> listtenphim, ArrayList<String> listtheloai, ArrayList<Integer> listgiaphim, ArrayList<Integer> listsoluong){
        this.context = context;
        this.listtaikhoan = listtaikhoan;
        this.listanhphim = listanhphim;
        this.listtenphim = listtenphim;
        this.listtheloai = listtheloai;
        this.listgiaphim = listgiaphim;
        this.listsoluong = listsoluong;
    }
    @NonNull
    @Override
    public HistoryFragmentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_quanly_lichsu, parent, false);
        return new HistoryFragmentAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryFragmentAdapter.MyViewHolder holder, int position) {
        holder.txt_tenphim.setText("Phim: " + String.valueOf(listtenphim.get(position)));
        holder.txt_theloai.setText("(" + String.valueOf(listtheloai.get(position)) + ")");
        holder.txt_tenkhachhang.setText("Khách hàng: " + String.valueOf(listtaikhoan.get(position)));
        holder.txt_gia.setText(String.valueOf("Giá: " + listgiaphim.get(position)) + "đ");
        holder.txt_soluong.setText("Số lượng: " + String.valueOf(listsoluong.get(position)));
        holder.img_anhphim.setImageBitmap(listanhphim.get(position));
    }

    @Override
    public int getItemCount() {
        return listtenphim.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_tenphim, txt_tenkhachhang, txt_gia, txt_soluong, txt_theloai;
        ImageView img_anhphim;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_tenphim = itemView.findViewById(R.id.txt_quanly_lichsu_tenphim);
            txt_theloai = itemView.findViewById(R.id.txt_quanly_lichsu_theloai);
            txt_tenkhachhang = itemView.findViewById(R.id.txt_quanly_lichsu_tenkhachhang);
            txt_gia = itemView.findViewById(R.id.txt_quanly_lichsu_giaphim);
            txt_soluong = itemView.findViewById(R.id.txt_quanly_lichsu_soluong);
            img_anhphim = itemView.findViewById(R.id.img_quanly_lichsu_anhphim);
        }
    }
}
