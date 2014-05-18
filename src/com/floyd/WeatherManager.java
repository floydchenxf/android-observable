package com.floyd;

import java.util.Map;

import rx.Observable;

import com.floyd.http.HttpMethod;
import com.floyd.http.ObservableHttp;
import com.floyd.map.WeatherMap;
import com.floyd.map.WeatherVO;

public class WeatherManager {
	
	private static WeatherManager instance = new WeatherManager();
	
	private WeatherManager() {
		
	}
	
	public static final WeatherManager getInstance() {
		return instance;
	}
	
	public Observable<WeatherVO> getWeatherInfo(String url, Map<String, String> params) {
		Observable<String> httpRequest = ObservableHttp.observableRequest(url, params, HttpMethod.GET);
		Observable<WeatherVO> result = httpRequest.map(new WeatherMap());
		return result;
	}
}
