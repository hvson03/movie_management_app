package com.example.android_ck.khachhang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_ck.DBHelper;
import com.example.android_ck.R;

import java.util.ArrayList;

public class YeuThichAdapter extends RecyclerView.Adapter<YeuThichAdapter.MyViewHolder> {
    private Context context;
    private ArrayList list_ma_phim, list_tentk;
    ImageView delete_btn;
    YeuThichAdapter(Context context, ArrayList list_ma_phim,ArrayList list_tentk){
        this.context = context;
        this.list_ma_phim = list_ma_phim;
        this.list_tentk = list_tentk;
    }
    @NonNull
    @Override
    public YeuThichAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_khachhang_yeuthich, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YeuThichAdapter.MyViewHolder holder, int position) {
        holder.ma_phim_txt.setText(String.valueOf(list_ma_phim.get(position)));
        holder.tentk_txt.setText(String.valueOf(list_tentk.get(position)));
        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(context);
                db.xoaDanhSachYeuThich(2);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_ma_phim.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView ma_phim_txt, tentk_txt;
        ImageView delete_btn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ma_phim_txt = itemView.findViewById(R.id.maphimtxt);
            tentk_txt = itemView.findViewById(R.id.tentktxt);
            delete_btn = itemView.findViewById(R.id.deletebtn);
        }
    }
}
