package com.floyd.core.operators;

import java.util.concurrent.ExecutorService;

import com.floyd.core.Observable;
import com.floyd.core.Observer;
import com.floyd.core.Operator;

public class OperatorSubscribeOn<T> implements Operator<T, Observable<T>> {

	private final ExecutorService scheduler;

	public OperatorSubscribeOn(ExecutorService scheduler) {
		this.scheduler = scheduler;
	}

	@Override
	public Observer<Observable<T>> call(final Observer<T> observer) {
		return new Observer<Observable<T>>() {

			@Override
			public void invokeError(int code, String info) {
				observer.invokeError(code, info);
			}

			@Override
			public void invokeProgress(int progress) {
				observer.invokeProgress(progress);

			}

			@Override
			public void invokeSuccess(final Observable<T> t) {
				scheduler.execute(new Runnable() {

					@Override
					public void run() {
						t.unsafeSubscribe(observer);
					}
				});
			}
		};
	}
}
