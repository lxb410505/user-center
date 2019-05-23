package com.hypersmart.usercenter.service.impl;

import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.FieldRelation;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.query.QueryOP;
import com.hypersmart.base.util.StringUtil;
import com.hypersmart.framework.service.GenericService;

import com.hypersmart.usercenter.mapper.QualityCheckMapper;
import com.hypersmart.usercenter.model.QualityCheck;
import com.hypersmart.usercenter.model.Satisfaction;
import com.hypersmart.usercenter.model.UcOrg;
import com.hypersmart.usercenter.service.QualityCheckService;
import com.hypersmart.usercenter.service.SatisfactionService;
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
import java.util.Date;
import java.util.List;

/**
 * @author Magellan
 * @email Magellan
 * @date 2019-05-21 16:18:32
 */
@Service("qualityCheckServiceImpl")
public class QualityCheckServiceImpl extends GenericService<String, QualityCheck> implements QualityCheckService {

    public QualityCheckServiceImpl(QualityCheckMapper mapper) {
        super(mapper);
    }

    @Resource
    QualityCheckMapper qualityCheckMapper;

    @Autowired
    UcOrgService ucOrgService ;

    @Autowired
    SatisfactionService satisfactionService;

    @Override
    public CommonResult<String> importData(MultipartFile file, String date) {
      //  date+="-01";
        StringBuffer message = new StringBuffer();
        boolean importState = true;
        List<Satisfaction> satisfactions = new ArrayList<>();

        try {
            if (file.isEmpty()) {
                message.append("导入文件丢失，请重新选择文件");
            }
            String[] headArr = {"序列", "分类", "组织名称", "综合满意度", "磨合期", "稳定期",
                    "老业主", "秩序服务单元", "环境服务单元-保洁", "环境服务单元-绿化", "工程服务单元"};
            InputStream in = file.getInputStream();
            List<List<Object>> tempResourceImportList = new ImportExcelUtil().getBankListByExcelFromTwo(in, file.getOriginalFilename());

            Integer hasRealCount = ImportExcelUtil.getRealCount(tempResourceImportList);

            if (hasRealCount > 3000) {
                message.append("文件数据超过3000条！");
                importState = false;
                throw new Exception("文件数据超过3000条");
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            List<QualityCheck> qualityChecks = new ArrayList<>();


            for (int i = 3; i < tempResourceImportList.size(); i++) {
                List<Object> row = tempResourceImportList.get(i);
                QualityCheck qualityCheck = new QualityCheck();
                qualityCheck.setArea(row.get(0).toString());
                List<String> orgCodes = this.getOrg(i, row.get(0).toString(), row.get(1).toString(), row.get(2).toString());
                qualityCheck.setAreaCode(orgCodes.get(0));
                qualityCheck.setArea(row.get(0).toString());
                qualityCheck.setProject(row.get(1).toString());
                qualityCheck.setProjectCode(orgCodes.get(1));
                qualityCheck.setMassif(row.get(2).toString());
                qualityCheck.setMassifCode(orgCodes.get(2));
                qualityCheck.setStoriedGrid(new BigDecimal(row.get(3).toString()));
                qualityCheck.setPublicAreaGrid(new BigDecimal(row.get(4).toString()));
                qualityCheck.setServiceCenterGrid(new BigDecimal(row.get(5).toString()));
                qualityCheck.setOrderServiceUnit(new BigDecimal(row.get(6).toString()));
                qualityCheck.setEsuComprehensiveScore(new BigDecimal(row.get(7).toString()));
                qualityCheck.setEsuCleaning(new BigDecimal(row.get(8).toString()));
                qualityCheck.setEsuGreen(new BigDecimal(row.get(9).toString()));
                qualityCheck.setPsuOperationAndMaintenance(new BigDecimal(row.get(10).toString()));
                qualityCheck.setPsuFacilities(new BigDecimal(row.get(11).toString()));
                qualityCheck.setFirstGradeRedLine(new BigDecimal(row.get(12).toString()));
                qualityCheck.setTwoLevelStrongControl(new BigDecimal(row.get(13).toString()));
                qualityCheck.setCreateDate(new Date());
                qualityCheck.setEffectiveTime(formatter.parse(date));
                qualityChecks.add(qualityCheck);


                //check org and get org info
            }
            //todo delete
            qualityCheckMapper.deleteByDate(date);

            insertBatch(qualityChecks);

            return new CommonResult<>(Boolean.TRUE,"导入成功");


        } catch (Exception e) {
            //todo
            return new CommonResult<>(Boolean.FALSE,e.getMessage());

        }

    }

    List<String> getOrg(int i,String val1,String val2 ,String val3) throws Exception {
        //area code
        UcOrg ucOrg = new UcOrg();
        ucOrg.setName(val1);
        ucOrg.setIsDele("0");
        List<UcOrg> ucOrgs = ucOrgService.selectAll(ucOrg);
        if(!checkHasOrg(ucOrgs)){
            throw new Exception("第"+i+"行组织名称错误请检查");
        }
        String code1 = ucOrgs.get(0).getCode();
        List<String> orgids= new ArrayList<>();

        orgids.add(code1);

        //project code
        ucOrg.setName(val2);
        ucOrg.setParentId(ucOrgs.get(0).getId());
        ucOrg.setIsDele("0");
        ucOrgs=ucOrgService.selectAll(ucOrg);
        if(!checkHasOrg(ucOrgs)){
            throw new Exception("第"+i+"行组织名称错误请检查");
        }

        if(ucOrgs.get(0).getGrade().equals("ORG_XiangMu")){
            orgids.add(ucOrgs.get(0).getCode());
        }else if(ucOrgs.get(0).getGrade().equals("ORG_ChengQu")){
            ucOrg.setName(ucOrgs.get(0).getName());
            ucOrg.setParentId(ucOrgs.get(0).getId());
            ucOrgs=ucOrgService.selectAll(ucOrg);
            if(!checkHasOrg(ucOrgs)){
                throw new Exception("第"+i+"行组织名称错误请检查");
            }
            orgids.add(ucOrgs.get(0).getCode());

        }else{
            throw new Exception("第"+i+"行组织名称错误请检查");

        }

        //di kuai  code
        ucOrg.setName(val3);
        ucOrg.setParentId(ucOrgs.get(0).getId());
        ucOrgs = ucOrgService.selectAll(ucOrg);
        if(!checkHasOrg(ucOrgs)){
            throw new Exception("第"+i+"行组织名称错误请检查");
        }
        orgids.add(ucOrgs.get(0).getCode());
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
        QualityCheck satisfaction = new QualityCheck();
        QueryFilter queryFilter = QueryFilter.build();

        try {
            Date day = formatter.parse(date);
            satisfaction.setEffectiveTime(day);
            queryFilter.addFilter("effective_time", date, QueryOP.EQUAL, FieldRelation.AND);
            List<QualityCheck> satisfactions = selectAll(satisfaction);
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