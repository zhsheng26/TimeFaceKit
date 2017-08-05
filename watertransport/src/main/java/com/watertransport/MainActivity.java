package com.watertransport;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.watertransport.ui.HomePageFragment;
import com.watertransport.ui.MineFragment;
import com.watertransport.ui.MsgFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.timeface.timekit.activity.TfBaseActivity;

public class MainActivity extends TfBaseActivity {
    @BindView(R.id.tv_menu)
    TextView tvMenu;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.rb_homepage)
    TextView rbHomepage;
    @BindView(R.id.rb_msg)
    TextView rbMsg;
    @BindView(R.id.rb_mine)
    TextView rbMine;
    private int menuTag = 0;

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        rbHomepage.performClick();
    }

    public void clickMainMenu(View view) {
        view.setSelected(true);
        switch (view.getId()) {
            case R.id.rb_homepage:
                menuTag = 0;
                rbMsg.setSelected(false);
                rbMine.setSelected(false);
                break;
            case R.id.rb_msg:
                menuTag = 1;
                rbHomepage.setSelected(false);
                rbMine.setSelected(false);
                break;
            case R.id.rb_mine:
                menuTag = 2;
                rbHomepage.setSelected(false);
                rbMsg.setSelected(false);
                break;
        }
        Fragment fragment = (Fragment) pagerAdapter.instantiateItem(flContainer, menuTag);
        pagerAdapter.setPrimaryItem(flContainer, 0, fragment);
        pagerAdapter.finishUpdate(flContainer);
    }

    private FragmentStatePagerAdapter pagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return HomePageFragment.newInstance();
                case 1:
                    return MsgFragment.newInstance();
                case 2:
                    return MineFragment.newInstance();
            }
            return null;
        }
    };

}
