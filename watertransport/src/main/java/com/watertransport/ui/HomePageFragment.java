package com.watertransport.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.watertransport.R;
import com.watertransport.support.FastData;
import com.watertransport.support.WtConstant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.timeface.timekit.fragment.TfBaseFragment;

/**
 * Created by zhangsheng on 2017/8/5.
 */

public class HomePageFragment extends TfBaseFragment {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    Unbinder unbinder;
    ArrayList<String> tabTitle = new ArrayList<>();
    @BindView(R.id.tv_menu)
    TextView tvMenu;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;

    public static HomePageFragment newInstance() {

        Bundle args = new Bundle();

        HomePageFragment fragment = new HomePageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View homepageView = inflater.inflate(R.layout.fragment_home_page, container, false);
        unbinder = ButterKnife.bind(this, homepageView);
        return homepageView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PagerAdapter adapter = new PagerAdapter(getChildFragmentManager());
        int userRole = FastData.getUserRole();
        if (userRole == WtConstant.USER_ROLE_BOAT) {
            tabTitle.add("最新发布运单");
            tabTitle.add("我的运单");
            tvMenu.setText("+ 记录运单");
            adapter.addFragment(ListContentFragment.newInstance(WtConstant.BOAT_PAGE_1));
            adapter.addFragment(ListContentFragment.newInstance(WtConstant.BOAT_PAGE_2));
        } else {
            tabTitle.add("发布中");
            tabTitle.add("未发布");
            tabTitle.add("已关闭");
            tvMenu.setText("+ 新增");
            adapter.addFragment(ListContentFragment.newInstance(WtConstant.CARGO_PAGE_1));
            adapter.addFragment(ListContentFragment.newInstance(WtConstant.CARGO_PAGE_2));
            adapter.addFragment(ListContentFragment.newInstance(WtConstant.CARGO_PAGE_3));
        }
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
        tvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userRole == WtConstant.USER_ROLE_BOAT) {
                    //记录运单
                    //新增运单
                    AddNewOrderActivity.start(getActivity());
                }

            }
        });

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

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (this.getView() != null) {
            this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
