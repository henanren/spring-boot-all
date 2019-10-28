package com.myzh.sharding.service;

import java.math.BigInteger;
import java.util.List;

import com.myzh.sharding.entities.UserInfo;

/**
 * 
 * @author ruqing
 * @since 2019-10-24 23:05:40
 */
public interface UserInfoService {
	void batchInsertAndfind();

	int findCount();

	int findCountForHintTest();

	int insert();

	int delete(BigInteger userId);

	int update(UserInfo info);

	List<UserInfo> findAll();

	List<UserInfo> findAllFromMaster();
}