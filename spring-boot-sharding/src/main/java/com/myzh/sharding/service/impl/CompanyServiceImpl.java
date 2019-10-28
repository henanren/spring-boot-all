package com.myzh.sharding.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myzh.sharding.entities.Company;
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

	@Override
	public int save(Company info) {
		return companyMapper.save(info);
	}

	@Override
	public int update(Company info) {
		return companyMapper.update(info);
	}

	@Override
	public int delete(Company info) {
		return companyMapper.delete(info);
	}

	@Override
	public Company findById(Map<String, Object> params) {
		return companyMapper.findById(params);
	}

	@Override
	public List<Company> findAll(Map<String, Object> params) {
		return companyMapper.findAll(params);
	}

	@Override
	public int findCount(Map<String, Object> params) {
		return companyMapper.findCount(params);
	}

}