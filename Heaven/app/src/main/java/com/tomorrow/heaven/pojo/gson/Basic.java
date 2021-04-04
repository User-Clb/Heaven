package com.tomorrow.heaven.pojo.gson;

import com.google.gson.annotations.SerializedName;

/**
 * 基本的城市信息
 */
public class Basic {

    /**
     * 城市名称
     */
    @SerializedName("city")
    public String  cityName;

    /**
     * 城市天气ID
     */
    @SerializedName("id")
    public String  weatherId;

    /**
     * 更新
     */
    public Update  update;


    public class Update{

        /**
         * 获取时间
         */
        @SerializedName("loc")
        public String updateTime;
    }

    @Override
    public String toString() {
        return "Basic{" +
                "cityName='" + cityName + '\'' +
                ", weatherId='" + weatherId + '\'' +
                ", update=" + update +
                '}';
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public Update getUpdate() {
        return update;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }
}
