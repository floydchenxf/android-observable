package com.floyd;

import java.util.concurrent.ExecutorService;

import com.floyd.callback.ErrorCallback;
import com.floyd.callback.ProgressCallback;
import com.floyd.callback.SuccessCallback;
import com.floyd.operators.OperatorSubscribeOn;

/**
 * 观察者統一類
 * 
 * @author cxf128
 * 
 * @param <T>
 */
public class Observable<T> {

	final OnSubscribe<T> onSubscribe;

	protected Observable(OnSubscribe<T> onSubscribe) {
		this.onSubscribe = onSubscribe;
	}

	/**
	 * 创建观察者链
	 * 
	 * @param f
	 * @return
	 */
	public final static <T> Observable<T> create(OnSubscribe<T> f) {
		return new Observable<T>(f);
	}

	public final <R> Observable<R> lift(final Operator<R, T> lift) {
		return new Observable<R>(new OnSubscribe<R>() {

			@Override
			public void call(Observer<R> observer) {
				Observer<T> s = lift.call(observer);
				onSubscribe.call(s);
			}
		});
	}

	public final Observable<T> subscribeOn(ExecutorService scheduler) {
		return nest().lift(new OperatorSubscribeOn<T>(scheduler));
	}

	public final static <T> Observable<T> just(final T value) {
		return Observable.create(new OnSubscribe<T>() {

			@Override
			public void call(Observer<T> observer) {
				observer.invokeSuccess(value);
			}

		});
	}

	public final Observable<Observable<T>> nest() {
		return just(this);
	}

	/**
	 * 获取执行器
	 * 
	 * @return
	 */
	public final EventExecutor executor() {
		EventExecutor executor = new EventExecutor();
		return executor;
	}

	public final void unsafeSubscribe(Observer<T> observer) {
		try {
			onSubscribe.call(observer);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public class EventExecutor {

		private SuccessCallback<T> successCallback;
		private ErrorCallback errorCallback;
		private ProgressCallback progressCallback;

		EventExecutor successCallback(SuccessCallback<T> successCallback) {
			this.successCallback = successCallback;
			return this;
		}

		public EventExecutor errorCallback(ErrorCallback errorCallback) {
			this.errorCallback = errorCallback;
			return this;
		}

		public EventExecutor progressCallback(ProgressCallback progressCallback) {
			this.progressCallback = progressCallback;
			return this;
		}

		public void execute() {
			this.execute(new Observer<T>() {

				@Override
				public void invokeSuccess(T t) {
					if (successCallback != null) {
						successCallback.onSuccess(t);
					}
				}

				@Override
				public void invokeError(int code, String info) {
					if (errorCallback != null) {
						errorCallback.onError(code, info);
					}
				}

				@Override
				public void invokeProgress(int progress) {
					if (progressCallback != null) {
						progressCallback.onProgress(progress);
					}
				}
			});
		}

		final void execute(Observer<T> observer) {
			OnSubscribe<T> onSubscript = Observable.this.onSubscribe;
			if (onSubscript == null) {
				throw new IllegalArgumentException("observer can not be null");
			}

			try {
				onSubscript.call(observer);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
