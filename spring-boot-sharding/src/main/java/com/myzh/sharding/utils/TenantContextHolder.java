package com.myzh.sharding.utils;

/**
 * 
 * @author xuruqing
 *
 */
public class TenantContextHolder {

	private static ThreadLocal<String> tenantThreadLocal = new ThreadLocal<String>();

	public static final void setTenant(String scheme) {
		tenantThreadLocal.set(scheme);
	}

	public static final String getTenant() {
		String scheme = tenantThreadLocal.get();
		if (scheme == null) {
			scheme = "";
		}
		return scheme;
	}

	// 清除
	public static final void remove() {
		tenantThreadLocal.remove();
	}

}