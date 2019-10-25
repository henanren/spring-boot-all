package com.myzh.sharding.entities;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
* 
* @author ruqing
* @since 2019-10-24 23:05:40
*/
@Data
@Builder
public class Company {
	/***/
	private BigInteger companyId;

	/***/
	private String companyName;

	/***/
	private String address;

	/***/
	private Date createTime;

}