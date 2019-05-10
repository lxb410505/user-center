package com.hypersmart.usercenter.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 【基础信息】分期
 *
 * @author sun
 * @email @sina,cn
 * @date 2019-01-12 10:26:36
 */

@Table(name = "divide")
@ApiModel(value = "Divide", description = "【基础信息】分期")
public class Divide implements Serializable{
private static final long serialVersionUID=1L;

        @Id
            @KeySql(genId = com.hypersmart.base.id.genId.Uid.class)
            @Column(name = "id")
    @ApiModelProperty("主键")
        private Long id;

        @Column(name = "sf_guid")
    @ApiModelProperty("Salesforce GUID")
        private String sfGuid;

        @Column(name = "invoice_terminal_code")
    @ApiModelProperty("【发票】开票点编码")
        private String invoiceTerminalCode;

        @Column(name = "wbs_code")
    @ApiModelProperty("WBS编码")
        private String wbsCode;

        @Column(name = "divide_code")
    @ApiModelProperty("divideCode")
        private String divideCode;

        @Column(name = "divide_name")
    @ApiModelProperty("分期名称（与对应的组织名称保持同步）")
        private String divideName;

        @Column(name = "divide_inner_name")
    @ApiModelProperty("分期别名(内部)")
        private String divideInnerName;

        @Column(name = "detail_address")
    @ApiModelProperty("详细地址")
        private String detailAddress;

        @Column(name = "building_area")
    @ApiModelProperty("建筑面积(㎡)")
        private BigDecimal buildingArea;

        @Column(name = "invoice_amount")
    @ApiModelProperty("开票限额")
        private BigDecimal invoiceAmount;

        @Column(name = "fee_area")
    @ApiModelProperty("收费面积(㎡)")
        private BigDecimal feeArea;

        @Column(name = "manage_area")
    @ApiModelProperty("管理面积(㎡)")
        private BigDecimal manageArea;

        @Column(name = "house_count")
    @ApiModelProperty("户数")
        private Integer houseCount;

        @Column(name = "parking_fixed_count")
    @ApiModelProperty("固定车位数")
        private Integer parkingFixedCount;

        @Column(name = "parking_temporary_count")
    @ApiModelProperty("临时车位数")
        private Integer parkingTemporaryCount;

        @Column(name = "green_rate")
    @ApiModelProperty("绿地率(%)")
        private BigDecimal greenRate;

        @Column(name = "volume_ratio")
    @ApiModelProperty("容积率")
        private BigDecimal volumeRatio;

        @Column(name = "completion_date")
    @ApiModelProperty("竣工日期")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        private Date completionDate;

        @Column(name = "zip_code")
    @ApiModelProperty("邮编")
        private String zipCode;

        @Column(name = "supervisor_unit")
    @ApiModelProperty("工程监理")
        private String supervisorUnit;

        @Column(name = "construction_unit")
    @ApiModelProperty("承建单位")
        private String constructionUnit;

        @Column(name = "design_unit")
    @ApiModelProperty("建筑设计")
        private String designUnit;

        @Column(name = "developers_id")
    @ApiModelProperty("开发商id")
        private String developersId;

        @Column(name = "developer_unit")
    @ApiModelProperty("开发商名称")
        private String developerUnit;

        @Column(name = "owners_committee_state")
    @ApiModelProperty("业委会成立情况（数据字典表）：已成立、未成立")
        private String ownersCommitteeState;

        @Column(name = "lift_count")
    @ApiModelProperty("电梯数量")
        private Integer liftCount;

        @Column(name = "aircondition_count")
    @ApiModelProperty("中央空调数量")
        private Integer airconditionCount;

        @Column(name = "leave_time")
    @ApiModelProperty("撤场日期")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        private Date leaveTime;

        @Column(name = "project_id")
    @ApiModelProperty("项目Id")
        private String projectId;

