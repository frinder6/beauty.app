package com.beauty.service;

import java.util.List;
import java.util.Map;

import com.beauty.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.beauty.entity.BeautyResource;
import com.beauty.mapper.BeautyResourceMapper;
import com.beauty.mapper.BeautyResourceMapperImpl;
import com.beauty.model.Value;

@Service
public class ResourceService extends BaseService<BeautyResource> {

    @Autowired
    private BeautyResourceMapper beautyResourceMapper;

    @Autowired
    private BeautyResourceMapperImpl beautyResourceMapperImpl;

    @Cacheable(value = RedisUtil._REDIS_CACHE_VALUE, key = "#params.get('_REDIS_CACHE_KEY')")
    public List<?> selectResource2Menu(Map<String, Object> params) {
        return this.beautyResourceMapperImpl.selectResource2Menu(params);
    }

    @Override
    public int selectCount(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return this.beautyResourceMapperImpl.selectCount(params);
    }


    @Cacheable(value = RedisUtil._REDIS_CACHE_VALUE, key = "#params.get('_REDIS_CACHE_KEY')")
    @Override
    public List<?> selectPage(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return this.beautyResourceMapperImpl.selectPage(params);
    }

    @Override
    public void updateByPrimaryKeySelective(BeautyResource entity) {
        this.beautyResourceMapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public void deleteByPrimaryKeys(List<Object> list) {
        this.beautyResourceMapperImpl.deleteByPrimaryKeys(list);
    }

    @Override
    public BeautyResource selectByPrimaryKey(Long id) {
        // TODO Auto-generated method stub
        return this.beautyResourceMapper.selectByPrimaryKey(id);
    }

    @Override
    public void insertSelective(BeautyResource entity) {
        // TODO Auto-generated method stub
        this.beautyResourceMapper.insertSelective(entity);
    }

    public void batchGroup(Value value) {
        this.beautyResourceMapperImpl.batchGroup(value);
    }

    @Cacheable(value = RedisUtil._REDIS_CACHE_VALUE, key = "#params.get('_REDIS_CACHE_KEY')")
    public List<?> selectMenuSelect(Map<String, Object> params) {
        return this.beautyResourceMapperImpl.selectMenuSelect(params);
    }
}
