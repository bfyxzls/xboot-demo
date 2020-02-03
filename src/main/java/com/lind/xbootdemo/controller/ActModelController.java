package com.lind.xbootdemo.controller;

import cn.hutool.core.date.DateTime;
import com.lind.xbootdemo.dao.ActLeaveDao;
import com.lind.xbootdemo.entity.ActLeave;
import com.lind.xbootdemo.entity.ActModel;
import com.lind.xbootdemo.service.ActModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class ActModelController {
    @Autowired
    ActModelService actModelService;

    @Autowired
    ActLeaveDao actLeaveDao;

    @GetMapping("/act/add")
    public String addModel() {
        log.info("add a model");
        actModelService.insert(ActModel.builder()
                .name("test")
                .description("test")
                .modelKey("test")
                .version(1)
                .build());
        return "ok";
    }

    @GetMapping("/act/leave/add")
    public String addLeave() {
        log.info("add a model");
        actLeaveDao.save(ActLeave.builder()
                .title("test")
                .applyTime(DateTime.now())
                .build());
        return "ok";
    }


    @GetMapping("/act/get")
    public List<ActModel> getModel() {
        log.info("get all model");
        return actModelService.getAll();
    }
}
