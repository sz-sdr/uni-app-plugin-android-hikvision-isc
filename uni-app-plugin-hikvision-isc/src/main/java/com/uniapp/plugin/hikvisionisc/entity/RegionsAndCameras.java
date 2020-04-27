package com.uniapp.plugin.hikvisionisc.entity;

import java.util.List;

/**
 * 创建人是: zsg 创建时间: 2019/9/12 0012.
 * <p>
 * 区域信息模型
 */
public class RegionsAndCameras {

    private Long id;

    /**
     * string 区域唯一标识码
     */
    private String indexCode;

    /**
     * string 区域名称
     */
    private String name;

    /**
     * string 父区域唯一标识码
     */
    private String parentIndexCode;

    /**
     * string 树编号
     */
    private String treeCode;

    /**
     * string 区域完整路径，含本节点，@进行分割，上级节点在前
     */
    private String regionPath;

    /**
     * boolean 用于标识区域节点是否有权限操作，true：有权限 false：无权限
     */
    private Boolean available;

    /**
     * boolean true:是叶子节点，表示该区域下面未挂区域 false:不是叶子节点，表示该区域下面挂有区域
     */
    private Boolean leaf;

    /**
     * string 级联平台标识，多个级联编号以@分隔，本级区域默认值“0”
     */
    private String cascadeCode;

    /**
     * number	区域标识
     * 0：本级
     * 1：级联
     * 2：混合，下级推送给上级的本级点（杭州本级有滨江，然后下级滨江又把自己推送上来了，滨江是混合区域节点）
     * 入参cascadeFlag与返回值对应：
     * cascadeFlag=0：返回0、1、2
     * cascadeFlag=1：返回0、2 cascadeFlag=2：返回1、2
     */
    private String cascadeType;

    /**
     * number	False	区域类型
     * 0: 国标区域
     * 1: 雪亮工程区域
     * 2: 司法行政区域
     * 9: 自定义区域
     * 10: 历史兼容版本占用普通区域
     * 11: 历史兼容版本占用级联区域
     * 12: 楼栋单元
     */
    private String catalogType;

    /**
     * string 外码(如：国际码)
     */
    private String externalIndexCode;


    /**
     * string 同级区域顺序
     */
    private String sort;

    /**
     * string 创建时间，要求遵守ISO8601标准，如2018-07-26T21:30:08.322+08:00
     */
    private String createTime;

    /**
     * string 创建时间，要求遵守ISO8601标准，如2018-07-26T21:30:08.322+08:00
     */
    private String updateTime;

    private List<RegionsAndCameras> child;

    private List<Cameras> cameras;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndexCode() {
        return indexCode;
    }

    public void setIndexCode(String indexCode) {
        this.indexCode = indexCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentIndexCode() {
        return parentIndexCode;
    }

    public void setParentIndexCode(String parentIndexCode) {
        this.parentIndexCode = parentIndexCode;
    }

    public String getTreeCode() {
        return treeCode;
    }

    public void setTreeCode(String treeCode) {
        this.treeCode = treeCode;
    }

    public String getRegionPath() {
        return regionPath;
    }

    public void setRegionPath(String regionPath) {
        this.regionPath = regionPath;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public String getCascadeCode() {
        return cascadeCode;
    }

    public void setCascadeCode(String cascadeCode) {
        this.cascadeCode = cascadeCode;
    }

    public String getCascadeType() {
        return cascadeType;
    }

    public void setCascadeType(String cascadeType) {
        this.cascadeType = cascadeType;
    }

    public String getCatalogType() {
        return catalogType;
    }

    public void setCatalogType(String catalogType) {
        this.catalogType = catalogType;
    }

    public String getExternalIndexCode() {
        return externalIndexCode;
    }

    public void setExternalIndexCode(String externalIndexCode) {
        this.externalIndexCode = externalIndexCode;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public List<RegionsAndCameras> getChild() {
        return child;
    }

    public void setChild(List<RegionsAndCameras> child) {
        this.child = child;
    }

    public List<Cameras> getCameras() {
        return cameras;
    }

    public void setCameras(List<Cameras> cameras) {
        this.cameras = cameras;
    }
}
