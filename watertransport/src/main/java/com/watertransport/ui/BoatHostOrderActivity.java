package com.watertransport.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.watertransport.R;
import com.watertransport.support.WtConstant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.timeface.timekit.activity.TfBaseActivity;

public class BoatHostOrderActivity extends TfBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    ArrayList<String> tabTitle = new ArrayList<>();

    public static void start(Context context) {
        Intent starter = new Intent(context, BoatHostOrderActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boat_hoster_order);
        ButterKnife.bind(this);
        setTitle("我的订单");
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        tabTitle.add(WtConstant.BOAT_ORDER_ALL);
        tabTitle.add(WtConstant.BOAT_ORDER_HAS);
        tabTitle.add(WtConstant.BOAT_ORDER_NO);
        adapter.addFragment(BoatHostOrderListFragment.newInstance(WtConstant.BOAT_ORDER_ALL));
        adapter.addFragment(BoatHostOrderListFragment.newInstance(WtConstant.BOAT_ORDER_HAS));
        adapter.addFragment(BoatHostOrderListFragment.newInstance(WtConstant.BOAT_ORDER_NO));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager, true);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(tabTitle.size());
    }


    private class PagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();

        PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        void addFragment(Fragment fragment) {
            mFragments.add(fragment);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitle.get(position);
        }

        @Override
        public int getCount() {
            return tabTitle.size();
        }
    }

}
