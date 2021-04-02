package com.tomorrow.heaven.fragment;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.tomorrow.heaven.R;
import com.tomorrow.heaven.pojo.City;
import com.tomorrow.heaven.pojo.County;
import com.tomorrow.heaven.pojo.Province;
import com.tomorrow.heaven.utils.HttpUtil;
import com.tomorrow.heaven.utils.JosnUtil;
import com.tomorrow.heaven.utils.MyApplication;

import org.jetbrains.annotations.NotNull;
import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChooseAreaFragment extends Fragment {
    //省市县标记
    private static final int LEVEL_PROVINCE = 0;
    private static final int LEVEL_CITY = 1;
    private static final int LEVEL_COUNTY = 2;

    //进度条
    private ProgressDialog progressDialog;

    //标题内容
    private TextView textView;
    private Button backButton;

    //视图内容
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<>();

    //省列表
    private List<Province> provinces;
    //市列表
    private List<City> cities;
    //县列表
    private List<County> counties;

    //选中的省
    private Province selectProvince;
    //选中的城市
    private City selectCity;

    //当前选中的级别
    private int currentLevel;


    //初始化视图
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area, container, false);
        textView = (TextView)view.findViewById(R.id.title_parent);
        backButton = (Button) view.findViewById(R.id.back_button);
        listView = (ListView) view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);
        return view;
    }

    //绑定事件
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(currentLevel == LEVEL_PROVINCE){
                    selectProvince = provinces.get(position);
                    queryCity();
                }else if (currentLevel == LEVEL_CITY){
                    selectCity = cities.get(position);
                    queryCounty();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (currentLevel == LEVEL_COUNTY){
                    queryCity();
                }else if (currentLevel == LEVEL_CITY){
                    queryProvince();
                }
            }
        });
        //第一次执行获取省级列表
        queryProvince();
    }


    //查询中国所有的省,优先从数据库查找，再从服务器去查找
    private void queryProvince(){
        textView.setText("中国");
        //取消返回按钮
        backButton.setVisibility(View.GONE);
        provinces = LitePal.findAll(Province.class);
        if(provinces.size()>0){
            dataList.clear();
            for (Province province: provinces){
                dataList.add(province.getProvinceName());
            }
            //通知更新数据
            adapter.notifyDataSetChanged();
            //设置选中第一个
            listView.setSelection(0);
            //设置当前list为省级
            currentLevel= LEVEL_PROVINCE;
        }else {
            String address = "http://guolin.tech/api/china";
            queryFromServer(address,"province");
        }
    }

    //查询选中省里的所有的市,优先从数据库查找，再从服务器去查找
    private void queryCity(){
        textView.setText(selectProvince.getProvinceName());
        //取消返回按钮
        backButton.setVisibility(View.VISIBLE);
        cities = LitePal.where("provinceid = ?",String.valueOf(selectProvince.getId())).find(City.class);
        if(cities.size()>0){
            dataList.clear();
            for (City city: cities){
                dataList.add(city.getCityName());
            }
            //通知更新数据
            adapter.notifyDataSetChanged();
            //设置选中第一个
            listView.setSelection(0);
            //设置当前list为省级
            currentLevel= LEVEL_CITY;
        }else {
            String address = "http://guolin.tech/api/china/"+selectProvince.getProvinceCode();
            queryFromServer(address,"city");
        }
    }

    //查询选中市里的所有的县,优先从数据库查找，再从服务器去查找
    private void queryCounty(){
        textView.setText(selectCity.getCityName());
        //取消返回按钮
        backButton.setVisibility(View.VISIBLE);
        counties = LitePal.where("cityid = ?",String.valueOf(selectCity.getId())).find(County.class);
        if(counties.size()>0){
            dataList.clear();
            for (County county: counties){
                dataList.add(county.getCountyName());
            }
            //通知更新数据
            adapter.notifyDataSetChanged();
            //设置选中第一个
            listView.setSelection(0);
            //设置当前list为省级
            currentLevel= LEVEL_COUNTY;
        }else {
            String address = "http://guolin.tech/api/china"+selectProvince.getId()+"/"+selectCity.getId();
            queryFromServer(address,"county");
        }
    }

    //根据传入的地址和类型从服务器上查询省市县数据
    private void queryFromServer(String address,final String type){
        showProgressDialog();
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String text = response.body().string();
                //将从服务器获取的数据存入数据库
                boolean result =false;
                if("province".equals(type)){
                    result = JosnUtil.getProvince(text);
                }else if ("city".equals(type)){
                    result = JosnUtil.getCity(text,selectProvince.getId());
                }else if ("county".equals(type)){
                    result = JosnUtil.getCounty(text,selectCity.getId());
                }

                //如果数据存放成功
                if(result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if("province".equals(type)){
                                 queryProvince();
                            }else if ("city".equals(type)){
                                queryCity();
                            }else if ("county".equals(type)){
                                queryCounty();
                            }
                        }
                    });
                }

            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getActivity(),"加载失败",Toast.LENGTH_LONG).show();
                    }
                });
            }

        });

    }

    //显示进度条对话框
    private void showProgressDialog(){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载中...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }


    //关闭进度条对话框
    private void closeProgressDialog(){
        if(null!=progressDialog){
            progressDialog.dismiss();
        }
    }

}
