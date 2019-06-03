package com.hypersmart.usercenter.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.*;
import com.hypersmart.base.util.StringUtil;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.mdm.feign.UcOrgFeignService;
import com.hypersmart.uc.api.impl.util.ContextUtil;
import com.hypersmart.usercenter.mapper.SatisfactionMapper;
import com.hypersmart.usercenter.mapper.UcOrgMapper;
import com.hypersmart.usercenter.model.GridBasicInfo;
import com.hypersmart.usercenter.model.Satisfaction;
import com.hypersmart.usercenter.model.UcOrg;
import com.hypersmart.usercenter.service.GridBasicInfoService;
import com.hypersmart.usercenter.service.SatisfactionService;
import com.hypersmart.usercenter.service.UcOrgService;
import com.hypersmart.usercenter.util.ImportExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    GridBasicInfoService getGridBasicInfoService;
    @Autowired
    UcOrgService ucOrgService;
    @Autowired
    GridBasicInfoService gridBasicInfoService;
    //查询组织机构;
    @Autowired
    UcOrgFeignService ucOrgFeignService;
    @Resource
    SatisfactionMapper satisfactionMapper;
    @Override
    public PageList<Satisfaction> getListBySearch(QueryFilter queryFilter) {
        List<FieldSort> sortList = new ArrayList<>();
        FieldSort fieldSort = new FieldSort();
        fieldSort.setDirection(Direction.ASC);
        fieldSort.setProperty("order_");
        sortList.add(fieldSort);
        queryFilter.setSorter(sortList);
        Map<String, Object> params = queryFilter.getParams();

        if (params.get("quyu") == null || StringUtils.isEmpty(params.get("quyu").toString())) {
            return this.query(queryFilter);
        } else if (
                params.get("quyu") != null && !StringUtils.isEmpty(params.get("quyu").toString())
                        && params.get("xiangmu") != null && !StringUtils.isEmpty(params.get("xiangmu").toString())
                        && params.get("dikuai") != null && !StringUtils.isEmpty(params.get("dikuai").toString())
                        && params.get("wangge") != null && !StringUtils.isEmpty(params.get("wangge").toString())
                ) {
            QueryFilter queryFilter2 = QueryFilter.build();
            queryFilter2.addFilter("org_name", params.get("wangge"), QueryOP.EQUAL, FieldRelation.AND);
            if (params.get("effective_time") != null)
                queryFilter2.addFilter("effective_time", params.get("effective_time"), QueryOP.EQUAL, FieldRelation.AND);

            return this.query(queryFilter2);
        } else if (
                params.get("quyu") != null
                        && params.get("xiangmu") != null
                        && params.get("dikuai") != null
                        && params.get("wangge") == null
                ) {
            //dikuai
            String dikuai = params.get("dikuai").toString();
            UcOrg ucOrg = new UcOrg();
            ucOrg.setName(dikuai);
            ucOrg.setIsDele("1");
            ucOrg.setGrade("ORG_DiKuai");
            List<UcOrg> ucOrgs = ucOrgService.selectAll(ucOrg);
            List<String> orgNames = new ArrayList<>();
            if (ucOrgs.size() > 0 && ucOrgs.get(0) != null) {
                String stangId = ucOrgs.get(0).getId();
                GridBasicInfo gridBasicInfo = new GridBasicInfo();
                gridBasicInfo.setStagingId(stangId);

                List<GridBasicInfo> gridBasicInfos = gridBasicInfoService.selectAll(gridBasicInfo);
                //get all orgName
                if (gridBasicInfos != null && gridBasicInfos.size() > 0) {
                    for (GridBasicInfo u :
                            gridBasicInfos) {
                        orgNames.add(u.getGridCode());

                    }
                }
                orgNames.add(ucOrgs.get(0).getCode());
                QueryFilter queryFilter2 = QueryFilter.build();
                queryFilter2.addFilter("org_code", orgNames, QueryOP.IN, FieldRelation.AND);

                queryFilter2.addFilter("effective_time", params.get("effective_time"), QueryOP.EQUAL, FieldRelation.AND);

                queryFilter2.setSorter(sortList);
                return this.query(queryFilter2);
            }
        } else if ((params.get("quyu") != null)
                ) {
            String dikuai = "";
            UcOrg ucOrg = new UcOrg();
            ucOrg.setIsDele("1");
            if (params.get("xiangmu") != null) {
                dikuai = params.get("xiangmu").toString();
                ucOrg.setGrade("ORG_XiangMu");
            } else {
                //查询仅区域；
                dikuai = params.get("quyu").toString();
                ucOrg.setGrade("ORG_QuYu");
            }
            ucOrg.setName(dikuai);

            List<UcOrg> ucOrgs = ucOrgService.selectAll(ucOrg);

            List<String> orgNames = new ArrayList<>();
            if (ucOrgs.size() <= 0) {
                QueryFilter queryFilter2 = QueryFilter.build();
                queryFilter2.addFilter("org_code", orgNames, QueryOP.IN, FieldRelation.AND);
                if (params.get("effective_time") != null)
                    queryFilter2.addFilter("effective_time", params.get("effective_time"), QueryOP.EQUAL, FieldRelation.AND);
                queryFilter2.setSorter(sortList);
                return this.query(queryFilter2);
            } else {
                String path = ucOrgs.get(0).getPath();
                QueryFilter queryFilter1 = QueryFilter.build();
                queryFilter1.addFilter("PATH_", path, QueryOP.RIGHT_LIKE, FieldRelation.AND);
                queryFilter1.addFilter("IS_DELE_", "0", QueryOP.EQUAL, FieldRelation.AND);
                PageList<UcOrg> query = ucOrgService.query(queryFilter1);
                //get all orgName
                if (query != null && query.getRows() != null) {
                    List<UcOrg> rows = query.getRows();
                    for (UcOrg u :
                            rows) {
                        orgNames.add(u.getCode());
                        if (u.getGrade().equals("ORG_DiKuai")) {
                            String stangId = u.getId();
                            GridBasicInfo gridBasicInfo = new GridBasicInfo();
                            gridBasicInfo.setStagingId(stangId);

                            List<GridBasicInfo> gridBasicInfos = gridBasicInfoService.selectAll(gridBasicInfo);
                            if (gridBasicInfos.size() > 0) {

                                for (GridBasicInfo gb :
                                        gridBasicInfos) {
                                    orgNames.add(gb.getGridCode());

                                }
                            }
                        }
                    }

                }
            }


            if (ucOrgs.size() > 0 && ucOrgs.get(0) != null) {

                orgNames.add(ucOrgs.get(0).getCode());
                QueryFilter queryFilter2 = QueryFilter.build();
                queryFilter2.addFilter("org_code", orgNames, QueryOP.IN, FieldRelation.AND);
                if (params.get("effective_time") != null)
                    queryFilter2.addFilter("effective_time", params.get("effective_time"), QueryOP.EQUAL, FieldRelation.AND);
                queryFilter2.setSorter(sortList);
                return this.query(queryFilter2);
            }
        }
        return null;
    }



    @Override
    public CommonResult<String> importData(MultipartFile file, String date) {
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
            List<List<Object>> tempResourceImportList = new ImportExcelUtil().getBankListByExcel4Statis(in, file.getOriginalFilename());

            Integer hasRealCount = getRealCount(tempResourceImportList);

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

            //基础校验 看看有没有相等的序列；
            //1取所有序列
            String[] id = new String[tempResourceImportList.size() - 2];
            for (int i = 2; i < tempResourceImportList.size(); i++) {
                List<Object> objects = tempResourceImportList.get(i);
                if (objects.get(0) == null || objects.get(0).toString().length() <= 0) {
                    throw new Exception("第" + (i + 1) + "行序列为空");
                } else {
                    //对小数点问题进行处理；
                    if (objects.get(0).toString().length() >= 4) {
                        fixId(tempResourceImportList, id, i, objects);
                    }
                    if (objects.get(0).toString() != null && objects.get(0).toString().split("\\.").length > 1) {
                        //替换1.0这种数据为1
                        String[] split = objects.get(0).toString().split("\\.");
                        if (split.length > 1 && split[split.length - 1].equals("0")) {
                            String objects1 = objects.get(0).toString();
                            objects.remove(0);
                            objects.add(0, objects1.substring(0, objects1.lastIndexOf(".")));
                            tempResourceImportList.remove(i);
                            tempResourceImportList.add(i, objects);
                        }

                    }

                }
                if (id[i - 2] == null) {
                    id[i - 2] = objects.get(0).toString();
                }
            }

            this.checkHasEqualId(id);

            //处理导入的数据；
            if (importState) {

                doData(message, satisfactions, tempResourceImportList, date);

                satisfactionMapper.deleteByDate(date);

                insertBatch(satisfactions);

            }

        } catch (Exception e) {
            return new CommonResult(false, e.getMessage());
        }
        return new CommonResult(true, "成功导入");
    }

    private void fixId(List<List<Object>> tempResourceImportList, String[] id, int i, List<Object> objects) {
        String substring = objects.get(0).toString().substring(objects.get(0).toString().length() - 3);
        String[] split = objects.get(0).toString().split("\\.");
        if (substring.equals(".10") && split.length == 2) {
            List<Object> o2 = tempResourceImportList.get(i - 1);

            if (o2 != null && o2.get(0) != null && o2.get(0).toString().length() > 0) {
                String[] split1 = o2.get(0).toString().split("\\.");
                if (split1.length == 1 || (split1.length > 1 && (split1[1].equals("00") || split1[1].equals("0")))) {
                    //替换值
                    id[i - 2] = objects.get(0).toString().substring(0, objects.get(0).toString().lastIndexOf("0"));
                    List<Object> objects1 = objects;
                    objects1.remove(0);
                    objects1.add(0, id[i - 2]);
                    tempResourceImportList.remove(i);
                    tempResourceImportList.add(i, objects1);

                }
            }
        }
    }

    /**
     * 校验是否含有重复序列;
     *
     * @param b
     */
    public void checkHasEqualId(String b[]) throws Exception {

        String temp = "";
        for (int i = 0; i < b.length - 1; i++) {
            temp = b[i];
            for (int j = i + 1; j < b.length; j++) {
                if (temp.equals(b[j])) {
                    System.out.println("第" + (i + 3) + "行序列跟第" + (j + 3) + "个重复，值是：" + temp);
                    throw new Exception("第" + (i + 3) + "行序列跟第" + (j + 3) + "个重复，值是：" + temp);
                }
            }
        }
    }

    private boolean isHasError(String[] headArr, List<List<Object>> tempResourceImportList) {
        List<Object> rowDataHeader = tempResourceImportList.get(1);
        List<String> rowDataHeaderStr = new ArrayList<>();
        rowDataHeaderStr = rowDataHeader.stream().map(r -> String.valueOf(r).replace(" ", "").replace("*", "")).collect(Collectors.toList());

        for (int i = 0; i < rowDataHeaderStr.size(); i++) {
            if (rowDataHeaderStr.get(i).isEmpty()) {
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
        return hasError;
    }

    @Override
    public CommonResult CheckHasExist(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Satisfaction satisfaction = new Satisfaction();
        QueryFilter queryFilter = QueryFilter.build();

        try {
            Date day = formatter.parse(date);
            satisfaction.setEffectiveTime(day);
            queryFilter.addFilter("effective_time", date, QueryOP.EQUAL, FieldRelation.AND);
            List<Satisfaction> satisfactions = selectAll(satisfaction);
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

    @Override
    public List<Satisfaction> getSatisfactionListByParam(JSONObject json) {
        return satisfactionMapper.getSatisfactionListByParam(JSONObject.toJavaObject(json, Map.class));
    }

    private void doData(StringBuffer message, List<Satisfaction> satisfactions, List<List<Object>> tempResourceImportList, String date) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        List<Object> rowDataLevel3 = null;
        for (int i = 2; i < tempResourceImportList.size(); i++) {
            List<Object> rowData = tempResourceImportList.get(i);

            boolean checkHasSpecialStr = checkHasSpecialStr(rowData.get(0).toString());
            if (checkHasSpecialStr) {
                throw new Exception("第" + (i + 1) + "行: 序列错误,包含除.以外特殊字符。");
            }

            String[] split = rowData.get(0).toString().split("\\.");
            int length = split.length;
            if (length < 1) {
                throw new Exception("第" + (i + 1) + "行 序列错误。");
            }
            int type = 0;
            if (rowData.get(1).toString() != null) {
                if (rowData.get(1).toString().equals("区域") && length == 1) {
                    type = 1;
                }
                if (rowData.get(1).toString().equals("项目") && length == 2) {
                    type = 2;
                }
                if (rowData.get(1).toString().equals("地块") && length == 3) {
                    type = 3;
                }
                if (rowData.get(1).toString().equals("网格") && length == 4) {
                    type = 4;
                }
                if (type == 0) {
                    throw new Exception("第" + (i + 1) + "行 分类内容错误或分类和序列层级不匹配！");
                }
            } else {
                throw new Exception("第" + (i + 1) + "行 网格没有对应得分类");
            }
            //获取上一级别得组织对象；
            List<Object> lastOrgRow = null;
            if (i > 2) {
                for (int j = i; j > 2; j--) {
                    List<Object> objects = tempResourceImportList.get(j);
                    if (objects.get(0).toString().split("\\.").length + 1 == rowData.get(0).toString().split("\\.").length) {
                        lastOrgRow = objects;
                    }
                }
            }
            if (type == 3) {
                rowDataLevel3 = rowData;
            }
            if (type != 3 && type != 4) {
                rowDataLevel3 = null;
            }
            if (type == 4 && rowDataLevel3 == null) {
                throw new Exception("第" + (i + 1) + "行 网格没有对应得上级组织");
            }
            String orgCode = checkData(lastOrgRow, rowDataLevel3, i + 1, message, rowData, type);

            //新增数据;
            Satisfaction satisfaction = new Satisfaction();
            //satisfaction.setCreateBy();
            satisfaction.setCreateTime(new Date());

            satisfaction.setOrder(rowData.get(0).toString());
            satisfaction.setType(rowData.get(1).toString());
            satisfaction.setOrgName(rowData.get(2).toString());
            if (rowData.get(3) != null && StringUtil.isNotEmpty(rowData.get(3).toString())) {
                satisfaction.setOverallSatisfaction(new BigDecimal(rowData.get(3).toString()));
            }
            if (rowData.get(4) != null && StringUtil.isNotEmpty(rowData.get(4).toString())) {
                satisfaction.setStorming(new BigDecimal(rowData.get(4).toString()));
            }
            if (rowData.get(5) != null && StringUtil.isNotEmpty(rowData.get(5).toString())) {
                satisfaction.setStationaryPhase(new BigDecimal(rowData.get(5).toString()));
            }
            if (rowData.get(6) != null && StringUtil.isNotEmpty(rowData.get(6).toString())) {
                satisfaction.setOldProprietor(new BigDecimal(rowData.get(6).toString()));
            }
            satisfaction.setEffectiveTime(formatter.parse(date));
            satisfaction.setOrgCode(orgCode);
            if (type < 4) {
                if (rowData.get(7) != null && StringUtil.isNotEmpty(rowData.get(7).toString())) {
                    satisfaction.setOrderServiceUnit(new BigDecimal(rowData.get(7).toString()));
                }
                if (rowData.get(8) != null && StringUtil.isNotEmpty(rowData.get(8).toString())) {
                    satisfaction.setEsuCleaning(new BigDecimal(rowData.get(8).toString()));
                }

                if (rowData.get(9) != null && StringUtil.isNotEmpty(rowData.get(9).toString())) {
                    satisfaction.setEsuGreen(new BigDecimal(rowData.get(9).toString()));
                }

                if (rowData.get(10) != null && StringUtil.isNotEmpty(rowData.get(10).toString())) {
                    satisfaction.setEngineeringServiceUnit(new BigDecimal(rowData.get(10).toString()));
                }

            }
            satisfactions.add(satisfaction);

        }
    }

    /**
     * 校验是否重复
     *
     * @return
     */
    private String checkRepeatId() {
        return null;
    }

    private String checkData(List<Object> lastLevelRow, List<Object> parentRow, int rowNun, StringBuffer message, List<Object> rowData, int type) throws Exception {
        //todo
        //校验与前面行得数据得归属关系;


        //校验是否有组织不匹配；根据层级和姓名，查询是否有组织匹配
        UcOrg ucOrg = new UcOrg();
        ucOrg.setName(rowData.get(2).toString());
        String orgCode = null;
        boolean hasOrg = false;

        if (type == 4) {
            //网格组织校验
            //todo
            ucOrg.setName(parentRow.get(2).toString());
            //ucOrg.setLevel(4);
            ucOrg.setGrade("ORG_DiKuai");
            ucOrg.setIsDele("1");
            List<UcOrg> ucOrgs = ucOrgService.selectAll(ucOrg);
            if (ucOrgs.size() <= 0) {
                throw new Exception("第" + rowNun + "行：" + rowData.get(2).toString() + "该网格对应得上级组织错误或缺失，请检查格式");
            }
            String parentId = ucOrgs.get(0).getId();
            GridBasicInfo gridBasicInfo = new GridBasicInfo();
            gridBasicInfo.setGridName(rowData.get(2).toString());
            gridBasicInfo.setStagingId(parentId);
            gridBasicInfo.setIsDeleted(0);
            List<GridBasicInfo> gridBasicInfos = gridBasicInfoService.selectAll(gridBasicInfo);

            if (gridBasicInfos.size() <= 0) {
                throw new Exception("第" + rowNun + "行：" + "没有该网格" + rowData.get(2).toString());//8

            }
            orgCode = gridBasicInfos.get(0).getGridCode();
            hasOrg = true;
        } else {
            if (type == 1) {
                // ucOrg.setLevel(1);
                ucOrg.setGrade("ORG_QuYu");
            }
            if (type == 2) {
                //ucOrg.setLevel(3);
                ucOrg.setGrade("ORG_XiangMu");
            }
            if (type == 3) {
                //ucOrg.setLevel(4);
                ucOrg.setGrade("ORG_DiKuai");
            }
            ucOrg.setIsDele("1");
            List<UcOrg> ucOrgs = ucOrgService.selectAll(ucOrg);
            if (ucOrgs.size() <= 0) {
                throw new Exception("第" + rowNun + "行：" + rowData.get(2).toString() + "组织名称不存在或组织名与对应得分类/层级不符");//8
            }
            //取最新版本
            if (ucOrgs.size() > 1) {
                ucOrgs.sort((UcOrg o1, UcOrg o2) -> o1.getVersion() - o2.getVersion());

            }
            orgCode = ucOrgs.get(ucOrgs.size() - 1).getCode();
            hasOrg = true;
        }


        if (!hasOrg) {
            throw new Exception("第" + rowNun + "行：" + rowData.get(2).toString() + "该组织名称与系统数据不匹配，请修改后重试。");

        }

        checkIsNullAndNum(rowNun, message, rowData, type);
        return orgCode;
    }

    private void checkIsNullAndNum(int rowNun, StringBuffer message, List<Object> rowData, int type) throws Exception {
        switch (type) {
            case 0:
                throw new Exception("请检查是否有空行");

            case 1:
            case 2:
            case 3:

                //  6   第XX行 { 必填项的表头名称，如分类 }缺少
                //  校验是否有不合法空值；
//                for (Object v :
//                        rowData) {
//                    if (v.toString() == null || v.toString().length() <= 0) {
//                        message.append("请检查数据是否为空");
//                        throw new Exception("第" + rowNun + "行：" + "请检查数据是否缺少");
//                    }
//
//                }
                for (int i = 3; i < rowData.size(); i++) {
                    if (rowData.get(i) != null && rowData.get(i).toString() != null && rowData.get(i).toString().trim().length() > 0 && !isBigDecimal(rowData.get(i).toString())) {
                        throw new Exception("第" + rowNun + "行：" + "数值格式错误（比如不是数字）");
                    }
                }
                break;
            case 4:

                for (int j = 0; j < rowData.size(); j++) {
//                    if (j < 7 && (rowData.get(j).toString() == null || rowData.get(j).toString().length() <= 0)) {
//                        throw new Exception("第" + rowNun + "行：" + "请检查数据是否缺少");
//                    }
                    if (rowData.get(j) != null && rowData.get(j).toString() != null && rowData.get(j).toString().trim().length() > 0 && j > 2 && j < 7 && !isBigDecimal(rowData.get(j).toString())) {
                        throw new Exception("第" + rowNun + "行：" + "数值格式错误（比如不是数字）");
                    }
                }
                break;

            default:


        }
    }

    /**
     * 获取查询数量
     *
     * @param tempResourceImportList
     * @return
     */
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
    public List<Satisfaction> getSatisfactionDetail(String orgCode, String time) {
        QueryFilter query = QueryFilter.build();
        query.addFilter("CODE_", orgCode, QueryOP.EQUAL);
        List<UcOrg> list = ucOrgService.query(query).getRows();
        List<Satisfaction> satisfactions = new ArrayList<>();
        if (list != null && list.size() > 0) {
            UcOrg ucOrg = list.get(0);
            if (ucOrg.getGrade().equals("ORG_DiKuai")) {
                List<GridBasicInfo> gridBasicInfoList = gridBasicInfoService.getGridsBySmcloudmassifId(ucOrg.getId());
                satisfactions = satisfactionMapper.getGridSatisfaction(gridBasicInfoList, time);
            } else if (ucOrg.getGrade().equals("ORG_QuYu")) {
                QueryFilter queryFilter = QueryFilter.build();
                queryFilter.addFilter("PARENT_ID_", list.get(0).getId(), QueryOP.EQUAL, FieldRelation.AND);
                queryFilter.addFilter("IS_DELE_", "0", QueryOP.EQUAL, FieldRelation.AND);
                List<UcOrg> chengQuList = ucOrgService.query(queryFilter).getRows();
                if (chengQuList != null && chengQuList.size() > 0) {
                    List<String> chengQuIdList = new ArrayList<>();
                    for (UcOrg chengQu : chengQuList) {
                        chengQuIdList.add(chengQu.getId());
                    }
                    QueryFilter filter = QueryFilter.build();
                    filter.addFilter("IS_DELE_", "0", QueryOP.EQUAL, FieldRelation.AND);
                    filter.addFilter("PARENT_ID_", chengQuIdList, QueryOP.IN, FieldRelation.AND);
                    List<UcOrg> projectList = ucOrgService.query(filter).getRows();
                    if (projectList != null && projectList.size() > 0) {
                        satisfactions = satisfactionMapper.getSatisfactionDetail(projectList, time);
                    }
                }
            } else {
                QueryFilter queryFilter = QueryFilter.build();
                queryFilter.addFilter("PARENT_ID_", list.get(0).getId(), QueryOP.EQUAL, FieldRelation.AND);
                queryFilter.addFilter("IS_DELE_", "0", QueryOP.EQUAL, FieldRelation.AND);
                List<UcOrg> ucOrgList = ucOrgService.query(queryFilter).getRows();
                if (ucOrgList != null && ucOrgList.size() > 0) {
                    satisfactions = satisfactionMapper.getSatisfactionDetail(ucOrgList, time);
                }
            }
        }
        return satisfactions;
    }

    @Override
    public Satisfaction getSingleSatisfaction(List<String> orgIds, String time) {
        List<UcOrg> ucOrg = ucOrgService.getByIds(orgIds.toArray(new String[orgIds.size()]));
        List<String> orgCodes = ucOrg.stream().map(UcOrg::getCode).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(ucOrg)) {
            List<Satisfaction> list = satisfactionMapper.getSatisfactionAvg(orgCodes, time);
            if (!CollectionUtils.isEmpty(list)) {
                return list.get(0);
            }
        }
        return null;
    }

    @Override
    public List<Satisfaction> getAllSatisfaction(String time) {
        String userId = ContextUtil.getCurrentUser().getUserId();
        List<UcOrg> ucOrgList = ucOrgService.getUserOrgListMerge(userId);
        List<UcOrg> quYuList = new ArrayList<>();
        for (UcOrg ucOrg : ucOrgList) {
            if (ucOrg.getLevel() == 1) {
                quYuList.add(ucOrg);
            }
        }
        return satisfactionMapper.getSatisfactionDetail(quYuList, time);
    }

    public static void main(String[] args) throws ParseException {
        String date = "2019-05-00";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-00");
        Date parse = formatter.parse(date);
        String ss = formatter.format(parse);
        System.out.println(ss);
        List<UcOrg> ucOrgs = new ArrayList<>();
        UcOrg u1 = new UcOrg();
        UcOrg u2 = new UcOrg();
        UcOrg u3 = new UcOrg();
        u1.setVersion(1);
        u2.setVersion(2);
        u3.setVersion(3);
        ucOrgs.add(u1);
        ucOrgs.add(u2);
        ucOrgs.add(u3);
        //取最新版本
        if (ucOrgs.size() > 1) {
            ucOrgs.sort((UcOrg o1, UcOrg o2) -> o1.getVersion() - o2.getVersion());

        }
        System.out.println(ucOrgs.toString());
        System.out.println(ucOrgs.get(0));
    }

    private boolean checkHasSpecialStr(String str) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    private boolean isBigDecimal(String integer) {
        try {
            BigDecimal bd = new BigDecimal(integer);
            //bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}