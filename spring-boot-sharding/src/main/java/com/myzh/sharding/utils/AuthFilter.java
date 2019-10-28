package com.myzh.sharding.utils;

/**
 * 
 */

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;

import com.alibaba.fastjson.JSONObject;

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
		try {
			HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
			HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
			String tenant = httpServletRequest.getHeader("tenant");
			log.info("tenant  ====  " + tenant);

			log.info("tenant  ds ? ====  " + TenantContextHolder.getTenant());
			if ("bj".equals(tenant)) {
				TenantContextHolder.setTenant("ds_0");

			} else if ("sh".equals(tenant)) {
				TenantContextHolder.setTenant("ds_1");
			} else if ("sz".equals(tenant)) {
				TenantContextHolder.setTenant("ds_2");
			}

			else {
				httpServletResponse.setCharacterEncoding("UTF-8");
				httpServletResponse.setContentType("application/json; charset=utf-8");
				JSONObject res = new JSONObject();
				res.put("status", "1");
				res.put("msg", "no tenant .");
				PrintWriter out = httpServletResponse.getWriter();
				out.append(res.toString());
				throw new Exception("no tenant .");
			}

			filterChain.doFilter(servletRequest, servletResponse);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			log.info("tenant 2  ds ? ====  " + TenantContextHolder.getTenant());
			TenantContextHolder.remove();
		}

	}

	// 退出的时候要求清除 tenant
	@Override
	public void destroy() {
	}

}
