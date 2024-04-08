package com.example.android_ck.khachhang;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.android_ck.DBHelper;
import com.example.android_ck.R;

public class AccountFragment extends Fragment{
    TextView tv_hoten,tv_email;
//    tv_gioitinh, tv_ngaysinh, tv_sdt, tv_phimyt, tv_tongtien
//    Button btn_qltk, btn_doimk, btn_dangxuat;

    CardView btn_doimk, btn_qltk, btn_dangxuat, hosocanhan;
    DBHelper dbHelper;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_khachhang_taikhoan,container,false);

        tv_hoten = view.findViewById(R.id.tv_hoten);
//        tv_gioitinh = view.findViewById(R.id.tv_gioitinh);
//        tv_ngaysinh = view.findViewById(R.id.tv_ngaysinh);
        tv_email = view.findViewById(R.id.tv_email);
//        tv_sdt = view.findViewById(R.id.tv_sdt);
//        tv_phimyt = view.findViewById(R.id.tv_phimyt);
//        tv_tongtien = view.findViewById(R.id.tv_tongtien);
        hosocanhan = view.findViewById(R.id.hosocanhan);
        btn_dangxuat = view.findViewById(R.id.btn_dangxuat);
        btn_doimk = view.findViewById(R.id.btn_doimk);
        btn_qltk = view.findViewById(R.id.btn_qltk);

        dbHelper = new DBHelper(getActivity());

        // Nhận Intent
        Intent myintent1 = getActivity().getIntent();
        // Lấy Bundle ra khỏi Intent
        Bundle mybundle1 = myintent1.getBundleExtra("dangnhappacket");

        String tk = mybundle1.getString("tk");

        hosocanhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), khachhang_hosocanhan.class);
                Bundle mybundle = new Bundle();
                mybundle.putString("tk", tk);
                // Đưa Bundle vào Intent
                intent.putExtra("taikhoan", mybundle);
                startActivity(intent);
            }
        });

        btn_doimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(getActivity(), khachhang_doimatkhau.class);
                // Đóng gói dữ liệu và đưa dữ liệu vào Bundle
                Bundle mybundle = new Bundle();
                mybundle.putString("tk", tk);

                // Đưa Bundle vào Intent
                myintent.putExtra("dangnhappacket", mybundle);
                startActivity(myintent);
            }
        });

        btn_dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Bạn có chắc chắn muốn đăng xuất?").setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getActivity(), khachhang_dangnhap.class);
                        startActivity(intent);
                    }
                }).setNegativeButton("Hủy", null).show();

            }
        });

        btn_qltk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), khachhang_quanlytaikhoan.class);
                Bundle bundle = new Bundle();
                bundle.putString("tk", tk);

                // Đưa Bundle vào Intent
                intent.putExtra("dangnhappacket", bundle);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Nhận Intent và cập nhật dữ liệu
        Intent myintent1 = getActivity().getIntent();
        Bundle mybundle1 = myintent1.getBundleExtra("dangnhappacket");
        String tk = mybundle1.getString("tk");

        Cursor cursor = dbHelper.layThongTinCaNhan(tk);
        if (cursor != null && cursor.moveToFirst()) {
            String hoten = cursor.getString(1);
//            String gioitinh = cursor.getString(2);
//            String ngaysinh = cursor.getString(3);
            String email = cursor.getString(4);
//            String sdt = cursor.getString(5);
            tv_hoten.setText(hoten);
//            tv_ngaysinh.setText(ngaysinh);
            tv_email.setText(email);
//            tv_sdt.setText(sdt);
//            tv_gioitinh.setText(gioitinh);
        }
        cursor.close();

//        int tongSoLuongPhimYeuThich = dbHelper.layTongSoLuongPhimYeuThich(tk);
//        int tongThanhTien = dbHelper.layTongThanhTien(tk);
//
//        tv_phimyt.setText(String.valueOf(tongSoLuongPhimYeuThich));
//        tv_tongtien.setText(String.valueOf(tongThanhTien));
    }


}
