package com.lind.xbootdemo.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
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
@Table(name = "t_act_leave")
@TableName("t_act_leave")
@ApiModel(value = "请假表")
@Builder(toBuilder = true)
public class ActLeave extends XbootBaseEntity {

    @ApiModelProperty(value = "申请标题")
    private String title;

    @ApiModelProperty(value = "创建用户id")
    private String userId;

    @ApiModelProperty(value = "结果状态 0未提交默认 1处理中 2通过 3驳回 4退回")
    private Integer result = 0;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "提交申请时间")
    private Date applyTime;

    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "性质")
    private String institutionNature;
}