        @Column(name = "project_name")
    @ApiModelProperty("【冗余】项目名称")
        private String projectName;

        @Column(name = "company_code")
    @ApiModelProperty("SAP公司代码")
        private String companyCode;

        @Column(name = "branch_company_id")
    @ApiModelProperty("分公司Id")
        private String branchCompanyId;

        @Column(name = "org_id")
    @ApiModelProperty("组织Id")
        private String orgId;

        @Column(name = "cell_category")
    @ApiModelProperty("小区类别")
        private String cellCategory;

        @Column(name = "income_mode")
    @ApiModelProperty(" 收入方式")
        private String incomeMode;

        @Column(name = "total_count")
    @ApiModelProperty("总套数")
        private Integer totalCount;

        @Column(name = "total_building")
    @ApiModelProperty("楼栋个数")
        private Integer totalBuilding;

        @Column(name = "afforested_area")
    @ApiModelProperty("绿化面积")
        private BigDecimal afforestedArea;

        @Column(name = "wuguan_house")
    @ApiModelProperty("物管用房")
        private String wuguanHouse;

        @Column(name = "instead_rate")
    @ApiModelProperty("清欠率指标（%）")
        private BigDecimal insteadRate;

        @Column(name = "collection_rate")
    @ApiModelProperty("收缴率指标（%）")
        private BigDecimal collectionRate;

        @Column(name = "receiving_date")
    @ApiModelProperty("接盘日期")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        private Date receivingDate;

        @Column(name = "first_hand_date")
    @ApiModelProperty("首次交房")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        private Date firstHandDate;

        @Column(name = "first_meet_date")
    @ApiModelProperty("首次接房")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        private Date firstMeetDate;

        @Column(name = "committee_filing_date")
    @ApiModelProperty("业委会 备案日期")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        private Date committeeFilingDate;

        @Column(name = "street_address")
    @ApiModelProperty("街道地址")
        private String streetAddress;

        @Column(name = "detailed_address")
    @ApiModelProperty("详细地址")
        private String detailedAddress;

        @Column(name = "taxpayer_identify_number")
    @ApiModelProperty("纳税人识别号")
        private String taxpayerIdentifyNumber;

        @Column(name = "company_name")
    @ApiModelProperty("公司名称")
        private String companyName;

        @Column(name = "telphone")
    @ApiModelProperty(" 电话")
        private String telphone;

        @Column(name = "bank_account_name")
    @ApiModelProperty("银行户名")
        private String bankAccountName;

        @Column(name = "bank_account")
    @ApiModelProperty("银行账号")
        private String bankAccount;

        @Column(name = "bank")
    @ApiModelProperty("开户 行")
        private String bank;

        @Column(name = "service_center")
    @ApiModelProperty("服务中心")
        private String serviceCenter;

        @Column(name = "service_bank")
    @ApiModelProperty("服务中心开户银行")
        private String serviceBank;

        @Column(name = "service_bank_account")
    @ApiModelProperty("服务中心开户银行账号")
        private String serviceBankAccount;

        @Column(name = "service_telphone")
    @ApiModelProperty("服务中心电话")
        private String serviceTelphone;

        @Column(name = "faxes")
    @ApiModelProperty("传真")
        private String faxes;

        @Column(name = "remark")
    @ApiModelProperty("备 注")
        private String remark;

        @Column(name = "enabled_computer")
    @ApiModelProperty("计算启用状态 0 禁用 1启用")
        private Integer enabledComputer;

        @Column(name = "corp_id")
    @ApiModelProperty("企业Id")
        private String corpId;

        @Column(name = "batch_id")
    @ApiModelProperty("批量导入批次号")
        private String batchId;

        @Column(name = "creation_date")
    @ApiModelProperty("首次创建时间")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        private Date creationDate;

        @Column(name = "created_by")
    @ApiModelProperty("创建人")
        private String createdBy;

