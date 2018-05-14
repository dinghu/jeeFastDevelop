package com.xjj.framework.service;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xjj.framework.entity.EntitySupport;
import com.xjj.framework.exception.DataAccessException;
import com.xjj.framework.web.support.Pagination;
import com.xjj.framework.web.support.XJJParameter;

public interface XjjService<E extends EntitySupport> {

	/**
	 * 保存
	 * @param obj
	 * @return
	 */
	public Long save(E obj);
	
	/**
	 * 更新
	 * @param obj
	 */
	public void update(E obj);
	
	public void delete(Long id);
	public void delete(E obj);

	public int getCount(XJJParameter param);
	
	public E getById(Long ID);
	public E getByParam(XJJParameter param) throws DataAccessException;
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<E> findAll();
	
	/**
	 * 根据参数查询列表
	 * @param param
	 * @return
	 */
	public List<E> findList(XJJParameter param);
	/**
	 * 根据某属性值数组查询列表
	 * @param property
	 * @param objArr
	 * @return
	 */
	public List<E> findListByColumnValues(String property,Object[] objArr);

	/**
	 * 分页查询列表
	 * @param param
	 * @param page
	 * @return
	 */
	public Pagination findPage(XJJParameter param, Pagination page);
	
	public boolean checkUniqueVal( String tableName,String columnName,String columnVal,Long id);
}