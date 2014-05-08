package com.floyd;

public interface OnSubscribe<T> {
	
	void call(Observer<T> observer);

}
