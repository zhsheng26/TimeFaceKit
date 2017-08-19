package com.watertransport.entity;

import java.util.List;

/**
 * Created by zhangsheng on 2017/8/19.
 */

public class PageInfo<T> {

    /**
     * pageNo : 1
     * pageSize : 10
     * startNum : 0
     * endNum : 0
     * count : 0
     */

    private int pageNo;
    private int pageSize;
    private int startNum;
    private int endNum;
    private int count;
    private List<T> list;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartNum() {
        return startNum;
    }

    public void setStartNum(int startNum) {
        this.startNum = startNum;
    }

    public int getEndNum() {
        return endNum;
    }

    public void setEndNum(int endNum) {
        this.endNum = endNum;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
