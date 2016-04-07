package com.beauty.controller;

import com.beauty.entity.BeautyExecute;
import com.beauty.entity.BeautyResource;
import com.beauty.entity.BeautyUrl;
import com.beauty.model.Value;
import com.beauty.service.ExecuteService;
import com.beauty.service.ResourceService;
import com.beauty.service.UrlService;
import com.beauty.util.CodeUtil;
import com.beauty.util.RedisUtil;
import com.beauty.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private UrlService urlService;

    @Autowired
    private ExecuteService executeService;


    @RequestMapping(value = "/load/page", produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<?> queryPage(BeautyResource entity) {
        Map<String, Object> params = new HashMap<>();
        params.put("type", entity.getType());
        params.put("parentId", 0);
        params.put(RedisUtil._REDIS_CACHE_KEY, RedisUtil.getRedisKey("RESOURCE", params));
        List<?> list = this.resourceService.selectPage(params);
        return list;
    }
    /*
    public Page queryPage(HttpServletRequest request, BeautyResource entity) {
        Page page = new Page();
        page.init(request);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("type", entity.getType());
        params.put("parentId", 0);
        // 将page值设置到map中
        page.pageToMap(BeautyResource.class, params);
        params.put(RedisUtil._KEY_1, 1);
        params.put(RedisUtil._REDIS_CACHE_KEY, RedisUtil.getRedisKey("RESOURCE", params));
        int count = this.resourceService.selectCount(params);
        params.put(RedisUtil._KEY_2, 2);
        params.put(RedisUtil._REDIS_CACHE_KEY, RedisUtil.getRedisKey("RESOURCE", params));
        List<?> list = this.resourceService.selectPage(params);
        page.setResult(list, count + "", count + "");
        return page;
    }*/

    @RequestMapping(value = "/group", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Value group(Value value) {
        value.setData(StringUtil.hashCode(value.getValue()));
        this.resourceService.batchGroup(value);
        return new Value(CodeUtil.SUCCESS);
    }

    @RequestMapping(value = "/add", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Value persist(BeautyResource entity) {
        this.resourceService.insertSelective(entity);
        return new Value(CodeUtil.ADD_SUCCESS);
    }

    @RequestMapping(value = "/update", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Value modify(BeautyResource entity) {
        this.resourceService.updateByPrimaryKeySelective(entity);
        return new Value(CodeUtil.EDIT_SUCCESS);
    }

    @RequestMapping(value = "/remove", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Value delete(Value value) {
        if (!value.getValues().isEmpty()) {
            this.resourceService.deleteByPrimaryKeys(value.getValues());
        }
        return new Value(CodeUtil.DELETE_SUCCESS);
    }

    @RequestMapping(value = "/load/id", produces = "application/json; charset=utf-8")
    @ResponseBody
    public BeautyResource load(@RequestParam("id") Long id) {
        return this.resourceService.selectByPrimaryKey(id);
    }

    @RequestMapping(value = "/export", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Value batchExecute2Resource(Value value) {
        if (!value.getValues().isEmpty()) {
            List<Object> ids = value.getValues();
            BeautyExecute execute;
            BeautyResource entity;
            long rid = Long.parseLong(value.getValue());
            BeautyResource pResource = this.resourceService.selectByPrimaryKey(rid);
            String templateName = pResource.getName().concat("[%s]");
            String templateCode = pResource.getCode().concat("-%s");
            long oid;
            for (Object id : ids) {
                oid = Long.parseLong(id.toString());
                execute = this.executeService.selectByPrimaryKey(oid);
                entity = new BeautyResource(String.format(templateName, execute.getName()), String.format(templateCode, execute.getCode()), rid, execute.getImage(), (byte) 0, oid, execute.getRemark());
                this.resourceService.insertSelective(entity);
            }
        }
        return new Value(CodeUtil.ADD_SUCCESS);
    }


    @RequestMapping(value = "/config", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Value config(Value value) {
        if (!value.getValues().isEmpty()) {
            List<Object> ids = value.getValues();
            BeautyUrl url;
            BeautyResource entity;
            for (Object id : ids) {
                url = this.urlService.selectByPrimaryKey(Long.parseLong(id.toString()));
                entity = new BeautyResource();
                entity.setUrl(url.getUrl());
                this.resourceService.insertSelective(entity);
            }
        }
        return new Value(CodeUtil.ADD_SUCCESS);
    }

}
