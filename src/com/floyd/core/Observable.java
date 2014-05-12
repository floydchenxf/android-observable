package com.floyd.core;

import java.util.concurrent.ExecutorService;

import android.os.Handler;
import android.os.Looper;

import com.floyd.core.callback.ErrorCallback;
import com.floyd.core.callback.ProgressCallback;
import com.floyd.core.callback.SuccessCallback;
import com.floyd.core.function.Func1;
import com.floyd.core.operators.OperatorMap;
import com.floyd.core.operators.OperatorRetry;
import com.floyd.core.operators.OperatorSubscribeOn;

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

	/**
	 * 设置线程执行器
	 * 
	 * @param scheduler
	 * @return
	 */
	public final Observable<T> subscribeOn(ExecutorService scheduler) {
		return nest().lift(new OperatorSubscribeOn<T>(scheduler));
	}

	public final Observable<T> retry(int retryCount) {
		return nest().lift(new OperatorRetry<T>(retryCount));
	}
	
	public final Observable<T> retry() {
		return nest().lift(new OperatorRetry<T>());
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

	public final <R> Observable<R> map(Func1<T, R> func) {
		return lift(new OperatorMap<T, R>(func));
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
		private boolean isMain = true;
		private Handler mHandler = new Handler(Looper.getMainLooper());

		public EventExecutor successCallback(SuccessCallback<T> successCallback) {
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

		public EventExecutor isMainLooper(boolean isMainLooper) {
			this.isMain = isMainLooper;
			return this;
		}

		public void execute() {
			this.execute(new Observer<T>() {

				@Override
				public void invokeSuccess(final T t) {
					if (successCallback == null) {
						return;
					}

					if (isMain) {
						mHandler.post(new Runnable() {
							@Override
							public void run() {
								successCallback.onSuccess(t);
							}
						});
					} else {
						successCallback.onSuccess(t);
					}
				}

				@Override
				public void invokeError(final int code, final String info) {
					if (errorCallback == null) {
						return;
					}

					if (isMain) {
						mHandler.post(new Runnable() {
							@Override
							public void run() {
								errorCallback.onError(code, info);
							}
						});
					} else {
						errorCallback.onError(code, info);
					}

				}

				@Override
				public void invokeProgress(final int progress) {
					if (progressCallback == null) {
						return;
					}

					if (isMain) {
						mHandler.post(new Runnable() {
							@Override
							public void run() {
								progressCallback.onProgress(progress);
							}
						});
					} else {
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
