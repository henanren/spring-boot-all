package com.myzh.sharding.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myzh.sharding.mapper.UserInfoMapper;
import com.myzh.sharding.model.UserInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Lance
 * @since 2019-03-01 23:05:40
 */
@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {
	@Autowired
	private UserInfoMapper userInfoMapper;

	public void batchInsertAndfind() {
		Map<String, Object> map = new HashMap<>();
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

		int i = userInfoMapper.findCount(map);
		System.out.println("1  ===========  " + i);

		userInfoMapper.batchSave(list);
		i = userInfoMapper.findCount(map);
		System.out.println("2  ===========  " + i);
	}

	private UserInfo createUser(BigInteger companyId, int index) {
		return UserInfo.builder().account("Account." + index).companyId(companyId)
				.password(RandomStringUtils.randomAlphabetic(8)).userName("Name." + index).build();
	}

}