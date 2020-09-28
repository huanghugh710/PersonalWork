package com.heng.vo;

import com.github.pagehelper.PageInfo;
import com.heng.utils.Constants;

/**
 * @author lfy
 * @Description:
 * @date 2020/9/1521:18
 */
public class SearchVo {

    //一级分类编码
    private String firstCode;
    //二级分类商品编码
    private String secondCode;
    //搜索框关键字
    private String keyword;
    //当前页
    private Integer pageIndex = 0;
    //每页显示多少条记录
    private Integer pageSize = Constants.PAGE_DEFAULT_SIZE;
    //总记录条数
    private Long recordCount;

//    private PageInfo pageInfo;

    public String getFirstCode() {
        return firstCode;
    }

    public void setFirstCode(String firstCode) {
        this.firstCode = firstCode;
    }

    public String getSecondCode() {
        return secondCode;
    }

    public void setSecondCode(String secondCode) {
        this.secondCode = secondCode;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Long recordCount) {
        this.recordCount = recordCount;
    }

    /*public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }*/
}
