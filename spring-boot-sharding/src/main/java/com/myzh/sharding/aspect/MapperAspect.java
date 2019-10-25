package com.myzh.sharding.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import io.shardingsphere.api.HintManager;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ruqing
 *
 */
// @Service
// @Aspect
@Slf4j
public class MapperAspect {
	@Pointcut("execution(public * com.myzh.sharding.mapper.*.*(..))")
	public void aspect() {
	}

	// HintManager hintManager = HintManager.getInstance();

	@Before("aspect()")
	public void doBefore(JoinPoint joinPoint) {
		log.info("===执行mapper====doBefore ");
		HintManager hintManager = HintManager.getInstance();
		Object s[] = joinPoint.getArgs();
		// 用户登录的时候，根据用户地点，把地点对应的db，存入threadlocal中，然后在这里取出来
		// HintManager.getInstance().setDatabaseShardingValue(TenantContextHolder.getTenant());
		// hintManager.setDatabaseShardingValue("ds_0");// 分库不分表
		hintManager.addDatabaseShardingValue("t_user_info", "ds_0");// 指定逻辑表到某分片
		for (int i = 0; i < 5; i++) {
			// hintManager.addDatabaseShardingValue("t_user_info", i);
			hintManager.addTableShardingValue("t_user_info", "t_user_info_" + i);
		}
		// hintManager.addDatabaseShardingValue("ds", 0);
		// hintManager.
		// HintManagerHolder
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
