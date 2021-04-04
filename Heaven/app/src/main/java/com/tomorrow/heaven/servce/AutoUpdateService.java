package com.tomorrow.heaven.servce;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;

import com.tomorrow.heaven.activity.WeatherActivity;
import com.tomorrow.heaven.pojo.gson.Weather;
import com.tomorrow.heaven.utils.HttpUtil;
import com.tomorrow.heaven.utils.JosnUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AutoUpdateService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        updateWeather();
        updateBingPic();
        AlarmManager systemService = (AlarmManager)getSystemService(ALARM_SERVICE);
        int anHour = 8 * 60 * 60 * 1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this,AutoUpdateService.class);
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        systemService.cancel(pi);
        systemService.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 更新天气信息
     */
    private void updateWeather(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = sharedPreferences.getString("weather", null);
        if(weatherString != null){
            //有缓存市直接解析天气数据
            Weather weather = JosnUtil.getWeather(weatherString);
            String weatherId = weather.basic.weatherId;
            System.out.println("天气城市id:"+weatherId);
            String weatherUrl = "http://guolin.tech/api/weather?cityid=CN101210303"+weatherId+"&key=b997b3672ead41a3bfe83ae98d213cfb";
            HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
                @Override
                public void onResponse( Call call,  Response response) throws IOException {

                    String responseText = response.body().string();
                    Weather weather1 = JosnUtil.getWeather(responseText);
                    if(weather1 != null && "ok".equals(weather1.status)){
                        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this).edit();
                        edit.putString("weather",responseText);
                        edit.apply();
                    }
                }
                @Override
                public void onFailure( Call call,  IOException e) {
                           e.printStackTrace();
                }

            });
        }
    }

    /**
     * 更新必应每日一图
     */
    private void updateBingPic(){
            String requestBingPic="http://guolin.tech/api/bing_pic";
            HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    final String bingPic = response.body().string();
                    SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this).edit();
                    edit.putString("bing_pic", bingPic);
                    edit.apply();
                }

                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                      e.printStackTrace();
                }
            });
    }


}
