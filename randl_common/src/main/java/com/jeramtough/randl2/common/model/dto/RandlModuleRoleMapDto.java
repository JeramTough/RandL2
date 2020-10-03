package com.jeramtough.randl2.common.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author JeramTough
 * @since 2020-10-02
 */
@ApiModel(value="RandlModuleRoleMap对象", description="")
public class RandlModuleRoleMapDto implements Serializable{

    private static final long serialVersionUID=1L;

    @TableId(value = "fid", type = IdType.AUTO)
    private Long fid;

    private Long moduleId;

    private Long roleId;

    private Integer isAble;


    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Integer getIsAble() {
        return isAble;
    }

    public void setIsAble(Integer isAble) {
        this.isAble = isAble;
    }

    @Override
    public String toString() {
        return "RandlModuleRoleMap{" +
        "fid=" + fid +
        ", moduleId=" + moduleId +
        ", roleId=" + roleId +
        ", isAble=" + isAble +
        "}";
    }
}
