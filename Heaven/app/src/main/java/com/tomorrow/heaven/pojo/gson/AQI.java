package com.tomorrow.heaven.pojo.gson;

/**
 * 空气质量指数
 */
public class AQI {

    public AQICity city;

    public class AQICity{
        /**
         * 空气质量指数
         */
        public String aqi;
        /**
         * pm2.5
         */
        public String pm25;
        /**
         * 空气质量评级
         */
        public String qlty;

        public String getAqi() {
            return aqi;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public String getPm25() {
            return pm25;
        }

        public void setPm25(String pm25) {
            this.pm25 = pm25;
        }

        public String getQlty() {
            return qlty;
        }

        public void setQlty(String qlty) {
            this.qlty = qlty;
        }

        @Override
        public String toString() {
            return "AQICity{" +
                    "aqi='" + aqi + '\'' +
                    ", pm25='" + pm25 + '\'' +
                    ", qlty='" + qlty + '\'' +
                    '}';
        }
    }
}
