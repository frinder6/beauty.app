package com.beauty.service;

import com.beauty.entity.BeautyResource;
import com.beauty.mapper.BeautyResourceExecuteMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ResourceExecuteService extends BaseService<BeautyResource> {

    @Autowired
    private BeautyResourceExecuteMapperImpl beautyResourceExecuteMapperImpl;


    @Override
    public int selectCount(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return this.beautyResourceExecuteMapperImpl.selectCount(params);
    }

    @Override
    public List<?> selectPage(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return this.beautyResourceExecuteMapperImpl.selectPage(params);
    }

}
