package com.myzh.sharding.config;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;

import io.shardingsphere.api.algorithm.sharding.ListShardingValue;
import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.ShardingValue;
import io.shardingsphere.api.algorithm.sharding.hint.HintShardingAlgorithm;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ruqing
 *
 */
@Slf4j
public class ProvinceHintTableShardingAlgorithm implements HintShardingAlgorithm {
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
	// @Override
	public Collection<String> doSharding1(Collection<String> availableTargetNames, ShardingValue shardingValue) {
		log.info("ruqing   ==== availableTargetNames :  " + availableTargetNames + "   ======");
		log.info("ruqing   ==== shardingValue :  " + shardingValue + "   ======");
		// availableTargetNames
		// 这个参数是所有的dataSource的集合，shardingValue是HintManager传过来的分片信息
		ListShardingValue listShardingValue = (ListShardingValue) shardingValue;
		Collection<String> shardingValueList = listShardingValue.getValues();
		// 因为调用的时候分片是直接传的 DataSource的名称，所以直接返回就可以了，如果传其它值则要加业务逻辑进行分片筛选
		// 返回结果只能是availableTargetNames 里边所包含的
		Collection<String> result = new ArrayList<String>();

		for (String temp : shardingValueList) {
			if (availableTargetNames.contains(temp)) {
				result.add(temp);
			}
		}
		System.out.println(result);
		return result;

	}

	@Override
	public Collection<String> doSharding(Collection<String> availableTargetNames, ShardingValue shardingValue) {
		if (shardingValue instanceof ListShardingValue
				&& !CollectionUtils.isEmpty(((ListShardingValue) shardingValue).getValues())) {
			log.info("---------------------" + ((ListShardingValue) shardingValue).getValues());
			return availableTargetNames.stream().filter(availableTargetName -> ((ListShardingValue) shardingValue)
					.getValues().contains(availableTargetName)).collect(Collectors.toList());
		}
		System.out.println(availableTargetNames);
		return availableTargetNames;

	}
}
