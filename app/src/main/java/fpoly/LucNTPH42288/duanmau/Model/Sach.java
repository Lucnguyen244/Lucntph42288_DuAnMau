package fpoly.LucNTPH42288.duanmau.Model;

public class Sach {
    private int maSach;
    private String tenSach;
    private int giaThue;
    private int maLoai;
    private String tenLoai;
    private int SoLanMuon, namxb;
    private int soluongdamuon;

    public Sach() {
    }

    public void setSoLanMuon(int soLanMuon) {
        SoLanMuon = soLanMuon;
    }

    public int getNamxb() {
        return namxb;
    }

    public void setNamxb(int namxb) {
        this.namxb = namxb;
    }

    public Sach(int maSach, String tenSach, int giaThue, int namxb, int maLoai, String tenLoai) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.giaThue = giaThue;
        this.namxb = namxb;
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
    }

    public int getSoluongdamuon() {
        return soluongdamuon;
    }

    public void setSoluongdamuon(int soluongdamuon) {
        this.soluongdamuon = soluongdamuon;
    }

    public Sach(int maSach, String tenSach, int giaThue, int maLoai, String tenLoai) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.giaThue = giaThue;
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
    }

    public Sach(int maSach, String tenSach, int soluongdamuon) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.soluongdamuon = soluongdamuon;
    }
//    public Sach(int MaSach, String TenSach, int soLanMuon) {
//        maSach = MaSach;
//        tenSach = TenSach;
//        SoLanMuon = soLanMuon;
//    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public int getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(int giaThue) {
        this.giaThue = giaThue;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public int getSoLanMuon() {
        return SoLanMuon;
    }
}
