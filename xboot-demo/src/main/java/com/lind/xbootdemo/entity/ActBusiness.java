package com.lind.xbootdemo.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 业务申请表.
 */
@Data
@Entity
@Table(name = "t_act_business")
@TableName("t_act_business")
@ApiModel(value = "业务申请")
public class ActBusiness extends XbootBaseEntity {

    @ApiModelProperty(value = "申请标题")
    private String title;

    @ApiModelProperty(value = "创建用户id")
    private String userId;

    @ApiModelProperty(value = "关联表id")
    private String tableId;

    @ApiModelProperty(value = "流程定义id")
    private String procDefId;

    @ApiModelProperty(value = "流程实例id")
    private String procInstId;

    @ApiModelProperty(value = "状态 0草稿默认 1处理中 2结束")
    private Integer status = 0;

    @ApiModelProperty(value = "结果状态 0未提交默认 1处理中 2通过 3驳回 4退回")
    private Integer result = 0;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "提交申请时间")
    private Date applyTime;


    @ApiModelProperty(value = "流程类型： 1. 合同流程   2. 制度流程")
    private String businessType = "1";


    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "分配用户ids")
    private String[] assignees;

    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "分配用户id")
    private String assignee;

    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "所属流程名")
    private String processName;

    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "前端路由名")
    private String routeName;

    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "任务优先级 默认0")
    private Integer priority = 0;

    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "当前任务")
    private String currTaskName;

    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "当前任务ID")
    private String taskId;

    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "business表中id, 用于我的送审再次编辑时,删除原数据")
    private String businessId;


    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "审批类型： 1. 为废止  2. 撤回")
    private String approveType;

    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "制度编号")
    private String institutionNumber;


    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "性质")
    private String institutionNature;


    @ApiModelProperty("回退人")
    private String retreatId;


    @ApiModelProperty(value = "回退之后，下次提交到哪一个节点，空表示按照流程，不空则表示按照指定节点走")
    private String backFlow;


}
