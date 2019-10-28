package com.myzh.sharding.utils;

import java.math.BigInteger;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author xuruqing
 *
 */
@Slf4j
public class TenantContextHolder {

	private static ThreadLocal<String> tenantThreadLocal = new ThreadLocal<String>();

	public static final void setTenant(String scheme) {
		tenantThreadLocal.set(scheme);
	}

	public static final String getTenant() {
		String scheme = tenantThreadLocal.get();
		if (scheme == null) {
			scheme = null;
		}
		return scheme;
	}

	// 清除
	public static final void remove() {
		tenantThreadLocal.remove();
	}

	// 分片 是dn_999999
	public static final BigInteger getProvinceId() {
		// BigInteger provinceId = getTenant() == null ? new
		// BigInteger("999999")
		// : new BigInteger(getTenant().substring(getTenant().indexOf("_") +
		// 1));

		BigInteger provinceId = getTenant() == null ? null
				: new BigInteger(getTenant().substring(getTenant().indexOf("_") + 1));

		log.info("===TenantContextHolder  ===== provinceId =====  " + provinceId);
		return provinceId;
	}
}