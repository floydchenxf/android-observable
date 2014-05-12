package com.floyd.core.operators;

import com.floyd.core.Observer;
import com.floyd.core.Operator;
import com.floyd.core.callback.ErrorCode;
import com.floyd.core.function.Func1;

public final class OperatorMap<T, R> implements Operator<R, T> {

	private final Func1<T, R> transformer;

	public OperatorMap(Func1<T, R> transformer) {
		this.transformer = transformer;
	}

	@Override
	public Observer<T> call(final Observer<R> o) {
		return new Observer<T>() {

			@Override
			public void invokeSuccess(T t) {
				R r = null;
				try {
					r = transformer.call(t);
				} catch (Exception e) {
					o.invokeError(ErrorCode.TRANSFORMER_ERROR.getCode(),
							e.getMessage());
					return;
				}

				o.invokeSuccess(r);
			}

			@Override
			public void invokeError(int code, String info) {
				o.invokeError(code, info);
			}

			@Override
			public void invokeProgress(int progress) {
				o.invokeProgress(progress);
			}
		};
	}

}
