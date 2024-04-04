//package com.example.android_ck.khachhang;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.example.android_ck.DBHelper;
//import com.example.android_ck.R;
//
//public class AddActivity extends AppCompatActivity {
//    EditText edt_ttk, edt_tp;
//    Button btn_add_dsyt;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_add);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//        edt_ttk = findViewById(R.id.tentaikhoan);
//        edt_tp = findViewById(R.id.tenphim);
//        btn_add_dsyt = findViewById(R.id.button_add_dsyt);
//        btn_add_dsyt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DBHelper myDB = new DBHelper(AddActivity.this);
//                myDB.themDanhSachYeuThich(edt_ttk.getText().toString().trim(),
//                        edt_tp.getText().toString().trim());
//            }
//        });
//    }
//}