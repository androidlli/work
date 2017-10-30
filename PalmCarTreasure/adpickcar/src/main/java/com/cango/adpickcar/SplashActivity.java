package com.cango.adpickcar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.cango.adpickcar.api.Api;
import com.cango.adpickcar.login.LoginActivity;
import com.cango.adpickcar.main.MainActivity;
import com.cango.adpickcar.util.AppUtils;
import com.cango.adpickcar.util.DatData;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TextView tvVersion = (TextView) findViewById(R.id.tv_version);
        tvVersion.setText("v" + AppUtils.getVersionName(this));

        if (ADApplication.mSPUtils.getString(Api.USERID) != null) {
            startDelay(MainActivity.class);
        } else {
            startDelay(LoginActivity.class);
        }
//        testJson();
    }

    private void startDelay(final Class cls) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, cls);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

    private void testJson(){
        String jsonArray = "[{\"IMEI\":\"ae4866ce-d9b5-3130-8e38-daa3e0b0179e\",\"address\":\"杨高南路428号由由世纪广场1号楼25楼\",\"city\":\"上海市\",\"companyName\":\"微艾科技有限公司\",\"hasImg\":\"0\",\"Lat\":\"31.217514\",\"Lon\":\"121.534683\",\"province\":\"上海市\"}]";
//        List<Student> datDataLists = JSON.parseArray(jsonArray, Student.class);
//        Logger.d(datDataLists.size());
        List<DatData> datDatas = JSON.parseArray(jsonArray,DatData.class);
        Logger.d(datDatas.size());
    }

    public static class Student{
        private int id;
        private String name;
        private int age;

        /**
         * 默认的构造方法必须不能省，不然不能解析
         */

        public Student(){

        }
        public Student(int id,String name,int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Student [id=" + id + ", name=" + name + ", age=" + age + "]";
        }
    }

    public class DatDataLists{
        public DatDataLists(){

        }
        public List<DatData> getDatDataList() {
            return datDataList;
        }

        public void setDatDataList(List<DatData> datDataList) {
            this.datDataList = datDataList;
        }

        private List<DatData> datDataList;
    }
//    public static class DatData {
//        public DatData(){
//
//        }
//        public String getIMEI() {
//            return IMEI;
//        }
//
//        public void setIMEI(String IMEI) {
//            this.IMEI = IMEI;
//        }
//
//        public String getAddress() {
//            return address;
//        }
//
//        public void setAddress(String address) {
//            this.address = address;
//        }
//
//        public String getCity() {
//            return city;
//        }
//
//        public void setCity(String city) {
//            this.city = city;
//        }
//
//        public String getCompanyName() {
//            return companyName;
//        }
//
//        public void setCompanyName(String companyName) {
//            this.companyName = companyName;
//        }
//
//        public String getHasImg() {
//            return hasImg;
//        }
//
//        public void setHasImg(String hasImg) {
//            this.hasImg = hasImg;
//        }
//
//        public String getLat() {
//            return Lat;
//        }
//
//        public void setLat(String lat) {
//            Lat = lat;
//        }
//
//        public String getLon() {
//            return Lon;
//        }
//
//        public void setLon(String lon) {
//            Lon = lon;
//        }
//
//        public String getProvince() {
//            return province;
//        }
//
//        public void setProvince(String province) {
//            this.province = province;
//        }
//
//        private String IMEI;
//        private String address;
//        private String city;
//        private String companyName;
//        private String hasImg;
//        private String Lat;
//        private String Lon;
//        private String province;
//    }
}
