package com.example.day02dangridaima.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.day02dangridaima.BaseApp;
import com.example.day02dangridaima.bean.Bean;

import java.util.ArrayList;
//1个app有几个上下文: 1 + activity数量 + service数量
//一个应用只有一个application
public class SqliteUtil {
    //为了避免内存泄漏,传递的上下文不使用activity,application
    private static volatile SqliteUtil sSqliteUtil;
    private MyHelper mMyHelper;



    private SqliteUtil(){
        mMyHelper = new MyHelper(BaseApp.sBaseApp);
    }
    public static SqliteUtil getInstance(){
        if (sSqliteUtil == null){
            synchronized (SqliteUtil.class){
                if (sSqliteUtil == null){
                    sSqliteUtil = new SqliteUtil();
                }
            }
        }

        return sSqliteUtil;
    }

    public void insert(Bean.ResultsBean bean){
        SQLiteDatabase db = mMyHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id",bean.get_id());
        values.put("des",bean.getDesc());
        values.put("url",bean.getUrl());
        db.insert("info",null,values);
        db.close();
    }

    public void delete(Bean.ResultsBean bean){
        SQLiteDatabase db = mMyHelper.getWritableDatabase();
        //String whereClause, 条件
        // String[] whereArgs,条件的参数
        db.delete("info","id=?",new String[]{bean.get_id()});
        db.close();
    }

    public void update(Bean.ResultsBean bean){
        SQLiteDatabase db = mMyHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("des",bean.getDesc());
        values.put("url",bean.getUrl());
        db.update("info",values,"id=?",new String[]{bean.get_id()});
        db.close();
    }

    public ArrayList<Bean.ResultsBean> queryAll(){

        ArrayList<Bean.ResultsBean> list = new ArrayList<>();
        SQLiteDatabase db = mMyHelper.getWritableDatabase();
        Cursor cursor = db.query("info", null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()){
            do {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String des = cursor.getString(cursor.getColumnIndex("des"));
                String url = cursor.getString(cursor.getColumnIndex("url"));

                Bean.ResultsBean bean = new Bean.ResultsBean();
                bean.set_id(id);
                bean.setDesc(des);
                bean.setUrl(url);
                list.add(bean);
            }while (cursor.moveToNext());
        }

        db.close();

        return list;
    }

    //都可以
    public ArrayList<Bean.ResultsBean> queryAll2(){

        ArrayList<Bean.ResultsBean> list = new ArrayList<>();
        SQLiteDatabase db = mMyHelper.getWritableDatabase();
        Cursor cursor = db.query("info", null, null, null, null, null, null);

        while (cursor.moveToNext()){
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String des = cursor.getString(cursor.getColumnIndex("des"));
            String url = cursor.getString(cursor.getColumnIndex("url"));

            Bean.ResultsBean bean = new Bean.ResultsBean();
            bean.set_id(id);
            bean.setDesc(des);
            bean.setUrl(url);
            list.add(bean);
        }
        db.close();

        return list;
    }

    /**
     * 根据id去查
     * @param id
     * @return
     */
    public ArrayList<Bean.ResultsBean> query(String id){

        ArrayList<Bean.ResultsBean> list = new ArrayList<>();
        SQLiteDatabase db = mMyHelper.getWritableDatabase();
        // String[] columns,查询的列 ,new String[]{"url",}
        // String selection,条件 "id=?"
        //            String[] selectionArgs, new String[]{id}
        //            String groupBy, 分组
        //            String having,分组条件
        //            String orderBy 排序
        Cursor cursor = db.query("info", null, "id=?",
                new String[]{id}, null, null, null);

        while (cursor.moveToNext()){

            String des = cursor.getString(cursor.getColumnIndex("des"));
            String url = cursor.getString(cursor.getColumnIndex("url"));

            Bean.ResultsBean bean = new Bean.ResultsBean();
            bean.set_id(id);
            bean.setDesc(des);
            bean.setUrl(url);
            list.add(bean);
        }
        db.close();

        return list;
    }

}
