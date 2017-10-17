package com.cango.adpickcar;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.cango.adpickcar.base.BaseActivity;
import com.github.chrisbanes.photoview.PhotoView;

public class PreviewActivity extends BaseActivity {

    private PhotoView mPhotoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        mPhotoView = (PhotoView) findViewById(R.id.photo_view);
        String path = getIntent().getStringExtra("preview_path");
        Glide.with(this)
                .load(path)
                .placeholder(R.drawable.loadfailure)
                .error(R.drawable.loadfailure)
                .into(mPhotoView);
        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreviewActivity.this.finish();
            }
        });
    }
}
