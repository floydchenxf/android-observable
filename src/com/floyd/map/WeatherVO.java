package com.floyd.map;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class WeatherVO {

	private String city;
	private Long cityid;
	private String WD;
	private String WS;
	private String SD;
	private String time;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getCityid() {
		return cityid;
	}

	public void setCityid(Long cityid) {
		this.cityid = cityid;
	}

	public String getWD() {
		return WD;
	}

	public void setWD(String wD) {
		WD = wD;
	}

	public String getWS() {
		return WS;
	}

	public void setWS(String wS) {
		WS = wS;
	}

	public String getSD() {
		return SD;
	}

	public void setSD(String sD) {
		SD = sD;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	// 发布者需要异步请求才能获取
	public Observable<String> getPublisher() {
		Observable<String> kk = Observable.create(new OnSubscribe<String>() {

			@Override
			public void call(Subscriber<? super String> arg0) {
				arg0.onNext("cxf128");
				arg0.onCompleted();
			}
		}).subscribeOn(Schedulers.newThread());
		return kk;
	}

}
