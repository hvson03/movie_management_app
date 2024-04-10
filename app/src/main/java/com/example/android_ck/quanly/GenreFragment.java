package com.example.android_ck.quanly;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_ck.DBHelper;
import com.example.android_ck.R;
import com.example.android_ck.model.TheLoai;

import java.util.List;

public class GenreFragment extends Fragment {
    ImageView imgb_add, imgb_delete;
    RecyclerView ql_theloai_recy;
    DBHelper dbHelper;
    GenreAdapter genreAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quanly_theloai, container, false);

        imgb_add = view.findViewById(R.id.img_ql_theloai_add);
        imgb_delete = view.findViewById(R.id.img_ql_theloai_delete);
        ql_theloai_recy = view.findViewById(R.id.ql_theloai_recy);

        imgb_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_theloai_add = new Intent(getActivity(), GenreAdd.class);
                startActivity(intent_theloai_add);
            }
        });
        imgb_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog();
            }
        });

        dbHelper = new DBHelper(getActivity());
        List<TheLoai> genreList = dbHelper.getAllGenreItems(); // Truy vấn cơ sở dữ liệu để lấy danh sách thể loại

        // Khởi tạo Adapter và thiết lập dữ liệu cho RecyclerView
        genreAdapter = new GenreAdapter(getActivity(), genreList);
        genreAdapter.updateData(genreList);
        ql_theloai_recy.setLayoutManager(new LinearLayoutManager(getActivity()));
        ql_theloai_recy.setAdapter(genreAdapter);

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        // Load lại dữ liệu từ cơ sở dữ liệu
        List<TheLoai> genreList = dbHelper.getAllGenreItems();

        // Cập nhật dữ liệu mới vào Adapter
        genreAdapter.updateData(genreList);
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Bạn có chắc muốn xóa toàn bộ thể loại?")
                .setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Xóa toàn bộ dữ liệu trong cơ sở dữ liệu
                        dbHelper.deleteAllGenres();

                        // Load lại dữ liệu từ cơ sở dữ liệu
                        List<TheLoai> genreList = dbHelper.getAllGenreItems();

                        // Cập nhật dữ liệu mới vào Adapter
                        genreAdapter.updateData(genreList);
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Đóng hộp thoại
                        dialog.dismiss();
                    }
                });
        // Tạo và hiển thị hộp thoại
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
