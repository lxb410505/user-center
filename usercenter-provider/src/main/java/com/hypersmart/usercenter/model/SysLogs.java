package com.hypersmart.usercenter.model;

import com.hypersmart.base.id.genId.Suid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.ToStringBuilder;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

@ApiModel(value = "SysLogs", description = "系统操作日志")
@Table(name = "portal_sys_logs")
public class SysLogs implements Serializable /*extends BaseModel<String>*/ {
    private static final long serialVersionUID = 1L;

    @Id
    @KeySql(genId = Suid.class)
    @Column(name = "ID_")
    @ApiModelProperty("编号")
    protected String id;

    @Column(name = "OPE_NAME_")
    @ApiModelProperty("操作名称")
    protected String opeName;

    @Column(name = "EXECUTION_TIME_")
    @ApiModelProperty("执行时间")
    protected Date executionTime;

    @Column(name = "EXECUTOR_")
    @ApiModelProperty("执行人")
    protected String executor;

    @Column(name = "IP_")
    @ApiModelProperty("ip地址")
    protected String ip;

    @Column(name = "LOG_TYPE_")
    @ApiModelProperty("日志类型")
    protected String logType;

    @Column(name = "MODULE_TYPE_")
    @ApiModelProperty("模块")
    protected String moduleType;

    @Column(name = "REQ_URL_")
    @ApiModelProperty("请求url地址")
    protected String reqUrl;

    @Column(name = "OPE_CONTENT_")
    @ApiModelProperty("操作内容")
    protected String opeContent;

    public SysLogs() {
    }

    public SysLogs(String opeName, Date executionTime, String executor, String ip, String logType,
                   String moduleType, String reqUrl, String opeContent) {
        this.opeName = opeName;
        this.executionTime = executionTime;
        this.executor = executor;
        this.ip = ip;
        this.logType = logType;
        this.moduleType = moduleType;
        this.reqUrl = reqUrl;
        this.opeContent = opeContent;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setOpeName(String opeName) {
        this.opeName = opeName;
    }

    public String getOpeName() {
        return this.opeName;
    }

    public void setExecutionTime(Date executionTime) {
        this.executionTime = executionTime;
    }

    public Date getExecutionTime() {
        return this.executionTime;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public String getExecutor() {
        return this.executor;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return this.ip;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getLogType() {
        return this.logType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public String getModuleType() {
        return this.moduleType;
    }

    public String getReqUrl() {
        return this.reqUrl;
    }

    public void setReqUrl(String reqUrl) {
        this.reqUrl = reqUrl;
    }

    public void setOpeContent(String opeContent) {
        this.opeContent = opeContent;
    }

    public String getOpeContent() {
        return this.opeContent;
    }

    public String toString() {
        return

                new ToStringBuilder(this).append("id", this.id).append("opeName", this.opeName)
                        .append("executionTime", this.executionTime).append("executor", this.executor).append("ip", this.ip)
                        .append("logType", this.logType).append("moduleType", this.moduleType)
                        .append("opeContent", this.opeContent).toString();
    }
}
