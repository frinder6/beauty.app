package com.beauty.service;

import java.util.List;
import java.util.Map;

public interface IMapperService<T> {

	public int selectCount(Map<String, Object> params);

	public List<?> selectPage(Map<String, Object> params);

	public void insertSelective(T entity);

	public void updateByPrimaryKeySelective(T entity);

	public void deleteByPrimaryKeys(List<Object> list);

	public T selectByPrimaryKey(Long id);

	public List<?> selectConfPage(Map<String, Object> params);

	public int selectConfCount(Map<String, Object> params);

}
