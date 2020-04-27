package com.uniapp.plugin.hikvisionisc.entity;


/**
 * 创建人是: zsg 创建时间: 2019/9/16 0016.
 * <p>
 * 监控点模型
 */
public class Cameras {

    private Long id;

    /**
     * string 海拔
     */
    private String altitude;

    /**
     * string 监控点唯一标识
     */
    private String cameraIndexCode;

    /**
     * string 监控点名称
     */
    private String cameraName;

    /**
     * number 监控点类型
     * 监控点类型码	监控点类型名称	备注
     * 0	枪机
     * 1	半球
     * 2	快球
     * 3	云台枪机
     */
    private Integer cameraType;

    /**
     * string 监控点类型说明
     */
    private String cameraTypeName;

    /**
     * string 能力集
     */
    private String capabilitySet;

    /**
     * string 能力集说明
     */
    private String capabilitySetName;

    /**
     * string 智能分析能力集，扩展字段，暂不使用
     */
    private String intelligentSet;

    /**
     * string 智能分析能力集说明，扩展字段，暂不使用
     */
    private String intelligentSetName;

    /**
     * string 通道编号
     */
    private String channelNo;

    /**
     * string 视频通道类型
     * 编码设备通道类型码	编码设备通道类型名称	备注
     * analog	模拟通道
     * digital	数字通道
     * mirror	镜像通道
     * record	录播通道
     * zero	零通道
     */
    private String channelType;

    /**
     * string 通道类型说明
     */
    private String channelTypeName;

    /**
     * string 创建时间，采用ISO8601标准，如2018-07-26T21:30:08+08:00 表示北京时间2018年7月26日21时30分08秒
     */
    private String createTime;

    /**
     * string 所属编码设备唯一标识
     */
    private String encodeDevIndexCode;

    /**
     * string 所属设备类型，扩展字段，暂不使用
     */
    private String encodeDevResourceType;

    /**
     * string 所属设备类型说明，扩展字段，暂不使用
     */
    private String encodeDevResourceTypeName;

    /**
     * string 监控点国标编号，即外码编号externalIndexCode
     */
    private String gbIndexCode;

    /**
     * string 安装位置：
     * communityPerimeter：小区周界,communityEntrance：小区出入口,fireChannel：消防通道,andscapePool：景观池,outsideBuilding：
     * 住宅楼外,parkEntrance：停车场（库）出入口,parkArea：停车场区,equipmentRoom：设备房（机房、配电房、泵房）,
     * monitorCenter：监控中心,stopArea：禁停区,vault：金库
     */
    private String installLocation;

    /**
     * string 键盘控制码
     */
    private String keyBoardCode;

    /**
     * string 纬度
     */
    private String latitude;

    /**
     * string 经度
     */
    private String longitude;

    /**
     * string 录像存储位置
     */
    private String recordLocation;

    /**
     * string 录像存储位置说明
     */
    private String recordLocationName;

    /**
     * string 所属区域唯一标识
     */
    private String regionIndexCode;

    /**
     * string 在线状态（0-未知，1-在线，2-离线）
     */
    private String status;

    /**
     * string 状态说明
     */
    private String statusName;

    /**
     * string 传输协议
     * 传输协议类型名称	传输协议类型编码
     * UDP	0
     * TCP	1
     */
    private String transType;

    /**
     * string 传输协议类型说明
     */
    private String transTypeName;

    /**
     * string  编码设备接入协议
     */
    private String treatyType;

    /**
     * string 接入协议类型说明
     */
    private String treatyTypeName;

    /**
     * string 更新时间
     * 采用ISO8601标准，如2018-07-26T21:30:08+08:00
     * 表示北京时间2017年7月26日21时30分08秒
     */
    private String updateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getCameraIndexCode() {
        return cameraIndexCode;
    }

    public void setCameraIndexCode(String cameraIndexCode) {
        this.cameraIndexCode = cameraIndexCode;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    public Integer getCameraType() {
        return cameraType;
    }

    public void setCameraType(Integer cameraType) {
        this.cameraType = cameraType;
    }

    public String getCameraTypeName() {
        return cameraTypeName;
    }

    public void setCameraTypeName(String cameraTypeName) {
        this.cameraTypeName = cameraTypeName;
    }

    public String getCapabilitySet() {
        return capabilitySet;
    }

    public void setCapabilitySet(String capabilitySet) {
        this.capabilitySet = capabilitySet;
    }

    public String getCapabilitySetName() {
        return capabilitySetName;
    }

    public void setCapabilitySetName(String capabilitySetName) {
        this.capabilitySetName = capabilitySetName;
    }

    public String getIntelligentSet() {
        return intelligentSet;
    }

    public void setIntelligentSet(String intelligentSet) {
        this.intelligentSet = intelligentSet;
    }

    public String getIntelligentSetName() {
        return intelligentSetName;
    }

    public void setIntelligentSetName(String intelligentSetName) {
        this.intelligentSetName = intelligentSetName;
    }

    public String getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getChannelTypeName() {
        return channelTypeName;
    }

    public void setChannelTypeName(String channelTypeName) {
        this.channelTypeName = channelTypeName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEncodeDevIndexCode() {
        return encodeDevIndexCode;
    }

    public void setEncodeDevIndexCode(String encodeDevIndexCode) {
        this.encodeDevIndexCode = encodeDevIndexCode;
    }

    public String getEncodeDevResourceType() {
        return encodeDevResourceType;
    }

    public void setEncodeDevResourceType(String encodeDevResourceType) {
        this.encodeDevResourceType = encodeDevResourceType;
    }

    public String getEncodeDevResourceTypeName() {
        return encodeDevResourceTypeName;
    }

    public void setEncodeDevResourceTypeName(String encodeDevResourceTypeName) {
        this.encodeDevResourceTypeName = encodeDevResourceTypeName;
    }

    public String getGbIndexCode() {
        return gbIndexCode;
    }

    public void setGbIndexCode(String gbIndexCode) {
        this.gbIndexCode = gbIndexCode;
    }

    public String getInstallLocation() {
        return installLocation;
    }

    public void setInstallLocation(String installLocation) {
        this.installLocation = installLocation;
    }

    public String getKeyBoardCode() {
        return keyBoardCode;
    }

    public void setKeyBoardCode(String keyBoardCode) {
        this.keyBoardCode = keyBoardCode;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getRecordLocation() {
        return recordLocation;
    }

    public void setRecordLocation(String recordLocation) {
        this.recordLocation = recordLocation;
    }

    public String getRecordLocationName() {
        return recordLocationName;
    }

    public void setRecordLocationName(String recordLocationName) {
        this.recordLocationName = recordLocationName;
    }

    public String getRegionIndexCode() {
        return regionIndexCode;
    }

    public void setRegionIndexCode(String regionIndexCode) {
        this.regionIndexCode = regionIndexCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getTransTypeName() {
        return transTypeName;
    }

    public void setTransTypeName(String transTypeName) {
        this.transTypeName = transTypeName;
    }

    public String getTreatyType() {
        return treatyType;
    }

    public void setTreatyType(String treatyType) {
        this.treatyType = treatyType;
    }

    public String getTreatyTypeName() {
        return treatyTypeName;
    }

    public void setTreatyTypeName(String treatyTypeName) {
        this.treatyTypeName = treatyTypeName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
