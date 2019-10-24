package com.myzh.sharding.config;

import java.math.BigDecimal;
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
public class CompanyPreciseDBShardingAlgorithm implements PreciseShardingAlgorithm<BigDecimal> {
	@Override
	public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<BigDecimal> shardingValue) {
		log.info("Database: {}, companyShardingValue: {}", JSON.toJSONString(availableTargetNames),
				JSON.toJSONString(shardingValue));
		String ds = availableTargetNames.stream()
				// .filter(t -> t.endsWith(shardingValue.getValue().longValue()
				// % availableTargetNames.size() + ""))
				.filter(t -> t.endsWith(shardingValue.getValue().longValue() + "")).findFirst().orElse(null);

		log.info("ruqing   ==== ds :  " + ds + "   ======");
		return ds;
	}
	// StandardShardingStrategy
	// ShardingStrategy;
	// HintShardingAlgorithm
	// NoneShardingStrategy
	//
	// Sharding提供了以下4种算法接口：
	//
	// PreciseShardingAlgorithm
	// RangeShardingAlgorithm
	// HintShardingAlgorithm
	// ComplexKeysShardingAlgorithm
}
