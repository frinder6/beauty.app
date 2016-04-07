package com.beauty.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beauty.entity.BeautyExecute;
import com.beauty.mapper.BeautyExecuteMapper;
import com.beauty.mapper.BeautyExecuteMapperImpl;

@Service
public class ExecuteService extends BaseService<BeautyExecute> {

	@Autowired
	private BeautyExecuteMapper beautyExecuteMapper;

	@Autowired
	private BeautyExecuteMapperImpl beautyExecuteMapperImpl;

	@Override
	public int selectCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.beautyExecuteMapperImpl.selectCount(params);
	}

	@Override
	public List<?> selectPage(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.beautyExecuteMapperImpl.selectPage(params);
	}

	@Override
	public void updateByPrimaryKeySelective(BeautyExecute entity) {
		this.beautyExecuteMapper.updateByPrimaryKeySelective(entity);
	}

	@Override
	public void deleteByPrimaryKeys(List<Object> list) {
		this.beautyExecuteMapperImpl.deleteByPrimaryKeys(list);
	}

	@Override
	public BeautyExecute selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return this.beautyExecuteMapper.selectByPrimaryKey(id);
	}

	@Override
	public void insertSelective(BeautyExecute entity) {
		// TODO Auto-generated method stub
		this.beautyExecuteMapper.insertSelective(entity);
	}

}
