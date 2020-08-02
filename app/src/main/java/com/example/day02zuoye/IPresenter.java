package com.example.day02zuoye;

import java.util.List;

public class IPresenter implements Presenter, CallBack {
    private IModel iModel;
    private IView view;

    public IPresenter(IView view) {
        iModel=new IModel();
        this.view=view;
    }

    @Override
    public void getData() {
        if (iModel!=null){
            iModel.getData(this);
        }
    }

    @Override
    public void dui(List<FuliBean.ResultsBean> fulibean) {
        if(view!=null){
            view.dui(fulibean);
        }
    }

    @Override
    public void cuo(String cuo) {
        if (view!=null)
            view.cuo(cuo);

    }
}
