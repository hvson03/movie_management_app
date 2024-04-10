package com.example.android_ck.quanly;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_ck.DBHelper;
import com.example.android_ck.R;
import com.example.android_ck.model.PhimVaTheLoai;

import java.util.List;

public class FilmFragment extends Fragment {
    TextView txt_phim;

    SearchView searchview_ql_phim;
    RecyclerView recyclerview_ql_phim;
    ImageView btn_ql_phim_xoa, btn_ql_phim_them;
    DBHelper dbHelper;
    FilmAdapter filmAdapter;
    List<PhimVaTheLoai> movieList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quanly_phim,container,false);
        searchview_ql_phim = view.findViewById(R.id.searchview_ql);
        recyclerview_ql_phim = view.findViewById(R.id.ql_phim_recy);
        btn_ql_phim_xoa = view.findViewById(R.id.img_ql_phim_delete);
        btn_ql_phim_them = view.findViewById(R.id.img_ql_phim_add);
        txt_phim = view.findViewById(R.id.txt_phim);

        dbHelper = new DBHelper(getActivity());

        // Lấy danh sách phim kèm thông tin thể loại từ DBHelper
         movieList = dbHelper.getAllMoviesWithGenre();


        // Khởi tạo và thiết lập FilmAdapter
        filmAdapter = new FilmAdapter(getActivity(),movieList);
//
        recyclerview_ql_phim.setAdapter(filmAdapter);
        recyclerview_ql_phim.setLayoutManager(new LinearLayoutManager(getActivity()));
        filmAdapter.updateData(movieList);

// Lắng nghe sự kiện tìm kiếm từ SearchView
        searchview_ql_phim.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter dữ liệu trong adapter khi có sự thay đổi trong SearchView
                filmAdapter.getFilter().filter(newText);
                return false;
            }
        });

        btn_ql_phim_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_ql_phim = new Intent(getActivity(), FilmAdd.class);
                startActivity(intent_ql_phim);
            }
        });

        btn_ql_phim_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog();
            }
        });

        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
        // Load lại dữ liệu từ cơ sở dữ liệu
        List<PhimVaTheLoai> movieList = dbHelper.getAllMoviesWithGenre();

        // Cập nhật dữ liệu mới vào Adapter
        filmAdapter.updateData(movieList);
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Bạn có chắc muốn xóa toàn bộ phim ?")
                .setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Xóa toàn bộ dữ liệu trong cơ sở dữ liệu
                        dbHelper.deleteAllFilm();
                        onResume();
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
