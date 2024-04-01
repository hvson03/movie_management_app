package com.example.android_ck.khachhang;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.android_ck.DBHelper;
import com.example.android_ck.R;

public class AccountFragment extends Fragment{
    TextView tv_hoten, tv_email, tv_sdt, tv_phimyt, tv_tongtien;
    Button btn_qltk, btn_doimk, btn_dangxuat;
    DBHelper dbHelper;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_khachhang_taikhoan,container,false);

        tv_hoten = view.findViewById(R.id.tv_hoten);
        tv_email = view.findViewById(R.id.tv_email);
        tv_sdt = view.findViewById(R.id.tv_sdt);
        tv_phimyt = view.findViewById(R.id.tv_phimyt);
        tv_tongtien = view.findViewById(R.id.tv_tongtien);
        btn_dangxuat = view.findViewById(R.id.btn_dangxuat);
        btn_doimk = view.findViewById(R.id.btn_doimk);
        btn_qltk = view.findViewById(R.id.btn_qltk);

        dbHelper = new DBHelper(getActivity());

        // Nhận Intent
        Intent myintent1 = getActivity().getIntent();
        // Lấy Bundle ra khỏi Intent
        Bundle mybundle1 = myintent1.getBundleExtra("dangnhappacket");

        String tk = mybundle1.getString("tk");
//        Cursor cursor = dbHelper.layThongTinCaNhan(tk);
//            cursor.moveToFirst();
//            while (cursor.isAfterLast()){
//                tv_hoten.setText(cursor.getString(1).toString());
//                tv_email.setText(cursor.getString(4).toString());
//                tv_sdt.setText(cursor.getString(5).toString());
//                cursor.moveToNext();
//            }
//            cursor.close();

        btn_doimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(getActivity(), khachhang_doimatkhau.class);
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

            }
        });

        return view;
    }

}
