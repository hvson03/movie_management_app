package com.example.android_ck.khachhang;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_ck.DBHelper;
import com.example.android_ck.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {
    FloatingActionButton add_button;
    RecyclerView recyclerView;
    DBHelper myDB;
    ArrayList<String> listtentk, listmaphim;
    YeuThichAdapter yeuThichAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_khachhang_yeuthich,container,false);
                add_button = view.findViewById(R.id.add_list_button);
                recyclerView = view.findViewById(R.id.recyclerView);
                add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), AddActivity.class)
                startActivity(i);
            }
        });

                myDB = new DBHelper(getContext());
                listtentk = new ArrayList<>();
                listmaphim = new ArrayList<>();

                storeDataInArrays();
                yeuThichAdapter = new YeuThichAdapter(getContext(), listmaphim, listtentk);
                recyclerView.setAdapter(yeuThichAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

            void storeDataInArrays(){
                Cursor cursor = myDB.layDuLieuBangDSYT();
                if(cursor.getCount() == 0)
                        Toast.makeText(getContext(), "No data", Toast.LENGTH_SHORT).show();
                else{
                        while (cursor.moveToNext()){
                                listtentk.add(cursor.getString(1));
                                listmaphim.add(cursor.getString(2));
                            }
                    }
            }
}
