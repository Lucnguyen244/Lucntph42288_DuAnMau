package fpoly.LucNTPH42288.duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;

import fpoly.LucNTPH42288.duanmau.Database.DbHelper;
import fpoly.LucNTPH42288.duanmau.Model.Sach;

public class SachDao {
    DbHelper dbHelper;

    public SachDao(Context context) {
        dbHelper = new DbHelper(context);
    }

    public ArrayList<Sach> getSachAll() {
        ArrayList<Sach> list = new ArrayList();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select sc.maSach, sc.tenSach, sc.tienThue,sc.namxb, ls.maLoai, ls.tenLoai from SACH sc, LOAISACH ls where sc.maLoai = ls.maLoai", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new Sach(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getString(5)));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public boolean insert(String tensach, int tienthue, int namxb, int maloai) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenSach", tensach);
        values.put("tienThue", tienthue);
        values.put("namxb", namxb);
        values.put("maLoai", maloai);
        long check = db.insert("SACH", null, values);
        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean update(int masach, String tensach, int giathue, int namxb, int maloai) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenSach", tensach);
        values.put("tienThue", giathue);
        values.put("namxb", namxb);
        values.put("maLoai", maloai);
        long check = db.update("SACH", values, "maSach = ?", new String[]{String.valueOf(masach)});
        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }

    public int delete(int masach) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from PHIEUMUON where maSach = ?", new String[]{String.valueOf(masach)});
        if (cursor.getCount() != 0) {
            return -1;
        }

        long check = db.delete("SACH", "maSach = ?", new String[]{String.valueOf(masach)});
        if (check == -1) {
            return 0;
        } else {
            return 1;
        }
    }

    // ...

    public ArrayList<Sach> getTangdan() {
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT sc.maSach, sc.tenSach, sc.tienThue, ls.maLoai, ls.tenLoai FROM SACH sc, LOAISACH ls WHERE sc.maLoai = ls.maLoai ORDER BY sc.tienThue ASC", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new Sach(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getString(4)));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public ArrayList<Sach> getGiamdan() {
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT sc.maSach, sc.tenSach, sc.tienThue, ls.maLoai, ls.tenLoai FROM SACH sc, LOAISACH ls WHERE sc.maLoai = ls.maLoai ORDER BY sc.tienThue DESC", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new Sach(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getString(4)));
            } while (cursor.moveToNext());
        }
        return list;
    }


}


