package top.ratil.entity;

import javax.validation.constraints.Future;
import javax.validation.constraints.Size;
import java.util.Date;

public class Plan {

    private Integer planId;

    private String planName;

    private Date planStartTime;

    private Date planEndTime;

    private Integer planDuration;

    private Integer userId;

    public Plan(Integer planId, String planName, Date planStartTime, Date planEndTime, Integer planDuration, Integer userId) {
        this.planId = planId;
        this.planName = planName;
        this.planStartTime = planStartTime;
        this.planEndTime = planEndTime;
        this.planDuration = planDuration;
        this.userId = userId;
    }

    public Plan() {
        super();
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName == null ? null : planName.trim();
    }

    public Date getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
    }

    public Date getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(Date planEndTime) {
        this.planEndTime = planEndTime;
    }

    public Integer getPlanDuration() {
        return planDuration;
    }

    public void setPlanDuration(Integer planDuration) {
        this.planDuration = planDuration;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "planId=" + planId +
                ", planName='" + planName + '\'' +
                ", planStartTime=" + planStartTime +
                ", planEndTime=" + planEndTime +
                ", planDuration=" + planDuration +
                ", userId=" + userId +
                '}';
    }
}