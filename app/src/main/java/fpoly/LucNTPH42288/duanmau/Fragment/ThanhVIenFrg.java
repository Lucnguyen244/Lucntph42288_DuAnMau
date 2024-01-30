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
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import fpoly.LucNTPH42288.duanmau.Adapter.ThanhVienAdapter;
import fpoly.LucNTPH42288.duanmau.DAO.ThanhVienDao;
import fpoly.LucNTPH42288.duanmau.Model.ThanhVien;
import fpoly.LucNTPH42288.duanmau.R;


public class ThanhVIenFrg extends Fragment {
    RecyclerView rcvtv;
    FloatingActionButton fltaddtv;

    ThanhVienDao dao;

    ThanhVienAdapter adapter;
    EditText timkiem;
    ArrayList<ThanhVien> list = new ArrayList<>();
    ArrayList<ThanhVien> mangdem = new ArrayList<>();

    public ThanhVIenFrg() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thanh_v_ien_frg, container, false);
        rcvtv = view.findViewById(R.id.rcvthanhvien);
        fltaddtv = view.findViewById(R.id.fltadthanhvien);
        dao = new ThanhVienDao(getContext());
        list = dao.getalltv();
        mangdem = dao.getalltv();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvtv.setLayoutManager(layoutManager);
        adapter = new ThanhVienAdapter(list, getContext());
        rcvtv.setAdapter(adapter);
        timkiem = view.findViewById(R.id.edtimkiem);
        timkiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                for (Thanh:
//                     ) {
//
//                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        fltaddtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAddTV();
            }
        });
        return view;
    }

    private void dialogAddTV() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.add_thanhvien, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextInputEditText ed_txtTenTV = view.findViewById(R.id.ed_addTenTV);
        TextInputEditText ed_txtNamSinh = view.findViewById(R.id.ed_addNamSinh);
        TextInputEditText ed_txtccd = view.findViewById(R.id.ed_addtcccd);
        TextInputLayout in_txtTenTV = view.findViewById(R.id.in_addTenTV);
        TextInputLayout in_txtNamSinh = view.findViewById(R.id.in_addNamSinh);
        TextInputLayout in_txtccd = view.findViewById(R.id.in_addcccd);

        Button btn_add = view.findViewById(R.id.TV_add);
        Button btn_cancel = view.findViewById(R.id.TV_Cancel);


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoten = ed_txtTenTV.getText().toString();
                String namsinh = ed_txtNamSinh.getText().toString();
                String cccd = ed_txtccd.getText().toString();

                boolean check = dao.insert(hoten, namsinh, cccd);

                if (hoten.isEmpty() || namsinh.isEmpty() || cccd.isEmpty()) {
                    if (hoten.equals("")) {
                        in_txtTenTV.setError("Vui lòng nhập đầy đủ tên thành viên");
                    } else {
                        in_txtTenTV.setError(null);
                    }

                    if (namsinh.equals("")) {
                        in_txtNamSinh.setError("Vui lòng nhập đầy đủ năm sinh thành viên");
                    } else {
                        in_txtNamSinh.setError(null);
                    }
                    if (cccd.equals("")) {
                        in_txtccd.setError("Vui lòng nhập đầy đủ căn cước công dân");
                    } else {
                        in_txtccd.setError(null);
                    }
                } else {
                    if (check) {
                        loadData();
                        Toast.makeText(getContext(), "Thêm Thành Viên Thành Công", Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Thêm Thành Viên Không Thành Công", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ed_txtTenTV.setText("");
//                ed_txtNamSinh.setText("");
                dialog.dismiss();
            }
        });
    }

    private void loadData() {
        list = dao.getalltv();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvtv.setLayoutManager(layoutManager);
        ThanhVienAdapter adapter = new ThanhVienAdapter(list, getContext());
        rcvtv.setAdapter(adapter);
    }
}