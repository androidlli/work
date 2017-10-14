package com.cango.adpickcar.detail.imageinfo;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.cango.adpickcar.R;
import com.cango.adpickcar.base.BaseFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class ImageInfoFragment extends BaseFragment {
    public static ImageInfoFragment getInstance() {
        ImageInfoFragment imageInfoFragment = new ImageInfoFragment();
        Bundle bundle = new Bundle();
        imageInfoFragment.setArguments(bundle);
        return imageInfoFragment;
    }

    @BindView(R.id.tl_image)
    TabLayout mTabLayout;
    @BindView(R.id.vp_image)
    ViewPager mViewPager;

    private ArrayList<String> tabList = new ArrayList<>();
    private ArrayList<Fragment> fgList = new ArrayList<>();
    private MyAdapter mAdapter;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_image_info_other;
    }

    @Override
    protected void initView() {
//        Map<String, Object> map = new HashMap<>();
//        map.put("ok", "ok");
//        Gson gson2 = new GsonBuilder().enableComplexMapKeySerialization().create();
//        Logger.d(gson2.toJson(map));
        initTabLayout();
    }

    private void initTabLayout() {
        tabList.add("外观照");
        tabList.add("详细照");
        tabList.add("补充照");
        fgList.add(new FacadeFragment());
        fgList.add(new ParticularFragment());
        fgList.add(new SupplementFragment());
        mAdapter = new MyAdapter(getChildFragmentManager(), tabList, fgList);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager, true);
    }

    @Override
    protected void initData() {

    }

    public class MyAdapter extends FragmentPagerAdapter {

        private ArrayList<String> titleList;
        private ArrayList<Fragment> fragmentList;

        public MyAdapter(FragmentManager fm, ArrayList<String> titleList, ArrayList<Fragment> fragmentList) {
            super(fm);
            this.titleList = titleList;
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }

}
