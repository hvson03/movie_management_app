package com.example.android_ck.quanly;

import static java.security.AccessController.getContext;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.android_ck.DBHelper;
import com.example.android_ck.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FilmAdd extends AppCompatActivity {
    private static final int REQUEST_IMAGE_PICK = 2;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    ImageButton btn_themphim_back,btn_themphim_chonanh,btn_themphim_them;
    Spinner spn_themphim_chontheloai;
    EditText edt_themphim_tenphim, edt_themphim_mota,edt_themphim_thoiluong,edt_themphim_ngaykhoichieu,edt_themphim_giave;
    ImageView img_anh;
    DBHelper dbHelper;
    FilmAdapter filmAdapter;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanly_phim_them);
        btn_themphim_back = findViewById(R.id.imgb_themphim_trolai);
        spn_themphim_chontheloai = findViewById(R.id.spinner2);
        edt_themphim_tenphim = findViewById(R.id.edt_ql_themphim_tenphim);
        edt_themphim_mota = findViewById(R.id.edt_ql_themphim_mota);
        edt_themphim_thoiluong = findViewById(R.id.edt_ql_themphim_thoiluong);
        edt_themphim_ngaykhoichieu = findViewById(R.id.edt_ql_themphim_ngaykhoichieu);
        edt_themphim_giave = findViewById(R.id.edt_ql_themphim_gia);
        btn_themphim_chonanh = findViewById(R.id.btn_ql_themphim_chonanh);
        btn_themphim_them = findViewById(R.id.btn_ql_themphim_them);
        img_anh = findViewById(R.id.img_ql_themphim_anh);

        dbHelper = new DBHelper(this);

        // Thiết lập adapter cho Spinner
        spn_themphim_chontheloai = findViewById(R.id.spinner2);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_themphim_chontheloai.setAdapter(adapter);

        // Cập nhật dữ liệu cho Spinner
        updateSpinnerData();

        btn_themphim_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        edt_themphim_thoiluong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        edt_themphim_ngaykhoichieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        btn_themphim_chonanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickerDialog();
            }
        });

        btn_themphim_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter.getCount() == 0) {
                    showToast("Vui lòng thêm ít nhất một thể loại trước khi thêm phim");
                    return;
                }

                // Kiểm tra xem người dùng đã chọn ảnh hay chưa
                if (img_anh.getDrawable() == null) {
                    showToast("Vui lòng điền đầy đủ thông tin");
                    return;
                }
                // Kiểm tra xem các trường dữ liệu có rỗng không
                String tenphim = edt_themphim_tenphim.getText().toString();
                String mota = edt_themphim_mota.getText().toString();
                String thoiluong = edt_themphim_thoiluong.getText().toString();
                String ngaykhoichieu = edt_themphim_ngaykhoichieu.getText().toString();
                String giavestr = edt_themphim_giave.getText().toString();

                if (tenphim.isEmpty() || mota.isEmpty() || thoiluong.isEmpty() || ngaykhoichieu.isEmpty() || giavestr.isEmpty()) {
                    // Hiển thị Toast nếu một trong các trường dữ liệu bị bỏ trống
                    showToast("Vui lòng điền đầy đủ thông tin");
                    return;
                }

                // Kiểm tra thời lượng phải lớn hơn 0:00
                if (thoiluong.equals("0:0")) {
                    showToast("Thời lượng phải lớn hơn 0:00");
                    return;
                }

                Calendar calendar = Calendar.getInstance();
                String[] parts = ngaykhoichieu.split("/");
                int year = Integer.parseInt(parts[2]);
                int month = Integer.parseInt(parts[1]) - 1;
                int day = Integer.parseInt(parts[0]);
                calendar.set(year, month, day);
                // Kiểm tra nếu ngày khởi chiếu nhỏ hơn ngày hiện tại
                if (calendar.compareTo(Calendar.getInstance()) < 0) {
                    showToast("Ngày khởi chiếu phải lớn hơn hoặc bằng ngày hiện tại");
                    return;
                }

                // Kiểm tra giá vé phải là số nguyên dương
                int giave;
                try {
                    giave = Integer.parseInt(giavestr);
                } catch (NumberFormatException e) {
                    showToast("Giá vé không hợp lệ");
                    return;
                }
                if (giave <= 0) {
                    showToast("Giá vé phải là số nguyên dương");
                    return;
                }

                // Chuyển đổi ảnh từ ImageView thành mảng byte[]
                byte[] anhphim = convertImageViewToByteArray(img_anh);

                // Thêm phim vào cơ sở dữ liệu
                boolean result = dbHelper.addMovie(tenphim, anhphim, ngaykhoichieu, mota, thoiluong, giave, spn_themphim_chontheloai.getSelectedItem().toString());
                if (result) {
                    showToast("Thêm phim thành công");
                    // Cập nhật danh sách phim trong Adapter
                    filmAdapter.updateData(dbHelper.getAllMoviesWithGenre());
                    // Thông báo cho RecyclerView cập nhật giao diện
                    filmAdapter.notifyDataSetChanged();
                    finish();
                } else {
                    showToast("Thêm phim thất bại");
                }
            }
        });

    }
    // chuyển đổi ảnh
    private byte[] convertImageViewToByteArray(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        Bitmap scaledBitmap = scaleBitmap(bitmap, 500); // Thay đổi 500 thành kích thước tối đa mong muốn
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        scaledBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
    //nén ảnh
    private Bitmap scaleBitmap(Bitmap bitmap, int maxSize) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }

    private void showToast(String message) {
        Toast.makeText(FilmAdd.this, message, Toast.LENGTH_SHORT).show();
    }
    // Phương thức này được gọi để cập nhật dữ liệu cho Spinner
    private void updateSpinnerData() {
        // Lấy danh sách tên thể loại từ cơ sở dữ liệu
        List<String> genreNames = dbHelper.getNameGenre();

        // Xóa dữ liệu cũ của adapter và thêm dữ liệu mới
        adapter.clear();
        adapter.addAll(genreNames);
        adapter.notifyDataSetChanged();
    }




    private void showTimePickerDialog() {
        // Lấy thời gian hiện tại
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Tạo dialog TimePicker
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Xử lý khi người dùng chọn giờ
                        String time = hourOfDay + " giờ " + minute +" phút";
                        edt_themphim_thoiluong.setText(time);
                    }
                }, hour, minute, true); // true: 24h format, false: 12h format

        // Hiển thị dialog
        timePickerDialog.show();
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Tạo DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(FilmAdd.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Lưu ngày được chọn
                        calendar.set(year, month, dayOfMonth);
                        // Hiển thị ngày được chọn trong EditText
                        edt_themphim_ngaykhoichieu.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, dayOfMonth);

        // Hiển thị DatePickerDialog
        datePickerDialog.show();
    }

    //Chọn ảnh
    private void showImagePickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn ảnh");
        builder.setItems(new CharSequence[]{"Chụp ảnh", "Chọn từ thư viện ảnh"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        dispatchTakePictureIntent();
                        break;
                    case 1:
                        openGallery();
                        break;
                }
            }
        });
        builder.show();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                img_anh.setImageBitmap(imageBitmap);
            } else if (requestCode == REQUEST_IMAGE_PICK && data != null) {
                Uri selectedImageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                    img_anh.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}