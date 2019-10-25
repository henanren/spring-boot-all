package com.myzh.sharding.controller;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myzh.sharding.entities.UserInfo;
import com.myzh.sharding.service.UserInfoService;
import com.myzh.sharding.utils.TenantContextHolder;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author xuruqing
 *
 */
@RestController
@Slf4j
@RequestMapping("/")
public class HelloController {
	@Autowired
	private UserInfoService userInfoService;

	/**
	 * 全局分片测试
	 * 
	 * @return
	 */
	@PostMapping("/all")
	public String all() {
		log.info(" ProvinceId()    :::   " + TenantContextHolder.getProvinceId());
		userInfoService.batchInsertAndfind();
		return "ok";
	}

	/**
	 * 单分片 插入
	 * 
	 * @return
	 */
	@PostMapping("/insert")
	public String insert() {
		userInfoService.insert();
		return "ok";
	}

	/**
	 * 单分片 查询
	 * 
	 * @return
	 */
	@GetMapping("/findAll")
	public List<UserInfo> get() {
		return userInfoService.findAll();
	}

	/**
	 * 单分片 查询 强制走 master
	 * 
	 * @return
	 */
	@GetMapping("/findAllFromMaster")
	public List<UserInfo> findAllFromMaster() {
		return userInfoService.findAllFromMaster();
	}

	/**
	 * 单分片 修改
	 * 
	 * @return
	 */
	@PutMapping("/put")
	public String put(@RequestParam("uId") BigInteger uId, @RequestParam("userName") String userName) {
		UserInfo info = UserInfo.builder().provinceId(TenantContextHolder.getProvinceId()).uId(uId).userName(userName)
				.build();
		int n = userInfoService.update(info);
		log.info("修改 ： " + n + "  条");
		return "ok";
	}

	/**
	 * 单分片 删除
	 * 
	 * @return
	 */
	@DeleteMapping("/delete")
	public String delete(@RequestParam("uId") BigInteger uId) {
		int n = userInfoService.delete(uId);
		log.info("删除 ： " + n + "  条");
		return "ok";
	}

}