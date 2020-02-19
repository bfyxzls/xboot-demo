package com.lind.xbootdemo.service;

import com.lind.xbootdemo.entity.ActModel;
import org.springframework.data.domain.Page;

/**
 * 模型管理接口
 *
 * @author Exrick
 */
public interface ActModelService extends XbootBaseService<ActModel, String> {

    /**
     * 多条件分页获取
     *
     * @param actModel
     * @return
     */
    Page<ActModel> findByCondition(ActModel actModel, int currentPage, int pageSize);

    void delete(String id);

    void insert(ActModel actModel);

}
