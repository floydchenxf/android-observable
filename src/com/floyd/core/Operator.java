package com.floyd.core;

import com.floyd.core.function.Func1;


/**
 * 执行类
 * 
 * @author cxf128
 * 
 * @param <R>
 * @param <T>
 */
public interface Operator<R, T> extends Func1<Observer<R>, Observer<T>> {

}
