package com.ibbhub.adapterdelegate;

/**
 * @author ：chezi008 on 2018/8/18 8:38
 * @description ：The listener of adapter.
 * @email ：chezi008@163.com
 */
public interface AdapterListener<T> {
    /**
     * item click listener
     *
     * @param t   item
     * @param pos The position of item
     */
    void onClick(T t, int pos);

    /**
     * item long click listener
     *
     * @param t
     */
    void onLongClick(T t, int pos);
}
