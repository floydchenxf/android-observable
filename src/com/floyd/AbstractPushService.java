package com.floyd;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.subjects.Subject;

public abstract class AbstractPushService<T> {

	private List<WeakReference<Subject<T, T>>> subjects = new ArrayList<WeakReference<Subject<T, T>>>();

	public void regSubject(Subject<T, T> subject) {
		subjects.add(new WeakReference<Subject<T, T>>(subject));
	}

	public void onPush() {
		Observable<T> observable = create();
		observable.subscribe(new Observer<T>() {

			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable arg0) {
				for (WeakReference<Subject<T, T>> weakSubject : subjects) {
					Subject<T, T> ss = weakSubject.get();
					if (ss != null) {
						ss.onError(arg0);
						;
					}
				}
			}

			@Override
			public void onNext(T t) {
				for (WeakReference<Subject<T, T>> weakSubject : subjects) {
					Subject<T, T> ss = weakSubject.get();
					if (ss != null) {
						ss.onNext(t);
					}
				}
			}
		});
	}

	abstract Observable<T> create();
}
