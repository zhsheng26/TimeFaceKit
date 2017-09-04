package com.watertransport.support;

/**
 * Created by zhangsheng on 2017/8/2.
 */

public interface WtConstant {
    int USER_ROLE_BOAT = 3;
    int USER_ROLE_CARGO = 2;
    int BOAT_PAGE_1 = 11;
    int BOAT_PAGE_2 = 12;
    int CARGO_PAGE_1 = 21;
    int CARGO_PAGE_2 = 22;
    int CARGO_PAGE_3 = 23;

    int PAGE_STATE_PUBLISHING = 1;
    int PAGE_STATE_NO_PUBLISH = 0;
    int PAGE_STATE_CLOSED = 2;

    String BOAT_ORDER_ALL = "全部";
    String BOAT_ORDER_HAS = "已结算";
    String BOAT_ORDER_NO = "未结算";
}
