package fpoly.LucNTPH42288.duanmau.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import fpoly.LucNTPH42288.duanmau.Adapter.PhieuMuonAdapter;
import fpoly.LucNTPH42288.duanmau.DAO.PhieuMuonDao;
import fpoly.LucNTPH42288.duanmau.DAO.SachDao;
import fpoly.LucNTPH42288.duanmau.DAO.ThanhVienDao;
import fpoly.LucNTPH42288.duanmau.Model.PhieuMuon;
import fpoly.LucNTPH42288.duanmau.Model.Sach;
import fpoly.LucNTPH42288.duanmau.Model.ThanhVien;
import fpoly.LucNTPH42288.duanmau.R;


public class PhieuMuonFrg extends Fragment {


    PhieuMuonAdapter adapter;
    PhieuMuonDao dao;
    RecyclerView rcv;

    public PhieuMuonFrg() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phieu_muon_frg, container, false);
        rcv = view.findViewById(R.id.rcvpm);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.fltaddphieumuon);
        dao = new PhieuMuonDao(getContext());
        ArrayList<PhieuMuon> list = dao.getDSPhieuMuon();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(layoutManager);
        adapter = new PhieuMuonAdapter(list, getContext());
        rcv.setAdapter(adapter);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAddPM();
            }
        });
        return view;
    }

    void dialogAddPM() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.add_phieumuon, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        Spinner spnThanhVien = view.findViewById(R.id.spnThanhVien);
        Spinner spnSach = view.findViewById(R.id.spnSach);
        Button PM_add = view.findViewById(R.id.PM_add);
        Button PM_cancel = view.findViewById(R.id.PM_Cancel);
        getDataThanhVien(spnThanhVien);
        getDataSach(spnSach);

        PM_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> hsTV = (HashMap<String, Object>) spnThanhVien.getSelectedItem();
                int matv = (int) hsTV.get("maTV");
                HashMap<String, Object> hsSach = (HashMap<String, Object>) spnSach.getSelectedItem();
                int masach = (int) hsSach.get("maSach");

                int tien = (int) hsSach.get("tienThue");

                AddPM(matv, masach, tien);

            }
        });
    }

    private void AddPM(int matv, int masach, int tien) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("User_File", Context.MODE_PRIVATE);
        String matt = sharedPreferences.getString("Username", "");

        Date currenTime = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String ngay = simpleDateFormat.format(currenTime);

        PhieuMuon phieuMuon = new PhieuMuon(matt, matv, masach, tien, 0, ngay);
        boolean check = dao.insert(phieuMuon);
        if (check) {
            Toast.makeText(getContext(), "Thêm phiếu mượn thành công", Toast.LENGTH_SHORT).show();
            loadData();
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getContext(), "Thêm phiếu mượn thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void getDataThanhVien(Spinner spnThanhVien) {
        ThanhVienDao thanhVienDao = new ThanhVienDao(getContext());
        ArrayList<ThanhVien> list = thanhVienDao.getalltv();

        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (ThanhVien tv : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("maTV", tv.getMaTV());
            hs.put("hoTen", tv.getHoTen());
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"hoTen"},
                new int[]{android.R.id.text1});
        spnThanhVien.setAdapter(simpleAdapter);
    }

    private void getDataSach(Spinner spnSach) {
        SachDao sachDao = new SachDao(getContext());
        ArrayList<Sach> list = sachDao.getSachAll();

        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (Sach sc : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("maSach", sc.getMaSach());
            hs.put("tenSach", sc.getTenSach());
            hs.put("tienThue", sc.getGiaThue());
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"tenSach"},
                new int[]{android.R.id.text1});
        spnSach.setAdapter(simpleAdapter);
    }

    private void loadData() {
        ArrayList<PhieuMuon> list = dao.getDSPhieuMuon();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(layoutManager);
        adapter = new PhieuMuonAdapter(list, getContext());
        rcv.setAdapter(adapter);
    }
}