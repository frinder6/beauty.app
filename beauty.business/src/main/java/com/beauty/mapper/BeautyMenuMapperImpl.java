package com.beauty.mapper;

import java.util.List;
import java.util.Map;

public interface BeautyMenuMapperImpl extends IMapperImpl {

	public List<?> selectMenuSelect(Map<String, Object> params);

	public List<?> selectMainMenu(Map<String, Object> params);

}
