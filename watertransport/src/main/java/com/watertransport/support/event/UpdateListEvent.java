package com.watertransport.support.event;

/**
 * Created by zhangsheng on 2017/8/28.
 */

public class UpdateListEvent {
    private int pageState;

    public UpdateListEvent(int pageState) {
        this.pageState = pageState;
    }

    public int getPageState() {
        return pageState;
    }

    public void setPageState(int pageState) {
        this.pageState = pageState;
    }
}
