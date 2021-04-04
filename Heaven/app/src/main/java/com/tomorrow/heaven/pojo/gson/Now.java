package com.tomorrow.heaven.pojo.gson;

import com.google.gson.annotations.SerializedName;

/**
 * 实时数据
 */
public class Now {
    /**
     * 温度
     */
    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond")
    public More more;

    public  class More{
        /**
         * 天气情况
         */
        @SerializedName("txt")
        public String info;
    }

    @Override
    public String toString() {
        return "Now{" +
                "temperature='" + temperature + '\'' +
                ", more=" + more +
                '}';
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public More getMore() {
        return more;
    }

    public void setMore(More more) {
        this.more = more;
    }
}
