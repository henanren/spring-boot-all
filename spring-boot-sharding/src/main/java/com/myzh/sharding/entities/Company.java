package com.myzh.sharding.entities;

import java.math.BigInteger;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author ruqing
 * @since 2019-10-24 23:05:40
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Company {
	/***/
	private BigInteger companyId;

	/***/
	private String companyName;

	/***/
	private String address;

	/***/
	private Date createTime;
	/***/
	private BigInteger provinceId;

}