        @Column(name = "updation_date")
    @ApiModelProperty("上次修改时间")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        private Date updationDate;

        @Column(name = "updated_by")
    @ApiModelProperty("更新人")
        private String updatedBy;

        @Column(name = "row_version")
    @ApiModelProperty("这条记录的版本号，每次更新操作都会使其发生变换")
        private Integer rowVersion;

        @Column(name = "is_deleted")
    @ApiModelProperty("删除标记")
        private Integer isDeleted;

        @Column(name = "enabled_flag")
    @ApiModelProperty("启用标记")
        private Integer enabledFlag;

        @Column(name = "address")
    @ApiModelProperty("地址")
        private String address;


        public void setId(Long id) {
            this.id = id;
        }

        public Long getId() {
            return this.id;
        }
        public void setSfGuid(String sfGuid) {
            this.sfGuid = sfGuid;
        }

        public String getSfGuid() {
            return this.sfGuid;
        }
        public void setInvoiceTerminalCode(String invoiceTerminalCode) {
            this.invoiceTerminalCode = invoiceTerminalCode;
        }

        public String getInvoiceTerminalCode() {
            return this.invoiceTerminalCode;
        }
        public void setWbsCode(String wbsCode) {
            this.wbsCode = wbsCode;
        }

        public String getWbsCode() {
            return this.wbsCode;
        }
        public void setDivideCode(String divideCode) {
            this.divideCode = divideCode;
        }

        public String getDivideCode() {
            return this.divideCode;
        }
        public void setDivideName(String divideName) {
            this.divideName = divideName;
        }

        public String getDivideName() {
            return this.divideName;
        }
        public void setDivideInnerName(String divideInnerName) {
            this.divideInnerName = divideInnerName;
        }

        public String getDivideInnerName() {
            return this.divideInnerName;
        }
        public void setDetailAddress(String detailAddress) {
            this.detailAddress = detailAddress;
        }

        public String getDetailAddress() {
            return this.detailAddress;
        }
        public void setBuildingArea(BigDecimal buildingArea) {
            this.buildingArea = buildingArea;
        }

        public BigDecimal getBuildingArea() {
            return this.buildingArea;
        }
        public void setInvoiceAmount(BigDecimal invoiceAmount) {
            this.invoiceAmount = invoiceAmount;
        }

        public BigDecimal getInvoiceAmount() {
            return this.invoiceAmount;
        }
        public void setFeeArea(BigDecimal feeArea) {
            this.feeArea = feeArea;
        }

        public BigDecimal getFeeArea() {
            return this.feeArea;
        }
        public void setManageArea(BigDecimal manageArea) {
            this.manageArea = manageArea;
        }

        public BigDecimal getManageArea() {
            return this.manageArea;
        }
        public void setHouseCount(Integer houseCount) {
            this.houseCount = houseCount;
        }

        public Integer getHouseCount() {
            return this.houseCount;
        }
        public void setParkingFixedCount(Integer parkingFixedCount) {
            this.parkingFixedCount = parkingFixedCount;
        }

        public Integer getParkingFixedCount() {
            return this.parkingFixedCount;
        }
        public void setParkingTemporaryCount(Integer parkingTemporaryCount) {
            this.parkingTemporaryCount = parkingTemporaryCount;
        }

        public Integer getParkingTemporaryCount() {
            return this.parkingTemporaryCount;
        }
        public void setGreenRate(BigDecimal greenRate) {
            this.greenRate = greenRate;
        }

        public BigDecimal getGreenRate() {
            return this.greenRate;
        }
        public void setVolumeRatio(BigDecimal volumeRatio) {
            this.volumeRatio = volumeRatio;
        }

        public BigDecimal getVolumeRatio() {
            return this.volumeRatio;
        }
        public void setCompletionDate(Date completionDate) {
            this.completionDate = completionDate;
        }

