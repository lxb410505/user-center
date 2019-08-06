package com.hypersmart.usercenter.service.impl;

import com.github.pagehelper.PageHelper;
import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.PageBean;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.query.QueryOP;
import com.hypersmart.base.util.BeanUtils;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.mdm.dto.UcOrg;
import com.hypersmart.usercenter.fegin.UcOrgFegin;
import com.hypersmart.usercenter.mapper.TgeSignificantQualityMapper;
import com.hypersmart.usercenter.model.TgeSignificantQuality;
import com.hypersmart.usercenter.service.TgeSignificantQualityService;
import com.hypersmart.usercenter.service.UcOrgService;
import com.hypersmart.usercenter.util.ImportExcelUtil;
import com.hypersmart.uc.api.impl.util.ContextUtil;
import com.hypersmart.uc.api.model.IUser;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 重大事件质量
 *
 * @author skeleton
 * @email skeleton
 * @date 2019-05-31 16:07:53
 */
@Service("tgeSignificantQualityServiceImpl")
public class TgeSignificantQualityServiceImpl extends GenericService<String, TgeSignificantQuality> implements TgeSignificantQualityService {

    @Autowired
    TgeSignificantQualityMapper tgeSignificantQualityMapper;
    @Autowired
    UcOrgFegin ucOrgService;
    @Autowired
    UcOrgService orgService;

    public TgeSignificantQualityServiceImpl(TgeSignificantQualityMapper mapper) {
        super(mapper);
    }
    @RequestMapping(value = "/importCustomerServiceInspectionWorkGuidance", method = RequestMethod.POST)
    public CommonResult<String> importCustomerServiceInspectionWorkGuidance(@RequestParam("file") MultipartFile file) throws Exception {
        return this.dataHandler(file);
    }


    /**
     * 检查数据完整性
     * @param significantQuality
     * @return
     */
    public boolean checkExists(TgeSignificantQuality significantQuality) {
        return tgeSignificantQualityMapper.selectOne(significantQuality) == null ? true : false;
    }


    public CommonResult<String> dataHandler(MultipartFile file) throws Exception {
        IUser user = ContextUtil.getCurrentUser();
        boolean importState = true;
        if (file.isEmpty()) {
            return new CommonResult<>("导入文件丢失，请重新选择文件");
        } else {
            InputStream in = file.getInputStream();
            //   调用导入工具类ImportExcelUtil，把excel中的数据拿出来
            List<List<Object>> orderWorkGuidance = new ImportExcelUtil().getBankListByExcelPK(in, file.getOriginalFilename());
            if (!(orderWorkGuidance != null && orderWorkGuidance.size() > 2)) {
                return new CommonResult<>("请先填写数据");
            }
            for (List<Object> objectList : orderWorkGuidance) {
                if (objectList.size() < 14) {
                    int s = objectList.size();
                    for (int i = 0; i < 14 - s; i++) {
                        objectList.add("");
                    }
                }
            }
            Iterator<List<Object>> it = orderWorkGuidance.iterator();
            while (it.hasNext()) {
                List<Object> x = it.next();
                if ("".equals(x.get(0).toString()) && "".equals(x.get(1).toString()) && "".equals(x.get(2).toString()) && "".equals(x.get(3).toString()) && "".equals(x.get(4).toString()) && "".equals(x.get(5).toString()) && "".equals(x.get(6).toString()) && "".equals(x.get(7).toString())
                        && "".equals(x.get(8).toString()) && "".equals(x.get(9).toString()) && "".equals(x.get(10).toString()) && "".equals(x.get(11).toString()) && "".equals(x.get(12).toString()) && "".equals(x.get(13).toString())) {
                    it.remove();
                }
            }
            if (orderWorkGuidance.get(0).size() != 14) {
                return new CommonResult<>("模板验证错误，请从系统中下载最新模板；");
            }


            List<String> headList = new ArrayList<>();
            List<String> origin = new ArrayList<>();
            List<Integer> ignoreColum = new ArrayList<>();
            headList.add(orderWorkGuidance.get(0).get(0).toString());
            headList.add(orderWorkGuidance.get(0).get(1).toString());
            headList.add(orderWorkGuidance.get(0).get(2).toString());
            headList.add(orderWorkGuidance.get(0).get(3).toString());
            headList.add(orderWorkGuidance.get(0).get(4).toString());
            headList.add(orderWorkGuidance.get(0).get(5).toString());
            headList.add(orderWorkGuidance.get(0).get(6).toString());

            origin.add("区域");
            origin.add("片区");
            origin.add("项目");
            origin.add("地块");
            origin.add("秩序服务单元");
            origin.add("环境服务单元");
            origin.add("工程服务单元");

            if (!ImportExcelUtil.checkHead(headList, origin, ignoreColum)) {
                return new CommonResult<>("模板验证错误，请从系统中下载最新模板；");
            }
        }
        return new CommonResult<>(true,"","");
    }


