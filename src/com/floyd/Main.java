package com.floyd;

import com.floyd.callback.ErrorCallback;
import com.floyd.callback.SuccessCallback;

public class Main {
	public static void main(String[] args) {

		Observable.create(new OnSubscribe<String>() {

			@Override
			public void call(Observer<String> observer) {
				System.out.println("111111111111111");
				observer.invokeSuccess("3");
				observer.invokeError(2, "ssss");

			}
		}).lift(new Operator<Integer, String>() {

			@Override
			public Observer<String> call(final Observer<Integer> t1) {
				Observer<String> aa = new Observer<String>() {

					@Override
					public void invokeSuccess(String t) {
						System.out.println("22222222222222");
						Integer b = Integer.parseInt(t);
						t1.invokeSuccess(b);
					}

					@Override
					public void invokeError(int code, String info) {
						System.out.println("333333333333");
						t1.invokeError(code, info);
					}

					@Override
					public void invokeProgress(int progress) {
						System.out.println("444444444444");
						t1.invokeProgress(progress);
					}

				};
				return aa;
			}
		}).lift(new Operator<String, Integer>() {

			@Override
			public Observer<Integer> call(final Observer<String> t1) {
				Observer<Integer> aa = new Observer<Integer>() {

					@Override
					public void invokeSuccess(Integer t) {
						System.out.println("555555555555");
						t1.invokeSuccess(t + "");
					}

					@Override
					public void invokeError(int code, String info) {
						System.out.println("666666666666");
						t1.invokeError(code, info);
					}

					@Override
					public void invokeProgress(int progress) {
						System.out.println("777777777777");
						t1.invokeProgress(progress);
					}

				};
				return aa;
			}

		}).executor().successCallback(new SuccessCallback<String>() {

			@Override
			public void onSuccess(String t) {
				System.out.println("---------");
				System.out.println(t);
			}
		}).errorCallback(new ErrorCallback() {

			@Override
			public void onError(int code, String info) {
				System.out.println("code" + code + "-----info" + info);

			}
		}).execute();
	}
}
