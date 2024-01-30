package fpoly.LucNTPH42288.duanmau.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import fpoly.LucNTPH42288.duanmau.Adapter.Top10Adapter;
import fpoly.LucNTPH42288.duanmau.DAO.ThongKeDao;
import fpoly.LucNTPH42288.duanmau.Model.Sach;
import fpoly.LucNTPH42288.duanmau.R;


public class Top10Frg extends Fragment {


    public Top10Frg() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top10_frg, container, false);
        RecyclerView rcvTop10 = view.findViewById(R.id.rcvTop10);

        ThongKeDao thongKeDAO = new ThongKeDao(getContext());
        ArrayList<Sach> list = thongKeDAO.getTop10();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvTop10.setLayoutManager(layoutManager);
        Top10Adapter adapter = new Top10Adapter(getContext(), list);
        rcvTop10.setAdapter(adapter);
        return view;
    }
}