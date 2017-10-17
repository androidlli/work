package com.cango.adpickcar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.cango.adpickcar.api.Api;
import com.cango.adpickcar.login.LoginActivity;
import com.cango.adpickcar.main.MainActivity;
import com.cango.adpickcar.util.AppUtils;

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
}
