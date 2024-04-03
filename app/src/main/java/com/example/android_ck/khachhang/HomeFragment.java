package com.example.android_ck.khachhang;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.android_ck.DBHelper;
import com.example.android_ck.R;
import com.example.android_ck.model.PhimVaTheLoai;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    SlideAdapter slideAdapter;
    DBHelper dbHelper;

    List<PhimVaTheLoai> mListPhoto;
    List<PhimVaTheLoai> movieList;
    Timer mTimer;

    RecyclerView khachhang_recy;
    SearchView searchView;
    HomeAdapter homeAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_khachhang_home, container, false);

        viewPager = view.findViewById(R.id.khachhang_slide_viewpager);
        circleIndicator = view.findViewById(R.id.khachhang_slide_circle_indicator);
        khachhang_recy = view.findViewById(R.id.khachhang_recy_phim);
        searchView = view.findViewById(R.id.searchview_kh);

        dbHelper = new DBHelper(getActivity());

        mListPhoto = getListPhoto();
        slideAdapter = new SlideAdapter(getActivity(), mListPhoto);

        viewPager.setAdapter(slideAdapter);
        circleIndicator.setViewPager(viewPager);
        slideAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

        autoSlideImages();

        movieList = dbHelper.getAllMoviesWithGenre();

        homeAdapter = new HomeAdapter(getActivity(), movieList);
        khachhang_recy.setAdapter(homeAdapter);
        onResume();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        khachhang_recy.setLayoutManager(layoutManager);


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
}
