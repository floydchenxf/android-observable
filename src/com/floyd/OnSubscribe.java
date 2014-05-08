package com.floyd;

/**
 * 订阅接口
 * 
 * @author cxf128
 * 
 * @param <T>
 */
public interface OnSubscribe<T> {

	/**
	 * 通知观察者
	 * 
	 * @param observer
	 */
	void call(Observer<T> observer);

}
