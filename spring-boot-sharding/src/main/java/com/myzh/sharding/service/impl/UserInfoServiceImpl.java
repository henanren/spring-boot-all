package com.myzh.sharding.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myzh.sharding.entities.UserInfo;
import com.myzh.sharding.mapper.UserInfoMapper;
import com.myzh.sharding.service.UserInfoService;
import com.myzh.sharding.utils.TenantContextHolder;

import io.shardingsphere.api.HintManager;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ruqing
 * @since 2019-10-24 23:05:40
 */
@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {
	@Autowired
	private UserInfoMapper userInfoMapper;

	public void batchInsertAndfind() {

		UserInfo UserInfo = createUser(new BigInteger("0"), 1);
		List<UserInfo> list = new ArrayList<>();
		list.add(UserInfo);

		UserInfo = createUser(new BigInteger("0"), 2);
		list.add(UserInfo);
		UserInfo = createUser(new BigInteger("1"), 3);
		list.add(UserInfo);
		UserInfo = createUser(new BigInteger("1"), 4);
		list.add(UserInfo);
		UserInfo = createUser(new BigInteger("2"), 5);
		list.add(UserInfo);

		UserInfo = createUser(new BigInteger("2"), 6);
		list.add(UserInfo);
		Map<String, Object> map = new HashMap<>();
		map.put("provinceId", 0);
		int i = userInfoMapper.findCount(map);
		System.out.println("1  ===========  " + i);

		userInfoMapper.batchSave(list);
		i = userInfoMapper.findCount(map);
		System.out.println("2  ===========  " + i);
	}

	@Override
	public int findCount() {
		HintManager hintManager = HintManager.getInstance();
		try {
			// 用户登录的时候，根据用户地点，把地点对应的db，存入threadlocal中，然后在这里取出来
			//
			// HintManager.getInstance().setDatabaseShardingValue(TenantContextHolder.getTenant());
			// hintManager.setDatabaseShardingValue("ds_0");// 分库不分表
			// 分库，库内 又分表
			// hintManager.addDatabaseShardingValue("t_user_info", "ds_0");
			// 指定逻辑表到某分片
			// for (int i = 0; i < 5; i++) {
			// // hintManager.addDatabaseShardingValue("t_user_info", i);
			// hintManager.addTableShardingValue("t_user_info", "t_user_info_" +
			// i);
			// }

			Map<String, Object> params = new HashMap<>();

			hintManager.setMasterRouteOnly();
			// params.put("companyId", 0);// 分片不成功的话，会全库扫描
			params.put("provinceId", TenantContextHolder.getProvinceId());// 按照分片查询

			return userInfoMapper.findCount(params);
		} finally {
			hintManager.close();

		}

	}

	@Override
	public int insert() {
		UserInfo info = createUser(TenantContextHolder.getProvinceId(), 5);
		return userInfoMapper.save(info);

	}

	@Override
	public int delete(BigInteger uId) {
		UserInfo info = UserInfo.builder().provinceId(TenantContextHolder.getProvinceId()).uId(uId).build();
		return userInfoMapper.delete(info);
	}

	@Override
	public int update(UserInfo info) {
		return userInfoMapper.update(info);
	}

	@Override
	public List<UserInfo> findAll() {
		Map<String, Object> params = new HashMap<>();
		// hintManager.setMasterRouteOnly();
		// params.put("companyId", 0);// 分片不成功的话，会全库扫描
		params.put("provinceId", TenantContextHolder.getProvinceId());// 按照分片查询
		return userInfoMapper.findAll(params);
	}

	@Override
	public List<UserInfo> findAllFromMaster() {
		HintManager hintManager = HintManager.getInstance();
		try {
			Map<String, Object> params = new HashMap<>();
			// hintManager.setMasterRouteOnly();
			// params.put("companyId", 0);// 分片不成功的话，会全库扫描
			params.put("provinceId", TenantContextHolder.getProvinceId());// 按照分片查询
			hintManager.setMasterRouteOnly();
			return userInfoMapper.findAll(params);
		} finally {
			hintManager.close();

		}
	}

	private UserInfo createUser(BigInteger provinceId, int index) {
		return UserInfo.builder().account("Account." + index).provinceId(provinceId)
				.password(RandomStringUtils.randomAlphabetic(8)).userName("Name." + index)
				.uId(new BigInteger(System.currentTimeMillis() + "")).build();
	}

}