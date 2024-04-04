package com.example.android_ck;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.android_ck.khachhang.khachhang_dangnhap;
import com.example.android_ck.khachhang.khachhang_quanlytaikhoan;
import com.example.android_ck.model.item_user;
import com.example.android_ck.quanly.quanly_xoataikhoankhachhang;

import java.util.ArrayList;
import java.util.List;

public class userAdapter extends RecyclerView.Adapter<userAdapter.userViewHolder> {

    private List<item_user> mylist;
    private Context context;
    DBHelper dbHelper;

    public userAdapter(Context context, DBHelper dbHelper) {
        this.context = context;
        this.dbHelper = dbHelper;
    }
    public void removeItem(int position) {
        mylist.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mylist.size());
    }



    public void setData(List<item_user> list) {
        this.mylist = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public userViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new userViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userAdapter.userViewHolder holder, int position) {
        item_user itemUser = mylist.get(position);
        if (itemUser == null) {
            return;
        }
        holder.tv_tk.setText(itemUser.getTk());
        holder.tv_hoten.setText(itemUser.getHoten());
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Bạn có chắc chắn muốn xóa?").setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Boolean ktra = dbHelper.xoaTaiKhoan(itemUser.getTk());

                        if (ktra) {
                            removeItem(position);
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }

                    }

                }).setNegativeButton("Hủy", null).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        if (mylist != null) {
            return mylist.size();
        }
        return 0;
    }

    public class userViewHolder extends ViewHolder {
        private TextView tv_tk;
        private TextView tv_hoten;
        private ImageView img_delete;

        public userViewHolder(@NonNull View itemView) {
            super((itemView));
            tv_tk = itemView.findViewById(R.id.item_tk);
            tv_hoten = itemView.findViewById(R.id.item_hoten);
            img_delete = itemView.findViewById(R.id.img_delete);
        }


    }
}
