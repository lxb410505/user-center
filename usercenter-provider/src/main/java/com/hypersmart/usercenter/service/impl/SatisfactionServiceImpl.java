package com.hypersmart.usercenter.service.impl;

import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.FieldRelation;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.query.QueryOP;
import com.hypersmart.base.util.StringUtil;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.mdm.feign.UcOrgFeignService;
import com.hypersmart.uc.api.impl.util.ContextUtil;
import com.hypersmart.usercenter.mapper.SatisfactionMapper;
import com.hypersmart.usercenter.mapper.UcOrgMapper;
import com.hypersmart.usercenter.model.Satisfaction;
import com.hypersmart.usercenter.model.UcOrg;
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
import java.util.stream.Collectors;

/**
 * @author magellan
 * @email magellan
 * @date 2019-05-14 13:37:39
 */
@Service("satisfactionServiceImpl")
public class SatisfactionServiceImpl extends GenericService<String, Satisfaction> implements SatisfactionService {

    public SatisfactionServiceImpl(SatisfactionMapper mapper) {
        super(mapper);
    }
    @Autowired
    UcOrgService ucOrgService;

    //查询组织机构;
    @Autowired
    UcOrgFeignService ucOrgFeignService;
    @Resource
    SatisfactionMapper satisfactionMapper;
    @Override
    public CommonResult<String> importData(MultipartFile file,String date) {
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

            Integer hasRealCount=getRealCount(tempResourceImportList);

            if (hasRealCount > 3000) {
                message.append("文件数据超过3000条！");
                importState = false;
                throw new Exception("文件数据超过3000条");
            }
            //从第二行开始是表头
            List<Object> rowDataHeader = tempResourceImportList.get(1);
            List<String> rowDataHeaderStr = new ArrayList<>();
            rowDataHeaderStr = rowDataHeader.stream().map(r -> String.valueOf(r).replace(" ", "").replace("*", "")).collect(Collectors.toList());

            for (int i = 0; i < rowDataHeaderStr.size(); i++) {
                if(rowDataHeaderStr.get(i).isEmpty()){
                    rowDataHeaderStr.remove(i);
                }
            }
            boolean hasError = false;
            for (int i = 0; i < rowDataHeaderStr.size(); i++) {
                if (!headArr[i].equals(rowDataHeaderStr.get(i))) {
                    hasError = true;
                    break;
                }
            }
            if (hasError) {
                message.append("标题栏数据顺序或格式不正确");
                importState = false;
                throw new Exception("标题栏数据顺序或格式不正确");

            }

            //处理导入的数据；
            if (importState) {

                doData(message, satisfactions, tempResourceImportList,date);

                satisfactionMapper.deleteByDate(date);

                insertBatch(satisfactions);

            }

        } catch (Exception e) {
            new CommonResult(false, e.toString());
        }
        return new CommonResult(true, "成功导入");
    }

    @Override
    public CommonResult CheckHasExist(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-00");
        Satisfaction satisfaction = new Satisfaction();
        QueryFilter queryFilter = QueryFilter.build();

        try {
            Date day = formatter.parse(date);
            satisfaction.setEffectiveTime(day);
            queryFilter.addFilter("effective_time",date, QueryOP.EQUAL, FieldRelation.AND);
            List<Satisfaction> satisfactions = selectAll(satisfaction);
            satisfactions=query(queryFilter).getRows();
            if(satisfactions!=null&&satisfactions.size()>0){
                return new CommonResult(false,"已存在数据");
            }else{
                return new CommonResult(true,"没有数据");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void doData(StringBuffer message, List<Satisfaction> satisfactions, List<List<Object>> tempResourceImportList,String date) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-00");
        for (int i = 2; i < tempResourceImportList.size(); i++) {
            List<Object> rowData = tempResourceImportList.get(i);
            System.out.println(rowData);

            String[] split = rowData.get(0).toString().split("\\.");

            String orgCode = checkData(message, rowData, split);

            //新增数据;
            Satisfaction satisfaction = new Satisfaction();
            //satisfaction.setCreateBy();
            satisfaction.setCreateTime(new Date());
            satisfaction.setOrder(rowData.get(0).toString());
            satisfaction.setType(rowData.get(1).toString());
            satisfaction.setOrgName(rowData.get(2).toString());
            satisfaction.setOverallSatisfaction(new BigDecimal(rowData.get(3).toString()));
            satisfaction.setStorming(new BigDecimal(rowData.get(4).toString()));
            satisfaction.setStationaryPhase(new BigDecimal(rowData.get(5).toString()));
            satisfaction.setOldProprietor(new BigDecimal(rowData.get(6).toString()));
            satisfaction.setEffectiveTime(formatter.parse(date));
            satisfaction.setOrgCode(orgCode);
            if (split.length < 4) {
                satisfaction.setOrderServiceUnit(new BigDecimal(rowData.get(7).toString()));
                satisfaction.setEsuCleaning(new BigDecimal(rowData.get(8).toString()));
                satisfaction.setEsuGreen(new BigDecimal(rowData.get(9).toString()));
                satisfaction.setEngineeringServiceUnit(new BigDecimal(rowData.get(10).toString()));

            }
            satisfactions.add(satisfaction);

        }
    }

    private String checkData(StringBuffer message, List<Object> rowData, String[] split) throws Exception {
        //校验是否有组织不匹配；根据层级和姓名，查询是否有组织匹配
        UcOrg ucOrg = new UcOrg();
        ucOrg.setName(rowData.get(2).toString());
        List<UcOrg> ucOrgs = ucOrgService.selectAll(ucOrg);
        String orgCode=null;
        boolean hasOrg=false;
        if(ucOrgs.size()<=0){
            throw new Exception(""+rowData.get(2).toString()+"该组织名称与系统数据不匹配，请修改后重试。");

        }
        for (UcOrg ucOrg1 :
                ucOrgs) {
            if(ucOrg1.getLevel().equals(split.length)){
                orgCode=ucOrg1.getCode();
                hasOrg=true;
            }

        }
        if(!hasOrg){
            throw new Exception(""+rowData.get(2).toString()+"该组织名称与系统数据不匹配，请修改后重试。");

        }
        //校验是否为数字
        //todo
        switch (split.length) {
            case 0:
                throw new Exception("请检查是否有空行");

            case 1:
            case 2:
            case 3:

                //校验是否有不合法空值；
                for (Object v :
                        rowData) {
                    if (v.toString() == null || v.toString().length() <= 0) {
                        message.append("请检查数据是否为空");
                        throw new Exception("请检查数据是否为空");
                    }
                }
                break;
            case 4:

                for (int j = 0; j < rowData.size(); j++) {
                    if (j < 7 && (rowData.get(j).toString() == null || rowData.get(j).toString().length() <= 0)) {
                        throw new Exception("请检查数据是否为空");
                    }
                }
                break;

            default:


        }
        return orgCode;
    }

    private Integer getRealCount(List<List<Object>> tempResourceImportList) {
         Integer hasRealCount = 1;
        for (int x = 1; x < tempResourceImportList.size(); x++) {
            List<Object> rowData = tempResourceImportList.get(x);
            int length = rowData.size();
            Boolean isEmptyRow = true;
            for (int s = 0; s < length; s++) {
                if (StringUtil.isNotEmpty(String.valueOf(rowData.get(s)))) {
                    isEmptyRow = false;
                }
            }
            if (isEmptyRow) {
                //整行为空，忽略
                continue;
            }

            hasRealCount++;
        }
        return hasRealCount;
    }

    @Override
    public List<Satisfaction> getSatisfactionDetail(String orgId, String time) {
        QueryFilter queryFilter = QueryFilter.build();
        queryFilter.addFilter("PARENT_ID_", orgId, QueryOP.EQUAL, FieldRelation.AND);
        queryFilter.addFilter("IS_DELE_", "0", QueryOP.EQUAL, FieldRelation.AND);
        List<UcOrg> ucOrgList = ucOrgService.query(queryFilter).getRows();
        List<Satisfaction> satisfactions = new ArrayList<>();
        if (ucOrgList!=null&&ucOrgList.size()>0){
            satisfactions = satisfactionMapper.getSatisfactionDetail(ucOrgList, time);
        }
        return satisfactions;
    }

    @Override
    public List<Satisfaction> getAllSatisfaction(String time) {
        String userId = ContextUtil.getCurrentUser().getUserId();
        List<UcOrg> ucOrgList = ucOrgService.getUserOrgListMerge(userId);
        List<UcOrg> quYuList = new ArrayList<>();
        for (UcOrg ucOrg:ucOrgList){
            if (ucOrg.getLevel()==1){
                quYuList.add(ucOrg);
            }
        }
        return satisfactionMapper.getSatisfactionDetail(quYuList,time);
    }

    public static void main(String[] args) throws ParseException {
        String date = "2019-05-00";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-00");
        Date parse = formatter.parse(date);
        String ss=formatter.format(parse);
        System.out.println(ss);
    }
}