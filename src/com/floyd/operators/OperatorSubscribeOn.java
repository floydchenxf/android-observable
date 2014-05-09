/**
 * Copyright 2014 Netflix, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.floyd.operators;

import java.util.concurrent.ExecutorService;

import com.floyd.Observable;
import com.floyd.Observer;
import com.floyd.Operator;

/**
 * Subscribes Observers on the specified Scheduler.
 * <p>
 * <img width="640" src=
 * "https://github.com/Netflix/RxJava/wiki/images/rx-operators/subscribeOn.png">
 */
public class OperatorSubscribeOn<T> implements Operator<T, Observable<T>> {

	private final ExecutorService scheduler;

	public OperatorSubscribeOn(ExecutorService scheduler) {
		this.scheduler = scheduler;
	}

	@Override
	public Observer<Observable<T>> call(final Observer<T> observer) {
		return new Observer<Observable<T>>() {

			@Override
			public void invokeError(int code, String info) {
				observer.invokeError(code, info);
			}

			@Override
			public void invokeProgress(int progress) {
				observer.invokeProgress(progress);

			}

			@Override
			public void invokeSuccess(final Observable<T> t) {
				scheduler.execute(new Runnable() {

					@Override
					public void run() {
						t.unsafeSubscribe(observer);
					}
				});
			}
		};
	}
}
