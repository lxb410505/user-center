package com.hypersmart.usercenter.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.pagehelper.PageHelper;
import com.hypersmart.base.feign.PortalFeignService;
import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.*;
import com.hypersmart.base.util.BeanUtils;
import com.hypersmart.base.util.ContextUtils;
import com.hypersmart.base.util.UniqueIdUtil;
import com.hypersmart.framework.service.GenericService;

import com.hypersmart.framework.utils.DateUtils;
import com.hypersmart.mdm.feign.PortalFeginClient;
import com.hypersmart.uc.api.impl.util.ContextUtil;
import com.hypersmart.usercenter.bo.UserHouseRefBO;
import com.hypersmart.usercenter.constant.OwnerStageEnum;
import com.hypersmart.usercenter.dto.ClientRelationDTO;
import com.hypersmart.usercenter.dto.HouseExcelInfoDTO;
import com.hypersmart.usercenter.mapper.HouseMapper;
import com.hypersmart.usercenter.model.House;
import com.hypersmart.usercenter.model.UserHouseRef;
import com.hypersmart.usercenter.service.HouseService;
import com.hypersmart.usercenter.util.ExportExcelUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.*;

/**
 * 【基础信息】房产
 *
 * @author godL
 * @email 121935403@qq.com
 * @date 2019-08-15 17:33:45
 */
@Service("houseServiceImpl")

public class HouseServiceImpl extends GenericService<String, House> implements HouseService {

    public HouseServiceImpl(HouseMapper mapper) {
        super(mapper);
    }

    @Resource
    HouseMapper houseMapper ;
    @Autowired
    private PortalFeginClient portalFeginClient;

    @Override
    public  PageList<Map<String, Object>> list(QueryFilter queryFilter) {
        //处理业主类型查询参数
        addOwnerStageFilter(queryFilter);
        //分页
        PageBean pageBean = queryFilter.getPageBean();
        if (BeanUtils.isEmpty(pageBean)) {
            PageHelper.startPage(1, Integer.MAX_VALUE, false);
        } else {
            PageHelper.startPage(pageBean.getPage().intValue(), pageBean.getPageSize().intValue(),
                    pageBean.showTotal());
        }

        List<Map<String, Object>> query=houseMapper.list(queryFilter.getParams());
        return new PageList<>(query);
    }
    //查询条件 业主类型转换
    private void addOwnerStageFilter(QueryFilter queryFilter){
        List<QueryField> querys = queryFilter.getQuerys();
        for (QueryField query : querys) {
            //业主类型
            if("ownerStage".equals(query.getProperty())){
                Object value = query.getValue();
                if(value!=null){
                    String ownerStage=String.valueOf(value);
                    LocalDate now = LocalDate.now();
                    if(OwnerStageEnum.STORMING1.getKey().equals(ownerStage)){
                        //磨合期1 半年以内
                        LocalDate date = now.plusMonths(-6);
                        queryFilter.addFilter("delivery_date", now, QueryOP.LESS_EQUAL, FieldRelation.AND);
                        queryFilter.addFilter("delivery_date", date, QueryOP.GREAT, FieldRelation.AND);
                    }else if(OwnerStageEnum.STORMING2.getKey().equals(ownerStage)){
                        //磨合期2 半年到一年
                        LocalDate date = now.plusMonths(-6);
                        queryFilter.addFilter("delivery_date", date, QueryOP.LESS_EQUAL, FieldRelation.AND);
                        LocalDate lastYear1 = now.plusYears(-1);
                        queryFilter.addFilter("delivery_date", lastYear1, QueryOP.GREAT, FieldRelation.AND);
                    }else if(OwnerStageEnum.STABLE.getKey().equals(ownerStage)){
                        //稳定期 一年到两年
                        LocalDate lastYear1 = now.plusYears(-1);
                        queryFilter.addFilter("delivery_date", lastYear1, QueryOP.LESS_EQUAL, FieldRelation.AND);
                        LocalDate lastYear2 = now.plusYears(-2);
                        queryFilter.addFilter("delivery_date", lastYear2, QueryOP.GREAT, FieldRelation.AND);
                    }else if(OwnerStageEnum.OLD_PROPRIETOR.getKey().equals(ownerStage)){
                        //老业主 两年以上
                        LocalDate lastYear2 = now.plusYears(-2);
                        queryFilter.addFilter("delivery_date", lastYear2, QueryOP.LESS_EQUAL, FieldRelation.AND);
                    }
                }
                querys.remove(query);
                break;
            }
        }

    }

    @Override
    public List<Map<String,Object>> selectGridBuilding(String id) {

        return houseMapper.selectGridBuilding(id);
    }

    @Override
    public List<Map<String,Object>> selectBuildingUnit(String id) {
        return houseMapper.selectBuildingUnit(id);
    }

