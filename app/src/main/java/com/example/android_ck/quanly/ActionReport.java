package com.example.android_ck.quanly;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_ck.DBHelper;
import com.example.android_ck.R;

import java.util.ArrayList;

public class ActionReport extends AppCompatActivity {
    RecyclerView recyclerView;
    DBHelper myDB;
    ArrayList<String> listtenphim, listtenkhachhang;
    ArrayList<Integer> listmaphim, listgiaphim, listsoluong, listthanhtien;
    ArrayList<Bitmap> listanhphim;
    ActionReportAdapter actionReportAdapter;
    ImageView img_back;
    TextView tv_tongtien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanly_tacvu_baocao);
        recyclerView = findViewById(R.id.recyclerViewBaoCao);
        img_back = findViewById(R.id.img_quanly_baocao_back);
        tv_tongtien = findViewById(R.id.txt_quanly_baocao_tongtien);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        myDB = new DBHelper(this);
        listanhphim = new ArrayList<Bitmap>();
        listmaphim = new ArrayList<Integer>();
        listtenphim = new ArrayList<String>();
        listtenkhachhang = new ArrayList<String>();
        listgiaphim = new ArrayList<Integer>();
        listsoluong = new ArrayList<Integer>();
        listthanhtien = new ArrayList<Integer>();

        tv_tongtien.setText("Tổng tiền: " + myDB.getTongTienCacHoaDon() + "đ");
        storeDataInArrays();
        actionReportAdapter = new ActionReportAdapter(this, listtenkhachhang, listanhphim, listmaphim, listtenphim, listgiaphim, listsoluong, listthanhtien);
        recyclerView.setAdapter(actionReportAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    void storeDataInArrays(){
        Cursor cursor = myDB.layDuLieuBangHoaDon();

        if(cursor.getCount() == 0){
            Toast.makeText(this, "Khong co du bao cao thong ke", Toast.LENGTH_SHORT).show();
        } else{
            while (cursor.moveToNext()){
                byte[] imageData = cursor.getBlob(8);
                if (imageData != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                    listanhphim.add(bitmap);
                }
                listmaphim.add(cursor.getInt(6));
                listtenphim.add(cursor.getString(7));
                listtenkhachhang.add(cursor.getString(1));
                listgiaphim.add(cursor.getInt(12));
                listsoluong.add(cursor.getInt(4));
                listthanhtien.add(cursor.getInt(5));
            }
        }
    }
}
