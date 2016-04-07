package com.beauty.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.beauty.entity.BeautyResource;
import com.beauty.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beauty.entity.BeautyMenu;
import com.beauty.entity.Page;
import com.beauty.model.Value;
import com.beauty.security.UserInfo;
import com.beauty.service.MenuService;
import com.beauty.util.CodeUtil;
import com.beauty.util.RedisUtil;
import com.beauty.util.StringUtil;

@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private ResourceService resourceService;


    @RequestMapping(value = "/select/menu", produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<?> selectMenu() {
        Map<String, Object> params = new HashMap<>();
        params.put(RedisUtil._REDIS_CACHE_KEY, RedisUtil.getRedisKey("MENU", params));
        return this.resourceService.selectMenuSelect(params);
    }


    @RequestMapping(value = "/load/menu", produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<?> loadResource2Menu() {
        Map<String, Object> params = new HashMap<>();
        UserInfo user = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        params.put("userId", user.getId());
        params.put(RedisUtil._REDIS_CACHE_KEY, RedisUtil.getRedisKey("MENU", params));
        return this.resourceService.selectResource2Menu(params);
    }


    @RequestMapping(value = "/load/page", produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<?> queryPage() {
        Map<String, Object> params = new HashMap<>();
        params.put("type", 1);
        params.put(RedisUtil._REDIS_CACHE_KEY, RedisUtil.getRedisKey("MENU", params));
        List<?> list = this.resourceService.selectPage(params);
        return list;
    }
    /*public Page queryPage(HttpServletRequest request) {
        Page page = new Page();
        page.init(request);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("type", 1);
        // 将page值设置到map中
        page.pageToMap(params);
        params.put(RedisUtil._KEY_1, 1);
        params.put(RedisUtil._REDIS_CACHE_KEY, RedisUtil.getRedisKey("MENU", params));
        int count = this.resourceService.selectCount(params);
        params.put(RedisUtil._KEY_2, 2);
        params.put(RedisUtil._REDIS_CACHE_KEY, RedisUtil.getRedisKey("MENU", params));
        List<?> list = this.resourceService.selectPage(params);
        page.setResult(list, count + "", count + "");
        return page;
    }*/

}
