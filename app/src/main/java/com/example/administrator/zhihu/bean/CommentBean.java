package com.example.administrator.zhihu.bean;

/**
 * 作者：JTR on 2017/1/5 10:06
 * 邮箱：2091320109@qq.com
 */
public class CommentBean {
    /**
     * long_comments : 0
     * popularity : 12
     * short_comments : 1
     * comments : 1
     */

    private int long_comments;
    private int popularity;
    private int short_comments;
    private int comments;

    public int getLong_comments() {
        return long_comments;
    }

    public void setLong_comments(int long_comments) {
        this.long_comments = long_comments;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getShort_comments() {
        return short_comments;
    }

    public void setShort_comments(int short_comments) {
        this.short_comments = short_comments;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }
}
