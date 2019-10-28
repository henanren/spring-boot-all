package com.myzh.sharding.service.impl;

import org.springframework.stereotype.Service;

import com.myzh.sharding.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	// static ThreadLocal<String> threadLocal = new ThreadLocal<>();
	static ThreadLocal<String> threadLocal = new InheritableThreadLocal<>();
	// static HystrixRequestVariableDefault<String> name = new
	// HystrixRequestVariableDefault<String>();

	public static void main(String[] args) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				OrderServiceImpl.threadLocal.set("123");
				// name.set("占小狼");
				new OrderServiceImpl().new Service().call();
			}
		}).start();

	}

	class Service {
		public void call() {
			System.out.println("Service:" + Thread.currentThread().getName());
			System.out.println("Service:" + OrderServiceImpl.threadLocal.get());
			// System.out.println("Service: name : " + name.get());
			new Dao().call();
		}

		public void call2() {
			System.out.println("Service:" + Thread.currentThread().getName());
			System.out.println("Service:" + OrderServiceImpl.threadLocal.get());
			new Thread(new Runnable() {
				@Override
				public void run() {
					new Dao().call();
				}
			}).start();

		}

	}

	class Dao {
		public void call() {
			System.out.println("==========================");
			System.out.println("Dao:" + Thread.currentThread().getName());
			System.out.println("Dao:" + OrderServiceImpl.threadLocal.get());

			// System.out.println("Service: name : " + name.get());

		}
	}
}
