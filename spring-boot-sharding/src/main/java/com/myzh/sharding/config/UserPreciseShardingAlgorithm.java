package com.myzh.sharding.config;

import java.util.Collection;

import com.alibaba.fastjson.JSON;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ruqing
 *
 */
@Slf4j
public class UserPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {
	@Override
	public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
		log.info("ruqing  ::::   Tables: {}, preciseValue: {}", JSON.toJSONString(availableTargetNames),
				JSON.toJSONString(shardingValue));
		String tableName = availableTargetNames.stream()
				.filter(t -> t.endsWith(shardingValue.getValue() % availableTargetNames.size() + "")).findFirst()
				.orElse(null);
		log.info("ruqing   ==== tableName :  " + tableName + "   ======");
		return tableName;
	}
}
