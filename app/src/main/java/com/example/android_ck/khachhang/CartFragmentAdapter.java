package com.example.android_ck.khachhang;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_ck.DBHelper;
import com.example.android_ck.R;

import java.util.ArrayList;

public class CartFragmentAdapter extends RecyclerView.Adapter<CartFragmentAdapter.MyViewHolder> {
    Context context;
    ArrayList<Bitmap> listanhphim;
    ArrayList<String> listtenphim;
    ArrayList<Integer> listmaphim, listgiaphim, listsoluong, listthanhtien;
    ArrayList<Integer> soluong;
    DBHelper myDB;
    String tentaikhoan;
    CartFragmentAdapter(Context context,String tentaikhoan, ArrayList<Bitmap> listanhphim, ArrayList<Integer> listmaphim, ArrayList<String> listtenphim, ArrayList<Integer> listgiaphim, ArrayList<Integer> listsoluong, ArrayList<Integer> listthanhtien){
        this.context = context;
        this.tentaikhoan = tentaikhoan;
        this.listanhphim = listanhphim;
        this.listmaphim = listmaphim;
        this.listtenphim = listtenphim;
        this.listgiaphim = listgiaphim;
        this.listsoluong = listsoluong;
        this.listthanhtien = listthanhtien;
        this.soluong = new ArrayList<>(listsoluong);
    }
    @NonNull
    @Override
    public CartFragmentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_khachhang_datve, parent, false);
        return new CartFragmentAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartFragmentAdapter.MyViewHolder holder, int position) {
        myDB = new DBHelper(context);
        holder.txt_tenphim.setText(String.valueOf(listtenphim.get(position)));
        holder.txt_giave.setText(String.valueOf("Giá: "+listgiaphim.get(position)) + " VNĐ");
        holder.img_anhphim.setImageBitmap(listanhphim.get(position));
        holder.txt_soluong.setText(String.valueOf(listsoluong.get(position)));
        holder.txt_thanhtien.setText("Thành tiền: " + String.valueOf(listthanhtien.get(position)) + " VNĐ");
        holder.img_bochon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int maphim = listmaphim.get(position);

                boolean result = myDB.xoaKhoiGioHang(tentaikhoan, maphim);
                if (result) {
                    Toast.makeText(context, "Đã xóa khỏi giỏ hàng", Toast.LENGTH_SHORT).show();
                    listmaphim.remove(position);
                    listtenphim.remove(position);
                    listgiaphim.remove(position);
                    listanhphim.remove(position);
                    listthanhtien.remove(position);
                    Intent intent = new Intent("update_checkout_button_text");
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    notifyItemRemoved(position);
                } else {
                    Toast.makeText(context, "Xóa khỏi giỏ hàng thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.txt_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = Integer.parseInt(holder.txt_soluong.getText().toString());
                if (currentQuantity > 1) {
                    currentQuantity--;
                    holder.txt_soluong.setText(String.valueOf(currentQuantity));
                    soluong.set(position, currentQuantity);
                    int maphim = listmaphim.get(position);
                    myDB.capNhatGioHang(tentaikhoan, maphim, currentQuantity);
                    holder.txt_thanhtien.setText("Thành tiền: " + String.valueOf(currentQuantity*myDB.layGiaPhim(maphim)) + " VNĐ");
                    Intent intent = new Intent("update_checkout_button_text");
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                } else {
                    Toast.makeText(context, "Số lượng không được nhỏ hơn 1", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.txt_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = Integer.parseInt(holder.txt_soluong.getText().toString());
                if (currentQuantity < 100) {
                    currentQuantity++;
                    holder.txt_soluong.setText(String.valueOf(currentQuantity));
                    soluong.set(position, currentQuantity);
                    int maphim = listmaphim.get(position);
                    myDB.capNhatGioHang(tentaikhoan, maphim, currentQuantity);
                    holder.txt_thanhtien.setText("Thành tiền: " + String.valueOf(currentQuantity*myDB.layGiaPhim(maphim)) + " VNĐ");
                    Intent intent = new Intent("update_checkout_button_text");
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                } else {
                    Toast.makeText(context, "Số lượng không được lớn hơn 100", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.txt_soluong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    soluong.set(position, Integer.parseInt(s.toString()));
                } catch (NumberFormatException e) {
                    soluong.set(position, 0);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    public int getItemCount() {
        return listtenphim.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_tenphim, txt_giave, txt_minus, txt_plus, txt_thanhtien;
        ImageView img_anhphim, img_bochon;
        EditText txt_soluong;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_anhphim = itemView.findViewById(R.id.img_khachhang_datve_anhphim);
            img_bochon = itemView.findViewById(R.id.img_khachhang_datve_bochon);
            txt_tenphim = itemView.findViewById(R.id.txt_khachhang_datve_tenphim);
            txt_giave = itemView.findViewById(R.id.txt_khachhang_datve_giave);
            txt_soluong = itemView.findViewById(R.id.txt_khachhang_datve_soluong);
            txt_thanhtien = itemView.findViewById(R.id.txt_khachhang_datve_thanhtien);
            txt_minus = itemView.findViewById(R.id.txt_khachhang_datve_minus);
            txt_plus = itemView.findViewById(R.id.txt_khachhang_datve_plus);
        }
    }

    public ArrayList<Integer> getSoLuong() {
        return soluong;
    }
}
