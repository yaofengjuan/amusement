package com.yao.amusement38demo.inter;

/**
 * Created by YaoFengjuan on 2016/9/22.
 */
public interface ILoadInfoView<T> {
    void setData(T data);

    void showError(int erroCode,String error);
}
