package com.jeramtough.randl2.common.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

/**
 * <pre>
 * Created on 2020/10/4 1:45
 * by @author WeiBoWen
 * </pre>
 */
public class RandlModuleAuthDto {

    @ApiModelProperty(value = "唯一id")
    private Long mid;

    @ApiModelProperty(value = "菜单中文名")
    private String name;

    @ApiModelProperty(value = "菜单描述")
    private String description;

    @ApiModelProperty(value = "菜单或则接口路径")
    private String path;

    @ApiModelProperty(value = "层级")
    private Integer level;

    @ApiModelProperty(value = "排序")
    private Integer moduleOrder;

    @ApiModelProperty(value = "菜单图标 默认为斜杠")
    private String icon;

    @ApiModelProperty(value = "上级菜单Id")
    private Long parentModuleId;

    @ApiModelProperty(value = "模块是否被管理员禁用")
    private Integer isAble;

    @ApiModelProperty(value = "模块是否被授权")
    private Integer isAuth;

    private Long appId;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "英文别名")
    private String alias;

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getModuleOrder() {
        return moduleOrder;
    }

    public void setModuleOrder(Integer moduleOrder) {
        this.moduleOrder = moduleOrder;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getParentModuleId() {
        return parentModuleId;
    }

    public void setParentModuleId(Long parentModuleId) {
        this.parentModuleId = parentModuleId;
    }

    public Integer getIsAble() {
        return isAble;
    }

    public void setIsAble(Integer isAble) {
        this.isAble = isAble;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(Integer isAuth) {
        this.isAuth = isAuth;
    }

    @Override
    public String toString() {
        return "RandlModuleAuthDto{" +
                "mid=" + mid +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", path='" + path + '\'' +
                ", level=" + level +
                ", moduleOrder=" + moduleOrder +
                ", icon='" + icon + '\'' +
                ", parentModuleId=" + parentModuleId +
                ", isAble=" + isAble +
                ", isAuth=" + isAuth +
                ", appId=" + appId +
                ", createTime=" + createTime +
                ", alias='" + alias + '\'' +
                '}';
    }
}
