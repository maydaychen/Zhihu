package io.github.maydaychen.mylibrary.net;

/**
 * 作者：JTR on 2016/11/25 10:31
 * 邮箱：2091320109@qq.com
 */
public class HttpResult<T> {
    private String type;
    private String message;
    private T data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {

        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
