package com.tomorrow.heaven.pojo.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 天气
 */
public class Weather {
    /**
     * 状态
     */
    public String status;
    /**
     * 基本的城市信息
     */
    public Basic basic;
    /**
     * 实时数据
     */
    public Now now;
    /**
     * 空气质量指数
     */
    public AQI aqi;
    /**
     * 天气舒适度
     */
    public Suggestion suggestion;
    /**
     * 未来几天的天气情况
     */
    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;

    @Override
    public String toString() {
        return "Weather{" +
                "status='" + status + '\'' +
                ", basic=" + basic +
                ", now=" + now +
                ", aqi=" + aqi +
                ", suggerstion=" + suggestion +
                ", forecastList=" + forecastList +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Basic getBasic() {
        return basic;
    }

    public void setBasic(Basic basic) {
        this.basic = basic;
    }

    public Now getNow() {
        return now;
    }

    public void setNow(Now now) {
        this.now = now;
    }

    public AQI getAqi() {
        return aqi;
    }

    public void setAqi(AQI aqi) {
        this.aqi = aqi;
    }

    public Suggestion getSuggerstion() {
        return suggestion;
    }

    public void setSuggerstion(Suggestion suggerstion) {
        this.suggestion = suggerstion;
    }

    public List<Forecast> getForecastList() {
        return forecastList;
    }

    public void setForecastList(List<Forecast> forecastList) {
        this.forecastList = forecastList;
    }
}
