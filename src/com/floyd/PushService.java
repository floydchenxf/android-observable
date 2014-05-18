package com.floyd;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;

public class PushService extends AbstractPushService<String> {

	@Override
	Observable<String> create() {
		Observable<String> result = Observable.create(new OnSubscribe<String>() {

			@Override
			public void call(Subscriber<? super String> arg0) {
				arg0.onNext("publish");
				arg0.onCompleted();
			}
		});
		return result;
	}

}
