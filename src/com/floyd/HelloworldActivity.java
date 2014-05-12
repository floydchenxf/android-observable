package com.floyd;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.floyd.core.Observable;
import com.floyd.core.Observer;
import com.floyd.core.OnSubscribe;
import com.floyd.core.callback.ErrorCallback;
import com.floyd.core.callback.SuccessCallback;
import com.floyd.http.BaseRequest;
import com.floyd.http.HttpMethod;
import com.floyd.http.Response;
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
		Observable
				.create(new OnSubscribe<String>() {
					@Override
					public void call(Observer<String> observer) {
						BaseRequest request = new BaseRequest(
								"http://www.weather.com.cn/data/sk/101110101.html",
								null, HttpMethod.GET);
						Response response = request.execute();
						if (response.isSuccess()) {
							String content = response.getContentString();
							observer.invokeSuccess(content);
							return;
						}
						int code = response.getResponseCode();
						String info = response.getResponseMessage();
						observer.invokeError(code, info);
					}
				})
				.subscribeOn(
						DefaultExecutorService.getInstance()
								.getExecutorService()).map(new WeatherMap())
				.executor().successCallback(new SuccessCallback<WeatherVO>() {

					@Override
					public void onSuccess(WeatherVO t) {
						cityTextView.setText(t.getCity());
						wdTextView.setText(t.getWD());

					}
				}).errorCallback(new ErrorCallback() {

					@Override
					public void onError(int code, String info) {
						Toast.makeText(HelloworldActivity.this, info,
								Toast.LENGTH_SHORT).show();
					}
				}).execute();
	}

}
