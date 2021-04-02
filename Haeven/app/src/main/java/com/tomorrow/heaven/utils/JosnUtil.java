package com.tomorrow.heaven.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tomorrow.heaven.pojo.City;
import com.tomorrow.heaven.pojo.County;
import com.tomorrow.heaven.pojo.Province;

import java.util.ArrayList;
import java.util.List;

public class JosnUtil {

    private static Gson gson = new Gson();

    /**
     * 处理省级信息
     */
    public static boolean getProvince(String response){
      if(!TextUtils.isEmpty(response)){
          List<Province> provinces = new ArrayList<Province>();
          try{
              provinces = gson.fromJson(response, new TypeToken<List<Province>>() {
              }.getType());
            /*
            保存进数据库
             */
              for (int i=0;i<provinces.size();i++){
                  provinces.get(i).save();
              }
              return true;
          }catch (Exception e){
              e.printStackTrace();
          }
      }
            return false;

    }

    /**
     * 处理市级信息
     */
    public static boolean getCity(String response,int provinceId){
        if(!TextUtils.isEmpty(response)) {
            List<City> citys = new ArrayList<City>();
            try {
                citys = gson.fromJson(response, new TypeToken<List<City>>() {
                }.getType());
            /*
            保存进数据库
             */
                for (int i = 0; i < citys.size(); i++) {
                    citys.get(i).setProvinceId(provinceId);
                    citys.get(i).save();
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
            return false;

    }

    /**
     * 处理县级信息
     */
    public static boolean getCounty(String response,int cityId){
        if(!TextUtils.isEmpty(response)) {
            List<County> countys = new ArrayList<County>();
            try {
                countys = gson.fromJson(response, new TypeToken<List<County>>() {
                }.getType());
            /*
            保存进数据库
             */
                for (int i = 0; i < countys.size(); i++) {
                    countys.get(i).setCityId(cityId);
                    countys.get(i).save();
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
         return false;
    }

}
