package com.lind.xbootdemo.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Exrickx
 * 前后端交互数据标准
 */
@Data
@AllArgsConstructor
public class Result<T> implements Serializable{

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final long serialVersionUID = 1L;

    public Result() {
    }

    /**
     * 成功标志
     */
    private boolean success;

    /**
     * 失败消息
     */
    private String message;

    /**
     * 返回代码
     */
    private Integer code;

    /**
     * 时间戳
     */
    private long timestamp = System.currentTimeMillis();

    /**
     * 结果对象
     */
    private T result;

    public String toJson() {
        try {
            return MAPPER.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }

    public Result(boolean success) {
        this.success = success;
    }

    /**
     * @Author 田培融
     * @Description  快速返回成功响应
     * @Date 13:19 2019/5/29
     * @Param []
     * @return com.bdyh.common.common.vo.Result
     **/
    public static Result success(){
        Result result = new Result();
        result.setCode(200);
        result.setSuccess(true);
        result.setMessage("成功");
        return result;
    }

    /**
     * @Author 田培融
     * @Description 返回状态码和信息
     * @Date 13:52 2019/5/30
     * @Param [code, msg]
     * @return com.bdyh.common.common.vo.Result
     **/
    public static Result success(Integer code, String msg){
        Result result = new Result(true);
        result.setCode(code);
        result.setMessage(msg);
        return result;
    }
}
