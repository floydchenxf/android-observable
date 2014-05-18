package com.floyd.http;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;

public class ObservableHttp {

	public Observable<String> request(final String url) {
		Observable<String> request = Observable
				.create(new OnSubscribe<String>() {

					@Override
					public void call(Subscriber<? super String> ob) {
						BaseRequest request = new BaseRequest(url, null,
								HttpMethod.GET);
						Response response = request.execute();
						if (response.isSuccess()) {
							String content = response.getContentString();
							ob.onNext(content);
							return;
						}
						String info = response.getResponseMessage();
						ob.onError(new IllegalArgumentException(info));
					}
				});

		return request;
	}

}
