package com.example.android_ck.khachhang;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_ck.DBHelper;
import com.example.android_ck.R;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    RecyclerView recyclerView;
    DBHelper myDB;
    ArrayList<String> listtenphim;
    ArrayList<Integer> listmaphim, listgiaphim, listsoluong, listthanhtien;
    ArrayList<Bitmap> listanhphim;
    CartFragmentAdapter cartFragmentAdapter;
    String tentaikhoan;
    Button btn_datmua;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_khachhang_giohang,container,false);
        myDB = new DBHelper(getContext());

        listanhphim = new ArrayList<Bitmap>();
        listmaphim = new ArrayList<Integer>();
        listtenphim = new ArrayList<String>();
        listgiaphim = new ArrayList<Integer>();
        listsoluong = new ArrayList<Integer>();
        listthanhtien = new ArrayList<Integer>();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("myPrefs", MODE_PRIVATE);
        tentaikhoan = sharedPreferences.getString("tentaikhoan", "");

        recyclerView = view.findViewById(R.id.recyclerViewDatVe);
        btn_datmua = view.findViewById(R.id.btn_khachhang_datve_datmua);

        if (myDB.getTongTienGioHang(tentaikhoan) == 0)
            btn_datmua.setEnabled(false);
         else
             btn_datmua.setEnabled(true);

        btn_datmua.setText("Tổng tiền: " + String.valueOf(myDB.getTongTienGioHang(tentaikhoan))  + " VNĐ");
        btn_datmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Bạn có chắc chắn muốn đặt hàng?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(myDB.kiemTraThongTinCaNhan(tentaikhoan)){
                            ArrayList<Integer> quantities = cartFragmentAdapter.getSoLuong();

                            for (int i = 0; i < quantities.size(); i++) {
                                int maphim = listmaphim.get(i);
                                int soluong = quantities.get(i);
                                myDB.themHoaDonMoi(tentaikhoan, maphim, soluong);
                            }

                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Đặt vé thành công!");
                            builder.setMessage("Cảm ơn bạn đã đặt vé! Mời bạn đến rạp Cuồng Phong Cinema để thưởng thức!");
                            builder.setIcon(R.drawable.logo_cinema);
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();

                            myDB.xoaGioHang(tentaikhoan);
                            listanhphim.clear();
                            listmaphim.clear();
                            listtenphim.clear();
                            listgiaphim.clear();
                            listsoluong.clear();
                            listthanhtien.clear();
                            storeDataInArrays(tentaikhoan);
                            cartFragmentAdapter.notifyDataSetChanged();
                            if (myDB.getTongTienGioHang(tentaikhoan) == 0){
                                btn_datmua.setEnabled(false);
                                btn_datmua.setText("Tổng tiền: 0 VNĐ");
                            }
                            else
                                btn_datmua.setEnabled(true);
                        }else{
                            Toast.makeText(getContext(), "Vui lòng cập nhật thông tin trước khi đặt hàng!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        storeDataInArrays(tentaikhoan);
        cartFragmentAdapter = new CartFragmentAdapter(getContext(), tentaikhoan, listanhphim, listmaphim, listtenphim, listgiaphim, listsoluong, listthanhtien);
        recyclerView.setAdapter(cartFragmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    private BroadcastReceiver updateCheckoutButtonTextReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (myDB.getTongTienGioHang(tentaikhoan) == 0) {
                btn_datmua.setEnabled(false);
            } else {
                btn_datmua.setEnabled(true);
            }
            btn_datmua.setText("Thanh toán: " + String.valueOf(myDB.getTongTienGioHang(tentaikhoan))  + " VNĐ");
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(updateCheckoutButtonTextReceiver, new IntentFilter("update_checkout_button_text"));
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(updateCheckoutButtonTextReceiver);
    }

    void storeDataInArrays(String tentaikhoan){
        Cursor cursor = myDB.layDuLieuBangGioHang(tentaikhoan);
        if(cursor.getCount() == 0){
//            Toast.makeText(getContext(), "Khong co du lieu dat ve truoc", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                byte[] imageData = cursor.getBlob(7);
                if (imageData != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                    listanhphim.add(bitmap);
                }
                listmaphim.add(cursor.getInt(5));
                listtenphim.add(cursor.getString(6));
                listgiaphim.add(cursor.getInt(11));
                listsoluong.add(cursor.getInt(3));
                listthanhtien.add(cursor.getInt(4));
            }
        }
    }
}
