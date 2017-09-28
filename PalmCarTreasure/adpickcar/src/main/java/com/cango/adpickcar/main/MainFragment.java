package com.cango.adpickcar.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cango.adpickcar.R;
import com.cango.adpickcar.base.BaseFragment;
import com.cango.adpickcar.baseAdapter.BaseHolder;
import com.cango.adpickcar.baseAdapter.OnBaseItemClickListener;
import com.cango.adpickcar.baseAdapter.OnLoadMoreListener;
import com.cango.adpickcar.detail.DetailActivity;
import com.cango.adpickcar.resetps.ResetPSActivity;
import com.cango.adpickcar.util.BarUtil;
import com.cango.adpickcar.util.CommUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import butterknife.BindView;
import butterknife.OnClick;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class MainFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, MainContract.View {
    public static final String CURRENT_TYPE = "current_type";

    public static MainFragment getInstance() {
        MainFragment mainFragment = new MainFragment();
        Bundle bundle = new Bundle();
        mainFragment.setArguments(bundle);
        return mainFragment;
    }

    @BindView(R.id.drawer_main)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.rl_drawer)
    RelativeLayout rlDrawer;
    @BindView(R.id.rl_main_head)
    RelativeLayout rlHead;
    @BindView(R.id.toolbar_main)
    Toolbar mToolbar;
    @BindView(R.id.ll_main_search)
    LinearLayout llMainSearch;
    @BindView(R.id.iv_main_popup_parent)
    ImageView ivPopupParent;
    @BindView(R.id.cardview_main)
    CardView mCardView;
    @BindView(R.id.tv_main_first_num)
    TextView tvFirstNum;
    @BindView(R.id.iv_main_first)
    ImageView ivFirst;
    @BindView(R.id.tv_main_first)
    TextView tvFirst;
    @BindView(R.id.tv_main_second_num)
    TextView tvSecondNum;
    @BindView(R.id.iv_main_second)
    ImageView ivSecond;
    @BindView(R.id.tv_main_second)
    TextView tvSecond;
    @BindView(R.id.tv_main_third_num)
    TextView tvThirdNum;
    @BindView(R.id.iv_main_third)
    ImageView ivThird;
    @BindView(R.id.tv_main_third)
    TextView tvThird;
    @BindView(R.id.tv_main_fourth_num)
    TextView tvFourthNum;
    @BindView(R.id.iv_main_fourth)
    ImageView ivFourth;
    @BindView(R.id.tv_main_fourth)
    TextView tvFourth;
    @BindView(R.id.tv_main_fifth_num)
    TextView tvFifthNum;
    @BindView(R.id.iv_main_fifth)
    ImageView ivFifth;
    @BindView(R.id.tv_main_fifth)
    TextView tvFifth;
    @BindView(R.id.srl_main)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.ll_sorry)
    LinearLayout llSorry;
    @BindView(R.id.recyclerview_main)
    RecyclerView mRecyclerView;
    @BindView(R.id.fl_shadow)
    FrameLayout flShadow;

    @OnClick({R.id.ll_modify_ps, R.id.ll_main_search, R.id.rl_main_first, R.id.rl_main_second, R.id.rl_main_third,
            R.id.rl_main_fourth, R.id.rl_main_fifth})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_modify_ps:
                startActivity(new Intent(mActivity, ResetPSActivity.class));
                break;
            case R.id.ll_main_search:
                showPopSearch();
                break;
            case R.id.rl_main_first:
                selectTitleStatus(0);
                break;
            case R.id.rl_main_second:
                selectTitleStatus(1);
                break;
            case R.id.rl_main_third:
                selectTitleStatus(2);
                break;
            case R.id.rl_main_fourth:
                selectTitleStatus(3);
                break;
            case R.id.rl_main_fifth:
                selectTitleStatus(4);
                break;
        }
    }

    private MainActivity mActivity;
    private MainContract.Presenter mPresenter;
    private MainAdapter mAdapter;
    private ArrayList<String> datas;
    private Badge firstQV, secondQV, thirdQV, fourthQV, fifthQV;
    private int selectColor, noSelectColor;
    private int mPageCount = 1, mTempPageCount = 2;
    static int PAGE_SIZE = 10;
    private boolean isLoadMore;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (!CommUtil.checkIsNull(mPresenter))
            mPresenter.onDetach();
    }

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_main;
    }

    /**
     *
     */
    @Override
    protected void initView() {
        int statusBarHeight = BarUtil.getStatusBarHeight(getActivity());
        int actionBarHeight = BarUtil.getActionBarHeight(getActivity());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight + actionBarHeight);
            mToolbar.setLayoutParams(layoutParams);
            mToolbar.setPadding(0, statusBarHeight, 0, 0);
        }
        mActivity.setSupportActionBar(mToolbar);
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mActivity.getSupportActionBar().setHomeButtonEnabled(true);
        mActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(mActivity, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mToggle.syncState();
        mToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.START);
            }
        });
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.START);
            }
        });

        mDrawerLayout.addDrawerListener(mToggle);

        initNum();
        selectTitleStatus(0);
        initRecyclerView();

