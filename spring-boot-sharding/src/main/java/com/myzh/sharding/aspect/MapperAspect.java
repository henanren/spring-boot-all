package com.myzh.sharding.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

import com.myzh.sharding.utils.TenantContextHolder;

import io.shardingsphere.api.HintManager;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ruqing
 *
 */
@Service
@Aspect
@Slf4j
public class MapperAspect {
	@Pointcut("execution(public * com.myzh.sharding.mapper.*.*(..))")
	public void aspect() {
	}

	@Before("aspect()")
	public void doBefore(JoinPoint joinPoint) {
		log.info("===执行mapper====doBefore ");
		// 用户登录的时候，根据用户地点，把地点对应的db，存入threadlocal中，然后在这里取出来
		HintManager.getInstance().setDatabaseShardingValue(TenantContextHolder.getTenant());
	}

	@After("aspect()")
	public void doAfter() {
		log.info("===执行mapper====doAfter ");
		HintManager.getInstance().close();

	}

	// 获取接口返回的内容
	// @AfterReturning(returning = "object", pointcut = "aspect()")
	// public void doAfterReturining(Object object) {
	//
	// }

}
