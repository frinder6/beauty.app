package com.beauty.controller;

import com.beauty.entity.BeautyQueue;
import com.beauty.entity.BeautyQueue;
import com.beauty.entity.Page;
import com.beauty.model.Value;
import com.beauty.service.QueueService;
import com.beauty.util.CodeUtil;
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
@RequestMapping("/queue")
public class QueueController {

    @Autowired
    private QueueService queueService;

    @RequestMapping(value = "/load/page", produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<?> queryPage() {
        Map<String, Object> params = new HashMap<>();
        List<?> list = this.queueService.selectPage(params);
        return list;
    }
    /*public Page queryPage(HttpServletRequest request) {
        Page page = new Page();
        page.init(request);
        Map<String, Object> params = new HashMap<String, Object>();
        page.pageToMap(params);
        int count = this.queueService.selectCount(params);
        List<?> list = this.queueService.selectPage(params);
        page.setResult(list, count + "", count + "");
        return page;
    }*/


    @RequestMapping(value = "/add", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Value persist(BeautyQueue entity) {
        this.queueService.insertSelective(entity);
        return new Value(CodeUtil.ADD_SUCCESS);
    }

    @RequestMapping(value = "/update", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Value modify(BeautyQueue entity) {
        this.queueService.updateByPrimaryKeySelective(entity);
        return new Value(CodeUtil.EDIT_SUCCESS);
    }

    @RequestMapping(value = "/remove", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Value delete(Value value) {
        if (!value.getValues().isEmpty()) {
            this.queueService.deleteByPrimaryKeys(value.getValues());
        }
        return new Value(CodeUtil.DELETE_SUCCESS);
    }

    @RequestMapping(value = "/load/id", produces = "application/json; charset=utf-8")
    @ResponseBody
    public BeautyQueue load(@RequestParam("id") Long id) {
        return this.queueService.selectByPrimaryKey(id);
    }


}
