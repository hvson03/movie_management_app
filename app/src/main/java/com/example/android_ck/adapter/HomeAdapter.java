package com.example.android_ck.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_ck.R;
import com.example.android_ck.khachhang.MovieDetail;
import com.example.android_ck.model.PhimVaTheLoai;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> implements Filterable {
    private Context context;
    private List<PhimVaTheLoai> filmList;
    private List<PhimVaTheLoai> filteredFilmList;

    public HomeAdapter(Context context, List<PhimVaTheLoai> movieList) {
        this.context = context;
        this.filmList = movieList;
        this.filteredFilmList = new ArrayList<>(movieList);
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_khachhang_phim, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
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

        holder.txt_khachhang_home_tenphim.setText(tenphim);
        Bitmap bitmap = BitmapFactory.decodeByteArray(anhphim, 0, anhphim.length);
        holder.img_khachhang_home_anhphim.setImageBitmap(bitmap);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    PhimVaTheLoai film = filteredFilmList.get(position);
                    Intent intent = new Intent(context, MovieDetail.class);
                    intent.putExtra("tenphim", film.getTenphim());
                    //intent.putExtra("tenphim", film.getTenphim());
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
    }

    @Override
    public int getItemCount() {
        return filteredFilmList.size();
    }

    class HomeViewHolder extends RecyclerView.ViewHolder {
        ImageView img_khachhang_home_anhphim;
        TextView txt_khachhang_home_tenphim;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            img_khachhang_home_anhphim = itemView.findViewById(R.id.img_khachhang_home_anhphim);
            txt_khachhang_home_tenphim = itemView.findViewById(R.id.txt_khachhang_home_tenphim);
        }
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
                filteredFilmList.addAll((List<PhimVaTheLoai>) results.values);
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
    public void updateData(List<PhimVaTheLoai> newList) {
        filteredFilmList.clear();
        filteredFilmList.addAll(newList);
        notifyDataSetChanged();
    }
}