    @Override
    public PageList<TgeSignificantQuality> queryList(QueryFilter queryFilter) {
        PageBean pageBean = queryFilter.getPageBean();
        if (BeanUtils.isEmpty(pageBean)) {
            PageHelper.startPage(1, Integer.MAX_VALUE, false);
        } else {
            PageHelper.startPage(pageBean.getPage().intValue(), pageBean.getPageSize().intValue(),
                    pageBean.showTotal());
        }
        List<TgeSignificantQuality> list = tgeSignificantQualityMapper.queryList(queryFilter.getParams());
        if(CollectionUtils.isEmpty(list)){
            PageList<TgeSignificantQuality> pageList=new PageList<>();
            pageList.setRows(new ArrayList<>());
            pageList.setPage(1);
            pageList.setTotal(0);
            pageList.setPageSize(20);
            return pageList;
        }

        return new PageList<>(list);
    }

    @Override
    public CommonResult<String> importData(MultipartFile file, String month) {

       // Object orgId= ContextUtils.get().getGlobalVariable(ContextUtils.DIVIDE_ID_KEY);
        String orgId = ContextUtil.getCurrentGroupId();
        // 先检查传入的月份是否有数据,如果有则将原先的数据删除
        // 删除原有月份的数据
        Example example=new Example(TgeSignificantQuality.class);
        example.createCriteria()
                .andEqualTo("month",month);
        tgeSignificantQualityMapper.deleteByExample(example);

        long inTime = System.currentTimeMillis();
        // 标识表格是否有错误
        boolean importState = true;
        StringBuffer message = new StringBuffer("");
        List<TgeSignificantQuality> resourceBasicInfoList = new ArrayList<>();
        long messageLengthFront = 0;//错误信息长度
        int fail = 0, success = 0;
        long v1Time = System.currentTimeMillis();
        try {
            if (orgId==null) {
                message.append("无法获取当前登录组织；");
            }
            if (file.isEmpty()) {
                message.append("导入文件丢失，请重新选择文件");
            } else {
                InputStream in = file.getInputStream();
                // 调用导入工具类ImportExcelUtil，把excel中的数据拿出来,将Excel的数据set进数据库
                List<List<Object>> tempResourceImportList = new ImportExcelUtil().getBankListByExcelFromTwo(in, file.getOriginalFilename());

                // 校验模板是否正确
                List<Object> rowDataHeader = tempResourceImportList.get(0);
                List<String> rowDataHeaderStr = new ArrayList<>();
                rowDataHeaderStr = rowDataHeader.stream().map(r -> String.valueOf(r).replace(" ", "").replace("*", "")).collect(Collectors.toList());
                if (!(rowDataHeaderStr.get(0).equals("区域") && rowDataHeaderStr.get(1).equals("城市公司/片区") && rowDataHeaderStr.get(2).equals("项目")
                        && rowDataHeaderStr.get(3).equals("地块") && rowDataHeaderStr.get(4).equals("秩序服务单元") && rowDataHeaderStr.get(5).equals("环境服务单元")
                        && rowDataHeaderStr.get(6).equals("工程服务单元"))) {
                    importState = false;
                }

               // String massifIdQD = (String) orgId, massifCode = "", massifNamePath = "";


                if (importState) {

                    for (int x = 2; x < tempResourceImportList.size(); x++) {
                        // 标识城区是否存在
                        boolean exists =true;
                        List<Object> rowData = tempResourceImportList.get(x);
                        if ("".equals(String.valueOf(rowData.get(0))) && "".equals(String.valueOf(rowData.get(1))) && "".equals(String.valueOf(rowData.get(2)))
                                && "".equals(String.valueOf(rowData.get(3))) && "".equals(String.valueOf(rowData.get(4)))) {
                            continue;
                        }
                        if (rowData.size() < 7) {
                            int length = rowData.size();
                            for (int s = 0; s < 7 - length; s++) {
                                rowData.add("");
                            }
                        }
                        TgeSignificantQuality resourceBasicInfo = new TgeSignificantQuality();

                        String typeCode = "", plantNameCode = "", resourceCode = "", massifName = String.valueOf(rowData.get(3));
                        if (org.springframework.util.StringUtils.isEmpty(rowData.get(0))) {
                            message.append("第").append(x + 1).append("行，区域名称不能为空；");
                        }
                        if (org.springframework.util.StringUtils.isEmpty(String.valueOf(rowData.get(2)))) {
                            message.append("第").append(x + 1).append("行，项目名称不能为空；");
                        }
                        if (org.springframework.util.StringUtils.isEmpty(String.valueOf(rowData.get(3)))) {
                            message.append("第").append(x + 1).append("行，地块名称不能为空；");
                        }
                        if (org.springframework.util.StringUtils.isEmpty(String.valueOf(rowData.get(4)))) {
                            message.append("第").append(x + 1).append("行，秩序服务单元名称不能为空；");
                        }
                        if (org.springframework.util.StringUtils.isEmpty(String.valueOf(rowData.get(5)))) {
                            message.append("第").append(x + 1).append("行，环境服务单元名称不能为空；");
                        }
                        if (org.springframework.util.StringUtils.isEmpty(String.valueOf(rowData.get(6)))) {
                            message.append("第").append(x + 1).append("行，工程服务单元名称不能为空；");
                        }
                        if (org.springframework.util.StringUtils.isEmpty(String.valueOf(rowData.get(1)))) {
                            Object o = rowData.get(3);
                            if(o!=null) {
                                QueryFilter build = QueryFilter.build();
                                build.addFilter("NAME_", o, QueryOP.EQUAL);

                                build.addFilter("GRADE_", "ORG_DiKuai", QueryOP.EQUAL);
                                PageList<com.hypersmart.usercenter.model.UcOrg> orgList = orgService.query(build);
//                                PageList<UcOrg> orgList = orgService.query(build);
                                if (orgList.getRows() != null) {
                                    List<com.hypersmart.usercenter.model.UcOrg> rows = orgList.getRows();
                                    String path = rows.get(0).getId();
                                    List<UcOrg> orgIdsByOrgs = ucOrgService.getAllParentByOrgId(path);
                                    // 该项目有城区，为填写
                                    exists = orgIdsByOrgs.stream().filter(e->"ORG_ChengQu".equals(e.getGrade())).count()>0;
                                }
                            }
                            if(exists) {
                                message.append("第").append(x + 1).append("行，片区名称不能为空；");
                            }
                        }
                        //检查execl中的数据是否在数据库中存在
                        QueryFilter build = QueryFilter.build();
                        build.addFilter("NAME_",rowData.get(3), QueryOP.EQUAL);
//                            build.addFilter("GRADE_","ORG_ChengQu", QueryOP.EQUAL);
                        build.addFilter("GRADE_","ORG_DiKuai", QueryOP.EQUAL);

                        PageList<UcOrg> orgList = ucOrgService.getOrgList(build);
                        String pathName = orgList.getRows().get(0).getPathName();
                        String[] split = StringUtils.split(pathName, "/");
                        if(exists) {
                            if (!split[1].equals(rowData.get(0))) {
                                message.append("第").append(x + 1).append("行，区域不存在；");
                            }
                            if (!split[2].equals(rowData.get(1))) {
                                message.append("第").append(x + 1).append("行，片区/城市公司不存在；");
                            }
                            if (!split[3].equals(rowData.get(2))) {
                                message.append("第").append(x + 1).append("行，项目不存在；");
                            }
                            if (!split[4].equals(rowData.get(3))) {
                                message.append("第").append(x + 1).append("行，地块不存在；");
                            }
                        }else{
                            if (!split[1].equals(rowData.get(0))) {
                                message.append("第").append(x + 1).append("行，区域不存在；");
                            }

                            if (!split[2].equals(rowData.get(2))) {
                                message.append("第").append(x + 1).append("行，项目不存在；");
                            }
                            if (!split[3].equals(rowData.get(3))) {
                                message.append("第").append(x + 1).append("行，地块不存在；");
                            }
                        }
                        if(rowData.get(3)!=null && StringUtils.isNotEmpty(exists?split[4]:split[3])){
                            QueryFilter queryFilter = QueryFilter.build();
                            queryFilter.addFilter("GRADE_", "ORG_DiKuai", QueryOP.EQUAL);
                            queryFilter.addFilter("NAME_",rowData.get(3), QueryOP.EQUAL);

                            PageList<UcOrg> list2 = ucOrgService.getOrgList(queryFilter);
                            if (list2.getRows() != null) {
                                List<UcOrg> rows = list2.getRows();
                                String path = rows.get(0).getPath();
                                String id = rows.get(0).getId();
                                List<UcOrg> allParentByOrgId = ucOrgService.getAllParentByOrgId(id);

                                for (UcOrg ucOrg : allParentByOrgId) {
                                    if("ORG_QuYu".equals(ucOrg.getGrade())){
                                        resourceBasicInfo.setRegionId(ucOrg.getId());
                                        resourceBasicInfo.setRegionName(ucOrg.getName());
                                    }
                                    else if("ORG_ChengQu".equals(ucOrg.getGrade())){
                                        resourceBasicInfo.setAreaId(ucOrg.getId());
                                        resourceBasicInfo.setAreaName(ucOrg.getName());
                                    }else if("ORG_XiangMu".equals(ucOrg.getGrade())){
                                        resourceBasicInfo.setProjectId(ucOrg.getId());
                                        resourceBasicInfo.setProjectName(ucOrg.getName());
                                    }else if("ORG_DiKuai".equals(ucOrg.getGrade())){
                                        resourceBasicInfo.setDivideId(ucOrg.getId());
                                        resourceBasicInfo.setDivideName(ucOrg.getName());
                                    }
                                }
                                resourceBasicInfo.setMonth(month);
                                resourceBasicInfo.setCreateBy(ContextUtil.getCurrentUserId());
                                resourceBasicInfo.setCreateTime(new Date());
                                resourceBasicInfo.setOperationOrgId(orgId);

                                resourceBasicInfo.setOrderServiceUnitScore(Integer.valueOf(rowData.get(4).toString()));
                                resourceBasicInfo.setEnvironmentalServiceUnitScore(Integer.valueOf(rowData.get(5).toString()));
                                resourceBasicInfo.setEngineeringServiceUnitScore(Integer.valueOf(rowData.get(6).toString()));
                                resourceBasicInfoList.add(resourceBasicInfo);
                            }
                        }




                        if (messageLengthFront < message.length()) {
                            fail++;
                            messageLengthFront = message.length();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            importState = false;
            message = new StringBuffer("文件导入失败，请修复错误数据，或查看模板是否正确");
        }
        long v2Time = System.currentTimeMillis();
        logger.debug("环境资源数据校验耗时：{}",v2Time - v1Time);
        StringBuffer startString = new StringBuffer("");
        if (!importState) {
            message.append("导入模板错误");
        } else {
            if (message.length() < 1) {
                // 执行批量新增
                if (resourceBasicInfoList != null && resourceBasicInfoList.size() > 0) {
                    this.tgeSignificantQualityMapper.insertList(resourceBasicInfoList);
                    importState = true;
                    startString.append("共" + resourceBasicInfoList.size() + "条数据，验证成功" + resourceBasicInfoList.size() + "条，成功导入" + resourceBasicInfoList.size() + "条");
                    message = startString.append(message);
                } else {
                    importState = false;
                    message = message.append("当前导入的模板文件无环境资源数据");
                }
            } else {
                importState = false;
                success = resourceBasicInfoList.size() - fail;
                startString.append("共" + resourceBasicInfoList.size() + "条数据，验证成功" + success + "条，验证失败" + fail + "条，失败原因如下；");
                message = startString.append(message);
            }
        }
        long endTime = System.currentTimeMillis();
        logger.debug("环境资源导入耗时：{}", endTime - inTime);
        return new CommonResult(importState, message.toString());
    }

    @Override
    public boolean checkExists(String month, Object orgId) {
        TgeSignificantQuality significantQuality = new TgeSignificantQuality();


        Example example = new Example(TgeSignificantQuality.class);
        example.createCriteria().andEqualTo("month",month);
        List<TgeSignificantQuality> significantQualities = tgeSignificantQualityMapper.selectByExample(example);
        return  CollectionUtils.isNotEmpty(significantQualities);
    }


    public List<UcOrg> getOrgIdsByOrgs(String id){
        return ucOrgService.getAllParentByOrgId(id);
    }

}