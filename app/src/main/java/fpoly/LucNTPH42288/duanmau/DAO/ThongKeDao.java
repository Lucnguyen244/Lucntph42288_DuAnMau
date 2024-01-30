package fpoly.LucNTPH42288.duanmau.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;

import fpoly.LucNTPH42288.duanmau.Database.DbHelper;
import fpoly.LucNTPH42288.duanmau.Model.Sach;

public class ThongKeDao {

    private DbHelper dbHelper;

    public ThongKeDao(Context context) {
        dbHelper = new DbHelper(context);
    }

    public ArrayList<Sach> getTop10() {
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT PHIEUMUON.maSach, SACH.tenSach, COUNT(PHIEUMUON.maSach) FROM PHIEUMUON, SACH WHERE PHIEUMUON.maSach = SACH.maSach GROUP BY PHIEUMUON.maSach, SACH.tenSach ORDER BY COUNT(PHIEUMUON.maSach) DESC LIMIT 10", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new Sach(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
            } while (cursor.moveToNext());
        }

        return list;
    }

    public int tongDoanhThu(String ngayBatDau, String ngayKetThuc) {
        ngayBatDau = ngayBatDau.replace("/", "");
        ngayKetThuc = ngayKetThuc.replace("/", "");

        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT SUM(TienThue) FROM PHIEUMUON WHERE substr(NGAY,7) || substr(NGAY,4,2) || substr(NGAY,1,2) BETWEEN ? AND ?", new String[]{ngayBatDau, ngayKetThuc});
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            return cursor.getInt(0);
        } else {

            return 0;
        }

    }
}
