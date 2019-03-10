package top.ratil.controller.front.plan;

import com.sun.corba.se.spi.ior.ObjectKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.ratil.entity.Plan;
import top.ratil.service.PlanService;
import top.ratil.utils.FieldErrorUtil;
import top.ratil.utils.PackageJsonUtil;
import top.ratil.utils.UserSessionUtil;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @program: HappyPlan
 * @description: 计划操作Controller
 * @author: Ratil
 * @create: 2018-08-19 17:36
 **/
@Controller
public class PlanController {
    @Autowired
    private PlanService planService;

    @Autowired
    private PackageJsonUtil<Plan> packageJson;

    private Integer planId;

    @RequestMapping("getAllPlan")
    @ResponseBody
    public Map<String, Object> selectAllPlan(Integer userId) {

        //如果查询的用户id不等于session的用户id
        if (!UserSessionUtil.getUserSession().getUserId().equals(userId)) {
            return packageJson.failStatus(null);
        }
        List<Plan> plans = planService.selectPlanByUserId(userId);
        return packageJson.successStatus(plans);
    }

    @RequestMapping(value = "addPlan", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addPlan(@RequestBody Plan plan) {
        Map<String, Object> map = new HashMap<>();

        System.out.println("添加的计划" + plan);
        //判断计划名字长度是否正确
        int length = plan.getPlanName().length();
        if (plan.getPlanName() == null || length > 15 || length < 1) {
            map.put("planName", "计划名字在1-15个字符之间");
            return packageJson.failStatus(map);
        }

        //判断开始和结束时间是否正确
        Date startTime = plan.getPlanStartTime();
        Date endTime = plan.getPlanEndTime();
        if (startTime == null || endTime == null) {
            if (startTime == null) {
                map.put("planStartTime", "日期为空!!");
            } else {
                map.put("planEndTime", "日期为空!!");
            }
            return packageJson.failStatus(map);
        }
        //判断两个日期前后顺序是否有误
        int days = (int) ((endTime.getTime() - startTime.getTime()) / 1000 / 60 / 60 / 24);
        if (days < 0) {
            map.put("planEndTime", "日期先后顺序有误");
            return packageJson.failStatus(map);
        }
        //得到两个日期相差的天数
        plan.setPlanDuration(days);
        //得到登录的用户id
        plan.setUserId(UserSessionUtil.getUserSession().getUserId());

        boolean flag = planService.insert(plan);
        //添加计划失败
        if (!flag) {
            map.put("error", "添加计划失败!");
            return packageJson.failStatus(map);
        }
        return packageJson.successStatus(null);
    }

    @RequestMapping(value = "editPlan", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editPlan(@RequestBody Plan plan) {
        Map<String, Object> map = new HashMap<>();

        if (this.planId == null || "".equals(this.planId)) {
            return packageJson.failStatus(null);
        }

        //判断计划名字长度是否正确
        int length = plan.getPlanName().length();
        if (plan.getPlanName() != null && length <= 0 || length > 15) {
            map.put("planName", "计划名字在1-15个字符之间");
            return packageJson.failStatus(map);
        }

        //判断开始和结束时间是否正确
        Date startTime = plan.getPlanStartTime();
        Date endTime = plan.getPlanEndTime();
        if ((startTime != null && endTime == null)
                || (startTime == null && endTime != null)) {
            map.put("planEndTime", "请两个日期都输入");
            return packageJson.failStatus(map);
        }

        //如果一个不为空,则两个都不为空,否则两个都为空
        if (startTime != null) {
            int days = (int) ((endTime.getTime() - startTime.getTime()) / 1000 / 60 / 60 / 24);
            if (days <= 0) {
                map.put("planEndTime", "日期先后顺序有误");
                return packageJson.failStatus(map);
            } else {
                //得到两个日期相差的天数
                plan.setPlanDuration(days);
            }
        }
        //设置要编辑计划的id
        plan.setPlanId(this.planId);

        boolean flag = planService.updatePlanByIdSelective(plan);
        if (!flag) {
            map.put("error", "修改计划失败!");
            return packageJson.failStatus(map);
        }
        return packageJson.successStatus(null);
    }

    @RequestMapping(value = "deletePlan", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deletePlan(Integer planId) {
        Map<String, Object> map = new HashMap<>();
        System.out.println("planId为" + planId);
        boolean flag = planService.deletePlanById(planId);
        if(flag) {
            return packageJson.successStatus(null);
        }
        return packageJson.failStatus(null);
    }

    @RequestMapping(value = "setPlanId", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> setPlanId(Integer planId) {
        if (planId == null || "".equals(planId)) {
            return packageJson.failStatus(null);
        }
        this.planId = planId;
        return packageJson.successStatus(null);
    }

    @RequestMapping(value = "getPlanId", method = RequestMethod.POST)
    @ResponseBody
    public boolean getPlanId() {
        return this.planId != null;
    }
}