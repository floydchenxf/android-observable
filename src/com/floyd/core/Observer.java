package com.floyd.core;

/**
 * 观察者
 * 
 * @author cxf128
 * 
 * @param <T>
 */
public interface Observer<T> {

	/**
	 * 通知成功的调用
	 * 
	 * @param t
	 */
	void invokeSuccess(T t);

	/**
	 * 通知错误的调用
	 * 
	 * @param code
	 * @param info
	 */
	void invokeError(int code, String info);

	/**
	 * 进度的调用
	 * 
	 * @param progress
	 */
	void invokeProgress(int progress);

}
