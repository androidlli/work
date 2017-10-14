package com.cango.adpickcar.camera;

import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cango.adpickcar.R;
import com.cango.adpickcar.detail.DetailActivity;
import com.cango.adpickcar.util.ToastUtils;
import com.google.android.cameraview.CameraView;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

// AspectRatioFragment.Listener
public class CameraActivity extends AppCompatActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback {

    private static final String TYPE = "type";
    /**
     * 0 : from itemInfoFG -1: 通用的
     */
    private int currentType = -1;
    private static final String TAG = "MainActivity";

    private static final int REQUEST_CAMERA_PERMISSION = 1;

    private static final String FRAGMENT_DIALOG = "dialog";

    private static final int[] FLASH_OPTIONS = {
            CameraView.FLASH_AUTO,
            CameraView.FLASH_OFF,
            CameraView.FLASH_ON,
    };

    private static final int[] FLASH_ICONS = {
            R.drawable.ic_flash_auto,
            R.drawable.ic_flash_off,
            R.drawable.ic_flash_on,
    };

    private static final int[] FLASH_TITLES = {
            R.string.flash_auto,
            R.string.flash_off,
            R.string.flash_on,
    };

    private int mCurrentFlash;

    private CameraView mCameraView;
    private ImageView ivCancal, ivFlash, ivNo, ivOk, ivResult;
    private ImageView fab;
    private RelativeLayout rlLeft, rlCenter, rlRight, rlPrompt;
    private ImageView ivPrompt;
    private boolean isFlash, isAnimOpen;

    private String mPath;
    private Handler mBackgroundHandler;

