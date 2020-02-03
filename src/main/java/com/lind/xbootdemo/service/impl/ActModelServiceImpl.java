package com.lind.xbootdemo.service.impl;

import com.lind.xbootdemo.dao.ActModelDao;
import com.lind.xbootdemo.dao.XbootBaseDao;
import com.lind.xbootdemo.entity.ActModel;
import com.lind.xbootdemo.service.ActModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * 模型管理接口实现
 *
 * @author Exrick
 */
@Slf4j
@Service
@Transactional
public class ActModelServiceImpl implements ActModelService {

    @Autowired
    private ActModelDao actModelDao;

    @Override
    public Page<ActModel> findByCondition(ActModel actModel, int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Specification<ActModel> specification = (Specification<ActModel>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!actModel.getName().equals(null)) {
                Predicate p2 = criteriaBuilder.equal(root.get("name"), actModel.getName());
                list.add(p2);
            }
            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        };
        return actModelDao.findAll(specification, pageable);
    }

    @Override
    public XbootBaseDao<ActModel, String> getRepository() {
        return actModelDao;
    }

    @Override
    public void delete(String id) {
        actModelDao.deleteById(id);
    }

    @Override
    public void insert(ActModel actModel) {
        actModelDao.save(actModel);
    }

}
