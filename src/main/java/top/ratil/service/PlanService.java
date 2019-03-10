package top.ratil.service;

import top.ratil.entity.Plan;

import java.util.List;

public interface PlanService {

    /**
     * 通过planId查询计划
     * @param planId
     * @return
     */
    Plan selectPlanById(Integer planId);

    /**
     * 通过userIdchaxun计划
     * @param userId
     * @return
     */
    List<Plan> selectPlanByUserId(Integer userId);

    /**
     * 添加计划计划
     * @param plan
     * @return
     */
    boolean insert(Plan plan);

    /**
     * 添加计划
     * 如果计划存在则覆盖
     * @param plan
     * @return
     */
    boolean insertSelective(Plan plan);

    /**
     * 删除计划
     * @param planId
     * @return
     */
    boolean deletePlanById(Integer planId);

    /**
     * 修改计划
     * @param plan
     * @return
     */
    boolean updatePlanByIdSelective(Plan plan);

    /**
     * 修改计划
     * @param plan
     * @return
     */
    boolean updatePlanById(Plan plan);
}
