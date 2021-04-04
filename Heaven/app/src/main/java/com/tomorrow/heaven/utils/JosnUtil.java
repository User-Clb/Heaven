package com.tomorrow.heaven.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tomorrow.heaven.pojo.City;
import com.tomorrow.heaven.pojo.County;
import com.tomorrow.heaven.pojo.Province;
import com.tomorrow.heaven.pojo.gson.Weather;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JosnUtil {

    private static Gson gson = new Gson();

    /**
     * 处理省级信息
     */
    public static boolean getProvince(String response){
      if(!TextUtils.isEmpty(response)){
          try{
              JSONArray jsonArray = new JSONArray(response);
            /*
            保存进数据库
             */
              for (int i=0;i<jsonArray.length();i++){
                  JSONObject jsonObject = jsonArray.getJSONObject(i);
                  int id = jsonObject.getInt("id");
                  String name = jsonObject.getString("name");
                  Province province = new Province();
                  province.setProvinceCode(id);
                  province.setProvinceName(name);
                  province.save();
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
            try {
                JSONArray jsonArray = new JSONArray(response);
            /*
            保存进数据库
             */
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    String name = jsonObject.getString("name");
                    City city = new City();
                    city.setProvinceId(provinceId);
                    city.setCityCode(id);
                    city.setCityName(name);
                    city.save();
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
            try {
                JSONArray jsonArray = new JSONArray(response);
            /*
            保存进数据库
             */
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String id = jsonObject.getString("weather_id");
                    String name = jsonObject.getString("name");
                    County county = new County();
                    county.setCityId(cityId);
                    county.setCountyName(name);
                    county.setWeatherId(id);
                    county.save();
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
         return false;
    }

    /**
     * 处理天气信息
     */
    public static Weather getWeather(String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weather = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weather,Weather.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
