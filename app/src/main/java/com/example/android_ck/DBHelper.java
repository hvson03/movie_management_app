package com.example.android_ck;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.android_ck.model.ThongTinCaNhan;
import com.example.android_ck.model.item_user;

import java.util.ArrayList;
import java.util.List;

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

        // Tạo bảng thông tin cá nhân
        String thongtincanhan = "CREATE TABLE thongtincanhan(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "hoten TEXT," +
                "gioitinh TEXT," +
                "ngaysinh TEXT," +
                "email TEXT," +
                "sdt TEXT," +
                "tentaikhoan TEXT," +
                "FOREIGN KEY(tentaikhoan) REFERENCES taikhoan(tentaikhoan))";
        db.execSQL(thongtincanhan);


        // Tạo bảng Thể loại
        String theloai = "CREATE TABLE theloai(" +
                "matheloai INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tentheloai TEXT)";
        db.execSQL(theloai);

        // Tạo bảng Phim
        String phim = "CREATE TABLE phim(" +
                "maphim INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenphim TEXT," +
                "anhphim BLOB," +
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
        db.execSQL("DROP TABLE IF EXISTS thongtincanhan");

        // Tạo lại các bảng mới
        onCreate(db);
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
        if(result==-1)return false;
        else return true;
    }

    public boolean ktraQuenmk(String email, String tentaikhoan) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM thongtincanhan WHERE email = ? AND tentaikhoan = ?", new String[]{email, tentaikhoan});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean suatMatKhau(String tentaikhoan, String matkhauMoi) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("matkhau", matkhauMoi);

        int rowsUpdated = myDB.update("taikhoan", contentValues, "tentaikhoan = ?", new String[]{tentaikhoan});

        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean suaThongtincanhan(String hoten, String gioitinh, String ngaysinh, String email, String sdt, String tentaikhoan) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("hoten", hoten);
        contentValues.put("gioitinh", gioitinh);
        contentValues.put("ngaysinh", ngaysinh);
        contentValues.put("email", email);
        contentValues.put("sdt", sdt);

        int rowUpdated = myDB.update("thongtincanhan", contentValues, "tentaikhoan = ?", new String[]{tentaikhoan});

        if (rowUpdated > 0) {
            return true;
        } else  {
            return false;
        }
    }

    public List<item_user> layTatCaThongTinCaNhan() {
        List<item_user> list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT thongtincanhan.hoten, taikhoan.tentaikhoan " +
                "FROM thongtincanhan " +
                "INNER JOIN taikhoan ON thongtincanhan.tentaikhoan = taikhoan.tentaikhoan " +
                "WHERE taikhoan.quyen = ?";

        Cursor cursor = db.rawQuery(query, new String[]{"khachhang"});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String hoten = cursor.getString(0);
                String tk = cursor.getString(1);
                list.add(new item_user(tk, hoten));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    public Cursor layThongTinCaNhan(String tentaikhoan) {
        SQLiteDatabase myDB = this.getReadableDatabase();
        Cursor cursor = myDB.rawQuery(
                "SELECT * FROM thongtincanhan WHERE tentaikhoan = ?", new String[]{tentaikhoan}
        );
        return cursor;
    }

    public Cursor layThongtintaikhoan(String tentaikhoan) {
        SQLiteDatabase myDB = this.getReadableDatabase();
        Cursor cursor = myDB.rawQuery(
                "SELECT * FROM taikhoan WHERE tentaikhoan = ?", new String[]{tentaikhoan}
        );
        return cursor;
    }

    public int layTongSoLuongPhimYeuThich(String tentaikhoan) {
        SQLiteDatabase myDB = this.getReadableDatabase();
        Cursor cursor = myDB.rawQuery(
                "SELECT COUNT(*) FROM danhsachyeuthich WHERE tentaikhoan = ?", new String[]{tentaikhoan}
        );

        int totalCount = 0;
        if (cursor.moveToFirst()) {
            totalCount = cursor.getInt(0);
        }

        cursor.close();
        return totalCount;
    }

    public int layTongThanhTien(String tentaikhoan) {
        SQLiteDatabase myDB = this.getReadableDatabase();
        Cursor cursor = myDB.rawQuery(
                "SELECT SUM(thanhtien) FROM chitiethoadon INNER JOIN hoadon ON chitiethoadon.mahoadon = hoadon.idhoadon WHERE hoadon.tentaikhoan = ?", new String[]{tentaikhoan}
        );

        int totalAmount = 0;
        if (cursor.moveToFirst()) {
            totalAmount = cursor.getInt(0);
        }

        cursor.close();
        return totalAmount;
    }

    public boolean xoaTaiKhoan(String tentaikhoan) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            // Xóa thông tin cá nhân của tài khoản từ bảng 'thongtincanhan'
            db.delete("thongtincanhan", "tentaikhoan = ?", new String[]{tentaikhoan});
            // Xóa danh sách yêu thích của tài khoản
            db.delete("danhsachyeuthich", "tentaikhoan = ?", new String[]{tentaikhoan});
            // Xóa hóa đơn của tài khoản
            db.delete("hoadon", "tentaikhoan = ?", new String[]{tentaikhoan});
            // Xóa tài khoản từ bảng 'taikhoan'
            int result = db.delete("taikhoan", "tentaikhoan = ?", new String[]{tentaikhoan});

            // Nếu số dòng bị ảnh hưởng bởi lệnh xóa lớn hơn 0, tức là đã xóa thành công
            if (result > 0) {
                return true;
            } else {
                return false;
            }
        } finally {
            // Đóng cơ sở dữ liệu
            db.close();
        }
    }




//    public void themDanhSachYeuThich(String tentaikhoan, String maphim){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put("tentaikhoan", tentaikhoan);
//        cv.put("maphim", maphim);
//        long result = db.insert("danhsachyeuthich", null, cv);
//        if(result==-1){
//            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
//        }else{
//            Toast.makeText(context, "Added successfully!", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public void xoaDanhSachYeuThich(Integer id){
//        SQLiteDatabase db = this.getWritableDatabase();
//        long result = db.delete("danhsachyeuthich", "iddansach=?", new String[]{String.valueOf(id)});
//        if(result == -1){
//            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
//        }else{
//            Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public Cursor layDuLieuBangDSYT(){
////        String query = "SELECT * FROM danhsachyeuthich dsyt INNER JOIN phim p WHERE dsyt.maphim=p.maphim";
//        String query = "SELECT * FROM danhsachyeuthich";
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = null;
//        if(db!=null){
//            cursor = db.rawQuery(query, null);
//        }
//        return cursor;
//    }
}
