package com.floyd.core.operators;

import java.util.concurrent.atomic.AtomicInteger;

import com.floyd.core.Observable;
import com.floyd.core.Observer;
import com.floyd.core.Operator;

public class OperatorRetry<T> implements Operator<T, Observable<T>> {

	private static final int INFINITE_RETRY = -1;

	private final int retryCount;

	public OperatorRetry(int retryCount) {
		this.retryCount = retryCount;
	}

	public OperatorRetry() {
		this(INFINITE_RETRY);
	}

	@Override
	public Observer<Observable<T>> call(final Observer<T> child) {
		Observer<Observable<T>> result = new Observer<Observable<T>>() {
			final AtomicInteger attempts = new AtomicInteger(0);

			@Override
			public void invokeSuccess(final Observable<T> t) {
				final Observer<Observable<T>> current = this;
				attempts.incrementAndGet();
				Observer<T> observer = new Observer<T>() {

					@Override
					public void invokeSuccess(T t) {
						child.invokeSuccess(t);
					}

					@Override
					public void invokeError(int code, String info) {
						if ((retryCount == INFINITE_RETRY || attempts.get() <= retryCount)) {
							// retry again
							current.invokeSuccess(t);
						} else {
							// give up and pass the failure
							child.invokeError(code, info);
						}

					}

					@Override
					public void invokeProgress(int progress) {
						child.invokeProgress(progress);
					}
				};
				t.unsafeSubscribe(observer);
			}

			@Override
			public void invokeError(int code, String info) {
				child.invokeError(code, info);
			}

			@Override
			public void invokeProgress(int progress) {
				child.invokeProgress(progress);
			}
		};

		return result;
	}
}
