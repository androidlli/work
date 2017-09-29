package com.cango.adpickcar;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        final TextInputLayout mYourMatchcodeHolder = (TextInputLayout) findViewById(R.id.your_matchcode_holder);
        EditText mYourMatchcode = (EditText) findViewById(R.id.your_matchcode);
        mYourMatchcode.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            //检测错误输入，当输入错误时，hint会变成红色并提醒
            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //检查实际是否匹配，由自己实现
                if (!charSequence.toString().equals("1")) {
                    mYourMatchcodeHolder.setErrorEnabled(true);
                    mYourMatchcodeHolder.setError("请检查格式");
                    return;
                } else {
                    mYourMatchcodeHolder.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

    }
}
