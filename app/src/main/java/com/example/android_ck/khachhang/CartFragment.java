package com.example.android_ck.khachhang;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_ck.DBHelper;
import com.example.android_ck.R;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    RecyclerView recyclerView;
    DBHelper myDB;
    ArrayList<String> listtenphim;
    ArrayList<Integer> listmaphim, listgiaphim, listsoluong;
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

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("myPrefs", MODE_PRIVATE);
        String tentaikhoan = sharedPreferences.getString("tentaikhoan", "");

        recyclerView = view.findViewById(R.id.recyclerViewDatVe);
        btn_datmua = view.findViewById(R.id.btn_khachhang_datve_datmua);
        btn_datmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> quantities = cartFragmentAdapter.getSoLuong();

                for (int i = 0; i < quantities.size(); i++) {
                    int maphim = listmaphim.get(i);
                    int soluong = quantities.get(i);
                    myDB.capNhatHoaDon(tentaikhoan, maphim, soluong);
                }
                Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            }
        });

        storeDataInArrays(tentaikhoan);
        cartFragmentAdapter = new CartFragmentAdapter(getContext(), tentaikhoan, listanhphim, listmaphim, listtenphim, listgiaphim, listsoluong);
        recyclerView.setAdapter(cartFragmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    void storeDataInArrays(String tentaikhoan){
        Cursor cursor = myDB.layDuLieuBangHoaDon(tentaikhoan);
        if(cursor.getCount() == 0){
//            Toast.makeText(getContext(), "Khong co du lieu dat ve truoc", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                byte[] imageData = cursor.getBlob(8);
                if (imageData != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                    listanhphim.add(bitmap);
                }
                listmaphim.add(cursor.getInt(6));
                listtenphim.add(cursor.getString(7));
                listgiaphim.add(cursor.getInt(12));
                listsoluong.add(cursor.getInt(4));
            }
        }
    }
}