    @Override
    public void exportExcel(QueryFilter queryFilter, HttpServletResponse response) throws Exception{
        //处理业主类型查询参数
        addOwnerStageFilter(queryFilter);
        List<HouseExcelInfoDTO> houseExcelInfoDTOS = this.houseMapper.selectHouseExcelInfo(queryFilter.getParams());
        List<JsonNode> houseType = portalFeginClient.getByTypeKey("houseType");
        List<JsonNode> houseUse = portalFeginClient.getByTypeKey("houseUse");
        List<JsonNode> renovationType = portalFeginClient.getByTypeKey("renovationType");
        Map<String,String> houseTypeMap=new HashMap<>();
        Map<String,String> houseUseMap=new HashMap<>();
        Map<String,String> renovationTypeMap=new HashMap<>();
        dicMap(houseTypeMap,houseType);
        dicMap(houseUseMap,houseUse);
        dicMap(renovationTypeMap,renovationType);
        for (HouseExcelInfoDTO houseExcelInfoDTO : houseExcelInfoDTOS) {
            houseExcelInfoDTO.setHouseType(houseTypeMap.get(houseExcelInfoDTO.getHouseType()));
            houseExcelInfoDTO.setHouseUse(houseUseMap.get(houseExcelInfoDTO.getHouseUse()));
            houseExcelInfoDTO.setRenovationType(renovationTypeMap.get(houseExcelInfoDTO.getRenovationType()));
        }
        ExportExcelUtils exportExcelUtils =new ExportExcelUtils();
        exportExcelUtils=addSheet(houseExcelInfoDTOS,null,exportExcelUtils,headList());
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(("房产信息表.xlsx").getBytes(), "iso-8859-1"));
        exportExcelUtils.write(response.getOutputStream());
        response.flushBuffer();
    }
    private void dicMap(Map<String,String> map,List<JsonNode> jsonNodes){
        for (JsonNode jsonNode : jsonNodes) {
            if(jsonNode.get("key")!=null && jsonNode.get("name")!=null){
                map.put(jsonNode.get("key").asText(),jsonNode.get("name").asText());
            }
        }
    }
    private ExportExcelUtils addSheet(List<HouseExcelInfoDTO> houseExcelInfoDTOS, String sheetName, ExportExcelUtils exportExcel, List<String> hearList){
        exportExcel.addSheet(hearList , StringUtils.isEmpty(sheetName)?"sheet1":sheetName);
        for (HouseExcelInfoDTO dto : houseExcelInfoDTOS) {
            Row row = exportExcel.addRow();
            exportExcel.addCell(row, 0, dto.getAreaName() == null ? "" :  dto.getAreaName());
            exportExcel.addCell(row, 1, dto.getCityName() == null ? "" : dto.getCityName());
            exportExcel.addCell(row, 2, dto.getProjectName() == null ? "" : dto.getProjectName());
            exportExcel.addCell(row, 3, dto.getMassifName() == null ? "" : dto.getMassifName());
            exportExcel.addCell(row, 4, dto.getGridName() == null ? "" : dto.getGridName());
            exportExcel.addCell(row, 5, dto.getHouseInnerName() == null ? "" : dto.getHouseInnerName());
            exportExcel.addCell(row, 6, dto.getUcMemberName() == null ? "" : dto.getUcMemberName());
            exportExcel.addCell(row, 7, dto.getPhone1() == null ? "" : dto.getPhone1());
            exportExcel.addCell(row, 8, dto.getPhone2() == null ? "" : dto.getPhone2());
            exportExcel.addCell(row, 9, dto.getPhone3() == null ? "" : dto.getPhone3());
            exportExcel.addCell(row, 10, dto.getPhone4() == null ? "" : dto.getPhone4());
            exportExcel.addCell(row, 11, dto.getHouseType() == null ? "" : dto.getHouseType());
            exportExcel.addCell(row, 12, dto.getRenovationType() == null ? "" : dto.getRenovationType());
            exportExcel.addCell(row, 13, dto.getDeliveryDate() == null ? "" : DateUtils.dateToStr(dto.getDeliveryDate(),11));
            exportExcel.addCell(row, 14, dto.getRealDeliveryDate() == null ? "" : DateUtils.dateToStr(dto.getRealDeliveryDate(),11));
            exportExcel.addCell(row, 15, dto.getIsOwnStaff() == null ? "" : dto.getIsOwnStaff());
            exportExcel.addCell(row, 16, dto.getHouseUse() == null ? "" : dto.getHouseUse());
            exportExcel.addCell(row, 17, dto.getRemarks() == null ? "" : dto.getRemarks());
        }
        return exportExcel;
    }
    private List<String> headList(){
        String [] headArray={"区域","城区","项目","地块","网格","房号","姓名","电话1","电话2","电话3","电话4","房屋类型","装修类型","集中交付日期","实际交付日期","是否是内部员工","房屋用途","备注"};
        return new ArrayList<>(Arrays.asList(headArray));
    }

    @Override
    public PageList<ClientRelationDTO> ucMemberRelationList(QueryFilter queryFilter) {
        //分页
        PageBean pageBean = queryFilter.getPageBean();
        if (BeanUtils.isEmpty(pageBean)) {
            PageHelper.startPage(1, Integer.MAX_VALUE, false);
        } else {
            PageHelper.startPage(pageBean.getPage().intValue(), pageBean.getPageSize().intValue(),
                    pageBean.showTotal());
        }
        List<ClientRelationDTO> clientRelationDTOS = houseMapper.selectUcMemberRelation(queryFilter.getParams());
        return new PageList<>(clientRelationDTOS);
    }

    @Override
    public CommonResult<String> addUserHouseRef(UserHouseRefBO model) {
        List<UserHouseRef> addData = getAddData(model);
        if(CollectionUtils.isEmpty(addData)){
            return new CommonResult<>(false,"没有数据");
        }
        int i = houseMapper.insertUserHouseRefList(addData);
        if(i>0){
            if("0".equals(model.getRelation())){
                updateHouse(model.getHouseId());
            }
            return new CommonResult<>("成功");
        }
        return new CommonResult<>(false,"没有数据");
    }
    private List<UserHouseRef> getAddData(UserHouseRefBO model){
        List<UserHouseRef> userHouseRefs=new ArrayList<>();
        List<String> memberIds = model.getMemberIds();
        String houseId = model.getHouseId();
        String relation = model.getRelation();
        String userId = ContextUtil.getCurrentUser().getUserId();
        if(!CollectionUtils.isEmpty(memberIds)){
            for (String memberId : memberIds) {
                UserHouseRef userHouseRef=new UserHouseRef();
                userHouseRefs.add(userHouseRef);
                userHouseRef.setId(UniqueIdUtil.getSuid());
                userHouseRef.setHouseId(houseId);
                userHouseRef.setMemberId(memberId);
                userHouseRef.setRelation(relation);
                userHouseRef.setIsDele("0");
                userHouseRef.setFrom("platform");
                userHouseRef.setCreatedBy(userId);
                userHouseRef.setCreateTime(new Date());
                userHouseRef.setUpdatedBy(userId);
                userHouseRef.setUpdateTime(new Date());
                userHouseRef.setVersion(0);
            }
        }
        return userHouseRefs;
    }
    private void updateHouse(String houseId){
        //产权人 维护house表的冗余字段
        Map<String, String> stringStringMap = houseMapper.selectMemberInfos(houseId);
        House house = houseMapper.get(houseId);
        if(stringStringMap==null){
            if(house!=null){
                house.setUcMemberId("");
                house.setUcMemberName("");
                house.setUcMemberMobile("");
                house.setUcMemberTelPhone("");
                house.setIsOwnStaff("");
                houseMapper.updateByPrimaryKeySelective(house);
            }
        }else{
            if(house!=null){
                house.setUcMemberId(stringStringMap.get("id"));
                house.setUcMemberName(stringStringMap.get("name"));
                house.setUcMemberMobile(stringStringMap.get("mobile"));
                house.setUcMemberTelPhone(stringStringMap.get("telPhone"));
                house.setIsOwnStaff(stringStringMap.get("isOwnStaff"));
                houseMapper.updateByPrimaryKeySelective(house);
            }
        }

    }
    @Override
    public CommonResult<String> updateUserHouseRef(String id) {
        UserHouseRef userHouseRef = houseMapper.selectUserHouseRef(id);
        if(userHouseRef!=null){
            String userId = ContextUtil.getCurrentUser().getUserId();
            Integer version = userHouseRef.getVersion();
            if(version!=null){
                userHouseRef.setVersion(version++);
            }else{
                userHouseRef.setVersion(0);
            }
            userHouseRef.setUpdateTime(new Date());
            userHouseRef.setUpdatedBy(userId);
            userHouseRef.setIsDele("1");
            int i = houseMapper.updateUserHouseRef(userHouseRef);
            if(i>0){
                if("0".equals(userHouseRef.getRelation())){
                    updateHouse(userHouseRef.getHouseId());
                }
                return new CommonResult<>("成功");
            }
        }
        return new CommonResult<>(false,"没有对应关系");
    }

    @Override
    public PageList<Map<String, Object>> selectHouseAndClient(QueryFilter queryFilter) {
        //分页
        PageBean pageBean = queryFilter.getPageBean();
        if (BeanUtils.isEmpty(pageBean)) {
            PageHelper.startPage(1, Integer.MAX_VALUE, false);
        } else {
            PageHelper.startPage(pageBean.getPage().intValue(), pageBean.getPageSize().intValue(),
                    pageBean.showTotal());
        }
        List<Map<String, Object>> list=houseMapper.selectHouseAndClient(queryFilter.getParams());
        return new PageList(list);
    }

    @Override
    public void syncRedundancyInfo(String memberId) {

    }
}