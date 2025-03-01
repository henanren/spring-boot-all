package com.myzh.sharding.mapper;

import java.util.List;
import java.util.Map;

import com.myzh.sharding.entities.Company;

/**
 * 
 * @author ruqing
 * @since 2019-10-24 23:05:40
 */
public interface CompanyMapper {

	/**
	 * 保存对象
	 * 
	 * @param info
	 *            保存对象
	 * @return 返回成功保存对象数量
	 * @author ruqing
	 * @since 2019-10-24 23:05:40
	 */
	int save(Company info);

	/**
	 * 修改对象
	 * 
	 * @param info
	 *            待修改对象
	 * @return 返回成功修改对象数量
	 * @author ruqing
	 * @since 2019-10-24 23:05:40
	 */
	int update(Company info);

	/**
	 * 删除对象
	 * 
	 * @param id
	 *            对象id
	 * @return 返回成功删除数量
	 * @author ruqing
	 * @since 2019-10-24 23:05:40
	 */
	int delete(Company info);

	/**
	 * 根据ID查询对象
	 * 
	 * @param id
	 *            对象id
	 * @return 返回根据id查询的对象
	 * @author ruqing
	 * @since 2019-10-24 23:05:40
	 */
	Company findById(Map<String, Object> params);

	/**
	 * 根据条件查询所有对象
	 * 
	 * @param params
	 *            查询参数map对象
	 * @return 返回查询对象集合
	 * @author ruqing
	 * @since 2019-10-24 23:05:40
	 */
	List<Company> findAll(Map<String, Object> params);

	/**
	 * 根据条件查询所有对象数量
	 * 
	 * @param params
	 *            查询参数map对象
	 * @return 返回根据条件查下对象的数量
	 * @author ruqing
	 * @since 2019-10-24 23:05:40
	 */
	int findCount(Map<String, Object> params);

}