package com.floyd.http;

import java.util.Map;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class ObservableHttp {

	public static Observable<String> observableRequest(final String url, final Map<String, String> params, final HttpMethod method) {
		Observable<String> request = Observable.create(new OnSubscribe<String>() {

			@Override
			public void call(Subscriber<? super String> ob) {
				BaseRequest request = new BaseRequest(url, params, method);
				Response response = request.execute();
				if (response.isSuccess()) {
					String content = response.getContentString();
					ob.onNext(content);
					ob.onCompleted();
					return;
				}
				String info = response.getResponseMessage();
				ob.onError(new IllegalArgumentException(info));
			}
		}).subscribeOn(Schedulers.io());

		return request;
	}
}
