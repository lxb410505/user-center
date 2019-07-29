package com.hypersmart.usercenter.service.impl;

import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.FieldRelation;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.query.QueryOP;
import com.hypersmart.base.util.BeanUtils;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.mapper.TgeQualityCheckMapper;
import com.hypersmart.usercenter.model.TgeQualityCheck;
import com.hypersmart.usercenter.model.UcOrg;
import com.hypersmart.usercenter.service.TgeQualityCheckService;
import com.hypersmart.usercenter.service.UcOrgService;
import com.hypersmart.usercenter.util.ImportExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 *
 * @author skeleton
 * @email skeleton
 * @date 2019-05-31 16:05:16
 */
@Service("tgeQualityCheckServiceImpl")
public class TgeQualityCheckServiceImpl extends GenericService<String, TgeQualityCheck> implements TgeQualityCheckService {

    public TgeQualityCheckServiceImpl(TgeQualityCheckMapper mapper) {
        super(mapper);
    }

    @Resource
    TgeQualityCheckMapper tgeQualityCheckMapper;

    @Autowired
    UcOrgService ucOrgService ;

    @Override
    public CommonResult<String> importData(MultipartFile file, String date) {
        //  date+="-01";
        StringBuffer message = new StringBuffer();
        boolean importState = true;
        try {
            if (file.isEmpty()) {
                message.append("导入文件丢失，请重新选择文件");
            }
            String[] headArr = {"区域", "项目", "地块", "网格",
                    "秩序服务单元", "环境服务单元", "工程服务单元-运维",
                    "工程服务单元-设施设备","一级红线","二级强控"};
            InputStream in = file.getInputStream();
            List<List<Object>> tempResourceImportList = new ImportExcelUtil().getBankListByExcelFromTwo(in, file.getOriginalFilename());

            Integer hasRealCount = ImportExcelUtil.getRealCount(tempResourceImportList);

            if (hasRealCount > 3000) {
                message.append("文件数据超过3000条！");
                importState = false;
                throw new Exception("文件数据超过3000条");
            }
            //从第二行开始是表头
            boolean hasError = isHasError(headArr, tempResourceImportList);
            if (hasError) {
                message.append("标题栏数据顺序或格式不正确");
                importState = false;
                throw new Exception("标题栏数据顺序或格式不正确");

            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            List<TgeQualityCheck> qualityChecks = new ArrayList<>();

            for (int i = 3; i < tempResourceImportList.size(); i++) {
                List<Object> row = tempResourceImportList.get(i);
                if (row.get(0) == null || row.get(0).toString().length() <= 0) {
                    throw new Exception("第" + (i + 1) + "行区域为空");
                }else {
                    TgeQualityCheck qualityCheck = new TgeQualityCheck();
                    List<String> orgCodes = this.getOrg(i, row.get(0).toString(), row.get(1).toString(), row.get(2).toString());
                    qualityCheck.setAreaCode(orgCodes.get(0));
                    qualityCheck.setArea(row.get(0).toString());
                    Object obj = null;
                    qualityCheck.setProject(!BeanUtils.isEmpty(obj=row.get(1))?obj.toString():"");
                    qualityCheck.setProjectCode(orgCodes.get(1));
                    qualityCheck.setMassif(!BeanUtils.isEmpty(obj=row.get(2))?obj.toString():"");
                    qualityCheck.setMassifCode(orgCodes.get(2));
                    BigDecimal demoBd = new BigDecimal(0);
                    qualityCheck.setStoriedGrid(!BeanUtils.isEmpty(obj=row.get(3))?new BigDecimal(obj.toString()):demoBd);
                    qualityCheck.setPublicAreaGrid(!BeanUtils.isEmpty(obj=row.get(4))?new BigDecimal(obj.toString()):demoBd);
                    qualityCheck.setServiceCenterGrid(!BeanUtils.isEmpty(obj=row.get(5))?new BigDecimal(obj.toString()):demoBd);
                    qualityCheck.setOrderServiceUnit(!BeanUtils.isEmpty(obj=row.get(6))?new BigDecimal(obj.toString()):demoBd);
                    qualityCheck.setEsuComprehensiveScore(!BeanUtils.isEmpty(obj=row.get(7))?new BigDecimal(obj.toString()):demoBd);
                    qualityCheck.setEsuCleaning(!BeanUtils.isEmpty(obj=row.get(8))?new BigDecimal(obj.toString()):demoBd);
                    qualityCheck.setEsuGreen(!BeanUtils.isEmpty(obj=row.get(9))?new BigDecimal(obj.toString()):demoBd);
                    qualityCheck.setPsuOperationAndMaintenance(!BeanUtils.isEmpty(obj=row.get(10))?new BigDecimal(obj.toString()):demoBd);
                    qualityCheck.setPsuFacilities(!BeanUtils.isEmpty(obj=row.get(11))?new BigDecimal(obj.toString()):demoBd);
                    qualityCheck.setFirstGradeRedLine(!BeanUtils.isEmpty(obj=row.get(12))?new BigDecimal(obj.toString()):demoBd);
                    qualityCheck.setTwoLevelStrongControl(!BeanUtils.isEmpty(obj=row.get(13))?new BigDecimal(obj.toString()):demoBd);
                    qualityCheck.setCreateDate(new Date());
                    qualityCheck.setEffectiveTime(formatter.parse(date));
                    qualityChecks.add(qualityCheck);
                }
                //check org and get org info
            }
            //todo delete
            tgeQualityCheckMapper.deleteByDate(date);

            insertBatch(qualityChecks);

            return new CommonResult<>(Boolean.TRUE,"导入成功");


        } catch (Exception e) {
            //todo
            return new CommonResult<>(Boolean.FALSE,e.getMessage());

        }

    }


    private boolean isHasError(String[] headArr, List<List<Object>> tempResourceImportList) {
        List<Object> rowDataHeader = tempResourceImportList.get(1);
        List<String> rowDataHeaderStr = new ArrayList<>();
        rowDataHeaderStr = rowDataHeader.stream().map(r -> String.valueOf(r).replace(" ", "").replace("*", "")).collect(Collectors.toList());

        for (int i = 0; i < rowDataHeaderStr.size(); i++) {
            if (rowDataHeaderStr.get(i).isEmpty()) {
                rowDataHeaderStr.remove(i);
                i=i-1;
            }
        }
        boolean hasError = false;
        for (int i = 0; i < rowDataHeaderStr.size(); i++) {
            if (!headArr[i].equals(rowDataHeaderStr.get(i))) {
                hasError = true;
                break;
            }
        }
        return hasError;
    }

    List<String> getOrg(int i,String val1,String val2 ,String val3) throws Exception {
        //dikuai code
        UcOrg ucOrg = new UcOrg();
        ucOrg.setName(val3);
        ucOrg.setIsDele("0");
        List<UcOrg> ucOrgs = ucOrgService.selectAll(ucOrg);
        if(!checkHasOrg(ucOrgs)){
            throw new Exception("第"+i+"行组织名称错误请检查");
        }
        UcOrg diOrg = ucOrgs.get(0);
        List<String> orgids= new ArrayList<>();
        orgids.add(diOrg.getCode());

        String pathName = ucOrgs.get(0).getPathName();
        String pathId = ucOrgs.get(0).getPath();

        String[] nameArr = pathName.split("\\/");
        String[] idArr = pathId.split("\\.");
        if (nameArr[2].equals(val1)) {
            UcOrg area = ucOrgService.get(idArr[2]);
            orgids.add(area.getCode());
        }else {
            throw new Exception("第"+i+"行组织名称错误请检查");
        }

        String parentId = diOrg.getParentId();
        UcOrg project = ucOrgService.get(parentId);
        if (project.getName().equals(val2)) {
            orgids.add(project.getCode());
        }else {
            throw new Exception("第"+i+"行组织名称错误请检查");
        }
        return orgids;
    }
    private boolean checkHasOrg( List<UcOrg> ucOrgs){
        if(ucOrgs==null||ucOrgs.size()<=0||ucOrgs.get(0)==null||ucOrgs.get(0).getCode()==null){
            return Boolean.FALSE;

        }else{
            return Boolean.TRUE;
        }
    }
    @Override
    public CommonResult CheckHasExist(String date) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        TgeQualityCheck satisfaction = new TgeQualityCheck();
        QueryFilter queryFilter = QueryFilter.build();

        try {
            Date day = formatter.parse(date);
            satisfaction.setEffectiveTime(day);
            queryFilter.addFilter("effective_time", date, QueryOP.EQUAL, FieldRelation.AND);
            List<TgeQualityCheck> satisfactions = selectAll(satisfaction);
            satisfactions = query(queryFilter).getRows();
            if (satisfactions != null && satisfactions.size() > 0) {
                return new CommonResult(false, "已存在数据");
            } else {
                return new CommonResult(true, "没有数据");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new CommonResult(true, "没有数据");
    }
}