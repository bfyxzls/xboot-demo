package com.lind.xbootdemo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 授权给代理商表.
 */
@Data
public class ActApproveAgent extends XbootBaseEntity {

    @ApiModelProperty("代理人ID")
    private String agentId;

    @ApiModelProperty("授权人ID")
    private String authorizerId;

    @ApiModelProperty("是否持续代理，0：持续代理   1：代理一段时间")
    private Integer agentTerm;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "授权开始时间")
    private Date agentStartTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "授权结束时间")
    private Date agentEndTime;

    @ApiModelProperty("代理状态，0：开启  1：关闭")
    private Integer status;
}
