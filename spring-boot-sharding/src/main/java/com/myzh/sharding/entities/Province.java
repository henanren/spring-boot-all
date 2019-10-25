package com.myzh.sharding.entities;

import lombok.Data;


/**
* 
* @author ruqing
* @since 2019-10-24 23:05:40
*/
@Data
public class Province {
	/***/
	private int id;

	/***/
	private int provId;

	/***/
	private String provName;

	/**1 - 直辖市
2 - 行政省
3 - 自治区
4 - 特别行政区
5 - 其他国家
见全局数据字典[省份类型] 
*/
	private String provType;

	/**0 - 禁用
1 - 启用*/
	private String provState;

}