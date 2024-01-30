package fpoly.LucNTPH42288.duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import fpoly.LucNTPH42288.duanmau.Database.DbHelper;


public class ThuThuDao {
    private DbHelper dbHelper;

    public ThuThuDao(Context context) {
        dbHelper = new DbHelper(context);
    }

    public boolean checkLogin(String maTT, String password) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT *FROM THUTHU WHERE hoTen=? AND matKhau=?", new String[]{maTT, password});
        if (cursor.getCount() > 0) {
            return true;
        }
        return false;
    }

    public boolean checkOldPassword(String matKhauCu) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM THUTHU WHERE  matkhau=?", new String[]{matKhauCu});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    // Cập nhật mật khẩu mới
    public void updatePassword(String tenDangNhap, String matKhauMoi) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("matKhau", matKhauMoi);
        db.update("ThuTHu", values, "tendangnhap=?", new String[]{tenDangNhap});
        db.close();
    }

    public boolean updateMK(String username, String oldPass, String newPass) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from ThuThu where hoTen = ? and matKhau = ?", new String[]{username, oldPass});
        if (cursor.getCount() > 0) {
            ContentValues values = new ContentValues();
            values.put("matKhau", newPass);
            long check = db.update("ThuThu", values, "hoTen = ?", new String[]{username});
            if (check == -1) {
                return false;
            } else {
                return true;
            }
        }
        return false;


    }
}
