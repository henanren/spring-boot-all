package com.myzh.sharding.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myzh.sharding.mapper.CompanyMapper;
import com.myzh.sharding.service.CompanyService;

/**
 * 
 * @author ruqing
 * @since 2019-10-24 23:05:40
 */
@Service
public class CompanyServiceImpl implements CompanyService {
	@Autowired
	private CompanyMapper companyMapper;

}