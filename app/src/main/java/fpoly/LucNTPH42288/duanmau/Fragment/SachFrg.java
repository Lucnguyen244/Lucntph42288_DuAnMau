package fpoly.LucNTPH42288.duanmau.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashMap;

import fpoly.LucNTPH42288.duanmau.Adapter.SachAdapter;
import fpoly.LucNTPH42288.duanmau.DAO.SachDao;
import fpoly.LucNTPH42288.duanmau.DAO.TheLoaiDao;
import fpoly.LucNTPH42288.duanmau.Model.Sach;
import fpoly.LucNTPH42288.duanmau.Model.TheLoai;
import fpoly.LucNTPH42288.duanmau.R;


public class SachFrg extends Fragment {

    RecyclerView rcv;
    FloatingActionButton fltaddsach;
    SachDao dao;
    SachAdapter adapter;
    ArrayList<Sach> list = new ArrayList<>();
    ArrayList<Sach> mangdem = new ArrayList<>();
    EditText edtimkiem;
    Button btntangdan, btngiamdan;

    public SachFrg() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sach_frg, container, false);
        rcv = view.findViewById(R.id.rcvsach);
        fltaddsach = view.findViewById(R.id.fltaddsach);
        dao = new SachDao(getContext());
        list = dao.getSachAll();
        mangdem = dao.getSachAll();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(layoutManager);
        adapter = new SachAdapter(list, getContext(), getDSLoaiSach());
        rcv.setAdapter(adapter);
        edtimkiem = view.findViewById(R.id.edtimkiemsach);
        btntangdan = view.findViewById(R.id.btntang);
        btngiamdan = view.findViewById(R.id.btngiam);
        edtimkiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            ////ko code
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                list.clear();
                for (Sach sach : mangdem) {
                    if (sach.getTenSach().contains(s.toString())) {
                        list.add(sach);
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {
////ko code
            }
        });
        btntangdan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list = dao.getTangdan(); // Lấy danh sách sách đã sắp xếp tăng dần
                adapter = new SachAdapter(list, getContext(), getDSLoaiSach());
                rcv.setAdapter(adapter);
            }
        });

        btngiamdan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list = dao.getGiamdan(); // Lấy danh sách sách đã sắp xếp giảm dần
                adapter = new SachAdapter(list, getContext(), getDSLoaiSach());
                rcv.setAdapter(adapter);
            }
        });
        fltaddsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSach();
            }
        });
        return view;
    }

    public void addSach() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.add_sach, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        TextInputLayout in_TenSach = view.findViewById(R.id.in_addTenS);
        TextInputLayout in_GiaThue = view.findViewById(R.id.in_addGiaThue);
        TextInputLayout in_Namxb = view.findViewById(R.id.in_addNamxb);
        TextInputEditText ed_TenSach = view.findViewById(R.id.ed_addTenS);
        TextInputEditText ed_Namxb = view.findViewById(R.id.ed_addNamxb);
        TextInputEditText ed_GiaThue = view.findViewById(R.id.ed_addGiaThue);
        Spinner spnSach = view.findViewById(R.id.spnSach);
        Button add = view.findViewById(R.id.S_add);
        Button cancel = view.findViewById(R.id.S_Cancel);
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                getDSLoaiSach(),
                android.R.layout.simple_list_item_1,
                new String[]{"tenLoai"},
                new int[]{android.R.id.text1}
        );
        spnSach.setAdapter(simpleAdapter);
        add.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                String tensach = ed_TenSach.getText().toString();
                String tien = ed_GiaThue.getText().toString();
                String namxb = ed_Namxb.getText().toString();

                HashMap<String, Object> hs = (HashMap<String, Object>) spnSach.getSelectedItem();
                int maloai = (int) hs.get("maLoai");


                if (tensach.isEmpty() || tien.isEmpty() || namxb.isEmpty()) {
                    if (tensach.equals("")) {
                        in_TenSach.setError("Vui lòng không để trống tên sách");
                    } else {
                        in_TenSach.setError(null);
                    }

                    if (tien.equals("")) {
                        in_GiaThue.setError("Vui lòng không để trống giá thuê");
                    } else {
                        in_GiaThue.setError(null);
                    }
                    if (namxb.equals("")) {
                        in_Namxb.setError("Vui lòng không để  trong nam xuat ban");
                    } else {
                        in_Namxb.setError(null);
                    }
                } else {
                    int checktien = Integer.parseInt(tien);
                    int namxbb = Integer.parseInt(namxb);

                    boolean check = dao.insert(tensach, checktien, namxbb, maloai);
                    if (check) {
                        loadData();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Thêm thành công sách", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Thêm không thành công sách", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ed_TenSach.setText("");
//                ed_GiaThue.setText("");
                dialog.dismiss();
            }
        });
    }

    private ArrayList<HashMap<String, Object>> getDSLoaiSach() {
        TheLoaiDao loaisach = new TheLoaiDao(getContext());
        ArrayList<TheLoai> list1 = loaisach.getalltheloai();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();

        for (TheLoai ls : list1) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("maLoai", ls.getMaLoai());
            hs.put("tenLoai", ls.getTenloai());
            listHM.add(hs);
        }
        return listHM;
    }

    private void loadData() {
        list = dao.getSachAll();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(layoutManager);
        SachAdapter adapter = new SachAdapter(list, getContext(), getDSLoaiSach());
        rcv.setAdapter(adapter);
    }
}