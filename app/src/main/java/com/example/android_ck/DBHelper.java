package com.example.android_ck;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBName = "app.db";
    public DBHelper(@Nullable Context context) {
        super(context, DBName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String taikhoan = "CREATE TABLE taikhoan(" +
                "tentaikhoan TEXT PRIMARY KEY," +
                "matkhau TEXT," +
                "quyen TEXT," +
                "ngaytao TEXT)";
        db.execSQL(taikhoan);
        // Tạo bảng Thể loại
        String theloai = "CREATE TABLE theloai(" +
                "matheloai INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tentheloai TEXT)";
        db.execSQL(theloai);

        // Tạo bảng Phim
        String phim = "CREATE TABLE phim(" +
                "maphim INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenphim TEXT," +
                "anhphim TEXT," +
                "ngaycongchieu TEXT," +
                "mota TEXT," +
                "thoiluong TEXT," +
                "gia INTEGER," +
                "matheloai INTEGER," +
                "FOREIGN KEY(matheloai) REFERENCES theloai(matheloai))";
        db.execSQL(phim);
        // Tạo bảng Danh sách yêu thích
        String danhsachyeuthich = "CREATE TABLE danhsachyeuthich(" +
                "iddansach INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tentaikhoan TEXT," +
                "maphim INTEGER," +
                "FOREIGN KEY(tentaikhoan) REFERENCES taikhoan(tentaikhoan)," +
                "FOREIGN KEY(maphim) REFERENCES phim(maphim))";
        db.execSQL(danhsachyeuthich);

        // Tạo bảng Hóa đơn
        String hoadon = "CREATE TABLE hoadon(" +
                "idhoadon INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tentaikhoan TEXT," +
                "ngaydat TEXT," +
                "FOREIGN KEY(tentaikhoan) REFERENCES taikhoan(tentaikhoan))";
        db.execSQL(hoadon);

        // Tạo bảng Chi tiết hóa đơn
        String chitiethoadon = "CREATE TABLE chitiethoadon(" +
                "idcthoadon INTEGER PRIMARY KEY AUTOINCREMENT," +
                "mahoadon INTEGER," +
                "maphim INTEGER," +
                "soluong INTEGER," +
                "thanhtien INTEGER," +
                "FOREIGN KEY(mahoadon) REFERENCES hoadon(idhoadon)," +
                "FOREIGN KEY(maphim) REFERENCES phim(maphim))";
        db.execSQL(chitiethoadon);



        // Kiểm tra xem có tài khoản admin trong cơ sở dữ liệu hay không
        Cursor cursor = db.rawQuery("SELECT * FROM taikhoan WHERE quyen = ?", new String[]{"admin"});

        if (cursor.getCount() == 0) {
            // Nếu không có, thêm một tài khoản admin mặc định
            ContentValues values = new ContentValues();
            values.put("tentaikhoan", "admin");
            values.put("matkhau", "admin"); // Bạn nên sử dụng mã hóa mật khẩu trong thực tế
            values.put("quyen", "admin");
            values.put("ngaytao", "27/03/2024"); // Ngày tạo tài khoản
            db.insert("taikhoan", null, values);
        }
        cursor.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop các bảng cũ nếu tồn tại
        db.execSQL("DROP TABLE IF EXISTS taikhoan");
        db.execSQL("DROP TABLE IF EXISTS theloai");
        db.execSQL("DROP TABLE IF EXISTS phim");
        db.execSQL("DROP TABLE IF EXISTS danhsachyeuthich");
        db.execSQL("DROP TABLE IF EXISTS hoadon");
        db.execSQL("DROP TABLE IF EXISTS chitiethoadon");

        // Tạo lại các bảng mới
        onCreate(db);
    }
    public void themDanhSachYeuThich(String tentaikhoan, String maphim){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("tentaikhoan", tentaikhoan);
        cv.put("maphim", maphim);
        long result = db.insert("danhsachyeuthich", null, cv);
        if(result==-1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Added successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public void xoaDanhSachYeuThich(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("danhsachyeuthich", "iddansach=?", new String[]{String.valueOf(id)});
        if(result == -1){
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor layDuLieuBangDSYT(){
//         String query = "SELECT * FROM danhsachyeuthich dsyt INNER JOIN phim p WHERE dsyt.maphim=p.maphim";
        String query = "SELECT * FROM danhsachyeuthich";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db!=null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public boolean themTaikhoan(String tentaikhoan, String matkhau, String ngaytao){
        SQLiteDatabase myDB = this.getWritableDatabase();
        String quyen = "khachhang";
        ContentValues contentValues = new ContentValues();
        contentValues.put("tentaikhoan", tentaikhoan);
        contentValues.put("matkhau",matkhau);
        contentValues.put("quyen",quyen);
        contentValues.put("ngaytao",ngaytao);
        long result = myDB.insert("taikhoan",null,contentValues);
        if(result==-1)return false;
        else return true;
    }

    public boolean ktraTen(String tentaikhoan){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from taikhoan where tentaikhoan = ?", new String[]{tentaikhoan});
        if(cursor.getCount()>0) return true;
        else return false;
    }

    public boolean ktraDangnhap(String tentaikhoan,String matkhau){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from taikhoan where tentaikhoan = ? and matkhau = ?", new String[]{tentaikhoan,matkhau});
        if(cursor.getCount()>0) return true;
        else return false;
    }

    public boolean themThongTinCaNhan(String hoten, String gioitinh, String ngaysinh, String email, String sdt, String tentakhoan) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("hoten", hoten);
        values.put("gioitinh", gioitinh);
        values.put("ngaysinh", ngaysinh);
        values.put("email", email);
        values.put("sdt", sdt);
        values.put("tentaikhoan", tentakhoan);

        long result = db.insert("thongtincanhan", null, values);
        if(result==-1)
            return false;
        else
            return true;
    }
}
