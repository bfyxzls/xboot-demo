package com.lind.xbootdemo.service.impl;

import com.lind.xbootdemo.dao.LogModelDao;
import com.lind.xbootdemo.dao.XbootBaseDao;
import com.lind.xbootdemo.entity.LogModel;
import com.lind.xbootdemo.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    LogModelDao logModelDao;

    @Override
    public void echo(LogModel logModel) {
        log.info("logModel:{}", logModel);
    }

    @Override
    public XbootBaseDao<LogModel, String> getRepository() {
        return logModelDao;
    }
}
