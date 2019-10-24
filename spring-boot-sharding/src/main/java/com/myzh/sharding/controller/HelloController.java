package com.myzh.sharding.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myzh.sharding.service.UserInfoService;

/**
 * 
 * @author xuruqing
 *
 */
@RestController
@RequestMapping("/")
public class HelloController {
	@Autowired
	private UserInfoService userInfoService;

	@RequestMapping("/test")
	public String hello() {
		userInfoService.batchInsertAndfind();
		return "ok";
	}

}