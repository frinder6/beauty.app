package com.beauty.mapper;

import com.beauty.model.Value;

import java.util.List;
import java.util.Map;

public interface BeautyResourceMapperImpl extends IMapperImpl {

	public List<?> selectResource2Menu(Map<String, Object> params);

	public void batchGroup(Value value);

	public List<?> selectMenuSelect(Map<String, Object> params);

}
