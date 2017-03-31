package com.zzy.util;

/**
 * Created by Administrator on 2017/3/27.
 */
public class Page {
    // 总页数
    private int totalPage;
    // 当前页
    private int currentPage;
    // 每页尺寸
    private int pageSize;
    // 总条数
    private int totalNum;
    public Page(int currentPage,int pageSize,int totalNum){
        this.pageSize=pageSize;
        this.currentPage=currentPage;
        this.totalNum=totalNum;
    }
    // 初始查询下标
    public int getIndex(){
        if(currentPage < 2){return 0;}
        return (currentPage-1)*pageSize;
    }
    // 返回总页数
    public int getTotalPage() {
        return totalNum==0?1:(totalNum+pageSize-1)/pageSize;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }
}
