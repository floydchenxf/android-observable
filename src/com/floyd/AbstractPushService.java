package com.floyd;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import rx.subjects.Subject;

public abstract class AbstractPushService<T> {

	private List<WeakReference<Subject<T, T>>> subjects = new ArrayList<WeakReference<Subject<T, T>>>();

	public void regSubject(Subject<T, T> subject) {
		subjects.add(new WeakReference<Subject<T, T>>(subject));
	}

	public void onPush() {
		T t = create();
		for (WeakReference<Subject<T, T>> weakSubject : subjects) {
			Subject<T, T> ss = weakSubject.get();
			if (ss != null) {
				ss.onNext(t);
			}
		}
	}

	abstract T create();
}