//        String key = "123456";
//        byte[] data = key.getBytes();
//        byte[] keyHex = EncryptUtils.hexString2Bytes(key);
//        Logger.d(EncryptUtils.encryptAES2HexString(data,keyHex));

        try {
            String ok = encrypt("123456", "123456");
            Logger.d(ok);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final static String HEX = "0123456789ABCDEF";
    private static final int keyLenght = 32;
    private static final String defaultV = "0";

    /**
     * 加密
     *
     * @param key 密钥
     * @param src 加密文本
     * @return
     * @throws Exception
     */
    public static String encrypt(String key, String src) throws Exception {
        // /src = Base64.encodeToString(src.getBytes(), Base64.DEFAULT);
        byte[] rawKey = toMakekey(key, keyLenght, defaultV).getBytes();// key.getBytes();
        //补全data
        byte[] appendBytes = src.getBytes("utf-8");
        long length = (appendBytes.length / 16 + 1) * 16 - appendBytes.length;
        byte[] newBytes = getNewBytes(appendBytes, length);
        byte[] result = encrypt(rawKey, newBytes);
        return Base64.encodeToString(result, Base64.DEFAULT);
    }

    private static byte[] getNewBytes(byte[] appendBytes, long length) {
        if (appendBytes.length % 16 != 0) {
            ArrayList<Byte> arrayList = new ArrayList<>();
            for (byte bean : appendBytes) {
                arrayList.add(bean);
            }
            byte[] lengthBytes = (length + "").getBytes();
            for (byte bean : lengthBytes) {
                arrayList.add(bean);
            }
            appendBytes = new byte[arrayList.size()];
            for (int i = 0; i < arrayList.size(); i++) {
                appendBytes[i] = arrayList.get(i);
            }
            return getNewBytes(appendBytes, length);
        } else {
            return appendBytes;
        }
    }

    /**
     * 真正的加密过程
     * 1.通过密钥得到一个密钥专用的对象SecretKeySpec
     * 2.Cipher 加密算法，加密模式和填充方式三部分或指定加密算 (可以只用写算法然后用默认的其他方式)Cipher.getInstance("AES");
     *
     * @param key
     * @param src
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] key, byte[] src) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        //CBC模式需要使用IV
//        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(src);
        return encrypted;
    }

    /**
     * 密钥key ,默认补的数字，补全16位数，以保证安全补全至少16位长度,android和ios对接通过
     *
     * @param str
     * @param strLength
     * @param val
     * @return
     */
    private static String toMakekey(String str, int strLength, String val) {
        int strLen = str.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                StringBuffer buffer = new StringBuffer();
                buffer.append(str).append(val);
                str = buffer.toString();
                strLen = str.length();
            }
        }
        return str;
    }

    /**
     * 二进制转字符,转成了16进制
     * 0123456789abcdefg
     *
     * @param buf
     * @return
     */
    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }


    private void initNum() {
        firstQV = new QBadgeView(mActivity).bindTarget(tvFirstNum).setBadgeNumber(1).setShowShadow(false).setBadgeBackgroundColor(Color.TRANSPARENT);
        secondQV = new QBadgeView(mActivity).bindTarget(tvSecondNum).setBadgeNumber(2).setShowShadow(false).setBadgeBackgroundColor(Color.TRANSPARENT);
        thirdQV = new QBadgeView(mActivity).bindTarget(tvThirdNum).setBadgeNumber(3).setShowShadow(false).setBadgeBackgroundColor(Color.TRANSPARENT);
        fourthQV = new QBadgeView(mActivity).bindTarget(tvFourthNum).setBadgeNumber(4).setBadgeTextColor(Color.WHITE).setShowShadow(false);
        fifthQV = new QBadgeView(mActivity).bindTarget(tvFifthNum).setBadgeNumber(5).setShowShadow(false).setBadgeBackgroundColor(Color.TRANSPARENT);
    }

    private void selectTitleStatus(int position) {
        switch (position) {
            case 0:
                firstQV.setBadgeTextColor(selectColor);
                secondQV.setBadgeTextColor(noSelectColor);
                thirdQV.setBadgeTextColor(noSelectColor);
                fourthQV.setBadgeBackgroundColor(noSelectColor);
                fifthQV.setBadgeTextColor(noSelectColor);
                ivFirst.setImageResource(R.drawable.weijieche_on);
                tvFirst.setTextColor(selectColor);
                ivSecond.setImageResource(R.drawable.weitijiao_off);
                tvSecond.setTextColor(noSelectColor);
                ivThird.setImageResource(R.drawable.shenhezhong_off);
                tvThird.setTextColor(noSelectColor);
                ivFourth.setImageResource(R.drawable.shenhetuihui_off);
                tvFourth.setTextColor(noSelectColor);
                ivFifth.setImageResource(R.drawable.shenhetongguo_off);
                tvFifth.setTextColor(noSelectColor);
                break;
            case 1:
                firstQV.setBadgeTextColor(noSelectColor);
                secondQV.setBadgeTextColor(selectColor);
                thirdQV.setBadgeTextColor(noSelectColor);
                fourthQV.setBadgeBackgroundColor(noSelectColor);
                fifthQV.setBadgeTextColor(noSelectColor);
                ivFirst.setImageResource(R.drawable.weijieche_off);
                tvFirst.setTextColor(noSelectColor);
                ivSecond.setImageResource(R.drawable.weitijiao_on);
                tvSecond.setTextColor(selectColor);
                ivThird.setImageResource(R.drawable.shenhezhong_off);
                tvThird.setTextColor(noSelectColor);
                ivFourth.setImageResource(R.drawable.shenhetuihui_off);
                tvFourth.setTextColor(noSelectColor);
                ivFifth.setImageResource(R.drawable.shenhetongguo_off);
                tvFifth.setTextColor(noSelectColor);
                break;
            case 2:
                firstQV.setBadgeTextColor(noSelectColor);
                secondQV.setBadgeTextColor(noSelectColor);
                thirdQV.setBadgeTextColor(selectColor);
                fourthQV.setBadgeBackgroundColor(noSelectColor);
                fifthQV.setBadgeTextColor(noSelectColor);
                ivFirst.setImageResource(R.drawable.weijieche_off);
                tvFirst.setTextColor(noSelectColor);
                ivSecond.setImageResource(R.drawable.weitijiao_off);
                tvSecond.setTextColor(noSelectColor);
                ivThird.setImageResource(R.drawable.shenhezhong_on);
                tvThird.setTextColor(selectColor);
                ivFourth.setImageResource(R.drawable.shenhetuihui_off);
                tvFourth.setTextColor(noSelectColor);
                ivFifth.setImageResource(R.drawable.shenhetongguo_off);
                tvFifth.setTextColor(noSelectColor);
                break;
            case 3:
                firstQV.setBadgeTextColor(noSelectColor);
                secondQV.setBadgeTextColor(noSelectColor);
                thirdQV.setBadgeTextColor(noSelectColor);
                fourthQV.setBadgeBackgroundColor(selectColor);
                fifthQV.setBadgeTextColor(noSelectColor);
                ivFirst.setImageResource(R.drawable.weijieche_off);
                tvFirst.setTextColor(noSelectColor);
                ivSecond.setImageResource(R.drawable.weitijiao_off);
                tvSecond.setTextColor(noSelectColor);
                ivThird.setImageResource(R.drawable.shenhezhong_off);
                tvThird.setTextColor(noSelectColor);
                ivFourth.setImageResource(R.drawable.shenhetuihui_on);
                tvFourth.setTextColor(selectColor);
                ivFifth.setImageResource(R.drawable.shenhetongguo_off);
                tvFifth.setTextColor(noSelectColor);
                break;
            case 4:
                firstQV.setBadgeTextColor(noSelectColor);
                secondQV.setBadgeTextColor(noSelectColor);
                thirdQV.setBadgeTextColor(noSelectColor);
                fourthQV.setBadgeBackgroundColor(noSelectColor);
                fifthQV.setBadgeTextColor(selectColor);
                ivFirst.setImageResource(R.drawable.weijieche_off);
                tvFirst.setTextColor(noSelectColor);
                ivSecond.setImageResource(R.drawable.weitijiao_off);
                tvSecond.setTextColor(noSelectColor);
                ivThird.setImageResource(R.drawable.shenhezhong_off);
                tvThird.setTextColor(noSelectColor);
                ivFourth.setImageResource(R.drawable.shenhetuihui_off);
                tvFourth.setTextColor(noSelectColor);
                ivFifth.setImageResource(R.drawable.shenhetongguo_on);
                tvFifth.setTextColor(selectColor);
                break;
        }
    }

    private void initRecyclerView() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.green, R.color.blue);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add(i + "");
        }
        mAdapter = new MainAdapter(mActivity, datas, true);
        mAdapter.setLoadingView(R.layout.load_loading_layout);
        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                if (mPageCount == mTempPageCount && !isReload) {
                    return;
                }
                isLoadMore = true;
                mPageCount = mTempPageCount;
                mPresenter.loadListByStatus(mPageCount, PAGE_SIZE);
            }
        });
        mAdapter.setOnItemClickListener(new OnBaseItemClickListener<String>() {
            @Override
            public void onItemClick(BaseHolder viewHolder, String data, int position) {
                new AlertDialog.Builder(mActivity)
                        .setTitle("确认接车")
                        .setMessage("申请编号\r\n" + "客户姓名\r\n" + "车牌号码\r\n" + "颜色\r\n")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(mActivity, DetailActivity.class));
                            }
                        })
                        .create().show();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        mActivity = (MainActivity) getActivity();
        selectColor = getResources().getColor(R.color.colorPrimary);
        noSelectColor = getResources().getColor(R.color.ad888888);
    }

    @Override
    public void onRefresh() {
        isLoadMore = false;
        mPageCount = 1;
        mTempPageCount = 2;
        mAdapter.notifyDataSetChanged();
        mAdapter.setLoadingView(R.layout.load_loading_layout);
        mPresenter.loadListByStatus(mPageCount, PAGE_SIZE);
    }

    private void showPopSearch() {
        if (mCardView.getWidth() > 0) {
            PopupWindow mSearchPopup = getPopupWindow(mActivity, R.layout.main_search_pop);
            rlHead.setBackgroundColor(Color.parseColor("#36000000"));
            flShadow.setVisibility(View.VISIBLE);
            mSearchPopup.showAsDropDown(ivPopupParent);
        }
    }

    private void closePopSearch() {
        rlHead.setBackgroundColor(Color.WHITE);
        flShadow.setVisibility(View.GONE);
    }

    public PopupWindow getPopupWindow(Context context, final int layoutId) {
        final PopupWindow popupWindow = new PopupWindow(mCardView.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
        View popupView = LayoutInflater.from(context).inflate(layoutId, null);
        popupWindow.setContentView(popupView);
//        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                closePopSearch();
            }
        });
        popupWindow.update();
        return popupWindow;
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showMainIndicator(final boolean active) {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(active);
            }
        });
    }

    @Override
    public void showMainError() {
        if (isLoadMore) {
            mAdapter.setLoadFailedView(R.layout.load_failed_layout);
        } else {
            llSorry.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showNoData() {
        if (isLoadMore) {
            mAdapter.setLoadEndView(R.layout.load_end_layout);
        } else {
            llSorry.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showMainSuccess(boolean isSuccess, String message) {
        llSorry.setVisibility(View.GONE);
        ArrayList<String> newArrays = new ArrayList<>();
        newArrays.add(message);
        if (isLoadMore) {
            mTempPageCount++;
            mAdapter.setLoadMoreData(newArrays);
        } else {
//            mAdapter.setNewData(tasks);
            mAdapter.setNewDataNoError(newArrays);
        }
        if (newArrays.size() < PAGE_SIZE) {
            mAdapter.setLoadEndView(R.layout.load_end_layout);
        }
    }

    @Override
    public void openOtherUi() {

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
