package com.tomorrow.heaven.pojo.gson;

import com.google.gson.annotations.SerializedName;

/**
 * 天气情况的一些温馨提示
 */
public class Suggestion {

    /**
     * 舒适度
     */
    @SerializedName("comf")
    public Comfort comfort;

    /**
     * 是否适合洗车
     */
    @SerializedName("cw")
    public CarWash carWash;

    /**
     * 是否适合运动
     */
    public Sport sport;

    public class Comfort{
        @SerializedName("txt")
        public String info;
    }
    public class CarWash{
        @SerializedName("txt")
        public String info;
    }
    public class  Sport{
        @SerializedName("txt")
        public String info;
    }

    @Override
    public String toString() {
        return "Suggerstion{" +
                "comfort=" + comfort +
                ", carWash=" + carWash +
                ", sport=" + sport +
                '}';
    }

    public Comfort getComfort() {
        return comfort;
    }

    public void setComfort(Comfort comfort) {
        this.comfort = comfort;
    }

    public CarWash getCarWash() {
        return carWash;
    }

    public void setCarWash(CarWash carWash) {
        this.carWash = carWash;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }
}
