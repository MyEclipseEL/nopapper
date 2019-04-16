package com.ladybird.hkd.model.json;

import java.io.Serializable;

/**
 * @author Shen
 * @description:
 * @create: 2019-04-19
 */
public class PageBean<T> implements Serializable {
    private Integer totalPages = 0;
    private Integer totalCount = 0;
    private Integer curPage = 0;
    private Integer pageCount = 0;
    private T data;

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
