package com.lind.xbootdemo.service;

import com.lind.xbootdemo.entity.LogModel;

/**
 * 日志业务.
 */
public interface LogService extends XbootBaseService<LogModel, String> {
    void echo(LogModel logModel);
}
