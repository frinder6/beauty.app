package com.beauty.controller;

import com.beauty.entity.BeautyResource;
import com.beauty.entity.BeautyUrl;
import com.beauty.entity.Page;
import com.beauty.model.Value;
import com.beauty.service.ResourceExecuteService;
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

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/re")
public class ResourceExecuteController {


    @Autowired
    private ResourceExecuteService resourceExecuteService;

    @RequestMapping(value = "/load/page", produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<?> queryPage(@RequestParam("id") Long id, @RequestParam("table") String table) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("table", table);
        List<?> list = this.resourceExecuteService.selectPage(params);
        return list;
    }
    /*public Page queryPage(HttpServletRequest request, @RequestParam("id") Long id, @RequestParam("table") String table) {
        Page page = new Page();
        page.init(request);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("table", table);
        // 将page值设置到map中
        page.pageToMap(BeautyResource.class, params);
        int count = this.resourceExecuteService.selectCount(params);
        List<?> list = this.resourceExecuteService.selectPage(params);
        page.setResult(list, count + "", count + "");
        return page;
    }*/


}
