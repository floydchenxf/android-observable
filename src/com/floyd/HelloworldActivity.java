package com.floyd;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.floyd.map.WeatherVO;

public class HelloworldActivity extends Activity {

	private TextView cityTextView;
	private TextView wdTextView;
	private TextView publisherView;
	private TextView publishMsgView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.helloworld);
		cityTextView = (TextView) findViewById(R.id.city);
		wdTextView = (TextView) findViewById(R.id.wd);
		publisherView = (TextView) findViewById(R.id.publisher);
		publishMsgView = (TextView) findViewById(R.id.publish_msg);
		PublishSubject<String> ps = PublishSubject.create();
		ps.observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>() {

			@Override
			public void call(String msg) {
				publishMsgView.setText(msg);

			}
		});
		PushService service = new PushService();
		service.regSubject(ps);
		service.onPush();

		Observable<WeatherVO> weatherInfo = WeatherManager.getInstance().getWeatherInfo("http://www.weather.com.cn/data/sk/101110101.html", null);
		weatherInfo.observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<WeatherVO>() {

			@Override
			public void call(WeatherVO arg0) {
				cityTextView.setText(arg0.getCity());
				wdTextView.setText(arg0.getWD());
				Observable<String> publisher = arg0.getPublisher();
				publisher.observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>() {

					@Override
					public void call(String publisher) {
						publisherView.setText(publisher);
					}
				}, new Action1<Throwable>() {

					@Override
					public void call(Throwable arg0) {
						Toast.makeText(HelloworldActivity.this, arg0.getMessage(), Toast.LENGTH_SHORT).show();
					}
				});
			}
		}, new Action1<Throwable>() {

			@Override
			public void call(Throwable arg0) {
				Toast.makeText(HelloworldActivity.this, arg0.getMessage(), Toast.LENGTH_SHORT).show();

			}
		});
	}

}