    //UI handler
    private Handler mHandler = new Handler();
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.take_picture:
                    if (mCameraView != null) {
                        if (mCameraView.isCameraOpened()) {
                            mCameraView.takePicture();
                        } else {
                            ToastUtils.showShort(R.string.camera_is_opening);
                        }
                    }
                    break;
                case R.id.iv_camera_prompt:
                    if (isAnimOpen) {
                        stopPrompt();
                    } else {
                        startPromptAnim();
                    }
                    break;
            }
        }
    };

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        if (getIntent()!=null){
            currentType = getIntent().getIntExtra(TYPE,-1);
        }
        ivCancal = (ImageView) findViewById(R.id.iv_camera_cancal);
        ivFlash = (ImageView) findViewById(R.id.iv_camera_flash);
        ivResult = (ImageView) findViewById(R.id.iv_result);
        ivNo = (ImageView) findViewById(R.id.iv_camera_no);
        ivOk = (ImageView) findViewById(R.id.iv_camera_ok);
        rlLeft = (RelativeLayout) findViewById(R.id.rl_camera_left);
        rlCenter = (RelativeLayout) findViewById(R.id.rl_camera_center);
        rlRight = (RelativeLayout) findViewById(R.id.rl_camera_right);
        rlPrompt = (RelativeLayout) findViewById(R.id.rl_camera_prompt);
        ivPrompt = (ImageView) findViewById(R.id.iv_camera_prompt);
        ivResult.setVisibility(View.GONE);
        ivNo.setVisibility(View.GONE);
        ivOk.setVisibility(View.GONE);
        mCameraView = (CameraView) findViewById(R.id.camera);
        if (mCameraView != null) {
            mCameraView.addCallback(mCallback);
        }
        fab = (ImageView) findViewById(R.id.take_picture);
        if (fab != null) {
            fab.setOnClickListener(mOnClickListener);
        }
        ivPrompt.setOnClickListener(mOnClickListener);

        if (currentType == 0){
            isAnimOpen = false;
            rlCenter.setVisibility(View.GONE);
        }else {
            isAnimOpen = true;
        }

        RelativeLayout rlRoot = (RelativeLayout) findViewById(R.id.root);
        rlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAnimOpen) {
                    stopPrompt();
                }
            }
        });
    }

    private void stopPrompt() {
        rlPrompt.setVisibility(View.GONE);
        ObjectAnimator anim = ObjectAnimator//
                .ofFloat(rlPrompt, "scaleX", 1.0F, 0.0F)
                .setDuration(100);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                rlPrompt.setAlpha(cVal);
                rlPrompt.setScaleX(cVal);
                rlPrompt.setScaleY(cVal);
            }
        });
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimOpen = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        anim.start();
    }

    private void startPromptAnim() {
        rlPrompt.setVisibility(View.VISIBLE);
        rlPrompt.setPivotX(rlPrompt.getWidth());
        rlPrompt.setPivotY(rlPrompt.getHeight());
        ObjectAnimator anim = ObjectAnimator//
                .ofFloat(rlPrompt, "scaleX", 0.0F, 1.0F)
                .setDuration(1000);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                rlPrompt.setAlpha(cVal);
                rlPrompt.setScaleX(cVal);
                rlPrompt.setScaleY(cVal);
            }
        });
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimOpen = true;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        anim.start();
    }

    public void cancal(View view) {
        finish();
    }

    public void flash(View view) {
        if (mCameraView != null) {
            if (isFlash) {
                mCameraView.setFlash(CameraView.FLASH_OFF);
                isFlash = false;
                ivFlash.setImageResource(R.drawable.flashoff);
            } else {
                mCameraView.setFlash(CameraView.FLASH_ON);
                isFlash = true;
                ivFlash.setImageResource(R.drawable.flashon);
            }
        }
    }

    public void cameraNo(View view) {
        deleteImageFile();
        rlLeft.setVisibility(View.VISIBLE);
        rlCenter.setVisibility(View.VISIBLE);
        rlRight.setVisibility(View.VISIBLE);
        fab.setVisibility(View.VISIBLE);
        ivResult.setVisibility(View.GONE);
        ivNo.setVisibility(View.GONE);
        ivOk.setVisibility(View.GONE);
    }

    public void cameraOk(View view) {
        Intent intent = new Intent(CameraActivity.this, DetailActivity.class);
        intent.putExtra("path", mPath);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            mCameraView.start();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            ConfirmationDialogFragment
                    .newInstance(R.string.camera_permission_confirmation,
                            new String[]{Manifest.permission.CAMERA},
                            REQUEST_CAMERA_PERMISSION,
                            R.string.camera_permission_not_granted)
                    .show(getSupportFragmentManager(), FRAGMENT_DIALOG);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCameraView.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBackgroundHandler != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                mBackgroundHandler.getLooper().quitSafely();
            } else {
                mBackgroundHandler.getLooper().quit();
            }
            mBackgroundHandler = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION:
                if (permissions.length != 1 || grantResults.length != 1) {
                    throw new RuntimeException("Error on requesting camera permission.");
                }
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, R.string.camera_permission_not_granted,
                            Toast.LENGTH_SHORT).show();
                }
                // No need to start camera here; it is handled by onResume
                break;
        }
    }

    private Handler getBackgroundHandler() {
        if (mBackgroundHandler == null) {
            HandlerThread thread = new HandlerThread("background");
            thread.start();
            mBackgroundHandler = new Handler(thread.getLooper());
        }
        return mBackgroundHandler;
    }

    private CameraView.Callback mCallback
            = new CameraView.Callback() {

        @Override
        public void onCameraOpened(CameraView cameraView) {
            Log.d(TAG, "onCameraOpened");
        }

        @Override
        public void onCameraClosed(CameraView cameraView) {
            Log.d(TAG, "onCameraClosed");
        }

        @Override
        public void onPictureTaken(CameraView cameraView, final byte[] data) {
            Logger.d(TAG, "onPictureTaken " + data.length);
            getBackgroundHandler().post(new Runnable() {
                @Override
                public void run() {
                    boolean isOk = false;
//                    File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),
//                            "picture.jpg");
                    File file = null;
                    try {
                        file = createImageFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    OutputStream os = null;
                    try {
                        isOk = true;
                        os = new FileOutputStream(file);
                        os.write(data);
                        os.close();
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                updateCameraDo();
                            }
                        });
                    } catch (IOException e) {
                        Log.w(TAG, "Cannot write to " + file, e);
                    } finally {
                        if (os != null) {
                            try {
                                os.close();
                            } catch (IOException e) {
                                // Ignore
                            }
                        }
                        if (isOk) {

                        } else {
//                            Intent intent = new Intent(CameraActivity.this, TrailerActivity.class);
//                            setResult(Activity.RESULT_CANCELED, intent);
//                            finish();
                        }
                    }
                }
            });
        }

    };

    private void updateCameraDo() {
        Glide.with(this).load(mPath).into(ivResult);
        fab.setVisibility(View.GONE);
        rlLeft.setVisibility(View.GONE);
        rlCenter.setVisibility(View.GONE);
        rlRight.setVisibility(View.GONE);
        ivResult.setVisibility(View.VISIBLE);
        ivNo.setVisibility(View.VISIBLE);
        ivOk.setVisibility(View.VISIBLE);
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        mPath = image.getAbsolutePath();
        return image;
    }

    private boolean deleteImageFile() {
        if (mPath != null) {
            File emptyFile = new File(mPath);
            if (emptyFile.exists())
                return emptyFile.delete();
        }
        return false;
    }

    public static class ConfirmationDialogFragment extends DialogFragment {

        private static final String ARG_MESSAGE = "message";
        private static final String ARG_PERMISSIONS = "permissions";
        private static final String ARG_REQUEST_CODE = "request_code";
        private static final String ARG_NOT_GRANTED_MESSAGE = "not_granted_message";

        public static ConfirmationDialogFragment newInstance(@StringRes int message,
                                                             String[] permissions, int requestCode, @StringRes int notGrantedMessage) {
            ConfirmationDialogFragment fragment = new ConfirmationDialogFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_MESSAGE, message);
            args.putStringArray(ARG_PERMISSIONS, permissions);
            args.putInt(ARG_REQUEST_CODE, requestCode);
            args.putInt(ARG_NOT_GRANTED_MESSAGE, notGrantedMessage);
            fragment.setArguments(args);
            return fragment;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Bundle args = getArguments();
            return new AlertDialog.Builder(getActivity())
                    .setMessage(args.getInt(ARG_MESSAGE))
                    .setPositiveButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String[] permissions = args.getStringArray(ARG_PERMISSIONS);
                                    if (permissions == null) {
                                        throw new IllegalArgumentException();
                                    }
                                    ActivityCompat.requestPermissions(getActivity(),
                                            permissions, args.getInt(ARG_REQUEST_CODE));
                                }
                            })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(getActivity(),
                                            args.getInt(ARG_NOT_GRANTED_MESSAGE),
                                            Toast.LENGTH_SHORT).show();
                                }
                            })
                    .create();
        }

    }

}
