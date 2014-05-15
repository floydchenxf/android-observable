package com.floyd;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.floyd.http.ObservableHttp;
import com.floyd.map.WeatherMap;
import com.floyd.map.WeatherVO;

public class HelloworldActivity extends Activity {

	private TextView cityTextView;
	private TextView wdTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.helloworld);
		cityTextView = (TextView) findViewById(R.id.city);
		wdTextView = (TextView) findViewById(R.id.wd);
		ObservableHttp http = new ObservableHttp();
		Observable<String> ss = http
				.request("http://www.weather.com.cn/data/sk/101110101.html");
		ss.map(new WeatherMap()).subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Action1<WeatherVO>() {

					@Override
					public void call(WeatherVO arg0) {
						cityTextView.setText(arg0.getCity());
						wdTextView.setText(arg0.getWD());
					}
				}, new Action1<Throwable>() {

					@Override
					public void call(Throwable arg0) {
						Toast.makeText(HelloworldActivity.this,
								arg0.getMessage(), Toast.LENGTH_SHORT).show();

					}
				});
	}

}
