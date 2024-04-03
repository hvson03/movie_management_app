package com.example.android_ck.quanly;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_ck.DBHelper;
import com.example.android_ck.R;
import com.example.android_ck.model.Phim;
import com.example.android_ck.model.PhimVaTheLoai;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmViewHolder> implements Filterable {

    private Context context;
    private List<PhimVaTheLoai> filmList;
    private List<PhimVaTheLoai> filteredFilmList;

    private DBHelper dbHelper;

    public FilmAdapter(Context context, List<PhimVaTheLoai> filmList) {
        this.context = context;
        this.filmList = filmList;
        this.dbHelper = new DBHelper(context);
        this.filteredFilmList = new ArrayList<>(filmList);
    }

    @NonNull
    @Override
    public FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_quanly_phim, parent, false);
        return new FilmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmViewHolder holder, int position) {
        PhimVaTheLoai film = filteredFilmList.get(position);

        // Lấy dữ liệu từ đối tượng PhimVaTheLoai
        String tenphim = film.getTenphim();
        int maphim = film.getMaphim();
        String mota = film.getMota();
        String thoiluong = film.getThoiluong();
        String ngaychieu = film.getNgaycongchieu();
        int giave = film.getGia();
        byte[] anhphim = film.getAnhphim(); // Lấy ảnh dưới dạng mảng byte

        // Lấy tên thể loại từ đối tượng PhimVaTheLoai
        String tentheloai = film.getTentheloai();

        // Hiển thị dữ liệu lên các thành phần UI của mỗi item trong RecyclerView
        holder.txt_ql_phim_tenphim.setText(tenphim);
        holder.txt_ql_phim_theloai.setText("Thể loại: " + tentheloai);
        holder.txt_ql_phim_giave.setText("Giá vé: " + giave + " VNĐ / 1 vé");
        holder.txt_ql_phim_khoichieu.setText(ngaychieu);

        // Hiển thị ảnh từ mảng byte lên ImageView
        Bitmap bitmap = BitmapFactory.decodeByteArray(anhphim, 0, anhphim.length);
        holder.imgv_ql_phim_anh.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return filteredFilmList.size();
    }

    class FilmViewHolder extends RecyclerView.ViewHolder {
        ImageView imgv_ql_phim_anh;
        TextView txt_ql_phim_tenphim, txt_ql_phim_theloai, txt_ql_phim_giave, txt_ql_phim_khoichieu;
        ImageButton btn_ql_phim_chitiet, btn_ql_phim_sua, btn_ql_phim_xoa;

        public FilmViewHolder(@NonNull View itemView) {
            super(itemView);
            imgv_ql_phim_anh = itemView.findViewById(R.id.imgv_ql_phim_img);
            txt_ql_phim_tenphim = itemView.findViewById(R.id.txt_ql_phim_tenphim);
            txt_ql_phim_theloai = itemView.findViewById(R.id.txt_ql_phim_theloai);
            txt_ql_phim_giave = itemView.findViewById(R.id.txt_ql_phim_giave);
            txt_ql_phim_khoichieu = itemView.findViewById(R.id.txt_ql_phim_khoichieu);
            btn_ql_phim_chitiet = itemView.findViewById(R.id.imgb_ql_phim_chitiet);
            btn_ql_phim_sua = itemView.findViewById(R.id.imgb_ql_phim_sua);
            btn_ql_phim_xoa = itemView.findViewById(R.id.imgb_ql_phim_xoa);

            btn_ql_phim_chitiet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        PhimVaTheLoai film = filteredFilmList.get(position);
                        Intent intent = new Intent(context, FilmDetail.class);
                        intent.putExtra("tenphim", film.getTenphim());
                        intent.putExtra("maphim", film.getMaphim());
                        intent.putExtra("mota", film.getMota());
                        intent.putExtra("thoiluong", film.getThoiluong());
                        intent.putExtra("ngaychieu", film.getNgaycongchieu());
                        intent.putExtra("giave", film.getGia());
                        intent.putExtra("anhphim", film.getAnhphim());
                        intent.putExtra("tentheloai", film.getTentheloai());
                        context.startActivity(intent);
                    }
                }
            });

            btn_ql_phim_sua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        PhimVaTheLoai film = filteredFilmList.get(position);
                        Intent intent = new Intent(context, FilmEdit.class);
                        intent.putExtra("tenphim", film.getTenphim());
                        intent.putExtra("maphim", film.getMaphim());
                        intent.putExtra("mota", film.getMota());
                        intent.putExtra("thoiluong", film.getThoiluong());
                        intent.putExtra("ngaychieu", film.getNgaycongchieu());
                        intent.putExtra("giave", film.getGia());
                        intent.putExtra("anhphim", film.getAnhphim());
                        intent.putExtra("tentheloai", film.getTentheloai());
                        context.startActivity(intent);
                    }
                }
            });

            btn_ql_phim_xoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        PhimVaTheLoai film = filteredFilmList.get(position);
                        int maphim = film.getMaphim();
                        showDeleteConfirmationDialog(maphim);
                    }
                }
            });
        }

        private void showDeleteConfirmationDialog(final int maphim) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Bạn có chắc muốn xóa phim này không ?")
                    .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dbHelper.deleteFilm(maphim);
                            updateData(dbHelper.getAllMoviesWithGenre());
                            Toast.makeText(context, "Đã xóa phim thành công", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    public void updateData(List<PhimVaTheLoai> newList) {
        filteredFilmList.clear();
        filteredFilmList.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<PhimVaTheLoai> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    // Nếu không có ràng buộc hoặc ràng buộc trống, hiển thị toàn bộ danh sách phim
                    filteredList.addAll(filmList);
                } else {
                    // Nếu có ràng buộc, lọc danh sách phim dựa trên ràng buộc
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (PhimVaTheLoai item : filmList) {
                        // Kiểm tra xem tên phim có chứa chuỗi tìm kiếm hay không
                        if (removeAccents(item.getTenphim().toLowerCase()).contains(removeAccents(filterPattern))) {
                            // Nếu có, thêm phim vào danh sách lọc
                            filteredList.add(item);
                        }
                    }
                }
                // Tạo kết quả lọc
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                // Xóa danh sách phim đã lọc và thêm danh sách phim đã lọc mới
                filteredFilmList.clear();
                filteredFilmList.addAll((List) results.values);
                // Thông báo cho Adapter cập nhật giao diện
                notifyDataSetChanged();
            }
        };
    }


    public static String removeAccents(String input) {
        String regex = "\\p{InCombiningDiacriticalMarks}+";
        String temp = Normalizer.normalize(input, Normalizer.Form.NFD);
        return temp.replaceAll(regex, "");
    }
}
