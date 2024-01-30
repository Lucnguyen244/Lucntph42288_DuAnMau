package fpoly.LucNTPH42288.duanmau.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


import java.util.Calendar;

import fpoly.LucNTPH42288.duanmau.DAO.ThongKeDao;
import fpoly.LucNTPH42288.duanmau.R;


public class DoanhThuFrg extends Fragment {


    public DoanhThuFrg() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doanh_thu_frg, container, false);
        EditText edtStart = view.findViewById(R.id.edtNgayBatDau);
        EditText edtEnd = view.findViewById(R.id.edtNgayKetThuc);
        Button btnThongKe = view.findViewById(R.id.btnThongKeDoanhThu);
        TextView txtKetQua = view.findViewById(R.id.txtKetQuaThongKe);
        Calendar calendar = Calendar.getInstance();
        edtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                                String ngay = "";
                                String thang = "";
                                if (dayOfMonth < 10) {
                                    ngay = "0" + dayOfMonth;
                                } else {
                                    ngay = String.valueOf(dayOfMonth);
                                }
                                if ((month + 1) < 10) {
                                    thang = "0" + (month + 1);
                                } else {
                                    thang = String.valueOf((month + 1));
                                }
                                edtStart.setText(year + "/" + thang + "/" + ngay);
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
            }
        });


        edtEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                                String ngay = "";
                                String thang = "";
                                if (dayOfMonth < 10) {
                                    ngay = "0" + dayOfMonth;
                                } else {
                                    ngay = String.valueOf(dayOfMonth);
                                }
                                if ((month + 1) < 10) {
                                    thang = "0" + (month + 1);
                                } else {
                                    thang = String.valueOf((month + 1));
                                }
                                edtEnd.setText(year + "/" + thang + "/" + ngay);
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
            }
        });

        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThongKeDao thongKeDAO = new ThongKeDao(getContext());
                String ngayBD = edtStart.getText().toString();
                String ngayKT = edtEnd.getText().toString();
                int doanhThu1 = thongKeDAO.tongDoanhThu(ngayBD, ngayKT);
                txtKetQua.setText(doanhThu1 + "VND");
            }
        });
        return view;
    }
}