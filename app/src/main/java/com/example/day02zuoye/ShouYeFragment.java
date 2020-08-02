package com.example.day02zuoye;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShouYeFragment extends Fragment implements IView {

    private ArrayList<FuliBean.ResultsBean> list;
    private RecyclerView rec;
    private RecAdapter recAdapter;
    private IPresenter iPresenter;
    private int position;
    private Button bt_delete;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shou_ye, container, false);
        iPresenter = new IPresenter(this);
        iPresenter.getData();
        initView(view);
        return view;

    }





    private void initView(View view) {
        rec = view.findViewById(R.id.rec);
        list = new ArrayList<>();
        rec.setLayoutManager(new LinearLayoutManager(getContext()));
        recAdapter = new RecAdapter(list,getContext());
        rec.setAdapter(recAdapter);

        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.popo, null, false);
        bt_delete = inflate.findViewById(R.id.delete);
        final PopupWindow popupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);



        recAdapter.setOnClickLong(new RecAdapter.setOnClickLong() {
            @Override
            public void OnClickLong(View view, int position) {

                popupWindow.showAsDropDown(view,100,100);
                popupWindow.setBackgroundDrawable(null);
                popupWindow.setOutsideTouchable(true);

            }
        });
        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.remove(list.get(position));
                recAdapter.notifyDataSetChanged();
                popupWindow.dismiss();
            }
        });

    }

    @Override
    public void dui(List<FuliBean.ResultsBean> fulibean) {
        Toast.makeText(getContext(), "ok", Toast.LENGTH_SHORT).show();
        list.addAll(fulibean);
        recAdapter.notifyDataSetChanged();

    }

    @Override
    public void cuo(String cuo) {
        Toast.makeText(getContext(), cuo, Toast.LENGTH_SHORT).show();
    }
}
