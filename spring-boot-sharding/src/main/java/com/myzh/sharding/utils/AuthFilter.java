package com.myzh.sharding.utils;

/**
 * 
 */

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.Order;

import lombok.extern.slf4j.Slf4j;

/**
 * @类名 AuthFilter.java
 * @作者 如慶
 * @创建日期 2016年7月15日
 * @描述
 * @版本 V 1.0
 */

@Order(0)
@WebFilter(urlPatterns = "/*", filterName = "appFilter")
@Slf4j
public class AuthFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		String tenant = httpServletRequest.getHeader("tenant");
		log.info("tenant  ====  " + tenant);
		if ("bj".equals(tenant)) {
			TenantContextHolder.setTenant("ds_0");

		} else if ("sh".equals(tenant)) {
			TenantContextHolder.setTenant("ds_1");
		} else if ("sz".equals(tenant)) {
			TenantContextHolder.setTenant("ds_2");
		} else {
			TenantContextHolder.setTenant("ds_999999");
		}

		filterChain.doFilter(servletRequest, servletResponse);

	}

	// 退出的时候要求清除 tenant
	@Override
	public void destroy() {
	}

}
