package com.myzh.sharding.entities;

import java.math.BigInteger;

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
public class UserInfo {
	/***/
	private BigInteger userId;

	/***/
	private BigInteger provinceId;

	/***/
	private String userName;

	/***/
	private String account;

	/***/
	private String password;
	/***/
	private BigInteger uId;

}