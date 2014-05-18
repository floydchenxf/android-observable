package com.floyd;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;

public class PushService extends AbstractPushService<String> {

	@Override
	Observable<String> create() {
		Observable<String> result = Observable.create(new OnSubscribe<String>() {

			@Override
			public void call(final Subscriber<? super String> arg0) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						arg0.onNext("publish");
						arg0.onCompleted();
					}
				}).start();
			}
		});
		return result;
	}

}
