package com.watertransport.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    String[] tabTitle = new String[3];

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
            tabTitle[0] = "货运公司发布的运单";
            tabTitle[1] = "个人装载运单";
            adapter.addFragment(ListContentFragment.newInstance(WtConstant.BOAT_PAGE_1));
            adapter.addFragment(ListContentFragment.newInstance(WtConstant.BOAT_PAGE_2));
        } else {
            tabTitle[0] = "发布中";
            tabTitle[1] = "未发布";
            tabTitle[2] = "已关闭";
            adapter.addFragment(ListContentFragment.newInstance(WtConstant.CARGO_PAGE_1));
            adapter.addFragment(ListContentFragment.newInstance(WtConstant.CARGO_PAGE_2));
            adapter.addFragment(ListContentFragment.newInstance(WtConstant.CARGO_PAGE_3));
        }
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager, true);
        viewPager.setCurrentItem(0);
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
            return tabTitle[position];
        }

        @Override
        public int getCount() {
            return tabTitle.length;
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
