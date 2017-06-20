package cn.timeface.timefacekit.ui.material;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.timeface.timefacekit.R;
import cn.timeface.timekit.activity.TfBaseActivity;

public class TypesetPhotoActivity extends TfBaseActivity {

    @BindView(R.id.iv_head_bg)
    ImageView ivHeadBg;
    @BindView(R.id.tv_pic_count)
    TextView tvPicCount;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_create_date)
    TextView tvCreateDate;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.rv_photo)
    RecyclerView rvPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typeset_photo);
        ButterKnife.bind(this);


    }
}
