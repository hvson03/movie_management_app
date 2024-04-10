package com.example.android_ck.khachhang;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_ck.DBHelper;
import com.example.android_ck.R;

import java.util.ArrayList;

public class FavouriteFragmentAdapter extends RecyclerView.Adapter<FavouriteFragmentAdapter.MyViewHolder> {
    Context context;
    ArrayList<Bitmap> listanhphim;
    ArrayList<String> listtenphim, listtheloai, listthoiluong;
    ArrayList<Integer> listmaphim;
    String tentaikhoan;
    public FavouriteFragmentAdapter(Context context, String tentaikhoan, ArrayList<Bitmap> listanhphim, ArrayList<Integer> listmaphim, ArrayList<String> listtenphim, ArrayList<String> listtheloai, ArrayList<String> listthoiluong){
        this.context = context;
        this.tentaikhoan = tentaikhoan;
        this.listanhphim = listanhphim;
        this.listmaphim = listmaphim;
        this.listtenphim = listtenphim;
        this.listtheloai = listtheloai;
        this.listthoiluong = listthoiluong;
    }
    public void updateData(){

    }
    @NonNull
    @Override
    public FavouriteFragmentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_khachhang_yeuthich, parent, false);
        return new FavouriteFragmentAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteFragmentAdapter.MyViewHolder holder, int position) {
        holder.txt_tenphim.setText(String.valueOf(listtenphim.get(position)));
        holder.txt_theloai.setText("Thể loại: "+String.valueOf(listtheloai.get(position)));
        holder.txt_thoiluong.setText("Thời gian: "+String.valueOf(listthoiluong.get(position)));
        holder.img_anhphim.setImageBitmap(listanhphim.get(position));

        holder.img_kh_yt_bothich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper myDB = new DBHelper(context);
                int maphim = listmaphim.get(position);

                // Tạo hộp thoại xác nhận trước khi xóa
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Bạn có chắc chắn muốn xóa khỏi danh sách yêu thích?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Xóa mục khỏi danh sách yêu thích
                        boolean result = myDB.xoaKhoiDanhSachYeuThich(tentaikhoan, maphim);
                        if (result) {
                            Toast.makeText(context, "Đã xóa khỏi danh sách yêu thích", Toast.LENGTH_SHORT).show();
                            removeItem(position);
                        } else {
                            Toast.makeText(context, "Xóa khỏi danh sách yêu thích thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Đóng hộp thoại nếu người dùng không muốn xóa
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listtenphim.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_tenphim, txt_theloai, txt_thoiluong;
        ImageView img_anhphim, img_kh_yt_bothich;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_anhphim = itemView.findViewById(R.id.img_khachhang_yeuthich_anhphim);
            txt_tenphim = itemView.findViewById(R.id.txt_khachhang_yeuthich_tenphim);
            txt_theloai = itemView.findViewById(R.id.txt_khachhang_yeuthich_theloai);
            txt_thoiluong = itemView.findViewById(R.id.txt_khachhang_yeuthich_thoiluong);
            img_kh_yt_bothich = itemView.findViewById(R.id.img_khachhang_yeuthich_bothich);
        }
    }

    public void removeItem(int position) {
        listmaphim.remove(position);
        listtenphim.remove(position);
        listtheloai.remove(position);
        listthoiluong.remove(position);
        listanhphim.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }
}
