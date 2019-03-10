package top.ratil.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.ratil.entity.Plan;
import top.ratil.mapper.PlanMapper;
import top.ratil.service.PlanService;

import java.util.List;

/**
 * @program: HappyPlan
 * @description: 计划操作serviceImpl
 * @author: Ratil
 * @create: 2018-08-19 17:48
 **/
@Service
public class PlanServiceImpl implements PlanService {
    @Autowired
    PlanMapper planMapper;

    @Override
    public Plan selectPlanById(Integer planId) {
        return null;
    }

    @Override
    public List<Plan> selectPlanByUserId(Integer userId) {
        return planMapper.selectByUserId(userId);
    }

    @Override
    public boolean insert(Plan plan) {
        return planMapper.insert(plan) > 0;
    }

    @Override
    public boolean insertSelective(Plan plan) {
        return false;
    }

    @Override
    public boolean deletePlanById(Integer planId) {
        return planMapper.deleteByPrimaryKey(planId) > 0;
    }

    @Override
    public boolean updatePlanByIdSelective(Plan plan) {
        return planMapper.updateByPrimaryKeySelective(plan) > 0;
    }

    @Override
    public boolean updatePlanById(Plan plan) {
        return false;
    }
}