        public Date getCompletionDate() {
            return this.completionDate;
        }
        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }

        public String getZipCode() {
            return this.zipCode;
        }
        public void setSupervisorUnit(String supervisorUnit) {
            this.supervisorUnit = supervisorUnit;
        }

        public String getSupervisorUnit() {
            return this.supervisorUnit;
        }
        public void setConstructionUnit(String constructionUnit) {
            this.constructionUnit = constructionUnit;
        }

        public String getConstructionUnit() {
            return this.constructionUnit;
        }
        public void setDesignUnit(String designUnit) {
            this.designUnit = designUnit;
        }

        public String getDesignUnit() {
            return this.designUnit;
        }
        public void setDevelopersId(String developersId) {
            this.developersId = developersId;
        }

        public String getDevelopersId() {
            return this.developersId;
        }
        public void setDeveloperUnit(String developerUnit) {
            this.developerUnit = developerUnit;
        }

        public String getDeveloperUnit() {
            return this.developerUnit;
        }
        public void setOwnersCommitteeState(String ownersCommitteeState) {
            this.ownersCommitteeState = ownersCommitteeState;
        }

        public String getOwnersCommitteeState() {
            return this.ownersCommitteeState;
        }
        public void setLiftCount(Integer liftCount) {
            this.liftCount = liftCount;
        }

        public Integer getLiftCount() {
            return this.liftCount;
        }
        public void setAirconditionCount(Integer airconditionCount) {
            this.airconditionCount = airconditionCount;
        }

        public Integer getAirconditionCount() {
            return this.airconditionCount;
        }
        public void setLeaveTime(Date leaveTime) {
            this.leaveTime = leaveTime;
        }

        public Date getLeaveTime() {
            return this.leaveTime;
        }
        public void setProjectId(String projectId) {
            this.projectId = projectId;
        }

        public String getProjectId() {
            return this.projectId;
        }
        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public String getProjectName() {
            return this.projectName;
        }
        public void setCompanyCode(String companyCode) {
            this.companyCode = companyCode;
        }

        public String getCompanyCode() {
            return this.companyCode;
        }
        public void setBranchCompanyId(String branchCompanyId) {
            this.branchCompanyId = branchCompanyId;
        }

        public String getBranchCompanyId() {
            return this.branchCompanyId;
        }
        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public String getOrgId() {
            return this.orgId;
        }
        public void setCellCategory(String cellCategory) {
            this.cellCategory = cellCategory;
        }

        public String getCellCategory() {
            return this.cellCategory;
        }
        public void setIncomeMode(String incomeMode) {
            this.incomeMode = incomeMode;
        }

        public String getIncomeMode() {
            return this.incomeMode;
        }
        public void setTotalCount(Integer totalCount) {
            this.totalCount = totalCount;
        }

        public Integer getTotalCount() {
            return this.totalCount;
        }
        public void setTotalBuilding(Integer totalBuilding) {
            this.totalBuilding = totalBuilding;
        }

        public Integer getTotalBuilding() {
            return this.totalBuilding;
        }
        public void setAfforestedArea(BigDecimal afforestedArea) {
            this.afforestedArea = afforestedArea;
        }

        public BigDecimal getAfforestedArea() {
            return this.afforestedArea;
        }
        public void setWuguanHouse(String wuguanHouse) {
            this.wuguanHouse = wuguanHouse;
        }

        public String getWuguanHouse() {
            return this.wuguanHouse;
        }
        public void setInsteadRate(BigDecimal insteadRate) {
            this.insteadRate = insteadRate;
        }

        public BigDecimal getInsteadRate() {
            return this.insteadRate;
        }
        public void setCollectionRate(BigDecimal collectionRate) {
            this.collectionRate = collectionRate;
        }

        public BigDecimal getCollectionRate() {
            return this.collectionRate;
        }
        public void setReceivingDate(Date receivingDate) {
            this.receivingDate = receivingDate;
        }

        public Date getReceivingDate() {
            return this.receivingDate;
        }
        public void setFirstHandDate(Date firstHandDate) {
            this.firstHandDate = firstHandDate;
        }

        public Date getFirstHandDate() {
            return this.firstHandDate;
        }
        public void setFirstMeetDate(Date firstMeetDate) {
            this.firstMeetDate = firstMeetDate;
        }

        public Date getFirstMeetDate() {
            return this.firstMeetDate;
        }
        public void setCommitteeFilingDate(Date committeeFilingDate) {
            this.committeeFilingDate = committeeFilingDate;
        }

        public Date getCommitteeFilingDate() {
            return this.committeeFilingDate;
        }
        public void setStreetAddress(String streetAddress) {
            this.streetAddress = streetAddress;
        }

        public String getStreetAddress() {
            return this.streetAddress;
        }
        public void setDetailedAddress(String detailedAddress) {
            this.detailedAddress = detailedAddress;
        }

        public String getDetailedAddress() {
            return this.detailedAddress;
        }
        public void setTaxpayerIdentifyNumber(String taxpayerIdentifyNumber) {
            this.taxpayerIdentifyNumber = taxpayerIdentifyNumber;
        }

        public String getTaxpayerIdentifyNumber() {
            return this.taxpayerIdentifyNumber;
        }
        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getCompanyName() {
            return this.companyName;
        }
        public void setTelphone(String telphone) {
            this.telphone = telphone;
        }

        public String getTelphone() {
            return this.telphone;
        }
        public void setBankAccountName(String bankAccountName) {
            this.bankAccountName = bankAccountName;
        }

        public String getBankAccountName() {
            return this.bankAccountName;
        }
        public void setBankAccount(String bankAccount) {
            this.bankAccount = bankAccount;
        }

        public String getBankAccount() {
            return this.bankAccount;
        }
        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getBank() {
            return this.bank;
        }
        public void setServiceCenter(String serviceCenter) {
            this.serviceCenter = serviceCenter;
        }

        public String getServiceCenter() {
            return this.serviceCenter;
        }
        public void setServiceBank(String serviceBank) {
            this.serviceBank = serviceBank;
        }

        public String getServiceBank() {
            return this.serviceBank;
        }
        public void setServiceBankAccount(String serviceBankAccount) {
            this.serviceBankAccount = serviceBankAccount;
        }

        public String getServiceBankAccount() {
            return this.serviceBankAccount;
        }
        public void setServiceTelphone(String serviceTelphone) {
            this.serviceTelphone = serviceTelphone;
        }

        public String getServiceTelphone() {
            return this.serviceTelphone;
        }
        public void setFaxes(String faxes) {
            this.faxes = faxes;
        }

        public String getFaxes() {
            return this.faxes;
        }
        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getRemark() {
            return this.remark;
        }
        public void setEnabledComputer(Integer enabledComputer) {
            this.enabledComputer = enabledComputer;
        }

        public Integer getEnabledComputer() {
            return this.enabledComputer;
        }
        public void setCorpId(String corpId) {
            this.corpId = corpId;
        }

        public String getCorpId() {
            return this.corpId;
        }
        public void setBatchId(String batchId) {
            this.batchId = batchId;
        }

        public String getBatchId() {
            return this.batchId;
        }
        public void setCreationDate(Date creationDate) {
            this.creationDate = creationDate;
        }

        public Date getCreationDate() {
            return this.creationDate;
        }
        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedBy() {
            return this.createdBy;
        }
        public void setUpdationDate(Date updationDate) {
            this.updationDate = updationDate;
        }

        public Date getUpdationDate() {
            return this.updationDate;
        }
        public void setUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
        }

        public String getUpdatedBy() {
            return this.updatedBy;
        }
        public void setRowVersion(Integer rowVersion) {
            this.rowVersion = rowVersion;
        }

        public Integer getRowVersion() {
            return this.rowVersion;
        }
        public void setIsDeleted(Integer isDeleted) {
            this.isDeleted = isDeleted;
        }

        public Integer getIsDeleted() {
            return this.isDeleted;
        }
        public void setEnabledFlag(Integer enabledFlag) {
            this.enabledFlag = enabledFlag;
        }

        public Integer getEnabledFlag() {
            return this.enabledFlag;
        }
        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddress() {
            return this.address;
        }
    public String toString(){
        return
        new ToStringBuilder(this)
                .append("id",this.id)
                .append("sfGuid",this.sfGuid)
                .append("invoiceTerminalCode",this.invoiceTerminalCode)
                .append("wbsCode",this.wbsCode)
                .append("divideCode",this.divideCode)
                .append("divideName",this.divideName)
                .append("divideInnerName",this.divideInnerName)
                .append("detailAddress",this.detailAddress)
                .append("buildingArea",this.buildingArea)
                .append("invoiceAmount",this.invoiceAmount)
                .append("feeArea",this.feeArea)
                .append("manageArea",this.manageArea)
                .append("houseCount",this.houseCount)
                .append("parkingFixedCount",this.parkingFixedCount)
                .append("parkingTemporaryCount",this.parkingTemporaryCount)
                .append("greenRate",this.greenRate)
                .append("volumeRatio",this.volumeRatio)
                .append("completionDate",this.completionDate)
                .append("zipCode",this.zipCode)
                .append("supervisorUnit",this.supervisorUnit)
                .append("constructionUnit",this.constructionUnit)
                .append("designUnit",this.designUnit)
                .append("developersId",this.developersId)
                .append("developerUnit",this.developerUnit)
                .append("ownersCommitteeState",this.ownersCommitteeState)
                .append("liftCount",this.liftCount)
                .append("airconditionCount",this.airconditionCount)
                .append("leaveTime",this.leaveTime)
                .append("projectId",this.projectId)
                .append("projectName",this.projectName)
                .append("companyCode",this.companyCode)
                .append("branchCompanyId",this.branchCompanyId)
                .append("orgId",this.orgId)
                .append("cellCategory",this.cellCategory)
                .append("incomeMode",this.incomeMode)
                .append("totalCount",this.totalCount)
                .append("totalBuilding",this.totalBuilding)
                .append("afforestedArea",this.afforestedArea)
                .append("wuguanHouse",this.wuguanHouse)
                .append("insteadRate",this.insteadRate)
                .append("collectionRate",this.collectionRate)
                .append("receivingDate",this.receivingDate)
                .append("firstHandDate",this.firstHandDate)
                .append("firstMeetDate",this.firstMeetDate)
                .append("committeeFilingDate",this.committeeFilingDate)
                .append("streetAddress",this.streetAddress)
                .append("detailedAddress",this.detailedAddress)
                .append("taxpayerIdentifyNumber",this.taxpayerIdentifyNumber)
                .append("companyName",this.companyName)
                .append("telphone",this.telphone)
                .append("bankAccountName",this.bankAccountName)
                .append("bankAccount",this.bankAccount)
                .append("bank",this.bank)
                .append("serviceCenter",this.serviceCenter)
                .append("serviceBank",this.serviceBank)
                .append("serviceBankAccount",this.serviceBankAccount)
                .append("serviceTelphone",this.serviceTelphone)
                .append("faxes",this.faxes)
                .append("remark",this.remark)
                .append("enabledComputer",this.enabledComputer)
                .append("corpId",this.corpId)
                .append("batchId",this.batchId)
                .append("creationDate",this.creationDate)
                .append("createdBy",this.createdBy)
                .append("updationDate",this.updationDate)
                .append("updatedBy",this.updatedBy)
                .append("rowVersion",this.rowVersion)
                .append("isDeleted",this.isDeleted)
                .append("enabledFlag",this.enabledFlag)
                .append("address",this.address)
            .toString();
    }
}
