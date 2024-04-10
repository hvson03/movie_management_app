package com.example.android_ck.khachhang;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.android_ck.DBHelper;
import com.example.android_ck.R;
import com.example.android_ck.adapter.HomeAdapter;
import com.example.android_ck.adapter.HomeGenreAdapter;
import com.example.android_ck.adapter.SlideAdapter;
import com.example.android_ck.model.PhimVaTheLoai;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment implements HomeGenreAdapter.GenreClickListener{
    TextView txt_khachhang_theloai_tatca;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    SlideAdapter slideAdapter;
    DBHelper dbHelper;

    List<PhimVaTheLoai> mListPhoto;
    List<PhimVaTheLoai> movieList;
    List<String> genreList;
    Timer mTimer;

    RecyclerView khachhang_recy, khachhang_recy_theloai;
    SearchView searchView;
    HomeAdapter homeAdapter;
    HomeGenreAdapter homeGenreAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_khachhang_home, container, false);

        txt_khachhang_theloai_tatca = view.findViewById(R.id.txt_khachhang_theloai_tatca);
        viewPager = view.findViewById(R.id.khachhang_slide_viewpager);
        circleIndicator = view.findViewById(R.id.khachhang_slide_circle_indicator);
        khachhang_recy = view.findViewById(R.id.khachhang_recy_phim);
        khachhang_recy_theloai = view.findViewById(R.id.khachhang_recy_theloai);
        searchView = view.findViewById(R.id.searchview_kh);

        // Slide show
        dbHelper = new DBHelper(getActivity());

        mListPhoto = getListPhoto();
        slideAdapter = new SlideAdapter(getActivity(), mListPhoto);

        viewPager.setAdapter(slideAdapter);
        circleIndicator.setViewPager(viewPager);
        slideAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

        autoSlideImages();

        //RecyclerView phim

        movieList = dbHelper.getAllMoviesWithGenre();

        homeAdapter = new HomeAdapter(getActivity(), movieList);
        khachhang_recy.setAdapter(homeAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        khachhang_recy.setLayoutManager(layoutManager);


        //RecyclerView TheLoai
        genreList = dbHelper.getDistinctGenreNames();
        homeGenreAdapter = new HomeGenreAdapter(getActivity(), genreList, this);

        khachhang_recy_theloai.setAdapter(homeGenreAdapter);
        LinearLayoutManager layoutManager_2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        khachhang_recy_theloai.setLayoutManager(layoutManager_2);



        // Lắng nghe sự kiện tìm kiếm từ SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter dữ liệu trong adapter khi có sự thay đổi trong SearchView
                homeAdapter.getFilter().filter(newText);
                homeAdapter.updateData(movieList);
                return false; // Trả về true để xác nhận rằng bạn đã xử lý sự kiện này
            }

        });

        txt_khachhang_theloai_tatca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSelected = v.isSelected();
                v.setSelected(!isSelected);
                if (!isSelected) {
                    txt_khachhang_theloai_tatca.setSelected(true);
                    onResume();
                }else {
                    txt_khachhang_theloai_tatca.setSelected(false);
                }

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
        homeAdapter.updateData(movieList);
    }

    public void updateMoviesByGenre(String genre) {
        // Gọi phương thức getMoviesByGenre với tên thể loại truyền từ Intent
        List<PhimVaTheLoai> moviesByGenre = dbHelper.getMoviesByGenre(genre);
        // Cập nhật RecyclerView với danh sách phim mới
        homeAdapter.updateData(moviesByGenre);
    }

    private List<PhimVaTheLoai> getListPhoto() {
        return dbHelper.getAllMoviesWithGenre();
    }

    private void autoSlideImages() {
        if (mListPhoto == null || mListPhoto.isEmpty() || viewPager == null) {
            return;
        }

        if (mTimer == null) {
            mTimer = new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = viewPager.getCurrentItem();
                        int totalItem = mListPhoto.size() - 1;
                        if (currentItem < totalItem) {
                            currentItem++;
                            viewPager.setCurrentItem(currentItem);
                        } else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        }, 500, 5000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }


    @Override
    public void onGenreClicked(String genreName) {
        updateMoviesByGenre(genreName);
    }
}
