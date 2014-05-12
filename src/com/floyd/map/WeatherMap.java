package com.floyd.map;

import com.floyd.core.function.Func1;
import com.google.gson.Gson;

public class WeatherMap implements Func1<String, WeatherVO> {

	@Override
	public WeatherVO call(String t1) {
		Gson gson = new Gson();
		WeatherObj obj = gson.fromJson(t1, WeatherObj.class);
		return obj.getWeatherinfo();
	}

	public class WeatherObj {
		private WeatherVO weatherinfo;

		public WeatherVO getWeatherinfo() {
			return weatherinfo;
		}

		public void setWeatherinfo(WeatherVO weatherinfo) {
			this.weatherinfo = weatherinfo;
		}

	}
}
