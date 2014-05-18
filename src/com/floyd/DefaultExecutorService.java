package com.floyd;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DefaultExecutorService {

	private static final DefaultExecutorService instance = new DefaultExecutorService();

	private ExecutorService ex = Executors.newFixedThreadPool(5);;

	private DefaultExecutorService() {
	}

	public static DefaultExecutorService getInstance() {
		return instance;
	}

	public ExecutorService getExecutorService() {
		return ex;
	}

}
