package com.floyd;

public interface Observer<T> {

	void invokeSuccess(T t);

	void invokeError(int code, String info);

	void invokeProgress(int progress);

}
