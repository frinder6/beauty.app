package com.beauty.mapper;

import com.beauty.entity.BeautyResource;

public interface BeautyResourceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BeautyResource record);

    int insertSelective(BeautyResource record);

    BeautyResource selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BeautyResource record);

    int updateByPrimaryKey(BeautyResource record);
}