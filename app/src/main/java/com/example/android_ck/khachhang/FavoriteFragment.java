package com.example.android_ck.khachhang;

import static android.content.Context.MODE_PRIVATE;

import android.content.BroadcastReceiver;
import android.content.Context;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_ck.DBHelper;
import com.example.android_ck.R;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {
    RecyclerView recyclerView;
    DBHelper myDB;
    ArrayList<String> listtenphim, listtheloai, listthoiluong;
    ArrayList<Integer> listmaphim;
    ArrayList<Bitmap> listanhphim;
    FavouriteFragmentAdapter favouriteFragmentAdapter;
    String tentaikhoan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_khachhang_yeuthich,container,false);
        myDB = new DBHelper(getContext());
        listanhphim = new ArrayList<Bitmap>();
        listmaphim = new ArrayList<Integer>();
        listtenphim = new ArrayList<String>();
        listtheloai = new ArrayList<String>();
        listthoiluong = new ArrayList<String>();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("myPrefs", MODE_PRIVATE);
        tentaikhoan = sharedPreferences.getString("tentaikhoan", "");

        recyclerView = view.findViewById(R.id.recyclerViewYeuThich);
        storeDataInArrays(tentaikhoan);
        favouriteFragmentAdapter = new FavouriteFragmentAdapter(getContext(), tentaikhoan, listanhphim, listmaphim, listtenphim, listtheloai, listthoiluong);
        recyclerView.setAdapter(favouriteFragmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(adapterDataChangedReceiver,new IntentFilter("adapter_data_changed"));
        return view;
    }
    private BroadcastReceiver adapterDataChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateData();
        }
    };
    private void updateData() {
        listanhphim.clear();
        listmaphim.clear();
        listtenphim.clear();
        listtheloai.clear();
        listthoiluong.clear();
        storeDataInArrays(tentaikhoan);
        favouriteFragmentAdapter.notifyDataSetChanged();
    }
    void storeDataInArrays(String tentaikhoan) {
        Cursor cursor = myDB.layDuLieuBangDSYT(tentaikhoan);
        if (cursor.getCount() == 0) {
            // Toast.makeText(getContext(), "Khong co du lieu yeu thich", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                byte[] imageData = cursor.getBlob(5);
                if (imageData != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                    listanhphim.add(bitmap);
                }
                listmaphim.add(cursor.getInt(3));
                listtenphim.add(cursor.getString(4));
                listtheloai.add(cursor.getString(12));
                listthoiluong.add(cursor.getString(8));
            }
        }
    }
}
