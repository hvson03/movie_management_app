package com.example.android_ck.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_ck.DBHelper;
import com.example.android_ck.R;
import com.example.android_ck.model.TheLoai;
import com.example.android_ck.quanly.GenreEdit;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreViewHolder>{
    private Context context;
    private List<TheLoai> genreList;
    private DBHelper dbHelper;



    public GenreAdapter(Context context, List<TheLoai> genreList) {
        this.context = context;
        this.genreList = genreList;
        this.dbHelper = new DBHelper(context);
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_quanly_theloai,parent,false);
        GenreViewHolder genreViewHolder = new GenreViewHolder(view);
        return genreViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        TheLoai genre = genreList.get(position);

        //getdata
        int matheloai = genre.getMaTheloai();
        String tentheloai = genre.getTenTheloai();

        //set data in view
        holder.txt_ql_theloai_matheloai.setText(String.valueOf(matheloai));

        holder.txt_ql_theloai_tentheloai.setText(tentheloai);
    }

    @Override
    public int getItemCount() {
        if(genreList!=null)return genreList.size();
        return 0;
    }

    class GenreViewHolder extends RecyclerView.ViewHolder{

        TextView txt_ql_theloai_matheloai, txt_ql_theloai_tentheloai;
        ImageButton btn_ql_theloai_sua, btn_ql_theloai_xoa;
        public GenreViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_ql_theloai_matheloai = itemView.findViewById(R.id.txt_ql_theloai_matheloai);
            txt_ql_theloai_tentheloai = itemView.findViewById(R.id.txt_ql_theloai_tentheloai);
            btn_ql_theloai_sua = itemView.findViewById(R.id.btn_ql_theloai_sua);
            btn_ql_theloai_xoa = itemView.findViewById(R.id.btn_ql_theloai_xoa);
            btn_ql_theloai_sua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Lấy tên thể loại từ TextView và chuyển sang trang sửa
                    Intent intent = new Intent(context, GenreEdit.class);
                    int matheloai = Integer.parseInt(txt_ql_theloai_matheloai.getText().toString());
                    String tentheloai = txt_ql_theloai_tentheloai.getText().toString();
                    intent.putExtra("matheloai", matheloai);
                    intent.putExtra("tentheloai", tentheloai);
                    context.startActivity(intent);
                }
            });

            btn_ql_theloai_xoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Lấy ID của thể loại cần xóa
                    int matheloai = Integer.parseInt(txt_ql_theloai_matheloai.getText().toString());
                    showDeleteConfirmationDialog(matheloai);
                }
            });
        }
        // Phương thức hiển thị hộp thoại xác nhận xóa
        private void showDeleteConfirmationDialog(final int matheloai) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Bạn có chắc muốn xóa thể loại có ID: " + matheloai + "?")
                    .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            // Gọi hàm xóa trong DBHelper để xóa thể loại
                            dbHelper.deleteGenre(matheloai);
                            // Cập nhật lại RecyclerView sau khi xóa
                            updateData(dbHelper.getAllGenreItems());
                            Toast.makeText(context, "Đã xóa thể loại có mã thể loại: " + matheloai, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Đóng hộp thoại
                            dialog.dismiss();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

    }
    public void updateData(List<TheLoai> newList) {
        this.genreList = newList;
        notifyDataSetChanged();
    }


}
